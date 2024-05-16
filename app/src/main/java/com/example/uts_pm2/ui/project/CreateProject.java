package com.example.uts_pm2.ui.project;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uts_pm2.R;
import com.example.uts_pm2.database.DBConnect;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateProject extends AppCompatActivity {

    private TextView startDateTextView;
    private TextView endDateTextView;
    private DBConnect dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        applyFullScreenMode();

        dbHelper = new DBConnect(this);

        startDateTextView = findViewById(R.id.textStartDate);
        endDateTextView = findViewById(R.id.textDueDate);

        MaterialCardView startDateButton = findViewById(R.id.cardStartDate);
        MaterialCardView endDateButton = findViewById(R.id.cardDueDate);

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        String todayString = sdf.format(today);

        startDateTextView.setText(todayString);
        endDateTextView.setText(todayString);


        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(true);
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(false);
            }
        });

        MaterialButton buttonAddProject = findViewById(R.id.buttonCreateProject);
        buttonAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProject();
            }
        });

        MaterialToolbar toolbar = findViewById(R.id.topAppBarCreateProject);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showDatePicker(boolean isStartDate) {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(isStartDate ? "Select Start Date" : "Select Due Date");

        final MaterialDatePicker<Long> datePicker = builder.build();
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Date date = new Date(selection);
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
                String dateString = sdf.format(date);

                if (isStartDate) {
                    startDateTextView.setText(dateString);
                } else {
                    endDateTextView.setText(dateString);
                }
            }
        });

        datePicker.show(getSupportFragmentManager(), datePicker.toString());
    }

    private void addProject(){
        TextInputLayout projectNameLayout = findViewById(R.id.edtProjectName);
        TextInputLayout projectDescriptionLayout = findViewById(R.id.edtProjectDescription);
        ChipGroup chipGroup = findViewById(R.id.chipGroup);

        EditText projectNameEditText = projectNameLayout.getEditText();
        EditText projectDescriptionEditText = projectDescriptionLayout.getEditText();

        String projectName = projectNameEditText.getText().toString();
        String projectDescription = projectDescriptionEditText.getText().toString();

        Chip selectedChip = (Chip) findViewById(chipGroup.getCheckedChipId());
        String projectStatus = selectedChip != null ? selectedChip.getText().toString() : "";

        String startDate = startDateTextView.getText().toString();
        String endDate = endDateTextView.getText().toString();

        if(projectName.isEmpty() || projectDescription.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else if (projectStatus.isEmpty()) {
            Toast.makeText(this, "Please select a project status", Toast.LENGTH_SHORT).show();
        } else if(startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Please select a start and end date", Toast.LENGTH_SHORT).show();
        } else{
            dbHelper.insertProject(projectName, projectDescription, startDate, endDate);
            Toast.makeText(this, "Project added successfully", Toast.LENGTH_SHORT).show();

            finish();
        }
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