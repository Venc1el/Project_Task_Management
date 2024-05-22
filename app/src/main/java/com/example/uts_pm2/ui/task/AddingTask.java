package com.example.uts_pm2.ui.task;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pm2.PrefsManager;
import com.example.uts_pm2.R;
import com.example.uts_pm2.adapter.UserAdapter;
import com.example.uts_pm2.data.UserData;
import com.example.uts_pm2.database.DBConnect;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class AddingTask extends AppCompatActivity {
    private TextInputLayout edtTaskNameLayout, edtTaskDescLayout;
    private TextInputEditText edtTaskName, edtProjectDescription;
    private ChipGroup chipGroup;
    private MaterialButton btnAddTask;
    private MaterialToolbar toolbar;
    private DBConnect db;
    private int idProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_task);

        btnAddTask = findViewById(R.id.btnAddTask);
        toolbar = findViewById(R.id.topAppBarCreateTask);

        idProject = getIntent().getIntExtra("id_project", 0);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProject();
            }
        });


    }

    private void addProject(){
        db = new DBConnect(this);
        PrefsManager prefsManager = new PrefsManager(this);

        idProject = getIntent().getIntExtra("id_project", 0);
        int idUser = prefsManager.getUserId();
        edtTaskNameLayout = findViewById(R.id.edtTaskNameLayout);
        edtTaskDescLayout = findViewById(R.id.edtTaskDescriptionLayout);
        edtTaskName = findViewById(R.id.edtTaskName);
        edtProjectDescription = findViewById(R.id.edtTaskDescription);
        chipGroup = findViewById(R.id.chipGroup);

        String taskName = edtTaskName.getText().toString();
        String taskDescription = edtProjectDescription.getText().toString();
        Chip selectedChip = (Chip) findViewById(chipGroup.getCheckedChipId());
        String taskPriority = selectedChip != null ? selectedChip.getText().toString() : "";

        if(taskName.isEmpty() || taskDescription.isEmpty() || taskPriority.isEmpty()){
            Toast.makeText(AddingTask.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            db.insertTask(idProject, idUser , taskName, taskDescription, taskPriority);
            Toast.makeText(AddingTask.this, "Task added successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}