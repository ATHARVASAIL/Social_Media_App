package com.example.social_media;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.squareup.picasso.Picasso;

public class Imagesfragment extends RecyclerView.ViewHolder {
    ImageView imageView;

    public Imagesfragment(@NonNull View itemView) {
        super(itemView);
    }

    public void SetImage(FragmentActivity activity, String name , String url, String postUri, String time, String uid, String type, String desc){


        imageView = itemView.findViewById(R.id.iv_post_ind);
        Picasso.get().load(postUri).into(imageView);

        }

    }

