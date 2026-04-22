package com.nexthire.model;

import java.util.List;
import java.util.ArrayList;

public class CVData {

    private String rawText;
    private List<String> skills;
    private List<String> projects;
    private List<String> certifications;

    public CVData() {
        skills = new ArrayList<>();
        projects = new ArrayList<>();
        certifications = new ArrayList<>();
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public List<String> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<String> certifications) {
        this.certifications = certifications;
    }
}
