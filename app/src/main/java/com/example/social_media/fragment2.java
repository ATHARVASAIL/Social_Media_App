package com.example.social_media;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class fragment2 extends Fragment {

    DatabaseReference profileRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RecyclerView recyclerView;
    EditText searchEt;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment2,container,false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searchEt = getActivity().findViewById(R.id.search_user);
        recyclerView = getActivity().findViewById(R.id.rv_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                Query search = profileRef.orderByChild("name").startAt(query).endAt(query + "\uf0ff");

                FirebaseRecyclerOptions<All_User_Member> options =
                        new FirebaseRecyclerOptions.Builder<All_User_Member>()
                                .setQuery(search, All_User_Member.class)
                                .build();
                FirebaseRecyclerAdapter<All_User_Member, searchViewholder> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<All_User_Member, searchViewholder>(options) {
                            @Override
                            public void onBindViewHolder(@NonNull searchViewholder holder, int position, @NonNull All_User_Member model) {

                                final String postkey = getRef(position).getKey();
                                holder.setProfileInSearch(getActivity(), model.getName(), model.getUid(), model.getProf(), model.getUrl());
                                String name = getItem(position).getName();
                                String url = getItem(position).getUrl();
                                String uid = getItem(position).getUid();
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
        });
    }
        @Override
        public void onStart() {
            super.onStart();
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
                            String name =getItem(position).getName();
                            String url = getItem(position).getUrl();
                            String uid = getItem(position).getUid();
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

}
