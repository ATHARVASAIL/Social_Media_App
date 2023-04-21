package com.example.social_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register_Activity extends AppCompatActivity {

    EditText email_ET,password_ET,confirm_pass_ET;
    Button register_btn,login_btn;
    CheckBox checkBox;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email_ET=findViewById(R.id.Register_Email);
        password_ET= findViewById(R.id.Register_Password);
        confirm_pass_ET=findViewById(R.id.Register_ConfirmPassword);
        register_btn=findViewById(R.id.Register_button);
        login_btn=findViewById(R.id.signup_to_login);
        checkBox=findViewById(R.id.Register_checkBox);
        progressBar=findViewById(R.id.Register_progressbar_login);
        mAuth=FirebaseAuth.getInstance();
        password_ET.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirm_pass_ET.setTransformationMethod(PasswordTransformationMethod.getInstance());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    password_ET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirm_pass_ET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    password_ET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirm_pass_ET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_ET.getText().toString();
                String password=password_ET.getText().toString();
                String confirm_password=confirm_pass_ET.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirm_password) )
                {
                    if (password.equals(confirm_password)){
                        progressBar.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    sendtoMain();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                                else{
                                    String error=task.getException().getMessage();
                                    Toast.makeText(Register_Activity.this, "Error :"+error, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                    else{
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(Register_Activity.this, "password and confirm password is not matching", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Register_Activity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();

                }
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Activity.this,Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void sendtoMain() {
        Intent intent = new Intent(Register_Activity.this,MainActivity.class);
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