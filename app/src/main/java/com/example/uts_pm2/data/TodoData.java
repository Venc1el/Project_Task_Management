package com.example.uts_pm2.data;

public class TodoData {
    private int todoId;
    private int taskId;
    private String todoName;
    private boolean isCompleted;

    public TodoData(int todoId, int taskId, String todoName, boolean isCompleted) {
        this.todoId = todoId;
        this.taskId = taskId;
        this.todoName = todoName;
        this.isCompleted = isCompleted;
    }

    public int getTodoId() {
        return todoId;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTodoName() {
        return todoName;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
