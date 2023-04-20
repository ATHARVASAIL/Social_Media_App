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

public class ProfileViewholder extends RecyclerView.ViewHolder {

    TextView sendmessagebtn;

    public ProfileViewholder(@NonNull View itemView) {
        super(itemView);
    }

    public void setProfileInChat(Application applicationActivity , String name , String uid , String prof, String url ){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        ImageView imageView = itemView.findViewById(R.id.iv_chat_item);
        TextView nametv = itemView.findViewById(R.id.name_chat_item_tv);
        TextView proftv = itemView.findViewById(R.id.prof_chat_item_tv);
        sendmessagebtn = itemView.findViewById(R.id.send_message_chat_item_tvbtn);

        if(userid.equals(uid)){
            Picasso.get().load(url).into(imageView);
            nametv.setText(name);
            proftv.setText(prof);
            sendmessagebtn.setVisibility(View.INVISIBLE);
        }else {
            Picasso.get().load(url).into(imageView);
            nametv.setText(name);
            proftv.setText(prof);
            sendmessagebtn.setVisibility(View.VISIBLE);
        }
    }
}

