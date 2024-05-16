package com.example.uts_pm2.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pm2.R;
import com.example.uts_pm2.adapter.ProjectAdapter;
import com.example.uts_pm2.data.ProjectData;
import com.example.uts_pm2.database.DBConnect;
import com.example.uts_pm2.ui.project.CreateProject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DashboardFragment extends Fragment {
    private ProjectAdapter projectAdapter;
    private FloatingActionButton buttonCreateProject;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewProject);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DBConnect db = new DBConnect(getContext());

        List<ProjectData> projectDataList = db.getAllProjectData();

        projectAdapter = new ProjectAdapter(projectDataList);
        recyclerView.setAdapter(projectAdapter);

        buttonCreateProject = root.findViewById(R.id.buttonCreateProject);

        buttonCreateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateProject.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        DBConnect db = new DBConnect(getContext());
        List<ProjectData> projectDataList = db.getAllProjectData();

        projectAdapter = new ProjectAdapter(projectDataList);
        recyclerView.setAdapter(projectAdapter);
    }


}
