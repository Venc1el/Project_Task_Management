package com.example.uts_pm2.ui.task;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.uts_pm2.R;
import com.google.android.material.chip.Chip;

public class DetailTask extends AppCompatActivity {

    private Chip priority;
    private TextView title;
    private TextView description;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        backButton = findViewById(R.id.backButton);
        priority = findViewById(R.id.chipPriority);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);

        String priorityText = getIntent().getStringExtra("task_priority");
        String titleText = getIntent().getStringExtra("task_name");
        String descriptionText = getIntent().getStringExtra("task_description");

        priority.setChipText(priorityText);
        title.setText(titleText);
        description.setText(descriptionText);
        backButton.setOnClickListener(v -> finish());

        setPriorityColor(priorityText);
    }

    private void setPriorityColor(String priorityText) {
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