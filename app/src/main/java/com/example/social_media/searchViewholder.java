package com.example.social_media;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class searchViewholder extends RecyclerView.ViewHolder {

    TextView sendmessagebtn;

    public searchViewholder(@NonNull View itemView) {
        super(itemView);
    }

    public void setProfileInSearch(FragmentActivity fragmentActivity , String name , String uid , String prof, String url ){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        ImageView imageView = itemView.findViewById(R.id.image_profile);
        TextView nametv = itemView.findViewById(R.id.username);
        TextView proftv = itemView.findViewById(R.id.prof);
        Picasso.get().load(url).into(imageView);
        nametv.setText(name);
        proftv.setText(prof);

    }
}


