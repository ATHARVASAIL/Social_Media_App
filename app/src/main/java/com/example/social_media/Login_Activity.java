package com.example.social_media;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class Login_Activity extends AppCompatActivity {

    EditText email_ET,password_ET;
    Button register_btn,login_btn;
    CheckBox checkBox;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    TextView forgotpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forgotpass = findViewById(R.id.tv_forgot_password);
        email_ET=findViewById(R.id.User_Email);
        password_ET= findViewById(R.id.User_Password);
        register_btn=findViewById(R.id.login_to_signup);
        login_btn=findViewById(R.id.Login_button);
        checkBox=findViewById(R.id.login_checkBox);
        progressBar=findViewById(R.id.progressbar_login);
        mAuth=FirebaseAuth.getInstance();

        password_ET.setTransformationMethod(PasswordTransformationMethod.getInstance());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    password_ET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    password_ET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this,Register_Activity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_ET.getText().toString();
                String password=password_ET.getText().toString();
                if(!TextUtils.isEmpty(email) &&!TextUtils.isEmpty(password)) {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                sendtoMain();
                                progressBar.setVisibility(View.INVISIBLE);
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(Login_Activity.this, "Error :" + error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Login_Activity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_ET.getText().toString();
                if (!TextUtils.isEmpty(email)) {
                    AlertDialog.Builder builder =new AlertDialog.Builder(Login_Activity.this);
                    builder.setTitle("Reset Password")
                            .setMessage("Are you sure to reset password")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(Login_Activity.this, "Reset Link Sent", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Login_Activity.this, "Error" + e, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                }
                            })
                            .setNegativeButton("No", (dialogInterface, i) -> {

                            });
                    builder.create();
                    builder.show();
                }
                else {
                    Toast.makeText(Login_Activity.this, "Please enter your email address.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendtoMain() {
        Intent intent = new Intent(Login_Activity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            sendtoMain();
        }
    }
}