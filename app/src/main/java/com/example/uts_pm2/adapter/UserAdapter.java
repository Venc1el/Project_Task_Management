package com.example.uts_pm2.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pm2.R;
import com.example.uts_pm2.data.UserData;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserData> userList;
    private Context context;

    public UserAdapter(List<UserData> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserData user = userList.get(position);
        holder.nameTextView.setText(user.getFullName());
        int avatar = context.getResources().getIdentifier(user.getAvatarUrl(), "drawable", context.getPackageName());
        holder.avatarImageView.setImageResource(avatar);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<UserData> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView avatarImageView, addImageView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            addImageView = itemView.findViewById(R.id.addImageView);
        }
    }
}
