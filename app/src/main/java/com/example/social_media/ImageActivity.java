package com.example.social_media;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView ;
    TextView textView ;
    Button btnEdit, btnDel ;
    DocumentReference documentReference;
    //try start
//    StorageReference storageReference;
//    FirebaseDatabase database=FirebaseDatabase.getInstance();
//    DatabaseReference databaseReference;
//    ProgressBar progressBar;
//    private static final int PICK_IMAGE =1;
//    Uri imageUri;
//    UploadTask uploadTask;
//    All_User_Member member;
    //try end
    String url;
    String currentid;
    FirebaseFirestore db =FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

//        member = new All_User_Member(); //try

        imageView = findViewById(R.id.iv_expand);
        textView = findViewById(R.id.tv_name_image);
        btnEdit = findViewById(R.id.btn_edit_iv);
        btnDel = findViewById(R.id.btn_del_iv);
//        progressBar=findViewById(R.id.progressbar_ia);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentid = user.getUid();
        documentReference = db.collection("user").document(currentid);
        //try start
//        storageReference= FirebaseStorage.getInstance().getReference("Profile images");
//        databaseReference= database.getReference("All Users");
        //try end

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                //noinspection deprecation
//                startActivityForResult(intent,PICK_IMAGE);
//                uploadData();
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.getResult().exists()){
                    String name = task.getResult().getString("name");
                    url = task.getResult().getString("url");

                    Picasso.get().load(url).into(imageView);
                    textView.setText(name);
                }else {
                    Toast.makeText(ImageActivity.this, "No profile", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




}