package com.example.social_media;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    TextView sender_tv , receiver_tv;
    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public void Set_message(Application application, String message , String time ,String date,String type,String sender_uid,String receiver_uid){

        sender_tv =itemView.findViewById(R.id.sender_tv);
        receiver_tv = itemView.findViewById(R.id.receiver_tv);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = user.getUid();

        if(currentUid.equals(sender_uid)){
            receiver_tv.setVisibility(View.GONE);
            sender_tv.setText(message);

        } else if (currentUid.equals(receiver_uid)) {
            sender_tv.setVisibility(View.GONE);
            receiver_tv.setText(message);
        }

    }
}
