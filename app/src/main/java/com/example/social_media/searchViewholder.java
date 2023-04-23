package com.example.social_media;

import android.annotation.SuppressLint;
import android.app.Application;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

public class searchViewholder extends RecyclerView.ViewHolder {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    Button followbtn;
    TextView viewProfile;
    int followcount;

    DatabaseReference follow_ref,following_ref,follower_ref ;

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

        viewProfile = itemView.findViewById(R.id.view_profile_tvBtn);
//        followbtn = itemView.findViewById(R.id.btn_follow);

        if(userid.equals(uid)){
            Picasso.get().load(url).into(imageView);
            nametv.setText(name);
            proftv.setText(prof);
            viewProfile.setVisibility(View.INVISIBLE);
//            followbtn.setVisibility(View.INVISIBLE);
        }else {
            Picasso.get().load(url).into(imageView);
            nametv.setText(name);
            proftv.setText(prof);
            viewProfile.setVisibility(View.VISIBLE);
//            followbtn.setVisibility(View.VISIBLE);
        }
    }

//    public void followChecker(final String follwing_user,final String postkey){
////        followbtn = itemView.findViewById(R.id.btn_follow);
//        follow_ref= database.getReference("Follow");
//        following_ref = follow_ref.child("Following");
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        final String uid = currentUser.getUid();
//
//        follow_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child("Following").child(uid).hasChild(follwing_user)){
//                    followbtn.setBackgroundResource(R.drawable.button_following);
//                    followbtn.setText("UNFOLLOW");
//                    followbtn.setTextColor(Color.RED);
//
//                }else {
//                    followbtn.setBackgroundResource(R.drawable.button);
//                    followbtn.setText("FOLLOW");
//                    followbtn.setTextColor(Color.WHITE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}


