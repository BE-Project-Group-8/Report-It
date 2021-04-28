package com.example.report_it.Report;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.report_it.R;

public class ReportOffline extends AppCompatActivity {
    private DatePicker datePicker;
    private Button btnShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_offline);
        datePicker=findViewById(R.id.etDateOfCrime);
        btnShow=findViewById(R.id.btnShowDate);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),datePicker.getMonth()+1+" "+datePicker.getDayOfMonth()+" "+datePicker.getYear(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}