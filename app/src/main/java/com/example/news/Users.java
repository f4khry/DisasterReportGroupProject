package com.example.news;

public class Users {

    static String userId;
    static String name;
    static String profile;

    public Users(String userId, String name, String profile) {
        this.userId = userId;
        this.name = name;
        this.profile = profile;
    }

    public Users() {
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getProfile() {
        return profile;
    }

    public static void setUserId(String userId1) {
        userId = userId1;
    }

    public static void setName(String name1) {
        name = name1;
    }

    public static void setProfile(String profile1) {
        profile = profile1;
    }
}
