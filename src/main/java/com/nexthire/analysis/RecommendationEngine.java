package com.nexthire.analysis;

import com.nexthire.model.Finding;
import com.nexthire.model.Recommendation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecommendationEngine {

    public List<Recommendation> generateRecommendations(List<Finding> findings) {
        List<Recommendation> recommendations = new ArrayList<>();

        for (Finding finding : findings) {
            Recommendation recommendation = new Recommendation();

            String type = finding.getType();

            if (type.equals("Missing Skill")) {
                // Extract skill name from description
                String description = finding.getDescription();
                String skillName = extractSkillName(description);

                recommendation.setType("Learn Skill");
                recommendation.setDescription("Learn " + skillName + ": Take an online course or build a project using " + skillName + ". Platforms like Udemy, Coursera, or YouTube are great for this.");
                recommendation.setPriority("High");

            } else if (type.equals("Low GitHub Activity")) {
                recommendation.setType("Improve GitHub");
                recommendation.setDescription("Create more GitHub repositories. Start with small projects like a to-do app, calculator, or a simple API. Aim to have at least 5 repositories.");
                recommendation.setPriority("High");

            } else if (type.equals("Low Contributions")) {
                recommendation.setType("Increase GitHub Contributions");
                recommendation.setDescription("Commit your code regularly to GitHub. Even small updates like fixing a bug or adding a feature count as contributions. Try to commit at least once a week.");
                recommendation.setPriority("Medium");

            } else if (type.equals("Missing Section")) {
                recommendation.setType("Improve CV");
                recommendation.setDescription("Update your CV: " + finding.getDescription() + " A well-structured CV increases your chances of getting shortlisted.");
                recommendation.setPriority("High");

            } else if (type.equals("Weak CV")) {
                recommendation.setType("Expand CV");
                recommendation.setDescription("Add more detail to your CV. Include work experience, projects, skills, certifications, and education. Each section should be clearly labeled.");
                recommendation.setPriority("High");

            } else if (type.equals("Missing Contact Info")) {
                recommendation.setType("Add Contact Information");
                recommendation.setDescription("Add your email address and phone number to your CV. Also consider adding your LinkedIn profile URL.");
                recommendation.setPriority("High");

            } else if (type.equals("Missing Education")) {
                recommendation.setType("Add Education Details");
                recommendation.setDescription("Add your educational background to the CV including degree name, university name, and year of graduation.");
                recommendation.setPriority("Medium");

            } else if (type.equals("Invalid GitHub URL")) {
                recommendation.setType("Fix GitHub URL");
                recommendation.setDescription("Make sure your GitHub URL is in the format: https://github.com/yourusername. Create a GitHub account if you do not have one.");
                recommendation.setPriority("High");

            } else if (type.equals("Good GitHub Activity")) {
                recommendation.setType("Maintain GitHub Activity");
                recommendation.setDescription("Great job maintaining your GitHub! Keep adding projects and contributing to open source to stand out even more.");
                recommendation.setPriority("Low");

            } else {
                recommendation.setType("General Improvement");
                recommendation.setDescription("Review this finding and work on improving: " + finding.getDescription());
                recommendation.setPriority("Medium");
            }

            recommendations.add(recommendation);
        }

        return recommendations;
    }

    private String extractSkillName(String description) {
        // Simple extraction - finding text between "Missing the skill: " and "."
        try {
            int startIndex = description.indexOf("skill: ") + 7;
            int endIndex = description.indexOf(".", startIndex);
            if (startIndex > 7 && endIndex > startIndex) {
                return description.substring(startIndex, endIndex);
            }
        } catch (Exception e) {
            // ignore
        }
        return "this skill";
    }
}
