package com.example.report_it.ContactEmergency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.report_it.R;
import com.example.report_it.Registrations.HomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AddEmergencyContact extends AppCompatActivity {
    Button bAddContact;
    EditText eName,ePhno;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emergency_contact);
        bAddContact=findViewById(R.id.btnAddEmergencyContact);
        eName=findViewById(R.id.etEmergencyName);
        ePhno=findViewById(R.id.etEmergencyPhno);

        bAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID=auth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("Emergency Contacts").document(userID);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()) {
                                String userID=auth.getCurrentUser().getUid();
                                DocumentReference documentReference = fstore.collection("Emergency Contacts").document(userID);
                                Map<String,Object> contact = new HashMap<>();
                                contact.put(eName.getText().toString(),ePhno.getText().toString());
                                documentReference.set(contact,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(),"Emergency Contact Added!!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                String userID=auth.getCurrentUser().getUid();
                                DocumentReference documentReference = fstore.collection("Emergency Contacts").document(userID);
                                Map<String,Object> contact = new HashMap<>();
                                contact.put(eName.getText().toString(),ePhno.getText().toString());
                                documentReference.set(contact).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(),"Emergency Contact Added!!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Unable To Extract Data",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Intent intent1 = new Intent(getApplicationContext(),HomePage.class);
                startActivity(intent1);
                finish();
            }
        });

    }
}