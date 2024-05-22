package com.example.uts_pm2.ui.project;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.uts_pm2.ui.task.AddingTask;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailProject extends AppCompatActivity {

    private TextView tvTitle, tvDescription, tvDueDate;
    private ImageView backButton, addButton;
    private TaskAdapter taskAdapter;
    private LinearLayout avatarContainer;
    private RecyclerView recyclerView;
    private int id_project;
    private DBConnect db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);

        db = new DBConnect(this);

        id_project = getIntent().getIntExtra("id_project", -1);

        tvTitle = findViewById(R.id.titleDetail);
        tvDescription = findViewById(R.id.descDetail);
        tvDueDate = findViewById(R.id.dueDate);
        backButton = findViewById(R.id.backButton);
        addButton = findViewById(R.id.addButton);
        avatarContainer = findViewById(R.id.avatarContainer);

        backButton.setOnClickListener(v -> finish());
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailProject.this, AddingTask.class);
                intent.putExtra("id_project", getIntent().getIntExtra("id_project", 0));
                startActivity(intent);
            }
        });

        tvTitle.setText(getIntent().getStringExtra("title"));
        tvDescription.setText(getIntent().getStringExtra("description"));
        tvDueDate.setText(getIntent().getStringExtra("due_date"));

        recyclerView = findViewById(R.id.recyclerViewTask);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<TaskData> taskList = db.getAllTaskData(getIntent().getIntExtra("id_project", 0));
        taskAdapter = new TaskAdapter(this,taskList, db);
        recyclerView.setAdapter(taskAdapter);

        if (id_project != -1) {
            showMemberAvatars(id_project);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData(){
        DBConnect db = new DBConnect(this);
        List<TaskData> taskList = db.getAllTaskData(getIntent().getIntExtra("id_project", 0));
        taskAdapter = new TaskAdapter(this,taskList, db);
        recyclerView.setAdapter(taskAdapter);
        showMemberAvatars(id_project);
    }

    private void showMemberAvatars(int projectId) {
        List<String> avatars = db.getDistinctMemberAvatars(projectId);
        avatarContainer.removeAllViews();

        int avatarsCount = avatars.size();
        int maxAvatarsToShow = 4;

        for (int i = 0; i < Math.min(avatarsCount, maxAvatarsToShow); i++) {
            CircleImageView avatarView = new CircleImageView(this);
            int imageResource = getResources().getIdentifier(avatars.get(i), "drawable", getPackageName());
            avatarView.setImageResource(imageResource);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(62, 62);
            params.setMargins(0, 0, -25, 0);
            avatarView.setBorderWidth(1);
            avatarView.setBorderColor(getResources().getColor(R.color.md_theme_onSurface));
            avatarView.setLayoutParams(params);
            avatarContainer.addView(avatarView);
        }

        if (avatarsCount > maxAvatarsToShow) {
            TextView moreAvatarsTextView = new TextView(this);
            moreAvatarsTextView.setText("+" + (avatarsCount - maxAvatarsToShow));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            moreAvatarsTextView.setLayoutParams(params);
            moreAvatarsTextView.setTextColor(getResources().getColor(R.color.md_theme_primary));
            moreAvatarsTextView.setTypeface(null, Typeface.BOLD);
            moreAvatarsTextView.setTextSize(12);
            moreAvatarsTextView.setBackgroundResource(R.drawable.circle_background);
            moreAvatarsTextView.setGravity(Gravity.CENTER);
            moreAvatarsTextView.setPadding(10, 5, 10, 5);
            avatarContainer.addView(moreAvatarsTextView);
        }
    }
}