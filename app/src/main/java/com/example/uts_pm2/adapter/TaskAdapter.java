package com.example.uts_pm2.adapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pm2.R;
import com.example.uts_pm2.data.TaskData;
import com.example.uts_pm2.ui.task.DetailTask;
import com.google.android.material.chip.Chip;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<TaskData> taskDataList;

    public TaskAdapter(List<TaskData> taskDataList) {
        this.taskDataList = taskDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_task, parent, false);
        return new ViewHolder(view, taskDataList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskData taskData = taskDataList.get(position);
        holder.bind(taskData);
    }

    @Override
    public int getItemCount() {
        return taskDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Chip chip;
        private TextView title;
        private List<TaskData> taskDataList;

        public ViewHolder(@NonNull View itemView, List<TaskData> taskDataList) {
            super(itemView);
            this.taskDataList = taskDataList;
            chip = itemView.findViewById(R.id.chipPriority);
            title = itemView.findViewById(R.id.taskTitle);
        }

        public void bind(TaskData taskData) {
            String priority = taskData.getPriority();
            chip.setChipText(priority);
            title.setText(taskData.getName());

            int chipTextColor;
            int chipBackgroundColor;

            switch (priority.toLowerCase()){
                case "high":
                    chipTextColor = ContextCompat.getColor(itemView.getContext(), R.color.chipTextColorHigh);
                    chipBackgroundColor = ContextCompat.getColor(itemView.getContext(), R.color.chipBackgroundTintHigh);
                    break;
                case "medium":
                    chipTextColor = ContextCompat.getColor(itemView.getContext(), R.color.chipTextColorMedium);
                    chipBackgroundColor = ContextCompat.getColor(itemView.getContext(), R.color.chipBackgroundTintMedium);
                    break;
                case "low" :
                    chipTextColor = ContextCompat.getColor(itemView.getContext(), R.color.chipTextColorLow);
                    chipBackgroundColor = ContextCompat.getColor(itemView.getContext(), R.color.chipBackgroundTintLow);
                    break;
                case "critical" :
                    chipTextColor = ContextCompat.getColor(itemView.getContext(), R.color.chipTextColorCritical);
                    chipBackgroundColor = ContextCompat.getColor(itemView.getContext(), R.color.chipBackgroundTintCritical);
                    break;
                default:
                    chipTextColor = ContextCompat.getColor(itemView.getContext(), R.color.defaultChipTextColor);
                    chipBackgroundColor = ContextCompat.getColor(itemView.getContext(), R.color.defaultChipBackgroundTint);
                    break;
            }

            chip.setTextColor(chipTextColor);
            chip.setChipBackgroundColor(ColorStateList.valueOf(chipBackgroundColor));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), DetailTask.class);
                    intent.putExtra("task_id", taskData.getTaskId());
                    intent.putExtra("task_name", taskData.getName());
                    intent.putExtra("task_description", taskData.getDescription());
                    intent.putExtra("task_priority", taskData.getPriority());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
