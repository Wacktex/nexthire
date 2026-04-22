package com.nexthire.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LLMService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String getRecommendations(String cvText, String githubUrl, String targetRole) {

        // Guard: if user has not set the API key yet, return a helpful message
        if (apiKey == null || apiKey.trim().isEmpty() || apiKey.equals("YOUR_GEMINI_API_KEY_HERE")) {
            return """
                   AI recommendations are not available.

                   To enable them:
                   1. Go to https://aistudio.google.com/ and get a free API key
                   2. Open src/main/resources/application.properties
                   3. Replace YOUR_GEMINI_API_KEY_HERE with your actual key
                   4. Restart the application""";
        }

String prompt = """
                You are a senior-level {role} professional with industry experience.

                You are evaluating a candidate's readiness for the role.

                Target Role:
                {role}

                User Skills: {skills}
                User Projects: {projects}
                Certifications: {certifications}
                GitHub Information:
                - Repository Count: {repository_count}
                - Contribution Count: {contribution_count}
                Required Skills for Role: {required_skills}

                Your task:
                1) Identify missing or weak skills
                2) Suggest specific projects to build
                3) Recommend useful certifications or courses
                4) Suggest improvements to the CV
                5) Prioritize recommendations by importance
                6) Keep the response realistic and industry-focused

                Return the result as bullet points.
                Limit the response to 6–8 recommendations.""";

        try {
            String apiUrl = "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash-latest:generateContent?key=" + apiKey;

            URL url = URI.create(apiUrl).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(30000);

            // Build the request body manually without extra libraries
            String requestBody = """
                    {
                      "contents": [
                        {
                          "parts": [
                            {
                              "text": """ + escapeJson(prompt) + """
                            }
                          ]
                        }
                      ]
                    }""";

            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(requestBody.getBytes("UTF-8"));
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                try (Scanner scanner = new Scanner(connection.getInputStream(), "UTF-8")) {
                    StringBuilder response = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        response.append(scanner.nextLine());
                    }
                    return extractTextFromResponse(response.toString());
                }

            } else {
                try (Scanner scanner = new Scanner(connection.getErrorStream(), "UTF-8")) {
                    StringBuilder errorResponse = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        errorResponse.append(scanner.nextLine());
                    }
                    System.out.println("Gemini API error response: " + errorResponse.toString());
                    return "Gemini API returned an error (HTTP " + responseCode + "). Please check your API key in application.properties.";
                }
            }

        } catch (java.io.IOException e) {
            System.out.println("Error calling Gemini API: " + e.getMessage());
            return "AI recommendations are currently unavailable. Error: " + e.getMessage();
        }
    }

    private String escapeJson(String text) {
        text = text.replace("\\", "\\\\");
        text = text.replace("\"", "\\\"");
        text = text.replace("\n", "\\n");
        text = text.replace("\r", "\\r");
        text = text.replace("\t", "\\t");
        return "\"" + text + "\"";
    }

    private String extractTextFromResponse(String jsonResponse) {
        try {
            int textIndex = jsonResponse.indexOf("\"text\":");
            if (textIndex != -1) {
                int startQuote = jsonResponse.indexOf("\"", textIndex + 7);
                if (startQuote != -1) {
                    int endQuote = startQuote + 1;
                    while (endQuote < jsonResponse.length()) {
                        char c = jsonResponse.charAt(endQuote);
                        if (c == '"' && jsonResponse.charAt(endQuote - 1) != '\\') {
                            break;
                        }
                        endQuote++;
                    }
                    String extracted = jsonResponse.substring(startQuote + 1, endQuote);
                    extracted = extracted.replace("\\n", "\n");
                    extracted = extracted.replace("\\\"", "\"");
                    extracted = extracted.replace("\\\\", "\\");
                    return extracted;
                }
            }
        } catch (Exception e) {
            System.out.println("Error extracting text from Gemini response: " + e.getMessage());
        }
        return "Could not parse AI response. Please check the Gemini API configuration.";
    }
}
