package com.nexthire.model;

public class Finding {

    private String type;
    private String description;
    private String source;

    public Finding() {
    }

    public Finding(String type, String description, String source) {
        this.type = type;
        this.description = description;
        this.source = source;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
