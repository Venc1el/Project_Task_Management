package com.example.uts_pm2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.uts_pm2.data.ProjectData;
import com.example.uts_pm2.data.TaskData;

import java.util.ArrayList;
import java.util.List;

public class DBConnect extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "management.db";
    private static final int DATABASE_VERSION = 1;

    public DBConnect(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String createUserTableQuery = "CREATE TABLE " + "user" + " (" +
                "user_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username" + " VARCHAR(50), " +
                "password" + " VARCHAR(100), " +
                "email" + " VARCHAR(100), " +
                "full_name" + " VARCHAR(100), " +
                "avatar_url" + " VARCHAR(20)" +
                ")";

        sqLiteDatabase.execSQL(createUserTableQuery);

        String createProjectTableQuery = "CREATE TABLE " + "project" + " (" +
                "project_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name" + " VARCHAR(100), " +
                "description" + " TEXT, " +
                "start_date" + " DATE, " +
                "end_date" + " DATE " +
                ")";
        sqLiteDatabase.execSQL(createProjectTableQuery);

        String createTaskTableQuery = "CREATE TABLE " + "task" + " (" +
                "task_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "project_id" + " INTEGER, " +
                "name" + " VARCHAR(100), " +
                "description" + " TEXT, " +
                "priority" + " VARCHAR(10),  " +
                "FOREIGN KEY (project_id) REFERENCES project(project_id) " +
                ")";
        sqLiteDatabase.execSQL(createTaskTableQuery);

        String insertProject1 = "INSERT INTO project (name, description, start_date, end_date) VALUES " +
                "('Website Redesign', 'Complete overhaul of the company website', '2024-01-15', 'June 30, 2024'), " +
                "('Mobile App Development', 'Development of a new mobile application', '2024-02-01', 'August 12, 2024'), " +
                "('Marketing Campaign', 'Launch and manage new marketing campaign', '2024-03-01', 'December 15, 2024')";
        sqLiteDatabase.execSQL(insertProject1);

        String insertTask1 = "INSERT INTO task (project_id, name, description, priority) VALUES " +
                "(1, 'Design new homepage', 'Create a modern design for the new homepage', 'High'), " +
                "(1, 'Develop homepage', 'Implement the homepage design in code', 'Medium'), " +
                "(1, 'Test homepage', 'Ensure the new homepage works on all devices', 'Low'), " +
                "(2, 'Set up project repository', 'Create a repository for the mobile app project', 'High'), " +
                "(2, 'Design app interface', 'Create wireframes and designs for the app', 'Medium'), " +
                "(2, 'Develop login feature', 'Implement user login functionality', 'High'), " +
                "(3, 'Plan campaign', 'Outline the campaign strategy and goals', 'High'), " +
                "(3, 'Create campaign materials', 'Develop graphics and content for the campaign', 'Medium'), " +
                "(3, 'Launch campaign', 'Launch the campaign and monitor its performance', 'High')";
        sqLiteDatabase.execSQL(insertTask1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS project");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS task");
        onCreate(sqLiteDatabase);
    }

    public void insertProject(String name, String description, String startDate, String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("start_date", startDate);
        values.put("end_date", endDate);
        db.insert("project", null, values);
        db.close();
    }

    public void insertTask(int projectId, String name, String description, String dueDate, String priority){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("project_id", projectId);
        values.put("name", name);
        values.put("description", description);
        values.put("priority", priority);
        db.insert("task", null, values);
    }

    public List<ProjectData> getAllProjectData(){
        List<ProjectData> projectDataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM project";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null && cursor.moveToFirst()){
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String start_date = cursor.getString(cursor.getColumnIndex("start_date"));
                String end_date = cursor.getString(cursor.getColumnIndex("end_date"));
                int id = cursor.getInt(cursor.getColumnIndex("project_id"));

                if (name != null && description != null && start_date != null && end_date != null){
                    ProjectData projectData = new ProjectData(id,name, description, start_date, end_date);
                    projectDataList.add(projectData);
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return projectDataList;
    }

    public List<TaskData> getAllTaskData(int projectId){
        List<TaskData> taskDataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM task WHERE project_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(projectId)});

        if(cursor != null && cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex("task_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String priority = cursor.getString(cursor.getColumnIndex("priority"));

                if (name != null && description != null && priority != null){
                    TaskData taskData = new TaskData(id, projectId,priority, name, description);
                    taskDataList.add(taskData);
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return taskDataList;
    }


    public void deleteProject(int projectId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("project", "project_id = ?", new String[]{String.valueOf(projectId)});
        db.close();
    }
}