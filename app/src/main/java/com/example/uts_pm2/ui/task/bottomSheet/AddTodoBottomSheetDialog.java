package com.example.uts_pm2.ui.task.bottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.uts_pm2.R;
import com.example.uts_pm2.database.DBConnect;
import com.example.uts_pm2.ui.task.OnTodoAddedListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddTodoBottomSheetDialog extends BottomSheetDialogFragment {
    private TextInputEditText etToDoItem;
    private MaterialButton btnAdd;
    private int taskId;
    private OnTodoAddedListener onTodoAddedListener;

    public AddTodoBottomSheetDialog(int taskId) {
        this.taskId = taskId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_todo_bottom_sheet, container, false);

        etToDoItem = view.findViewById(R.id.etToDoItem1);
        btnAdd = view.findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> addTodosToDatabase());

        return view;
    }

    public void setOnTodoAddedListener(OnTodoAddedListener listener) {
        this.onTodoAddedListener = listener;
    }

    private void addTodosToDatabase() {
        DBConnect dbConnect = new DBConnect(getContext());

        String todoItem = etToDoItem.getText().toString();
        if (todoItem.isEmpty()) {
            etToDoItem.setError("Please enter a ToDo item");
        } else {
            dbConnect.insertTodo(taskId, todoItem);
            Toast.makeText(getContext(), "ToDo item added", Toast.LENGTH_SHORT).show();
            if (onTodoAddedListener != null) {
                onTodoAddedListener.onTodoAdded();
            }
            dismiss();
        }
    }
}
