package com.example.uts_pm2.data;

public class UserData {
    private int userId;
    private String username;
    private String email;
    private String avatarUrl;
    private String fullName;

    public UserData(int userId, String username, String email, String avatarUrl, String fullName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.fullName = fullName;
    }

    public int getUserId(){
        return userId;
    }
    public String getUsername(){
        return username;
    }
    public String getEmail(){
        return email;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public String getFullName() {
        return fullName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
