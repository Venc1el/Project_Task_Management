package com.example.uts_pm2.data;


public class ProjectData {
    private int id;
    private int userId;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private String username;
    private String avatar;

    public ProjectData(int id, int userId,String title, String description, String startDate, String endDate, String username, String avatar) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.username = username;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }
    public int getUserId() {
        return userId;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public String getUsername() {
        return username;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
