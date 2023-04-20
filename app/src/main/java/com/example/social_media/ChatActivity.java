package com.example.social_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ChatActivity extends AppCompatActivity {

    DatabaseReference profileRef;
    FirebaseDatabase  database = FirebaseDatabase.getInstance();
    RecyclerView recyclerView;
    EditText searchEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        searchEt = findViewById(R.id.search_user_chat);
        recyclerView = findViewById(R.id.rv_chat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        profileRef = database.getReference("All Users");

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String query = searchEt.getText().toString().toUpperCase();
                Query search = profileRef.orderByChild("name").startAt(query).endAt(query+"\uf0ff");

                FirebaseRecyclerOptions<All_User_Member> options =
                        new FirebaseRecyclerOptions.Builder<All_User_Member>()
                                .setQuery(search,All_User_Member.class)
                                .build();
                FirebaseRecyclerAdapter<All_User_Member,ProfileViewholder> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<All_User_Member, ProfileViewholder>(options) {
                            @Override
                            public void onBindViewHolder(@NonNull ProfileViewholder holder, int position, @NonNull All_User_Member model) {

                                final String postkey = getRef(position).getKey();
                                holder.setProfileInChat(getApplication(), model.getName(), model.getUid(), model.getProf(), model.getUrl());
                                String name =getItem(position).getName();
                                String url = getItem(position).getUrl();
                                String uid = getItem(position).getUid();

                                holder.sendmessagebtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(ChatActivity.this,MessageActivity.class);
                                        intent.putExtra("n",name);
                                        intent.putExtra("u",url);
                                        intent.putExtra("uid",uid);
                                        startActivity(intent);
                                    }
                                });

                            }

                            @NonNull
                            @Override
                            public ProfileViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.chat_profile_item,parent,false);
                                return new ProfileViewholder(view);
                            }
                        };
                        firebaseRecyclerAdapter.startListening();
                        recyclerView.setAdapter(firebaseRecyclerAdapter);

                };

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<All_User_Member> options =
                new FirebaseRecyclerOptions.Builder<All_User_Member>()
                        .setQuery(profileRef,All_User_Member.class)
                        .build();
        FirebaseRecyclerAdapter<All_User_Member,ProfileViewholder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<All_User_Member, ProfileViewholder>(options) {
                    @Override
                    public void onBindViewHolder(@NonNull ProfileViewholder holder, int position, @NonNull All_User_Member model) {

                        final String postkey = getRef(position).getKey();
                        holder.setProfileInChat(getApplication(), model.getName(), model.getUid(), model.getProf(), model.getUrl());
                        String name =getItem(position).getName();
                        String url = getItem(position).getUrl();
                        String uid = getItem(position).getUid();

                        holder.sendmessagebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Replace MessageActivity with ShowUser
                                Intent intent = new Intent(ChatActivity.this,MessageActivity.class);
                                intent.putExtra("n",name);
                                intent.putExtra("u",url);
                                intent.putExtra("uid",uid);
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProfileViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.chat_profile_item,parent,false);
                        return new ProfileViewholder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}