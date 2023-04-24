package com.example.social_media;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fragment3 extends Fragment {

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference follow_ref,follower_ref,following_ref,request_ref ,reference;
    Boolean followchecker = false;
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment3,container,false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();
        follow_ref = database.getReference("Follow");
        following_ref = follow_ref.child("Following");
        follower_ref = follow_ref.child("Follower");
        request_ref = follow_ref.child("Request");
        reference = request_ref.child(currentuid);
        recyclerView = getActivity().findViewById(R.id.rv_notification);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<All_User_Member> options =
                new FirebaseRecyclerOptions.Builder<All_User_Member>().setQuery(reference,All_User_Member.class).build();

        FirebaseRecyclerAdapter<All_User_Member,NotificationViewholder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<All_User_Member, NotificationViewholder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull NotificationViewholder holder, final int position, @NonNull All_User_Member model) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String currentUserid=user.getUid();
                        holder.setProfileInNotification(getActivity(), model.getName(), model.getUid(), model.getProf(), model.getUrl());

                        final String followingUserId =  getItem(position).getUid();

                        holder.accept_follow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                followchecker = true;
                                if(!TextUtils.isEmpty(currentUserid)|| !TextUtils.isEmpty(followingUserId)){
                                    follow_ref.child("Request").child(currentUserid).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(followchecker.equals(true)){
                                               if(snapshot.hasChild(followingUserId)){
                                                   following_ref.child(followingUserId).child(currentUserid).setValue(true);
                                                   follower_ref.child(currentUserid).child(followingUserId).setValue(true);
                                                   request_ref.child(currentUserid).child(followingUserId).removeValue();
                                                   followchecker = false;
                                               }else {
                                                   Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                                               }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }
                        });

                        holder.reject_follow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                followchecker=true;
                                if(!TextUtils.isEmpty(currentUserid)|| !TextUtils.isEmpty(followingUserId)){
                                    follow_ref.child("Request").child(currentUserid).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(followchecker.equals(true)){
                                                if(snapshot.hasChild(followingUserId)){
                                                    request_ref.child(currentUserid).child(followingUserId).removeValue();
                                                    followchecker = false;
                                                }else {
                                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public NotificationViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.notification_item,parent,false);
                        return new NotificationViewholder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}