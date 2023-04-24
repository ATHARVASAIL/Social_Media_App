package com.example.social_media;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class NotificationViewholder extends RecyclerView.ViewHolder {
//    public Object setProfileInNotification;

//    FirebaseDatabase database = FirebaseDatabase.getInstance();

//    Button accept_follow,reject_follow;
//    ImageView imageView;
//    TextView nametv,proftv;

//    boolean follow_checker=false;
//
//    DatabaseReference follow_ref,following_ref,follower_ref,request_ref;
    Button accept_follow = itemView.findViewById(R.id.follow_accept_btn);
    Button reject_follow = itemView.findViewById(R.id.follow_reject_btn);

    public NotificationViewholder(@NonNull View itemView) {
        super(itemView);
    }

    public void setProfileInNotification(FragmentActivity fragmentActivity , String name , String uid , String prof, String url ) {

//        Button accept_follow = itemView.findViewById(R.id.follow_accept_btn);
//        Button reject_follow = itemView.findViewById(R.id.follow_reject_btn);

        ImageView imageView = itemView.findViewById(R.id.iv_notification);
        TextView nametv = itemView.findViewById(R.id.name_notification_tv);
        TextView proftv = itemView.findViewById(R.id.prof_notification_tv);
            Picasso.get().load(url).into(imageView);
            nametv.setText(name);
            proftv.setText(prof);

//        accept_follow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                follow_checker = true;
//                follow_ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (follow_checker == true) {
//
//                            following_ref.child(uid).child(userid).setValue(true);
//                            follower_ref.child(userid).child(uid).setValue(true);
//                            request_ref.child(userid).child(uid).removeValue();
//                            follow_checker = false;
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//
//            }
//        });
//
//        reject_follow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                follow_checker=false;
//                follow_ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (follow_checker == false) {
//                            request_ref.child(userid).child(uid).removeValue();
//                            follow_checker = false;
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });
    }
}
