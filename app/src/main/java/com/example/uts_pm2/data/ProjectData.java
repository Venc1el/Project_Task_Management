package com.example.uts_pm2.data;


public class ProjectData {
    private int id;
    private String title;
    private String description;
    private String startDate;
    private String endDate;

    public ProjectData(int id,String title, String description, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
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

}
