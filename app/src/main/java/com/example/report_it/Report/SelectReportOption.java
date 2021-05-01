package com.example.report_it.Report;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.report_it.R;

public class SelectReportOption extends AppCompatActivity {
    private Button btnUploadVideoPage,btnUploadPhotoPage;
    private ImageView ibUploadVideoPage,ibUploadImagePage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_report_option);
        btnUploadVideoPage=findViewById(R.id.btnUploadVideoPage);
        btnUploadPhotoPage=findViewById(R.id.btnUploadImagePage);
        ibUploadVideoPage=findViewById(R.id.ibtnUploadVideoPage);
        ibUploadImagePage=findViewById(R.id.ibtnUploadImagePage);

        btnUploadVideoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(getApplicationContext(), UploadVideos.class);
                startActivity(intent1);
            }
        });
        btnUploadPhotoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(getApplicationContext(), UploadPhotos.class);
                startActivity(intent1);
            }
        });
        ibUploadVideoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(getApplicationContext(), UploadVideos.class);
                startActivity(intent1);
            }
        });
        ibUploadImagePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(getApplicationContext(), UploadPhotos.class);
                startActivity(intent1);
            }
        });
    }
}