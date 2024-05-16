package com.example.uts_pm2.ui.project;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pm2.R;
import com.example.uts_pm2.adapter.TaskAdapter;
import com.example.uts_pm2.data.TaskData;
import com.example.uts_pm2.database.DBConnect;

import java.util.List;

public class DetailProject extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvDueDate;
    private ImageView backButton;
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);
        applyFullScreenMode();

        DBConnect db = new DBConnect(this);

        tvTitle = findViewById(R.id.titleDetail);
        tvDescription = findViewById(R.id.descDetail);
        tvDueDate = findViewById(R.id.dueDate);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        tvTitle.setText(getIntent().getStringExtra("title"));
        tvDescription.setText(getIntent().getStringExtra("description"));
        tvDueDate.setText(getIntent().getStringExtra("due_date"));

        recyclerView = findViewById(R.id.recyclerViewTask);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<TaskData> taskList = db.getAllTaskData(getIntent().getIntExtra("id_project", 0));
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);
    }

    private void applyFullScreenMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().getDecorView().getWindowInsetsController().hide(
                    android.view.WindowInsets.Type.navigationBars()
            );
            getWindow().getDecorView().getWindowInsetsController().setSystemBarsBehavior(
                    android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }
}