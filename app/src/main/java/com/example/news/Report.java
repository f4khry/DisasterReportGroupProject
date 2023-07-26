package com.example.news;

public class Report {
    private String damageType;
    private String description;
    private String userId;

    public Report() {
        // Default constructor required for calls to DataSnapshot.getValue(Report.class)
    }

    public Report(String damageType, String description, String userId) {
        this.damageType = damageType;
        this.description = description;
        this.userId = userId;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
