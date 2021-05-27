package com.example.report_it.Contact_and_feedback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.report_it.R;

public class Feedback extends AppCompatActivity {
    private EditText etSenderEmail, etSenderMobileNo, etSenderSubject, etSenderFeedback;
    private ActionBar actionBar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Feedback");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        etSenderEmail=findViewById(R.id.etFeedbackEmail);
        etSenderMobileNo=findViewById(R.id.etFeedbackPhno);
        etSenderFeedback=findViewById(R.id.etFeedback);
        etSenderSubject=findViewById(R.id.etFeedbackSubject);
        Button btnSendFeedback=findViewById(R.id.btnFeedbackSend);

        btnSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

    }
    private void sendMail(){
        String message="Mobile Number: "+etSenderMobileNo.getText().toString()+"\n\n"+"Feedback: \n"+etSenderFeedback.getText().toString();
        String subject=etSenderSubject.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] recipients = "reportit@gmail.com".split(",");
        intent.putExtra(Intent.EXTRA_EMAIL,recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose Email Option:"));
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}