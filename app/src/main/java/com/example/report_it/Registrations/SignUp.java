package com.example.report_it.Registrations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.report_it.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    private Button bBackToLogin, bRegister;
    private EditText eName, eSignUpEmail, eSignUpPassword, eSignUpConfirmPassword, eAadhaar;
    FirebaseAuth fAuth;
    DatabaseReference mDatabase;
    private String associatedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        eName = (EditText) findViewById(R.id.etSignUpName);
        eSignUpEmail = (EditText) findViewById(R.id.etSignUpEmail);
        eSignUpPassword = (EditText) findViewById(R.id.etSignUpPassword);
        eSignUpConfirmPassword = (EditText) findViewById(R.id.etSignUpConfirmPassword);
        bBackToLogin = (Button) findViewById(R.id.btnHaveAccount);
        bRegister = (Button) findViewById(R.id.btnRegister);
        eAadhaar = (EditText) findViewById(R.id.etSignUpAadharNum);
        fAuth = FirebaseAuth.getInstance();
        bBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
                finish();
            }
        });
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = eName.getText().toString().trim();
                String email = eSignUpEmail.getText().toString().trim();
                String password = eSignUpPassword.getText().toString().trim();
                String confirmPassword = eSignUpConfirmPassword.getText().toString().trim();
                String aadhaar = eAadhaar.getText().toString().trim();
                if (fullName.isEmpty()) {
                    eName.setError("Enter Full Name");
                    return;
                }
                if (email.isEmpty()) {
                    eSignUpEmail.setError("Enter Email");
                    return;
                }
                if (password.isEmpty()) {
                    eSignUpPassword.setError("Enter Password");
                    return;
                }
                if (confirmPassword.isEmpty()) {
                    eSignUpConfirmPassword.setError("Enter Confirm Password");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    eSignUpConfirmPassword.setError("Password Does Not Match");
                    return;
                }
                if (!isValidEmail(email)) {
                    eSignUpEmail.setError("Enter Valid Email Address");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                final ArrayList<String> list=new ArrayList<String>();
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Aadhaar Details");
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        associatedNumber = snapshot.child(aadhaar).getValue().toString();
                        Toast.makeText(SignUp.this, associatedNumber, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SignUp.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(SignUp.this, "Invalid Aadhaar Number", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private boolean isValidEmail(CharSequence email) {
        if (!TextUtils.isEmpty(email)) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }
}