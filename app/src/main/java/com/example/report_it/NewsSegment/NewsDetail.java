package com.example.report_it.NewsSegment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.report_it.R;

public class NewsDetail extends AppCompatActivity {
    private TextView tvNewsDescription, tvnewsContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        tvNewsDescription=findViewById(R.id.newsDescription);
        tvnewsContent=findViewById(R.id.newsContent);

    }
}