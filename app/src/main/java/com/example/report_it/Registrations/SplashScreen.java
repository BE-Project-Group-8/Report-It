package com.example.report_it.Registrations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.report_it.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIMEOUT=5000;
    View first,second,third,fourth,fifth,sixth,seventh;
    TextView appTitle,tagLine1,tagLine2;
    Animation topAnimation,bottomAnimation,middleAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        topAnimation= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        middleAnimation= AnimationUtils.loadAnimation(this,R.anim.middle_animation);

        first=findViewById(R.id.first_line);
        second=findViewById(R.id.second_line);
        third=findViewById(R.id.third_line);
        fourth=findViewById(R.id.fourth_line);
        fifth=findViewById(R.id.fifth_line);
        sixth=findViewById(R.id.sixth_line);
        seventh=findViewById(R.id.seventh_line);

        appTitle=findViewById(R.id.appTitle);
        tagLine1=findViewById(R.id.tagLine1);
        tagLine2=findViewById(R.id.tagLine2);

        first.setAnimation(topAnimation);
        second.setAnimation(topAnimation);
        third.setAnimation(topAnimation);
        fourth.setAnimation(topAnimation);
        fifth.setAnimation(topAnimation);
        sixth.setAnimation(topAnimation);
        seventh.setAnimation(topAnimation);
        appTitle.setAnimation(middleAnimation);
        tagLine1.setAnimation(bottomAnimation);
        tagLine2.setAnimation(bottomAnimation);

    }
    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    Intent intent=new Intent(SplashScreen.this,HomePage.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_TIMEOUT);
        }
        else
        {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    Intent intent=new Intent(SplashScreen.this,LoginPage.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_TIMEOUT);
        }
    }

}