package com.nexthire.model;

public class UserProfile {

    private CVData cvData;
    private GitHubProfile gitHubProfile;
    private TargetRole targetRole;
    private String targetCompany;

    public UserProfile() {
    }

    public CVData getCvData() {
        return cvData;
    }

    public void setCvData(CVData cvData) {
        this.cvData = cvData;
    }

    public GitHubProfile getGitHubProfile() {
        return gitHubProfile;
    }

    public void setGitHubProfile(GitHubProfile gitHubProfile) {
        this.gitHubProfile = gitHubProfile;
    }

    public TargetRole getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(TargetRole targetRole) {
        this.targetRole = targetRole;
    }

    public String getTargetCompany() {
        return targetCompany;
    }

    public void setTargetCompany(String targetCompany) {
        this.targetCompany = targetCompany;
    }
}
