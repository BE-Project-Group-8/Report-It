package com.example.report_it.ContactEmergency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.report_it.R;
import com.example.report_it.Registrations.HomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmergencyContacts extends AppCompatActivity {
    private TextView tvNoContacts;
    private Button btncontact1,btncontact2,btncontact3,btncontact4,btncontact5;
    private String num;
    private FloatingActionButton addContacts;
    private static final int REQUEST_CALL=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);
        tvNoContacts = (TextView)findViewById(R.id.noContacts);
        btncontact1 = (Button)findViewById(R.id.contact1);
        btncontact2 = (Button)findViewById(R.id.contact2);
        btncontact3 = (Button)findViewById(R.id.contact3);
        btncontact4 = (Button)findViewById(R.id.contact4);
        btncontact5 = (Button)findViewById(R.id.contact5);
        addContacts = (FloatingActionButton)findViewById(R.id.btnaddcontact);
        Map<String,Object> contacts = (HashMap<String,Object>)getIntent().getSerializableExtra("Contacts Map");
        int size = contacts.size();
        List<String> contactName = new ArrayList<String>();
        List<String> phno = new ArrayList<String>();
        for (Map.Entry<String,Object> entry : contacts.entrySet())
        {
            contactName.add(entry.getKey());
            phno.add(entry.getValue().toString());
        }
        if(size==0)
        {
            tvNoContacts.setVisibility(View.VISIBLE);
        }
        else if(size==1)
        {
            btncontact1.setVisibility(View.VISIBLE);
            btncontact1.setText(contactName.get(0));
        }
        else if(size==2)
        {
            btncontact1.setVisibility(View.VISIBLE);
            btncontact1.setText(contactName.get(0));
            btncontact2.setVisibility(View.VISIBLE);
            btncontact2.setText(contactName.get(1));
        }
        else if(size==3)
        {
            btncontact1.setVisibility(View.VISIBLE);
            btncontact1.setText(contactName.get(0));
            btncontact2.setVisibility(View.VISIBLE);
            btncontact2.setText(contactName.get(1));
            btncontact3.setVisibility(View.VISIBLE);
            btncontact3.setText(contactName.get(2));
        }
        else if(size==4)
        {
            btncontact1.setVisibility(View.VISIBLE);
            btncontact1.setText(contactName.get(0));
            btncontact2.setVisibility(View.VISIBLE);
            btncontact2.setText(contactName.get(1));
            btncontact3.setVisibility(View.VISIBLE);
            btncontact3.setText(contactName.get(2));
            btncontact4.setVisibility(View.VISIBLE);
            btncontact4.setText(contactName.get(3));
        }
        else if(size>=5)
        {
            addContacts.setVisibility(View.GONE);
            btncontact1.setVisibility(View.VISIBLE);
            btncontact1.setText(contactName.get(0));
            btncontact2.setVisibility(View.VISIBLE);
            btncontact2.setText(contactName.get(1));
            btncontact3.setVisibility(View.VISIBLE);
            btncontact3.setText(contactName.get(2));
            btncontact4.setVisibility(View.VISIBLE);
            btncontact4.setText(contactName.get(3));
            btncontact5.setVisibility(View.VISIBLE);
            btncontact5.setText(contactName.get(4));
        }

        btncontact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=phno.get(0);
                makePhoneCall(num);
            }
        });
        btncontact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=phno.get(1);
                makePhoneCall(num);
            }
        });
        btncontact3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=phno.get(2);
                makePhoneCall(num);
            }
        });
        btncontact4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=phno.get(3);
                makePhoneCall(num);
            }
        });
        btncontact5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=phno.get(4);
                makePhoneCall(num);
            }
        });
        addContacts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddEmergencyContact.class));
                finish();
            }
        });

    }
    private void makePhoneCall(String number){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }
        else{
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));
            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL)
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                makePhoneCall(num);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Permission DENIED",Toast.LENGTH_SHORT).show();
            }
        }
    }
}