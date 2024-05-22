package com.example.uts_pm2.data;

public class TaskData {
    private int taskId;
    private int projectId;
    private int userId;
    private String priority;
    private String name;
    private String description;
    private String username;
    private String avatar;

    public TaskData(int taskId, int projectId, int userId, String priority, String name, String description, String username, String avatar) {
        this.taskId = taskId;
        this.projectId = projectId;
        this.userId = userId;
        this.priority = priority;
        this.name = name;
        this.description = description;
        this.username = username;
        this.avatar = avatar;
    }

    public int getTaskId() {
        return taskId;
    }
    public int getProjectId() {
        return projectId;
    }
    public int getUserId() {
        return userId;
    }
    public String getPriority() {
        return priority;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getUsername(){
        return username;
    }
    public String getAvatar(){
        return avatar;
    }
}
