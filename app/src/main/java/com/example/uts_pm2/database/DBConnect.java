package com.example.uts_pm2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.uts_pm2.data.ProjectData;
import com.example.uts_pm2.data.TaskData;
import com.example.uts_pm2.data.TodoData;
import com.example.uts_pm2.data.UserData;

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
                "avatar_url" + " VARCHAR(20)," +
                "logged_in" + " INTEGER DEFAULT 0" +
                ")";

        sqLiteDatabase.execSQL(createUserTableQuery);

        String createProjectTableQuery = "CREATE TABLE " + "project" + " (" +
                "project_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id" + " INTEGER, " +
                "name" + " VARCHAR(100), " +
                "description" + " TEXT, " +
                "start_date" + " DATE, " +
                "end_date" + " DATE " +
                ")";
        sqLiteDatabase.execSQL(createProjectTableQuery);

        String createTaskTableQuery = "CREATE TABLE " + "task" + " (" +
                "task_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "project_id" + " INTEGER, " +
                "user_id" + " INTEGER, " +
                "name" + " VARCHAR(100), " +
                "description" + " TEXT, " +
                "priority" + " VARCHAR(10),  " +
                "FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE, " +
                "FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE" +
                ")";
        sqLiteDatabase.execSQL(createTaskTableQuery);

        String createTodoTableQuery = "CREATE TABLE " + "todo" + " (" +
                "todo_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "task_id" + " INTEGER, " +
                "todo_name" + " TEXT, " +
                "completed" + " INTEGER DEFAULT 0, " +
                "FOREIGN KEY (task_id) REFERENCES task(task_id) ON DELETE CASCADE" +
                ")";
        sqLiteDatabase.execSQL(createTodoTableQuery);

        String insertUser1 = "INSERT INTO user (username, password, email, full_name, avatar_url) VALUES " +
                "('alice', 'passwordAlice', 'alice@example.com', 'Alice Johnson', 'toon_10'), " +
                "('bob', 'passwordBob', 'bob@example.com', 'Bob Smith', 'toon_2'), " +
                "('charlie', 'passwordCharlie', 'charlie@example.com', 'Charlie Brown', 'toon_7'), " +
                "('diana', 'passwordDiana', 'diana@example.com', 'Diana Prince', 'toon_5'), " +
                "('eve', 'passwordEve', 'eve@example.com', 'Eve Anderson', 'toon_3'), " +
                "('frank', 'passwordFrank', 'frank@example.com', 'Frank Miller', 'toon_8'), " +
                "('grace', 'passwordGrace', 'grace@example.com', 'Grace Taylor', 'toon_1'), " +
                "('henry', 'passwordHenry', 'henry@example.com', 'Henry Wilson', 'toon_6')";
        sqLiteDatabase.execSQL(insertUser1);

        String insertProject1 = "INSERT INTO project (user_id, name, description, start_date, end_date) VALUES " +
                "(1,'Website Redesign', 'Complete overhaul of the company website', '2024-01-15', 'May 2, 2024'), " +
                "(1,'Mobile App Development', 'Development of a new mobile application', '2024-02-01', 'June 2, 2024'), " +
                "(2,'Marketing Campaign', 'Launch and manage new marketing campaign', '2024-03-01', 'January 2, 2024'), " +
                "(3,'Product Launch', 'Launch of a new product line', '2024-04-10', 'August 15, 2024'), " +
                "(4,'Sales Training', 'Training sessions for sales team', '2024-05-01', 'September 30, 2024')";
        sqLiteDatabase.execSQL(insertProject1);

        String insertTask1 = "INSERT INTO task (project_id, user_id, name, description, priority) VALUES " +
                "(1, 1, 'Design new homepage', 'Create a modern design for the new homepage', 'High'), " +
                "(1, 2, 'Develop homepage', 'Implement the homepage design in code', 'Medium'), " +
                "(1, 3, 'Test homepage', 'Ensure the new homepage works on all devices', 'Low'), " +
                "(2, 2, 'Set up project repository', 'Create a repository for the mobile app project', 'High'), " +
                "(2, 4, 'Design app interface', 'Create wireframes and designs for the app', 'Medium'), " +
                "(2, 1, 'Develop login feature', 'Implement user login functionality', 'High'), " +
                "(3, 3, 'Plan campaign', 'Outline the campaign strategy and goals', 'High'), " +
                "(3, 4, 'Create campaign materials', 'Develop graphics and content for the campaign', 'Medium'), " +
                "(3, 1, 'Launch campaign', 'Launch the campaign and monitor its performance', 'High'), " +
                "(4, 2, 'Develop Training Modules', 'Create modules for sales training', 'High'), " +
                "(4, 3, 'Schedule Training Sessions', 'Organize timings and locations for training', 'Medium'), " +
                "(5, 4, 'Market Research', 'Conduct research on target market', 'High'), " +
                "(5, 1, 'Create Sales Strategy', 'Develop a strategy to increase sales', 'High')";
        sqLiteDatabase.execSQL(insertTask1);

        String insertTodo1 = "INSERT INTO todo (task_id, todo_name) VALUES " +
                "(1, 'Create wireframes'), " +
                "(1, 'Review design with team'), " +
                "(2, 'Setup development environment'), " +
                "(2, 'Write initial HTML'), " +
                "(3, 'Run cross-browser tests'), " +
                "(3, 'Fix layout issues'), "+
                "(4, 'Create project structure'), " +
                "(4, 'Implement version control system'), " +
                "(5, 'Gather requirements from stakeholders'), " +
                "(5, 'Create mockups for user interface'), " +
                "(6, 'Write backend API endpoints'), " +
                "(6, 'Integrate frontend with backend')," +
                "(7, 'Research competitor products'), " +
                "(7, 'Identify target demographics'), " +
                "(8, 'Book training venues'), " +
                "(8, 'Coordinate with trainers'), " +
                "(9, 'Survey potential customers'), " +
                "(9, 'Analyze market trends')";
        sqLiteDatabase.execSQL(insertTodo1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS project");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS task");
        onCreate(sqLiteDatabase);
    }

    public void insertProject(int userId, String name, String description, String startDate, String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("name", name);
        values.put("description", description);
        values.put("start_date", startDate);
        values.put("end_date", endDate);
        db.insert("project", null, values);
        db.close();
    }

    public void insertTask(int projectId, int userId, String name, String description, String priority){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("project_id", projectId);
        values.put("user_id", userId);
        values.put("name", name);
        values.put("description", description);
        values.put("priority", priority);
        db.insert("task", null, values);
    }

    public void insertTodo(int taskId, String todo_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("task_id", taskId);
        values.put("todo_name", todo_name);
        db.insert("todo", null, values);
    }

    public void createAccount(String username, String password, String email, String fullname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("email", email);
        values.put("full_name", fullname);
        values.put("avatar_url", "toon_8");
        db.insert("user", null, values);
    }

    public boolean updateProject(int projectId, String name, String description, String endDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("end_date", endDate);

        int result = db.update("project", values, "project_id = ?", new String[]{String.valueOf(projectId)});
        db.close();

        return result > 0;
    }

    public boolean updateTask(int taskId, String name, String description, String priority){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("priority", priority);

        int result = db.update("task", values, "task_id = ?", new String[]{String.valueOf(taskId)});
        db.close();

        return result > 0;
    }

    public boolean updateTodoStatus(int todoId, boolean isCompleted) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("completed", isCompleted ? 1 : 0);

        int rowsAffected = db.update("todo", values, "todo_id = ?", new String[]{String.valueOf(todoId)});
        db.close();

        return rowsAffected > 0;
    }

    public boolean updateUserAvatar(int userId, String avatarUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("avatar_url", avatarUrl);

        int rowsAffected = db.update("user", values, "user_id = ?", new String[]{String.valueOf(userId)});
        db.close();

        return rowsAffected > 0;
    }

    public List<ProjectData> getAllProjectData(){
        List<ProjectData> projectDataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query =  "SELECT project.*, user.username, user.avatar_url" +
                        " FROM project " +
                        "JOIN user ON project.user_id = user.user_id";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null && cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex("project_id"));
                int user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String start_date = cursor.getString(cursor.getColumnIndex("start_date"));
                String end_date = cursor.getString(cursor.getColumnIndex("end_date"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String avatar_url = cursor.getString(cursor.getColumnIndex("avatar_url"));

                if (name != null && description != null && start_date != null && end_date != null){
                    ProjectData projectData = new ProjectData(id, user_id ,name, description, start_date, end_date, username, avatar_url);
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

    public List<UserData> getAllUserData(){
        List<UserData> userDataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query =  "SELECT * FROM user";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null && cursor.moveToFirst()){
            do{
                int userId = cursor.getInt(cursor.getColumnIndex("user_id"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String full_name = cursor.getString(cursor.getColumnIndex("full_name"));
                String avatar_url = cursor.getString(cursor.getColumnIndex("avatar_url"));

                if (username != null && email != null && full_name != null && avatar_url != null){
                    UserData userData = new UserData(userId, username, email, avatar_url, full_name);
                    userDataList.add(userData);
                }
            }while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return userDataList;
    }

    public List<TaskData> getAllTaskData(int projectId){
        List<TaskData> taskDataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query =  "SELECT task.*, user.username, user.avatar_url " +
                "FROM task " +
                "JOIN user ON task.user_id = user.user_id " +
                "WHERE project_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(projectId)});

        if(cursor != null && cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex("task_id"));
                int user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String priority = cursor.getString(cursor.getColumnIndex("priority"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String avatar_url = cursor.getString(cursor.getColumnIndex("avatar_url"));

                if (name != null && description != null && priority != null){
                    TaskData taskData = new TaskData(id, projectId, user_id,priority, name, description, username, avatar_url);
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

    public List<TodoData> getAllTodoData(int taskId){
        List<TodoData> todoDataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query =  "SELECT * FROM todo WHERE task_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(taskId)});

        if(cursor != null && cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex("todo_id"));
                String todo_name = cursor.getString(cursor.getColumnIndex("todo_name"));
                int completed = cursor.getInt(cursor.getColumnIndex("completed"));

                boolean isCompleted = completed == 1;

                if (todo_name != null){
                    TodoData todoData = new TodoData(id, taskId, todo_name, isCompleted);
                    todoDataList.add(todoData);
                }
            } while (cursor.moveToNext());

        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return todoDataList;
    }

    public List<String> getDistinctMemberAvatars(int projectId) {
        List<String> avatars = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT user.avatar_url FROM user JOIN task ON user.user_id = task.user_id WHERE task.project_id = ?", new String[]{String.valueOf(projectId)});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                avatars.add(cursor.getString(cursor.getColumnIndex("avatar_url")));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return avatars;
    }

    public void deleteProject(int projectId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("project", "project_id = ?", new String[]{String.valueOf(projectId)});
        db.close();
    }

    public void deleteTaskWithTodos(int taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("todo", "task_id = ?", new String[]{String.valueOf(taskId)});
        db.delete("task", "task_id = ?", new String[]{String.valueOf(taskId)});
        db.close();
    }

    public boolean areAllTodosCompleted(int taskId) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean allCompleted = false;

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM todo WHERE task_id = ?", new String[]{String.valueOf(taskId)});
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            if (count == 0) {
                // No todos associated with the task, return false
                allCompleted = false;
            } else {
                cursor = db.rawQuery("SELECT COUNT(*) FROM todo WHERE task_id = ? AND completed = 0", new String[]{String.valueOf(taskId)});
                if (cursor != null && cursor.moveToFirst()) {
                    int completedCount = cursor.getInt(0);
                    allCompleted = completedCount == 0;
                }
            }
        }
        
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return allCompleted;
    }


    public boolean validateLogin(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        boolean isValid = cursor != null && cursor.moveToFirst();
        if (cursor != null){
            cursor.close();
        }
        return isValid;
    }

    public void markUserLoggedIn(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("logged_in", 1);
        db.update("user", values, "email = ?", new String[]{email});
        db.close();
    }

    public void markUserLoggedOut(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("logged_in", 0);
        db.update("user", values, null, null);
        db.close();
    }

    public boolean isAnyUserLoggedIn(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user WHERE logged_in = 1";
        Cursor cursor = db.rawQuery(query, null);
        boolean isLoggedIn = cursor != null && cursor.moveToFirst();
        if (cursor != null){
            cursor.close();
        }
        return isLoggedIn;
    }

    public UserData getUserData(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("user", new String[]{"user_id","username", "email", "full_name", "avatar_url"},
                "email = ?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.moveToFirst()){
            int userId = cursor.getInt(cursor.getColumnIndex("user_id"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String full_name = cursor.getString(cursor.getColumnIndex("full_name"));
            String avatar_url = cursor.getString(cursor.getColumnIndex("avatar_url"));
            cursor.close();
            db.close();
            return new UserData(userId,username, email, avatar_url, full_name);
        }
        if (cursor != null){
            cursor.close();
        }
        db.close();
        return null;
    }
}