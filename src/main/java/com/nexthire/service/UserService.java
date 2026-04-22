package com.nexthire.service;

import com.nexthire.model.*;
import com.nexthire.repository.RoleRepository;
import com.nexthire.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public UserProfile buildUserProfile(String cvText, String githubUrl, String targetRoleName, int repoCount, int contributionCount) {

        // Build CV Data
        CVData cvData = new CVData();
        cvData.setRawText(cvText);

        // Extract skills from CV text using simple keyword check
        List<String> extractedSkills = extractSkillsFromText(cvText);
        cvData.setSkills(extractedSkills);

        // Build GitHub Profile
        GitHubProfile gitHubProfile = new GitHubProfile();
        gitHubProfile.setProfileUrl(githubUrl);
        gitHubProfile.setRepositoryCount(repoCount);
        gitHubProfile.setContributionCount(contributionCount);

        // Get Target Role from repository
        TargetRole targetRole = roleRepository.getRoleByName(targetRoleName);

        // Build User Profile
        UserProfile userProfile = new UserProfile();
        userProfile.setCvData(cvData);
        userProfile.setGitHubProfile(gitHubProfile);
        userProfile.setTargetRole(targetRole);

        return userProfile;
    }

    private List<String> extractSkillsFromText(String cvText) {
        List<String> skills = new ArrayList<>();
        String lowerText = cvText.toLowerCase();

        // Simple keyword detection for common skills
        String[] commonSkills = {
                "Java", "Python", "JavaScript", "HTML", "CSS", "SQL", "MySQL",
                "Spring Boot", "React", "Angular", "Node.js", "Docker", "Kubernetes",
                "Git", "GitHub", "Linux", "REST API", "MongoDB", "PostgreSQL",
                "Machine Learning", "Data Analysis", "CI/CD", "AWS", "Azure",
                "C++", "C#", "PHP", "TypeScript", "Pandas", "NumPy"
        };

        for (String skill : commonSkills) {
            if (lowerText.contains(skill.toLowerCase())) {
                skills.add(skill);
            }
        }

        return skills;
    }

    public void saveResultToDatabase(String cvText, String githubUrl, String targetRole, List<Recommendation> recommendations) {
        StringBuilder recommendationsText = new StringBuilder();
        for (int i = 0; i < recommendations.size(); i++) {
            Recommendation rec = recommendations.get(i);
            recommendationsText.append((i + 1) + ". " + rec.getDescription() + "\n");
        }
        userRepository.saveResult(cvText, githubUrl, targetRole, recommendationsText.toString());
    }

    public List<String> getAllRoleNames() {
        return roleRepository.getAllRoleNames();
    }
}
