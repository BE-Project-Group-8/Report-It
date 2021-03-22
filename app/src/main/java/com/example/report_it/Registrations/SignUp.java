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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {
    private Button bBackToLogin, bRegister;
    private EditText eName, eSignUpEmail, eSignUpPassword, eSignUpConfirmPassword, eAadhaar;
    FirebaseAuth fAuth;
    DatabaseReference mDatabase;
    String associatedNumber;

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
                getNumberFromAadhaar(aadhaar);
            }
        });


    }

    private boolean isValidEmail(CharSequence email) {
        if (!TextUtils.isEmpty(email)) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }
    private void getNumberFromAadhaar(String aadhaar){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Aadhaar Details").child(aadhaar);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(!snapshot.exists())
                {
                    Toast.makeText(SignUp.this, "Invalid Aadhaar Number", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),SignUp.class));
                }
                associatedNumber = snapshot.getValue().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91"+associatedNumber,
                        60, TimeUnit.SECONDS,
                        SignUp.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Intent intent = new Intent(getApplicationContext(),OtpPopUp.class);
                                intent.putExtra("Name",eName.getText().toString().trim());
                                intent.putExtra("Email",eSignUpEmail.getText().toString().trim());
                                intent.putExtra("Password",eSignUpPassword.getText().toString().trim());
                                intent.putExtra("Aadhaar",eAadhaar.getText().toString().trim());
                                intent.putExtra("Mobile",associatedNumber);
                                intent.putExtra("verificationId",verificationId);
                                startActivity(intent);
                            }
                        }
                );
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(SignUp.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(SignUp.this, "Invalid Aadhaar Number", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}