package com.example.uts_pm2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pm2.R;
import com.example.uts_pm2.data.TodoData;
import com.example.uts_pm2.database.DBConnect;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<TodoData> todoDataList;
    private DBConnect dbConnect;

    public TodoAdapter(List<TodoData> todoDataList, DBConnect dbConnect) {
        this.todoDataList = todoDataList;
        this.dbConnect = dbConnect;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoData data = todoDataList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return todoDataList.size();
    }

    public void updateData(List<TodoData> newTodoDataList) {
        this.todoDataList = newTodoDataList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialCheckBox todoCheckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            todoCheckbox = itemView.findViewById(R.id.checkboxToDoList);
        }

        public void bind(TodoData data) {
            todoCheckbox.setText(data.getTodoName());
            todoCheckbox.setChecked(data.isCompleted());

            todoCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                dbConnect.updateTodoStatus(data.getTodoId(), isChecked);
            });
        }
    }
}
