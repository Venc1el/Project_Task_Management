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
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pm2.R;
import com.example.uts_pm2.data.ProjectData;
import com.example.uts_pm2.database.DBConnect;
import com.example.uts_pm2.ui.project.DetailProject;
import com.example.uts_pm2.ui.project.UpdateProject;
import com.google.android.material.snackbar.Snackbar;

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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProjectData project = projectDataList.get(position);
        holder.bind(project);
    }

    @Override
    public int getItemCount() {
        return projectDataList.size();
    }

    public List<ProjectData> getProjectDataList() {
        return projectDataList;
    }

    public void updateAvatar(String newAvatarUrl, int position) {
        projectDataList.get(position).setAvatar(newAvatarUrl);
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView dotMenuMore, imgAvatar;
        private TextView titleTextView, createdByTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.imageAvatar);
            createdByTextView = itemView.findViewById(R.id.assignedByName);
            titleTextView = itemView.findViewById(R.id.titleProject);
            dotMenuMore = itemView.findViewById(R.id.dotMenuMore);
        }

        public void bind(ProjectData project) {
            int imageResource = itemView.getContext().getResources().getIdentifier(project.getAvatar(), "drawable", itemView.getContext().getPackageName());

            titleTextView.setText(project.getTitle());
            createdByTextView.setText(project.getUsername());
            imgAvatar.setImageResource(imageResource);

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
                                Intent intent = new Intent(itemView.getContext(), UpdateProject.class);
                                intent.putExtra("id_project", project.getId());
                                intent.putExtra("title", project.getTitle());
                                intent.putExtra("description", project.getDescription());
                                intent.putExtra("due_date", project.getEndDate());
                                view.getContext().startActivity(intent);
                                return true;
                            } else if (menuItem.getItemId() == R.id.deleteProject) {
                                int position = getAdapterPosition();
                                if (position != RecyclerView.NO_POSITION) {
                                    ProjectData deletedProject = projectDataList.get(position);
                                    int deletedProjectPosition = position;

                                    projectDataList.remove(deletedProjectPosition);
                                    notifyItemRemoved(deletedProjectPosition);
                                    DBConnect dbConnect = new DBConnect(itemView.getContext());
                                    dbConnect.deleteProject(deletedProject.getId());

                                    showDeleteSnackbar(deletedProject, deletedProjectPosition);
                                }
                                return true;
                            } else {
                                return false;
                            }
                        }

                        private void showDeleteSnackbar(ProjectData deletedProject, int position) {
                            Snackbar.make(itemView, "Project deleted", Snackbar.LENGTH_LONG)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            projectDataList.add(position, deletedProject);
                                            notifyItemInserted(position);
                                        }
                                    }).show();
                        }

                    });
                    popupMenu.show();
                }
            });
        }
    }
}
