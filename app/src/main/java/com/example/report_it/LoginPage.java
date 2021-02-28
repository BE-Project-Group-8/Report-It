package com.example.report_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {
    private ImageButton bSignUp,bLogin;
    private EditText eEmail,ePassword;
    private FirebaseAuth fAuth;
    private boolean isValid = false;
    private ImageView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        title=(ImageView)findViewById(R.id.tTitle);
        eEmail = (EditText)findViewById(R.id.etLoginEmail);
        ePassword=(EditText)findViewById(R.id.epLoginPassword);
        bLogin=(ImageButton)findViewById(R.id.btnLogin);
        bSignUp=(ImageButton)findViewById(R.id.btnSignUp);
        fAuth=FirebaseAuth.getInstance();
        bLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email=eEmail.getText().toString().trim();
                String password=ePassword.getText().toString().trim();
                if(TextUtils.isEmpty(email))
                {
                    eEmail.setError("Email is required!");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    ePassword.setError("Password is required!");
                    return;
                }
                if(password.length()<6)
                {
                    ePassword.setError("Password length should be greater than 6");
                    return;
                }
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginPage.this,"User Logged-In Successfully",Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent1 =new Intent(getApplicationContext(), HomePage.class);
                            startActivity(intent1);
                        }
                        else {
                            Toast.makeText(LoginPage.this, "Incorrect Username/Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        bSignUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(LoginPage.this,"Let us sign-up",Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(),HomePage.class));
            }
        });
    }
    public void buttonSignUp(View view)
    {
        //startActivity(new Intent(getApplicationContext(),SignUp.class));
    }
}