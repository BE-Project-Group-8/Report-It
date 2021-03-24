package com.example.report_it.Registrations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.report_it.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity {
    private ImageView iLogoLoginPage;
    private Button bLogin,bSignUp;
    private EditText eEmail,ePassword;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    private boolean isValid = false;
    private ImageView title;
    private Map<String,Object> contacts = new HashMap<String,Object>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        title=(ImageView)findViewById(R.id.tTitle);
        eEmail = (EditText)findViewById(R.id.etLoginEmail);
        ePassword=(EditText)findViewById(R.id.epLoginPassword);
        bLogin=(Button)findViewById(R.id.btnLogin);
        bSignUp=(Button)findViewById(R.id.btnSignUp);
        iLogoLoginPage=(ImageView)findViewById(R.id.imgLogoLoginPage);
        fAuth=FirebaseAuth.getInstance();

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eEmail.getText().toString().isEmpty())
                {
                    eEmail.setError("Enter Email");
                    return;
                }
                if(ePassword.getText().toString().isEmpty())
                {
                    ePassword.setError("Enter Password");
                    return;
                }
                fAuth.signInWithEmailAndPassword(eEmail.getText().toString().trim(),ePassword.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent1 = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(intent1);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginPage.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        bSignUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(LoginPage.this,"Let us sign-up",Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });
        iLogoLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),HomePage.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),HomePage.class));
            finish();
        }
    }
}