package com.example.uts_pm2.ui.task;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pm2.R;
import com.example.uts_pm2.adapter.TodoAdapter;
import com.example.uts_pm2.data.TodoData;
import com.example.uts_pm2.database.DBConnect;
import com.example.uts_pm2.ui.task.bottomSheet.AddTodoBottomSheetDialog;
import com.google.android.material.chip.Chip;

import java.util.List;

public class DetailTask extends AppCompatActivity implements OnTodoAddedListener {

    private Chip priority;
    private TodoAdapter todoAdapter;
    private RecyclerView recyclerView;
    private List<TodoData> todoDataList;
    private DBConnect db;
    private TextView title, description, tvAuthor, addTodo;
    private ImageView backButton, imgAvatar;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        db = new DBConnect(this);

        backButton = findViewById(R.id.backButton);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        tvAuthor = findViewById(R.id.author);
        imgAvatar = findViewById(R.id.avatar);
        addTodo = findViewById(R.id.addToDoList);

        taskId = getIntent().getIntExtra("task_id", -1);

        recyclerView = findViewById(R.id.recyclerViewTodoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoDataList = db.getAllTodoData(taskId);
        todoAdapter = new TodoAdapter(todoDataList, db);
        recyclerView.setAdapter(todoAdapter);

        String titleText = getIntent().getStringExtra("task_name");
        String descriptionText = getIntent().getStringExtra("task_description");
        String authorText = getIntent().getStringExtra("task_username");
        String avatarText = getIntent().getStringExtra("task_avatar");

        int avatarResId = getResources().getIdentifier(avatarText, "drawable", getPackageName());

        title.setText(titleText);
        tvAuthor.setText(authorText);
        imgAvatar.setImageResource(avatarResId);
        description.setText(descriptionText);
        backButton.setOnClickListener(v -> finish());

        addTodo.setOnClickListener(v -> showAddBottomSheetDialog());

        setPriorityColor();
    }

    private void showAddBottomSheetDialog() {
        AddTodoBottomSheetDialog bottomSheetDialog = new AddTodoBottomSheetDialog(taskId);
        bottomSheetDialog.setOnTodoAddedListener(this);
        bottomSheetDialog.show(getSupportFragmentManager(), bottomSheetDialog.getTag());
    }

    @Override
    public void onTodoAdded() {
        refreshData();
    }

    private void refreshData() {
        todoDataList = db.getAllTodoData(taskId);
        todoAdapter.updateData(todoDataList);
    }

    private void setPriorityColor() {
        String priorityText = getIntent().getStringExtra("task_priority");
        priority = findViewById(R.id.chipPriority);
        priority.setChipText(priorityText);

        int chipTextColor;
        int chipBackgroundColor;

        switch (priorityText.toLowerCase()) {
            case "high":
                chipTextColor = ContextCompat.getColor(this, R.color.chipTextColorHigh);
                chipBackgroundColor = ContextCompat.getColor(this, R.color.chipBackgroundTintHigh);
                break;
            case "medium":
                chipTextColor = ContextCompat.getColor(this, R.color.chipTextColorMedium);
                chipBackgroundColor = ContextCompat.getColor(this, R.color.chipBackgroundTintMedium);
                break;
            case "low":
                chipTextColor = ContextCompat.getColor(this, R.color.chipTextColorLow);
                chipBackgroundColor = ContextCompat.getColor(this, R.color.chipBackgroundTintLow);
                break;
            case "critical":
                chipTextColor = ContextCompat.getColor(this, R.color.chipTextColorCritical);
                chipBackgroundColor = ContextCompat.getColor(this, R.color.chipBackgroundTintCritical);
                break;
            default:
                chipTextColor = ContextCompat.getColor(this, R.color.defaultChipTextColor);
                chipBackgroundColor = ContextCompat.getColor(this, R.color.defaultChipBackgroundTint);
                break;
        }

        priority.setTextColor(chipTextColor);
        priority.setChipBackgroundColor(ColorStateList.valueOf(chipBackgroundColor));
    }
}
