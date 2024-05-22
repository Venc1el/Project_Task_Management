package com.example.uts_pm2.ui.task;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.uts_pm2.R;
import com.example.uts_pm2.database.DBConnect;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

public class UpdateTask extends AppCompatActivity {

    private TextInputEditText edtTaskName, edtProjectDescription;
    private ChipGroup chipGroup;
    private MaterialButton btnUpdateTask;
    private MaterialToolbar toolbar;
    private DBConnect db;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        db = new DBConnect(this);

        edtTaskName = findViewById(R.id.edtTaskName);
        edtProjectDescription = findViewById(R.id.edtTaskDescription);
        chipGroup = findViewById(R.id.chipGroup);
        btnUpdateTask = findViewById(R.id.btnUpdateTask);
        toolbar = findViewById(R.id.topAppBarUpdateTask);

        taskId = getIntent().getIntExtra("task_id", -1);
        String taskName = getIntent().getStringExtra("task_name");
        String taskDescription = getIntent().getStringExtra("task_description");
        String taskPriority = getIntent().getStringExtra("task_priority");

        edtTaskName.setText(taskName);
        edtProjectDescription.setText(taskDescription);
        selectChipByPriority(taskPriority);

        toolbar.setNavigationOnClickListener(view -> finish());
        btnUpdateTask.setOnClickListener(view -> updateTask(taskId));

    }

    private void updateTask(int taskId) {
        String taskName = edtTaskName.getText().toString();
        String taskDescription = edtProjectDescription.getText().toString();
        Chip selectedChip = (Chip) findViewById(chipGroup.getCheckedChipId());
        String taskPriority = selectedChip != null ? selectedChip.getText().toString() : "";

        if (taskName.isEmpty()) {
            edtTaskName.setError("Task name is required");
        }else if (taskDescription.isEmpty()){
            edtProjectDescription.setError("Project description is required");
        } else if (taskPriority.isEmpty()) {
            Toast.makeText(this, "Please select a priority", Toast.LENGTH_SHORT).show();
        } else {
            boolean isUpdate = db.updateTask(taskId, taskName, taskDescription, taskPriority);
            if (isUpdate){
                Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void selectChipByPriority(String priority) {
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            if (chip.getText().toString().equalsIgnoreCase(priority)) {
                chip.setChecked(true);
                break;
            }
        }
    }
}