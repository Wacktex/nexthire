package com.nexthire.model;

import java.util.List;
import java.util.ArrayList;

public class TargetRole {

    private String roleName;
    private List<String> requiredSkills;
    private List<String> requiredProjectTypes;

    public TargetRole() {
        requiredSkills = new ArrayList<>();
        requiredProjectTypes = new ArrayList<>();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public List<String> getRequiredProjectTypes() {
        return requiredProjectTypes;
    }

    public void setRequiredProjectTypes(List<String> requiredProjectTypes) {
        this.requiredProjectTypes = requiredProjectTypes;
    }
}
