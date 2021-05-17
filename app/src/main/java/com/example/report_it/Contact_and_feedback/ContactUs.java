package com.example.report_it.Contact_and_feedback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.report_it.R;

public class ContactUs extends AppCompatActivity {
    private TextView tvWebsite,tAppSupportContactNum,tAppAdvertContactNum,tvAppSupportContactEmail,tvAppAdvertContactEmail;
    private static final int REQUEST_CALL=1;
    String num;
    private ActionBar actionBar;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Contact Us");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        tvWebsite=findViewById(R.id.tvWebsitelink);
        tAppSupportContactNum=findViewById(R.id.tvAppSupportContactNum);
        tAppAdvertContactNum=findViewById(R.id.tvAppAdvertContactNum);
        tvAppAdvertContactEmail=findViewById(R.id.tvAppAdvertContactEmail);
        tvAppSupportContactEmail=findViewById(R.id.tvAppSupportContactEmail);
        tvWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBrowseClick(v);
            }
        });
        tAppSupportContactNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=tAppSupportContactNum.getText().toString();
                makePhoneCall(num);
            }
        });
        tAppAdvertContactNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=tAppAdvertContactNum.getText().toString();
                makePhoneCall(num);
            }
        });
        tvAppAdvertContactEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail("reportitadvert@gmail.com");
            }
        });
        tvAppSupportContactEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail("reportit@gmail.com");
            }
        });

    }
    public void onBrowseClick(View v) {
        String url = "https://github.com/Irfan7014/Report-It";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);

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

    private void sendMail(String emailAddress){
        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] recipients = emailAddress.split(",");
        intent.putExtra(Intent.EXTRA_EMAIL,recipients);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose Email Option:"));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL)
        {
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
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