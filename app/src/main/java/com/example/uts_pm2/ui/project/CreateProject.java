package com.example.uts_pm2.ui.project;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.uts_pm2.PrefsManager;
import com.example.uts_pm2.R;
import com.example.uts_pm2.database.DBConnect;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateProject extends AppCompatActivity {
    private DBConnect dbConnect;
    private TextInputEditText projectNameEditText, projectDescriptionEditText,deadlineEditText;
    private String endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        dbConnect = new DBConnect(this);

        MaterialButton buttonAddProject = findViewById(R.id.buttonCreateProject);
        buttonAddProject.setOnClickListener(view -> addProject());

        MaterialToolbar toolbar = findViewById(R.id.topAppBarCreateProject);
        toolbar.setNavigationOnClickListener(view -> finish());

        deadlineEditText = findViewById(R.id.deadlineEdtText);
        deadlineEditText.setOnClickListener(view -> showDatePicker());
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select end date")
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
            endDate = sdf.format(selection);
            deadlineEditText.setText(endDate);
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    private void addProject() {
        projectNameEditText = findViewById(R.id.edtProjectName);
        projectDescriptionEditText = findViewById(R.id.edtProjectDescription);

        PrefsManager prefsManager = new PrefsManager(this);

        int userId  = prefsManager.getUserId();
        String projectName = projectNameEditText.getText().toString();
        String projectDescription = projectDescriptionEditText.getText().toString();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        String startDate = sdf.format(calendar.getTime());

        if (projectName.isEmpty()) {
            projectNameEditText.setError("Please enter a project name");
        } else if (projectDescription.isEmpty()) {
            projectDescriptionEditText.setError("Please enter a project description");
        } else if (endDate == null || endDate.isEmpty()) {
            Toast.makeText(this, "Please select an end date", Toast.LENGTH_SHORT).show();
        } else {
            dbConnect.insertProject(userId, projectName, projectDescription, startDate, endDate);
            Toast.makeText(this, "Project added successfully", Toast.LENGTH_SHORT).show();

            finish();
        }
    }
}
