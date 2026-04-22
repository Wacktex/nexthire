package com.nexthire.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nexthire.analysis.CVAnalyzer;
import com.nexthire.analysis.GitHubAnalyzer;
import com.nexthire.analysis.RecommendationEngine;
import com.nexthire.analysis.SkillGapAnalyzer;
import com.nexthire.model.Finding;
import com.nexthire.model.Recommendation;
import com.nexthire.model.UserProfile;
import com.nexthire.service.LLMService;
import com.nexthire.service.UserService;

@Controller
public class WebController {

    @Autowired
    private CVAnalyzer cvAnalyzer;

    @Autowired
    private GitHubAnalyzer gitHubAnalyzer;

    @Autowired
    private SkillGapAnalyzer skillGapAnalyzer;

    @Autowired
    private RecommendationEngine recommendationEngine;

    @Autowired
    private LLMService llmService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showIndexPage(Model model) {
        List<String> roleNames = userService.getAllRoleNames();
        model.addAttribute("roles", roleNames);
        return "index";
    }

    @PostMapping("/analyze")
    public String analyzeProfile(
            @RequestParam("cvText") String cvText,
            @RequestParam("githubUrl") String githubUrl,
            @RequestParam("targetRole") String targetRoleName,
            @RequestParam(value = "repoCount", defaultValue = "0") int repoCount,
            @RequestParam(value = "contributionCount", defaultValue = "0") int contributionCount,
            Model model) {

        // Step 1: Build user profile
        UserProfile userProfile = userService.buildUserProfile(cvText, githubUrl, targetRoleName, repoCount, contributionCount);

        // Step 2: Run CV analysis
        List<Finding> cvFindings = cvAnalyzer.analyze(userProfile.getCvData());

        // Step 3: Run GitHub analysis
        List<Finding> githubFindings = gitHubAnalyzer.analyze(userProfile.getGitHubProfile());

        // Step 4: Run skill gap analysis
        List<Finding> skillGapFindings = new ArrayList<>();
        if (userProfile.getTargetRole() != null) {
            skillGapFindings = skillGapAnalyzer.analyze(userProfile.getCvData(), userProfile.getTargetRole());
        }

        // Step 5: Combine all findings
        List<Finding> allFindings = new ArrayList<>();
        allFindings.addAll(cvFindings);
        allFindings.addAll(githubFindings);
        allFindings.addAll(skillGapFindings);

        // Step 6: Generate recommendations from findings
        List<Recommendation> recommendations = recommendationEngine.generateRecommendations(allFindings);

        // Step 7: Call LLM for AI-based recommendations
        String llmSuggestions = llmService.getRecommendations(cvText, githubUrl, targetRoleName);

        // Step 8: Save results to database
        userService.saveResultToDatabase(cvText, githubUrl, targetRoleName, recommendations);

        // Step 9: Add everything to the model for the result page
        model.addAttribute("findings", allFindings);
        model.addAttribute("recommendations", recommendations);
        model.addAttribute("llmSuggestions", llmSuggestions);
        model.addAttribute("targetRole", targetRoleName);
        model.addAttribute("githubUrl", githubUrl);

        // Add skills found in CV
        List<String> foundSkills = userProfile.getCvData().getSkills();
        model.addAttribute("foundSkills", foundSkills);

        // Add missing skills list
        List<String> missingSkills = new ArrayList<>();
        if (userProfile.getTargetRole() != null) {
            missingSkills = skillGapAnalyzer.getMissingSkills(userProfile.getCvData(), userProfile.getTargetRole());
        }
        model.addAttribute("missingSkills", missingSkills);

        return "result";
    }
}
