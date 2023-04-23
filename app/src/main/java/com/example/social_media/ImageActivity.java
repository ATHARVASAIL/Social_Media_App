package com.example.social_media;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    Button btnEdit, btnDel;
    DocumentReference documentReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference databaseReference;
    private static final int PICK_IMAGE =1;

    String url;
    String currentid;
    All_User_Member member;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(R.id.iv_expand);
        textView = findViewById(R.id.tv_name_image);
        btnEdit = findViewById(R.id.btn_edit_iv);
        btnDel = findViewById(R.id.btn_del_iv);
        member = new All_User_Member();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentid = user.getUid();
        documentReference = db.collection("user").document(currentid);
        databaseReference = database.getReference("All Users");

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete the user's profile picture from Firebase Storage
                FirebaseStorage.getInstance().getReferenceFromUrl(url).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // On success, update the user's document in Firestore to remove the URL
                                documentReference.update("url", FieldValue.delete())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // On success, display a toast message and finish the activity
                                                
                                                Toast.makeText(ImageActivity.this, "Profile picture deleted", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // On failure, display an error message
                                                Toast.makeText(ImageActivity.this, "Failed to delete profile picture", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // On failure, display an error message
                                Toast.makeText(ImageActivity.this, "Failed to delete profile picture", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            uploadImage(imageUri);
        }
    }
    private void uploadImage(Uri imageUri) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Profile images");
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

        fileReference.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();

                    updateProfileImage(downloadUri);
//                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                    String currentUserUid = currentUser.getUid();
//                    DatabaseReference profile_ref = FirebaseDatabase.getInstance().getReference("All Users").child(currentUserUid).child("url");
//                    profile_ref.setValue(imageUri.toString());
                } else {
                    Toast.makeText(ImageActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void updateProfileImage(Uri downloadUri) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentId = user.getUid();
        DocumentReference documentReference = db.collection("user").document(currentId);

        Map<String, Object> updates = new HashMap<>();
        updates.put("url", downloadUri.toString());


        databaseReference.child(currentid).child("url").setValue(downloadUri.toString());

        documentReference.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Picasso.get().load(downloadUri).into(imageView);
                Toast.makeText(ImageActivity.this, "Profile image updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.getResult().exists()) {
                    String name = task.getResult().getString("name");
                    url = task.getResult().getString("url");

                    Picasso.get().load(url).into(imageView);
                    textView.setText(name);
                } else {
                    Toast.makeText(ImageActivity.this, "No profile", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}