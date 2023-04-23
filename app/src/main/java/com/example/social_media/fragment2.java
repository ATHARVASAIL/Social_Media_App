package com.example.social_media;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class fragment2 extends Fragment implements View.OnClickListener{

    DatabaseReference profileRef,following_ref ,follower_ref,follow_ref;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RecyclerView recyclerView;
    SearchView searchView;

    Boolean follow_checker = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment2,container,false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        recyclerView = getActivity().findViewById(R.id.rv_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        profileRef = database.getReference("All Users");
//        follow_ref = database.getReference("Follow");
//        following_ref = follow_ref.child("Following");
//        follower_ref = follow_ref.child("Follwer");
        searchView = getActivity().findViewById(R.id.search_view_frag2);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

    }

    private void processsearch(String s) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUserUid= currentUser.getUid();
        Query search = profileRef.orderByChild("name").startAt(s).endAt(s+"\uf0ff");
        FirebaseRecyclerOptions<All_User_Member> options =
                new FirebaseRecyclerOptions.Builder<All_User_Member>()
                        .setQuery(search,All_User_Member.class)
                        .build();
        FirebaseRecyclerAdapter<All_User_Member, searchViewholder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<All_User_Member, searchViewholder>(options) {
                    @Override
                    public void onBindViewHolder(@NonNull searchViewholder holder, int position, @NonNull All_User_Member model) {
                        final String postkey = getRef(position).getKey();
                        holder.setProfileInSearch(getActivity(), model.getName(), model.getUid(), model.getProf(), model.getUrl());
                        String name =getItem(position).getName();
                        String url = getItem(position).getUrl();
                        final String user_profile = getItem(position).getUid();

//                        holder.followChecker(follwing_user,postkey);
//                        holder.viewProfile.setOnClickListener(view -> {
//                            follow_checker = true;
//                            follow_ref.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    if (follow_checker.equals(true)) {
//                                        if (snapshot.child("Following").child(currentUserUid).hasChild(follwing_user) ) {
//                                            following_ref.child(currentUserUid).child(follwing_user).removeValue();
//                                            follow_checker = false;
//                                        } else {
//                                            following_ref.child(currentUserUid).child(follwing_user).setValue(true);
//                                            follow_checker = false;
//                                        }
//                                        if (snapshot.child("Follwer").child(follwing_user).hasChild(currentUserUid)){
//                                            follower_ref.child(follwing_user).child(currentUserUid).removeValue();
//                                            follow_checker = false;
//                                        }else {
//                                            follower_ref.child(follwing_user).child(currentUserUid).setValue(true);
//                                            follow_checker = false;
//                                        }
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//
//                        });
//later use for follow button
//                                holder.sendmessagebtn.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        Intent intent = new Intent(getActivity(), MessageActivity.class);
//                                        intent.putExtra("n", name);
//                                        intent.putExtra("u", url);
//                                        intent.putExtra("uid", uid);
//                                        startActivity(intent);
//                                    }
//                                });
                        holder.viewProfile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Replace fragment 2 with ViewProfileActivity
                                Intent intent = new Intent(getActivity(),ViewProfileActivity.class);
                                intent.putExtra("user",user_profile);
                                startActivity(intent);

                            }
                        });
                    }
                    @NonNull
                    @Override
                    public searchViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.user_item, parent, false);
                        return new searchViewholder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
        public void onStart() {
            super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUserUid= currentUser.getUid();
            FirebaseRecyclerOptions<All_User_Member> options =
                    new FirebaseRecyclerOptions.Builder<All_User_Member>()
                            .setQuery(profileRef,All_User_Member.class)
                            .build();
            FirebaseRecyclerAdapter<All_User_Member, searchViewholder> firebaseRecyclerAdapter =
                    new FirebaseRecyclerAdapter<All_User_Member, searchViewholder>(options) {
                        @Override
                        public void onBindViewHolder(@NonNull searchViewholder holder, int position, @NonNull All_User_Member model) {

                            final String postkey = getRef(position).getKey();
                            holder.setProfileInSearch(getActivity(), model.getName(), model.getUid(), model.getProf(), model.getUrl());
                            final String name =getItem(position).getName();
                            final String url = getItem(position).getUrl();
                            final String user_profile = getItem(position).getUid();

//                            holder.followChecker(follwing_user,postkey);
//                            holder.viewProfile.setOnClickListener(view -> {
//                                follow_checker = true;
//                                follow_ref.addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        if (follow_checker.equals(true)) {
//                                            if (snapshot.child("Following").child(currentUserUid).hasChild(follwing_user) ) {
//                                                following_ref.child(currentUserUid).child(follwing_user).removeValue();
//                                                follow_checker = false;
//                                            } else {
//                                                following_ref.child(currentUserUid).child(follwing_user).setValue(true);
//                                                follow_checker = false;
//                                            }
//                                            if (snapshot.child("Follwer").child(follwing_user).hasChild(currentUserUid)){
//                                                follower_ref.child(follwing_user).child(currentUserUid).removeValue();
//                                                follow_checker = false;
//                                            }else {
//                                                follower_ref.child(follwing_user).child(currentUserUid).setValue(true);
//                                                follow_checker = false;
//                                            }
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });
//
//                            });

//later use for follow button
//                                holder.sendmessagebtn.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        Intent intent = new Intent(getActivity(), MessageActivity.class);
//                                        intent.putExtra("n", name);
//                                        intent.putExtra("u", url);
//                                        intent.putExtra("uid", uid);
//                                        startActivity(intent);
//                                    }
//                                });
                            holder.viewProfile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //Replace fragment 2 with ViewProfileActivity
                                    Intent intent = new Intent(getActivity(),ViewProfileActivity.class);
                                    intent.putExtra("user",user_profile);
                                    startActivity(intent);

                                }
                            });
                        }

                        @NonNull
                        @Override
                        public searchViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.user_item, parent, false);
                            return new searchViewholder(view);
                        }
                    };
            firebaseRecyclerAdapter.startListening();
            recyclerView.setAdapter(firebaseRecyclerAdapter);

        }

    @Override
    public void onClick(View view) {

    }
}
