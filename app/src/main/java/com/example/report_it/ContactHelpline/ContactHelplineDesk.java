package com.example.report_it.ContactHelpline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.report_it.R;

public class ContactHelplineDesk extends AppCompatActivity {
    private String num;
    private static final int REQUEST_CALL=1;
    private Button bNationalCall,bPolice,bAmbulance,bFire,bSenior,bWomen,bDisaster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_helpline_desk);

        bNationalCall=(Button)findViewById(R.id.btnNationalHelpCall);
        bPolice=(Button)findViewById(R.id.btnPoliceCall);
        bAmbulance=(Button)findViewById(R.id.btnAmbulanceCall);
        bFire=(Button)findViewById(R.id.btnFireCall);
        bSenior=(Button)findViewById(R.id.btnSeniorCtzCall);
        bWomen=(Button)findViewById(R.id.btnWomensCall);
        bDisaster=(Button)findViewById(R.id.btnDisasterCall);


        bNationalCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                num="9819308655";
                makePhoneCall(num);
            }
        });
        bPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                num="9819308655";
                makePhoneCall(num);
            }
        });
        bAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                num="9819308655";
                makePhoneCall(num);
            }
        });
        bFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                num="9819308655";
                makePhoneCall(num);
            }
        });
        bWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                num="9819308655";
                makePhoneCall(num);
            }
        });
        bSenior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                num="9819308655";
                makePhoneCall(num);
            }
        });
        bDisaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                num="9819308655";
                makePhoneCall(num);
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