package com.example.report_it.SendSOS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.report_it.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SendSosMsg extends AppCompatActivity {
    private TextView tvNoContacts;
    private Button btncontact1, btncontact2, btncontact3, btncontact4, btncontact5;
    private String num;
    private FloatingActionButton sendToAll;
    String sos;
    private ActionBar actionBar;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sos_msg);
        actionBar = getSupportActionBar();
        actionBar.setTitle("SEND SOS");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        tvNoContacts = (TextView) findViewById(R.id.noContactsSos);
        btncontact1 = (Button) findViewById(R.id.sosContact1);
        btncontact2 = (Button) findViewById(R.id.sosContact2);
        btncontact3 = (Button) findViewById(R.id.sosContact3);
        btncontact4 = (Button) findViewById(R.id.sosContact4);
        btncontact5 = (Button) findViewById(R.id.sosContact5);
        sendToAll = (FloatingActionButton) findViewById(R.id.btnSendSosToAll);
        Map<String, Object> contacts = (HashMap<String, Object>) getIntent().getSerializableExtra("Contacts Map");
        sos = "Emergency SOS.\nThe sender has made an emergency call. You are receiving this message because you are an emergency contact.";
        int size = contacts.size();
        List<String> contactName = new ArrayList<String>();
        List<String> phno = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : contacts.entrySet()) {
            contactName.add(entry.getKey());
            phno.add(entry.getValue().toString());
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (size == 0) {
            sendToAll.setVisibility(View.GONE);
            tvNoContacts.setVisibility(View.VISIBLE);
        } else if (size == 1) {
            btncontact1.setVisibility(View.VISIBLE);
            btncontact1.setText(contactName.get(0));
        } else if (size == 2) {
            btncontact1.setVisibility(View.VISIBLE);
            btncontact1.setText(contactName.get(0));
            btncontact2.setVisibility(View.VISIBLE);
            btncontact2.setText(contactName.get(1));
        } else if (size == 3) {
            btncontact1.setVisibility(View.VISIBLE);
            btncontact1.setText(contactName.get(0));
            btncontact2.setVisibility(View.VISIBLE);
            btncontact2.setText(contactName.get(1));
            btncontact3.setVisibility(View.VISIBLE);
            btncontact3.setText(contactName.get(2));
        } else if (size == 4) {
            btncontact1.setVisibility(View.VISIBLE);
            btncontact1.setText(contactName.get(0));
            btncontact2.setVisibility(View.VISIBLE);
            btncontact2.setText(contactName.get(1));
            btncontact3.setVisibility(View.VISIBLE);
            btncontact3.setText(contactName.get(2));
            btncontact4.setVisibility(View.VISIBLE);
            btncontact4.setText(contactName.get(3));
        } else if (size >= 5) {
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
        sendToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (size == 1) {
                    num = phno.get(0);
                    SendSMS(v, num);
                }
                if (size == 2) {
                    num = phno.get(0);
                    SendSMS(v, num);
                    num = phno.get(1);
                    SendSMS(v, num);
                }
                if (size == 3) {
                    num = phno.get(0);
                    SendSMS(v, num);
                    num = phno.get(1);
                    SendSMS(v, num);
                    num = phno.get(2);
                    SendSMS(v, num);
                }
                if (size == 4) {
                    num = phno.get(0);
                    SendSMS(v, num);
                    num = phno.get(1);
                    SendSMS(v, num);
                    num = phno.get(2);
                    SendSMS(v, num);
                    num = phno.get(3);
                    SendSMS(v, num);
                }
                if (size == 5) {
                    num = phno.get(0);
                    SendSMS(v, num);
                    num = phno.get(1);
                    SendSMS(v, num);
                    num = phno.get(2);
                    SendSMS(v, num);
                    num = phno.get(3);
                    SendSMS(v, num);
                    num = phno.get(4);
                    SendSMS(v, num);
                }
            }
        });
        ActivityCompat.requestPermissions(SendSosMsg.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
        btncontact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = phno.get(0);
                SendSMS(v, num);
            }
        });
        btncontact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = phno.get(1);
                SendSMS(v, num);
            }
        });
        btncontact3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = phno.get(2);
                SendSMS(v, num);
            }
        });
        btncontact4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = phno.get(3);
                SendSMS(v, num);
            }
        });
        btncontact5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = phno.get(4);
                SendSMS(v, num);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    public void SendSMS(View view, String number) {
        if (ActivityCompat.checkSelfPermission(SendSosMsg.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if(location!=null)
                        {
                            try {
                                Geocoder geocoder = new Geocoder(SendSosMsg.this,
                                        Locale.getDefault());
                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(),location.getLongitude(),1
                                );
                                SmsManager mySmsManager = SmsManager.getDefault();
                                mySmsManager.sendTextMessage(number, null, sos, null, null);
                                Toast.makeText(getApplicationContext(),"Getting Location",Toast.LENGTH_SHORT).show();
                                sos="Their current location: https://maps.google.com/?q="+addresses.get(0).getLatitude()+","+addresses.get(0).getLongitude();
                                SmsManager mySmsManager1 = SmsManager.getDefault();
                                mySmsManager1.sendTextMessage(number, null, sos, null, null);
                                Log.d("LOG5",sos);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        } else {
            ActivityCompat.requestPermissions(SendSosMsg.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }
}