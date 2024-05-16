package com.example.uts_pm2.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pm2.R;
import com.example.uts_pm2.data.ProjectData;
import com.example.uts_pm2.database.DBConnect;
import com.example.uts_pm2.ui.project.DetailProject;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    private List<ProjectData> projectDataList;

    public ProjectAdapter(List<ProjectData> projectDataList){
        this.projectDataList = projectDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_project, parent, false);
        return new ViewHolder(view, projectDataList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProjectData project = projectDataList.get(position);
        holder.bind(project, position);
    }

    @Override
    public int getItemCount() {
        return projectDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView dotMenuMore;
        private TextView titleTextView;
        private List<ProjectData> projectDataList;

        public ViewHolder(View itemView, List<ProjectData> projectDataList) {
            super(itemView);
            this.projectDataList = projectDataList;

            titleTextView = itemView.findViewById(R.id.titleProject);
            dotMenuMore = itemView.findViewById(R.id.dotMenuMore);
        }

        public void bind(ProjectData project, int position) {
            titleTextView.setText(project.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailProject.class);
                    intent.putExtra("id_project", project.getId());
                    intent.putExtra("title", project.getTitle());
                    intent.putExtra("description", project.getDescription());
                    intent.putExtra("due_date", project.getEndDate());
                    view.getContext().startActivity(intent);
                }
            });

            dotMenuMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(itemView.getContext(), dotMenuMore);
                    popupMenu.inflate(R.menu.card_project_menu);

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getItemId() == R.id.updateProject) {
                                
                                return true;
                            } else if (menuItem.getItemId() == R.id.deleteProject) {
                                int position = getAdapterPosition();
                                if (position != RecyclerView.NO_POSITION) {
                                    ProjectData project = projectDataList.get(position);
                                    int projectId = project.getId();

                                    projectDataList.remove(position);
                                    notifyItemRemoved(position);
                                    DBConnect dbConnect = new DBConnect(itemView.getContext());
                                    dbConnect.deleteProject(projectId);
                                }
                                return true;
                            } else {
                                return false;
                            }
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }
}
