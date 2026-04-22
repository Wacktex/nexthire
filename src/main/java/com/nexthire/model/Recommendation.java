package com.nexthire.model;

public class Recommendation {

    private String type;
    private String description;
    private String priority;

    public Recommendation() {
    }

    public Recommendation(String type, String description, String priority) {
        this.type = type;
        this.description = description;
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
