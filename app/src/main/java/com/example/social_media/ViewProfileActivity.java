package com.example.social_media;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ViewProfileActivity extends AppCompatActivity {

    ImageView userProfileImage;
    TextView followers,following,user_name,user_prof,user_bio,user_email,user_phone_no;
    Button follow;
    FirebaseDatabase database  = FirebaseDatabase.getInstance();
    DatabaseReference following_ref ,follower_ref,follow_ref, request_ref , reference;

    String privacy;
    int follower_count = 0 ,following_count = 0;
    boolean follow_checker =false;

    String currentUser_name, currentUser_prof, currentUser_url;
    String name,prof,bio,email,phoneNo,url;
    String user_profile;
    All_User_Member member;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            user_profile = bundle.getString("user");
        }else {
            Toast.makeText(this, "User Missing", Toast.LENGTH_SHORT).show();
        }

        userProfileImage = findViewById(R.id.iv_view_profile);
        followers = findViewById(R.id.followers_tv_vprofile);
        following = findViewById(R.id.following_tv_vprofile);
        user_name = findViewById(R.id.tv_name_view_profile);
        user_prof = findViewById(R.id.tv_prof_view_profile);
        user_bio = findViewById(R.id.tv_bio_view_profile);
        user_email = findViewById(R.id.tv_email_view_profile);
        user_phone_no = findViewById(R.id.tv_phone_no_view_profile);
        follow = findViewById(R.id.btn_follow_view_profile);

        follow_ref = database.getReference("Follow");
        following_ref = follow_ref.child("Following");
        follower_ref = follow_ref.child("Follower");
        request_ref = follow_ref.child("Request");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserUid = currentUser.getUid();



        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                follow_checker = true;
                follow_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (follow_checker == true) {
                            if(privacy.equals("Private")){
                                if(snapshot.child("Request").child(user_profile).hasChild(currentUserUid)){
                                    request_ref.child(user_profile).child(currentUserUid).removeValue();
                                    follow_checker = false;
                                }else if (snapshot.child("Following").child(currentUserUid).hasChild(user_profile) ) {
                                    following_ref.child(currentUserUid).child(user_profile).removeValue();
                                    follow_checker = false;
                                }else{
                                    requestFollow(currentUserUid);
                                    follow_checker = false;
                                }
                                if (snapshot.child("Follower").child(user_profile).hasChild(currentUserUid)) {
                                    follower_ref.child(user_profile).child(currentUserUid).removeValue();
                                    follow_checker = false;
                                }
                            }
                            else {
                                if (snapshot.child("Following").child(currentUserUid).hasChild(user_profile)) {
                                    following_ref.child(currentUserUid).child(user_profile).removeValue();
                                    follow_checker = false;
                                } else {
                                    following_ref.child(currentUserUid).child(user_profile).setValue(true);
                                    follow_checker = false;
                                }
                                if (snapshot.child("Follower").child(user_profile).hasChild(currentUserUid)) {
                                    follower_ref.child(user_profile).child(currentUserUid).removeValue();
                                    follow_checker = false;
                                } else {
                                    follower_ref.child(user_profile).child(currentUserUid).setValue(true);
                                    follow_checker = false;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void requestFollow(String currentUserUid) {
        reference = FirebaseDatabase.getInstance().getReference("All Users");
        reference.child(currentUserUid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        currentUser_name = String.valueOf(dataSnapshot.child("name").getValue());
                        currentUser_prof = String.valueOf(dataSnapshot.child("prof").getValue());
                        currentUser_url = String.valueOf(dataSnapshot.child("url").getValue());
                    }else {
                        Toast.makeText(ViewProfileActivity.this, "user doesnot found", Toast.LENGTH_SHORT).show();
                    }

                    if (!TextUtils.isEmpty(currentUser_name) || !TextUtils.isEmpty(currentUser_prof) || !TextUtils.isEmpty(currentUser_url) || !TextUtils.isEmpty(currentUserUid) ){

                        HashMap<String,Object> m =new HashMap<String,Object>();
                        m.put("name",currentUser_name);
                        m.put("prof",currentUser_prof);
                        m.put("url",currentUser_url);
                        m.put("uid",currentUserUid);
                        request_ref.child(user_profile).child(currentUserUid).setValue(m);

                    }

                }else {
                    Toast.makeText(ViewProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        follow_ref = database.getReference("Follow");
        following_ref = follow_ref.child("Following");
        follower_ref = follow_ref.child("Follower");

        follow_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Follower").child(user_profile).exists()){
                    follower_count = (int)snapshot.child("Follower").child(user_profile).getChildrenCount();
                }else{
                    follower_count = 0;
                }
                if (snapshot.child("Following").child(user_profile).exists()){
                    following_count = (int)snapshot.child("Following").child(user_profile).getChildrenCount();
                }else {
                    following_count = 0;
                }
                followers.setText(Integer.toString(follower_count));
                following.setText(Integer.toString(following_count));
               followChecker();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserUid = currentUser.getUid();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference reference = firestore.collection("user").document(user_profile);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    name = task .getResult().getString("name");
                    prof = task.getResult().getString("prof");
                    privacy = task.getResult().getString("privacy");
                    bio = task.getResult().getString("bio");
                    email = task.getResult().getString("email");
                    phoneNo = task.getResult().getString("phone_no");
                    url = task.getResult().getString("url");
                    Picasso.get().load(url).into(userProfileImage);
                    user_name.setText(name);
                    user_bio.setText(bio);
                    user_email.setText(email);
                    user_phone_no.setText(phoneNo);
                    user_prof.setText(prof);
                    followers.setText(Integer.toString(follower_count));
                    following.setText(Integer.toString(following_count));
                    if(privacy.equals("Private")){
                    follow_ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child("Follower").child(user_profile).hasChild(currentUserUid)){
                                Picasso.get().load(url).into(userProfileImage);
                                user_name.setText(name);
                                user_bio.setText(bio);
                                user_email.setText(email);
                                user_phone_no.setText(phoneNo);
                                user_prof.setText(prof);

                            }else {
                                Picasso.get().load(url).into(userProfileImage);
                                user_name.setText(name);
                                user_bio.setText("********");
                                user_email.setText("********");
                                user_phone_no.setText("********");
                                user_prof.setText(prof);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    } else if (privacy.equals("Public")) {
                        Picasso.get().load(url).into(userProfileImage);
                        user_name.setText(name);
                        user_bio.setText(bio);
                        user_email.setText(email);
                        user_phone_no.setText(phoneNo);
                        user_prof.setText(prof);

                    }
                }else {
                    Toast.makeText(ViewProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        if(privacy!=null){
//            if(privacy.equals("Private")){
//                follow_ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.child("Follwer").child(user_profile).hasChild(currentUserUid)){
//                            Picasso.get().load(url).into(userProfileImage);
//                            user_name.setText(name);
//                            user_bio.setText(bio);
//                            user_email.setText(email);
//                            user_phone_no.setText(phoneNo);
//                            user_prof.setText(prof);
//                            followers.setText(Integer.toString(follower_count));
//                            following.setText(Integer.toString(following_count));
//                        }else {
//                            Picasso.get().load(url).into(userProfileImage);
//                            user_name.setText(name);
//                            user_bio.setText("********");
//                            user_email.setText("********");
//                            user_phone_no.setText("********");
//                            user_prof.setText(prof);
//                            followers.setText(Integer.toString(follower_count));
//                            following.setText(Integer.toString(following_count));
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//            } else if (privacy.equals("Public")) {
//                Picasso.get().load(url).into(userProfileImage);
//                user_name.setText(name);
//                user_bio.setText(bio);
//                user_email.setText(email);
//                user_phone_no.setText(phoneNo);
//                user_prof.setText(prof);
//                followers.setText(Integer.toString(follower_count));
//                following.setText(Integer.toString(following_count));
//            }else {
//                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//            }
//        }


    }

    private void followChecker() {
        follow_ref= database.getReference("Follow");
        following_ref = follow_ref.child("Following");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();

        follow_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Following").child(uid).hasChild(user_profile)){
                    follow.setBackgroundResource(R.drawable.button_following);
                    follow.setText("UNFOLLOW");
                    follow.setTextColor(Color.RED);

                } else if (snapshot.child("Request").child(user_profile).hasChild(uid)) {
                    follow.setBackgroundResource(R.drawable.button_following);
                    follow.setText("REQUESTED");
                    follow.setTextColor(Color.RED);

                } else {
                    follow.setBackgroundResource(R.drawable.button);
                    follow.setText("FOLLOW");
                    follow.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}