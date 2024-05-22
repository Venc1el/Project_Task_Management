package com.example.uts_pm2.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.uts_pm2.R;
import com.example.uts_pm2.database.DBConnect;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class BottomSheetChangeAvatar extends BottomSheetDialogFragment {

    private CircleImageView selectedImageView = null;
    private OnAvatarChangedListener onAvatarChangedListener;
    private MaterialButton ubahButton;
    private int userId;

    public BottomSheetChangeAvatar(int userId){
        this.userId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_avatar_bottom_sheet, container, false);

        ubahButton = view.findViewById(R.id.btnUbah);

        CircleImageView[] imageViews = new CircleImageView[10];
        imageViews[0] = view.findViewById(R.id.toon_1);
        imageViews[1] = view.findViewById(R.id.toon_2);
        imageViews[2] = view.findViewById(R.id.toon_3);
        imageViews[3] = view.findViewById(R.id.toon_4);
        imageViews[4] = view.findViewById(R.id.toon_5);
        imageViews[5] = view.findViewById(R.id.toon_6);
        imageViews[6] = view.findViewById(R.id.toon_7);
        imageViews[7] = view.findViewById(R.id.toon_8);
        imageViews[8] = view.findViewById(R.id.toon_9);
        imageViews[9] = view.findViewById(R.id.toon_10);

        for (CircleImageView imageView : imageViews) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImageView((CircleImageView) v);
                }
            });
        }

        ubahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImageView != null) {
                    String drawableName = getResources().getResourceEntryName(selectedImageView.getId());
                    addAvatarToDatabase(drawableName);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void setOnAvatarChangedListener(OnAvatarChangedListener onAvatarChangedListener) {
        this.onAvatarChangedListener = onAvatarChangedListener;
    }

    private void selectImageView(CircleImageView imageView) {
        if (selectedImageView != null) {
            selectedImageView.setBorderWidth(0);
        }

        selectedImageView = imageView;

        selectedImageView.setBorderWidth(5);
        selectedImageView.setBorderColor(getResources().getColor(R.color.completed));

        String drawableName = getResources().getResourceEntryName(selectedImageView.getId());
    }

    private void addAvatarToDatabase(String avatarUrl){
        DBConnect db = new DBConnect(getContext());
        db.updateUserAvatar(userId, avatarUrl);

        if (onAvatarChangedListener != null){
            onAvatarChangedListener.onAvatarChanged(avatarUrl);
        }
    }

    public interface OnAvatarChangedListener {
        void onAvatarChanged(String avatarUrl);
    }
}
