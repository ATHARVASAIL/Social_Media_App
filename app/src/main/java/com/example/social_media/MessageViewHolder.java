package com.example.social_media;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    TextView sender_tv , receiver_tv;
    ImageView iv_sender,iv_receiver;
    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public void Set_message(Application application, String message , String time ,String date,String type,String sender_uid,String receiver_uid){

        sender_tv =itemView.findViewById(R.id.sender_tv);
        receiver_tv = itemView.findViewById(R.id.receiver_tv);

        iv_receiver = itemView.findViewById(R.id.iv_receiver);
        iv_sender = itemView.findViewById(R.id.iv_sender);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = user.getUid();

        if(type.equals("text")){
            if(currentUid.equals(sender_uid)){
                receiver_tv.setVisibility(View.GONE);
                sender_tv.setText(message);

            } else if (currentUid.equals(receiver_uid)) {
                sender_tv.setVisibility(View.GONE);
                receiver_tv.setText(message);
            }
        }else if (type.equals("iv")) {
            if (currentUid.equals(sender_uid)) {
                receiver_tv.setVisibility(View.GONE);
                sender_tv.setVisibility(View.GONE);
                iv_sender.setVisibility(View.VISIBLE);
                Picasso.get().load(message).into(iv_sender);

            } else if (currentUid.equals(receiver_uid)) {
                sender_tv.setVisibility(View.GONE);
                receiver_tv.setVisibility(View.GONE);
                iv_receiver.setVisibility(View.VISIBLE);
                Picasso.get().load(message).into(iv_receiver);
            }
        }

    }
}
