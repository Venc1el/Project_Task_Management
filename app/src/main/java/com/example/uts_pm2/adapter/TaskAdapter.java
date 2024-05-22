package com.example.uts_pm2.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pm2.R;
import com.example.uts_pm2.data.TaskData;
import com.example.uts_pm2.database.DBConnect;
import com.example.uts_pm2.ui.task.DetailTask;
import com.example.uts_pm2.ui.task.UpdateTask;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<TaskData> taskDataList;
    private DBConnect dbConnect;
    private Context context;

    public TaskAdapter(Context context, List<TaskData> taskDataList, DBConnect dbConnect) {
        this.context = context;
        this.taskDataList = taskDataList;
        this.dbConnect = dbConnect;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_task, parent, false);
        return new ViewHolder(view);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private Chip chip;
        private TextView title, tvAssignedBy, taskStatus;
        private ImageView avatar;
        private TaskData taskData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.taskImage);
            tvAssignedBy = itemView.findViewById(R.id.assignedByTextView);
            chip = itemView.findViewById(R.id.chipPriority);
            taskStatus = itemView.findViewById(R.id.taskStatus);
            title = itemView.findViewById(R.id.taskTitle);

            itemView.setOnCreateContextMenuListener(this);
        }

        public void bind(TaskData taskData) {
            this.taskData = taskData;
            int imageResource = context.getResources().getIdentifier(taskData.getAvatar(), "drawable", context.getPackageName());
            boolean allTodosCompleted = dbConnect.areAllTodosCompleted(taskData.getTaskId());

            if (allTodosCompleted) {
                taskStatus.setText("Completed");
                taskStatus.setTextColor(ContextCompat.getColor(context, R.color.completed));
            } else {
                taskStatus.setText("On Progress");
                taskStatus.setTextColor(ContextCompat.getColor(context, R.color.md_theme_primary));
            }
            String priority = taskData.getPriority();
            chip.setText(priority);
            title.setText(taskData.getName());
            tvAssignedBy.setText("Assigned by " + taskData.getUsername());
            avatar.setImageResource(imageResource);

            int chipTextColor;
            int chipBackgroundColor;

            switch (priority.toLowerCase()){
                case "high":
                    chipTextColor = ContextCompat.getColor(context, R.color.chipTextColorHigh);
                    chipBackgroundColor = ContextCompat.getColor(context, R.color.chipBackgroundTintHigh);
                    break;
                case "medium":
                    chipTextColor = ContextCompat.getColor(context, R.color.chipTextColorMedium);
                    chipBackgroundColor = ContextCompat.getColor(context, R.color.chipBackgroundTintMedium);
                    break;
                case "low" :
                    chipTextColor = ContextCompat.getColor(context, R.color.chipTextColorLow);
                    chipBackgroundColor = ContextCompat.getColor(context, R.color.chipBackgroundTintLow);
                    break;
                case "critical" :
                    chipTextColor = ContextCompat.getColor(context, R.color.chipTextColorCritical);
                    chipBackgroundColor = ContextCompat.getColor(context, R.color.chipBackgroundTintCritical);
                    break;
                default:
                    chipTextColor = ContextCompat.getColor(context, R.color.defaultChipTextColor);
                    chipBackgroundColor = ContextCompat.getColor(context, R.color.defaultChipBackgroundTint);
                    break;
            }

            chip.setTextColor(chipTextColor);
            chip.setChipBackgroundColor(ColorStateList.valueOf(chipBackgroundColor));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailTask.class);
                    intent.putExtra("task_id", taskData.getTaskId());
                    intent.putExtra("task_name", taskData.getName());
                    intent.putExtra("task_description", taskData.getDescription());
                    intent.putExtra("task_priority", taskData.getPriority());
                    intent.putExtra("task_username", taskData.getUsername());
                    intent.putExtra("task_avatar", taskData.getAvatar());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem delete = menu.add(0, 1, 0, "Delete");
            MenuItem update = menu.add(0, 2, 1, "Update");

            delete.setOnMenuItemClickListener(this);
            update.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case 1:
                    TaskData deletedTask = taskDataList.get(getAdapterPosition());
                    int deletedTaskPosition = getAdapterPosition();

                    dbConnect.deleteTaskWithTodos(taskData.getTaskId());
                    taskDataList.remove(deletedTaskPosition);
                    notifyItemRemoved(deletedTaskPosition);

                    showDeleteSnackbar(deletedTask, deletedTaskPosition);
                    return true;
                case 2:
                    Intent intent = new Intent(context, UpdateTask.class);
                    intent.putExtra("task_id", taskData.getTaskId());
                    intent.putExtra("task_name", taskData.getName());
                    intent.putExtra("task_description", taskData.getDescription());
                    intent.putExtra("task_priority", taskData.getPriority());
                    context.startActivity(intent);
                    return true;
                default:
                    return false;
            }
        }

        private void showDeleteSnackbar(TaskData deletedTask, int position) {
            Snackbar.make(itemView, "Task deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            taskDataList.add(position, deletedTask);
                            notifyItemInserted(position);
                        }
                    }).show();
        }
    }
}
