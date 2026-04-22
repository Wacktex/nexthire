package com.nexthire.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LLMService {

    @Value("${openrouter.api.key}")
    private String apiKey;

    public String getRecommendations(String cvText, String githubUrl, String targetRole) {

        if (apiKey == null || apiKey.trim().isEmpty()) {
            return """
                   AI recommendations are not available.

                   Please set OPENROUTER_API_KEY in environment variables.
                   """;
        }

        String prompt = """
                You are a senior-level %s professional with industry experience.

                You are evaluating a candidate's readiness for the role.

                Candidate CV:
                %s

                GitHub Profile:
                %s

                Your task:
                1) Identify missing or weak skills
                2) Suggest specific projects to build
                3) Recommend useful certifications or courses
                4) Suggest improvements to the CV
                5) Prioritize recommendations by importance
                6) Keep the response realistic and industry-focused

                Return the result as bullet points.
                Limit the response to 6–8 recommendations.
                """.formatted(targetRole, cvText, githubUrl);

        try {

            String apiUrl = "https://openrouter.ai/api/v1/chat/completions";

            URL url = URI.create(apiUrl).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            connection.setRequestProperty(
                    "Authorization",
                    "Bearer " + apiKey
            );

            connection.setRequestProperty(
                    "Content-Type",
                    "application/json"
            );

            connection.setRequestProperty(
                    "HTTP-Referer",
                    "https://nexthire.onrender.com"
            );

            connection.setRequestProperty(
                    "X-Title",
                    "NEXTHIRE"
            );

            connection.setDoOutput(true);

            connection.setConnectTimeout(15000);
            connection.setReadTimeout(30000);

            String requestBody = """
                    {
                      "model": "deepseek/deepseek-chat",
                      "messages": [
                        {
                          "role": "user",
                          "content": "%s"
                        }
                      ],
                      "temperature": 0.7,
                      "max_tokens": 500
                    }
                    """.formatted(escapeJson(prompt));

            try (OutputStream outputStream =
                         connection.getOutputStream()) {

                outputStream.write(
                        requestBody.getBytes(
                                StandardCharsets.UTF_8
                        )
                );
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {

                try (Scanner scanner =
                             new Scanner(
                                     connection.getInputStream(),
                                     StandardCharsets.UTF_8
                             )) {

                    StringBuilder response =
                            new StringBuilder();

                    while (scanner.hasNextLine()) {
                        response.append(
                                scanner.nextLine()
                        );
                    }

                    return extractTextFromResponse(
                            response.toString()
                    );
                }

            } else {

                try (Scanner scanner =
                             new Scanner(
                                     connection.getErrorStream(),
                                     StandardCharsets.UTF_8
                             )) {

                    StringBuilder error =
                            new StringBuilder();

                    while (scanner.hasNextLine()) {
                        error.append(
                                scanner.nextLine()
                        );
                    }

                    System.out.println(
                            "OpenRouter error: " +
                            error.toString()
                    );

                    return "AI service returned error (HTTP "
                            + responseCode + ")";
                }
            }

        } catch (java.io.IOException e) {

            System.out.println(
                    "Error calling OpenRouter: "
                            + e.getMessage()
            );

            return "AI recommendations unavailable: "
                    + e.getMessage();
        }
    }

    private String escapeJson(String text) {

        text = text.replace("\\", "\\\\");
        text = text.replace("\"", "\\\"");
        text = text.replace("\n", "\\n");
        text = text.replace("\r", "\\r");
        text = text.replace("\t", "\\t");

        return text;
    }

    private String extractTextFromResponse(String json) {

        try {

            int contentIndex =
                    json.indexOf("\"content\":\"");

            if (contentIndex != -1) {

                int start =
                        contentIndex + 11;

                int end = start;

                while (end < json.length()) {

                    char c =
                            json.charAt(end);

                    if (c == '"' &&
                        json.charAt(end - 1) != '\\') {
                        break;
                    }

                    end++;
                }

                String result =
                        json.substring(start, end);

                result = result.replace(
                        "\\n",
                        "\n"
                );

                result = result.replace(
                        "\\\"",
                        "\""
                );

                result = result.replace(
                        "\\\\",
                        "\\"
                );

                return result;
            }

        } catch (Exception e) {

            System.out.println(
                    "Parse error: "
                            + e.getMessage()
            );
        }

        return "Could not parse AI response.";
    }
}