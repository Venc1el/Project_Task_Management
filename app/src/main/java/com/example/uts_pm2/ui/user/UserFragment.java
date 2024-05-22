package com.example.uts_pm2.ui.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uts_pm2.R;

public class UserFragment extends Fragment {
    private ImageView icInstagram, icTiktok, icGithub;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        icInstagram = root.findViewById(R.id.instagramIcon);
        icTiktok = root.findViewById(R.id.tiktokIcon);
        icGithub = root.findViewById(R.id.githubIcon);

        icInstagram.setOnClickListener(v -> openUrl("https://www.instagram.com/venciel_"));
        icTiktok.setOnClickListener(v -> openUrl("https://www.tiktok.com/@venciel_"));
        icGithub.setOnClickListener(v -> openUrl("https://github.com/venc1el"));

        return root;
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
