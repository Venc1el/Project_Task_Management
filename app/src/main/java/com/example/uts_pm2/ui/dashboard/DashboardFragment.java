package com.example.uts_pm2.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pm2.PrefsManager;
import com.example.uts_pm2.R;
import com.example.uts_pm2.adapter.ProjectAdapter;
import com.example.uts_pm2.data.ProjectData;
import com.example.uts_pm2.data.UserData;
import com.example.uts_pm2.database.DBConnect;
import com.example.uts_pm2.ui.login.Login;
import com.example.uts_pm2.ui.project.CreateProject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DashboardFragment extends Fragment implements BottomSheetChangeAvatar.OnAvatarChangedListener {
    private PrefsManager prefsManager;
    private DBConnect db;
    private ProjectAdapter projectAdapter;
    private TextView tvFullName;
    private ImageView avatarProfile, buttonLogout;
    private FloatingActionButton buttonCreateProject;
    private RecyclerView recyclerView;
    private int userId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewProject);
        tvFullName = root.findViewById(R.id.usernameTextView);
        avatarProfile = root.findViewById(R.id.avatarImageView);
        buttonLogout = root.findViewById(R.id.logoutImageView);
        prefsManager = new PrefsManager(getContext());
        userId = prefsManager.getUserId();

        UserData userData = prefsManager.getUserData();

        if (userData != null) {
            int avatar = getResources().getIdentifier(userData.getAvatarUrl(), "drawable", getContext().getPackageName());
            tvFullName.setText("Hi, " + userData.getFullName());
            avatarProfile.setImageResource(avatar);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = new DBConnect(getContext());
        List<ProjectData> projectDataList = db.getAllProjectData();
        projectAdapter = new ProjectAdapter(projectDataList);
        recyclerView.setAdapter(projectAdapter);

        buttonCreateProject = root.findViewById(R.id.buttonCreateProject);
        buttonCreateProject.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), CreateProject.class);
            startActivity(intent);
        });

        avatarProfile.setOnClickListener(view -> showAvatarChangeBottomSheet());

        buttonLogout.setOnClickListener(view -> {
            logout();
            redirectToLogin();
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
        updateAvatar();
    }

    private void refreshData() {
        List<ProjectData> projectDataList = db.getAllProjectData();
        projectAdapter = new ProjectAdapter(projectDataList);
        recyclerView.setAdapter(projectAdapter);
    }

    private void logout() {
        db.markUserLoggedOut();
    }

    private void redirectToLogin() {
        Intent intent = new Intent(getContext(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private void showAvatarChangeBottomSheet(){
        BottomSheetChangeAvatar bottomSheetChangeAvatar = new BottomSheetChangeAvatar(userId);
        bottomSheetChangeAvatar.setOnAvatarChangedListener(this);
        bottomSheetChangeAvatar.show(getChildFragmentManager(), bottomSheetChangeAvatar.getTag());
    }

    private void updateAvatar() {
        UserData userData = prefsManager.getUserData();
        if (userData != null) {
            int avatar = getResources().getIdentifier(userData.getAvatarUrl(), "drawable", getContext().getPackageName());
            avatarProfile.setImageResource(avatar);
        }
    }

    @Override
    public void onAvatarChanged(String avatarUrl) {
        int avatar = getResources().getIdentifier(avatarUrl, "drawable", getContext().getPackageName());
        avatarProfile.setImageResource(avatar);

        UserData userData = prefsManager.getUserData();
        if (userData != null) {
            userData.setAvatarUrl(avatarUrl);
            prefsManager.setUserData(userData);
        }

        db.updateUserAvatar(userId, avatarUrl);
        updateAvatarInProjectAdapter(avatarUrl);
    }

    private void updateAvatarInProjectAdapter(String newAvatarUrl) {
        for (int i = 0; i < projectAdapter.getItemCount(); i++) {
            if (projectAdapter.getProjectDataList().get(i).getUserId() == userId) {
                projectAdapter.updateAvatar(newAvatarUrl, i);
            }
        }
    }
}

