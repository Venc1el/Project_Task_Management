package com.example.uts_pm2.ui.project;

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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class UpdateProject extends AppCompatActivity {
    private TextInputEditText edProjectName, edProjectDesc, edProjectEnd;
    private MaterialButton btnUpdate;
    private MaterialToolbar appBar;
    private String deadlineDate;
    private DBConnect db;
    private int projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_project);

        appBar = findViewById(R.id.topAppBarUpdateProject);
        edProjectName = findViewById(R.id.edtProjectNameEditText);
        edProjectDesc = findViewById(R.id.edtProjectDescriptionEditText);
        edProjectEnd = findViewById(R.id.deadlineEdtText);
        btnUpdate = findViewById(R.id.buttonUpdate);

        edProjectEnd.setOnClickListener(view -> showDatePicker());

        appBar.setNavigationOnClickListener(view -> finish());

        projectId = getIntent().getIntExtra("id_project", -1);
        String projectName = getIntent().getStringExtra("title");
        String projectDesc = getIntent().getStringExtra("description");
        String projectEnd = getIntent().getStringExtra("due_date");

        edProjectName.setText(projectName);
        edProjectDesc.setText(projectDesc);
        edProjectEnd.setText(projectEnd);

        deadlineDate = projectEnd;

        btnUpdate.setOnClickListener(view -> updateProject(projectId));
    }

    private void showDatePicker(){
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().build();
        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
            deadlineDate = dateFormat.format(selection);
            edProjectEnd.setText(deadlineDate);
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    private void updateProject(int id_project) {
        String name = edProjectName.getText().toString();
        String description = edProjectDesc.getText().toString();
        String endDate = deadlineDate;

        if(name.isEmpty()){
            edProjectName.setError("Project Name cannot be empty");
        }else if(description.isEmpty()){
            edProjectDesc.setError("Project Description cannot be empty");
        }else if(endDate.isEmpty()){
            edProjectEnd.setError("Deadline cannot be empty");
        } else{
            db = new DBConnect(this);
            boolean isUpdate = db.updateProject(id_project, name, description, endDate);
            if(isUpdate){
                Toast.makeText(UpdateProject.this, "Project Updated", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(UpdateProject.this, "Failed to Update Project", Toast.LENGTH_SHORT).show();
            }
        }
    }
}