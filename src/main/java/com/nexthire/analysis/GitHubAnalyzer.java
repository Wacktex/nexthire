package com.nexthire.analysis;

import com.nexthire.model.Finding;
import com.nexthire.model.GitHubProfile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GitHubAnalyzer {

    public List<Finding> analyze(GitHubProfile gitHubProfile) {
        List<Finding> findings = new ArrayList<>();

        // Check repository count
        if (gitHubProfile.getRepositoryCount() < 2) {
            Finding finding = new Finding();
            finding.setType("Low GitHub Activity");
            finding.setDescription("You have less than 2 repositories on GitHub. Recruiters expect to see your code. Try to add more projects.");
            finding.setSource("GitHub Analyzer");
            findings.add(finding);
        }

        // Check contribution count
        if (gitHubProfile.getContributionCount() < 10) {
            Finding finding = new Finding();
            finding.setType("Low Contributions");
            finding.setDescription("Very few GitHub contributions detected. Regular commits show that you are actively coding.");
            finding.setSource("GitHub Analyzer");
            findings.add(finding);
        }

        // Check if profile URL is valid
        String url = gitHubProfile.getProfileUrl();
        if (url == null || url.isEmpty() || !url.contains("github.com")) {
            Finding finding = new Finding();
            finding.setType("Invalid GitHub URL");
            finding.setDescription("The GitHub URL provided does not look correct. Please enter a valid GitHub profile URL.");
            finding.setSource("GitHub Analyzer");
            findings.add(finding);
        }

        // Good activity feedback
        if (gitHubProfile.getRepositoryCount() >= 10) {
            Finding finding = new Finding();
            finding.setType("Good GitHub Activity");
            finding.setDescription("You have a good number of repositories. This is a positive sign for recruiters.");
            finding.setSource("GitHub Analyzer");
            findings.add(finding);
        }

        return findings;
    }
}
