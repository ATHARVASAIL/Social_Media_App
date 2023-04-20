package com.example.social_media;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CommentActivity extends AppCompatActivity {
ArrayList<CommentModel> arrComments = new ArrayList<>();

    Button btn_comment;
    EditText add_comment;
    fragment4 f4= new fragment4();
    String postkey=f4.postkey;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference comment_reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        RecyclerView recyclerView = findViewById(R.id.rv_comment);
        add_comment=findViewById(R.id.et_addcomment);
        btn_comment=findViewById(R.id.btn_comment);
        comment_reference=database.getReference("Comment");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommentModel commentModel = new CommentModel();

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddComment();
            }
        });
    }

    public void AddComment() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();
        String comment = add_comment.getText().toString();
         Calendar cdate = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
        final String savedate = currentdate.format(cdate.getTime());

        Calendar ctime = Calendar.getInstance();
        SimpleDateFormat currenttime= new SimpleDateFormat("HH:mm:ss");
        final String savetime = currenttime.format(ctime.getTime());

        String time = savedate + ":" + savetime;

    }

}