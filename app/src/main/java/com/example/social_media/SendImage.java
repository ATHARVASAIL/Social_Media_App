package com.example.social_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SendImage extends AppCompatActivity {

    String url,receiver_name,sender_uid,receiver_uid;
ImageView imageView;
Uri imageurl;
ProgressBar progressBar;
Button button;
UploadTask uploadTask;

StorageReference storageReference;
FirebaseStorage firebaseStorage;
DatabaseReference rootRef1,rootRef2;
FirebaseDatabase database = FirebaseDatabase.getInstance();
TextView textView;
private Uri uri;
MessageMember messageMember;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image);

        messageMember = new MessageMember();
        storageReference = firebaseStorage.getInstance().getReference("Message Images");

        imageView = findViewById(R.id.iv_sendImage);
        button = findViewById(R.id.btn_sendImage);
        progressBar = findViewById(R.id.pb_sendImage);
        textView = findViewById(R.id.tv_dont);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            url = bundle.getString("u");
            receiver_name = bundle.getString("n");
            receiver_uid = bundle.getString("ruid");
            sender_uid = bundle.getString("suid");

        }else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        Picasso.get().load(url).into(imageView);
        imageurl = Uri.parse(url);

        rootRef1 = database.getReference("Message").child(sender_uid).child(receiver_uid);
        rootRef2 = database.getReference("Message").child(receiver_uid).child(sender_uid);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendImage();
                textView.setVisibility(View.VISIBLE);
            }
        });
    }

    private String getFileExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType((contentResolver.getType(uri)));
    }
    private void sendImage() {
        if (imageurl != null){
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference reference = storageReference.child(System.currentTimeMillis()+"."+getFileExt(imageurl));
            uploadTask = reference.putFile(imageurl);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();

                }

            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();

                        Calendar cdate = Calendar.getInstance();
                        SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
                        final String savedate = currentdate.format(cdate.getTime());

                        Calendar ctime = Calendar.getInstance();
                        SimpleDateFormat currenttime= new SimpleDateFormat("HH:mm:ss");
                        final String savetime = currenttime.format(ctime.getTime());

                        String time = savedate + ":" + savetime;
                        messageMember.setDate(savedate);
                        messageMember.setTime(savetime);
                        messageMember.setMessage(downloadUri.toString());
                        messageMember.setReceiver_uid(receiver_uid);
                        messageMember.setSender_uid(sender_uid);
                        messageMember.setType("iv");

                        String id = rootRef1.push().getKey();
                        rootRef1.child(id).setValue(messageMember);

                        String id1 = rootRef2.push().getKey();
                        rootRef2.child(id1).setValue(messageMember);
                        progressBar.setVisibility(View.INVISIBLE);
                        textView.setVisibility(View.INVISIBLE);
                    }
                }
            });

        }else {
            Toast.makeText(this, "Please select something", Toast.LENGTH_SHORT).show();
        }
    }

}