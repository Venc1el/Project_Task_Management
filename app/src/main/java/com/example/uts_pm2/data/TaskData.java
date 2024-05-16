package com.example.uts_pm2.data;

public class TaskData {
    private int taskId;
    private int projectId;
    private String priority;
    private String name;
    private String description;

    public TaskData(int taskId, int projectId, String priority, String name, String description) {
        this.taskId = taskId;
        this.projectId = projectId;
        this.priority = priority;
        this.name = name;
        this.description = description;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getProjectId() {
        return projectId;
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

}
