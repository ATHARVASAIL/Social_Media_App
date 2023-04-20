//package com.example.social_media;
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class User_List extends AppCompatActivity {
//    RecyclerView recyclerView;
//
//    DatabaseReference database;
//
//    Search_Adapter myAdapter;
//
//    ArrayList<User> list;
//
//    @Override
//
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.fragment2);
//
//        recyclerView.findViewById(R.id.search_User);
//
//        database = FirebaseDatabase.getInstance().getReference("Users");
//        recyclerView.setHasFixedSize(true);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        list = new ArrayList<>();
//
//        myAdapter = new Search_Adapter(this, list);
//
//        recyclerView.setAdapter(myAdapter);
//
//        database.addValueEventListener(new ValueEventListener() {
//
//            @Override
//
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                    User user = dataSnapshot.getValue(User.class);
//
//                    list.add(user);
//                }
//
//                myAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//
//        });
//    }
//}
