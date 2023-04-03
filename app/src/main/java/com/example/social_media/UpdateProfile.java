package com.example.social_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.squareup.picasso.Picasso;

public class UpdateProfile extends AppCompatActivity {

    EditText etname,etBio,etProfession,etEmail,etPhone;
    Button button;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    DocumentReference documentReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String currentuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentuid = user.getUid();
        documentReference=db.collection("user").document(currentuid);

        etBio = findViewById(R.id.et_bio_up);
        etEmail = findViewById(R.id.et_email_up);
        etname = findViewById(R.id.et_name_up);
        etProfession = findViewById(R.id.et_profession_up);
        etPhone = findViewById(R.id.et_Phone_No_up);
        button = findViewById(R.id.btn_up);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.getResult().exists()){
                            String nameResult = task.getResult().getString("name");
                            String bioResult = task.getResult().getString("bio");
                            String emailResult = task.getResult().getString("email");
                            String phoneResult = task.getResult().getString("phone_no");
                            String url = task.getResult().getString("url");
                            String profResult = task.getResult().getString("prof");

                            etname.setText(nameResult);
                            etBio.setText(bioResult);
                            etEmail.setText(emailResult);
                            etPhone.setText(phoneResult);
                            etProfession.setText(profResult);
                        }else {
                            Toast.makeText(UpdateProfile.this, "No Profile", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void updateProfile() {

        String name = etname.getText().toString();
        String bio = etBio.getText().toString();
        String prof = etProfession.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();

        final DocumentReference sDoc = db.collection("user").document(currentuid);
        db.runTransaction(new Transaction.Function<Void>() {
                    @Override
                    public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
//                        DocumentSnapshot snapshot = transaction.get(sfDocRef);
                        transaction.update(sDoc, "name", name);
                        transaction.update(sDoc, "prof", prof);
                        transaction.update(sDoc, "email",email);
                        transaction.update(sDoc, "phone_no", phone);
                        transaction.update(sDoc,"bio",bio);


                        // Success
                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateProfile.this, "Updated", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfile.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}