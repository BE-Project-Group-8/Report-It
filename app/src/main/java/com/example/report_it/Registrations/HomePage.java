package com.example.report_it.Registrations;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.report_it.ContactEmergency.EmergencyContacts;
import com.example.report_it.ContactHelpline.ContactHelplineDesk;
import com.example.report_it.Contact_and_feedback.ContactUs;
import com.example.report_it.Contact_and_feedback.Feedback;
import com.example.report_it.MissingPeopleClasses.MissingPeople;
import com.example.report_it.NearbyPlaceClasses.NearestEmergency;
import com.example.report_it.NewsSegment.NewsApp;
import com.example.report_it.R;
import com.example.report_it.Report.SelectReportOption;
import com.example.report_it.Report.multipleFileUpload;
import com.example.report_it.SendSOS.SendSosMsg;
import com.example.report_it.WantedCriminalClasses.WantedCriminal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView tVerifyEmail;
    private Button bVerifyEmail;
    private ImageView imgbtnNews, imgbtnSendSos,
            imgbtnEmergencyCall,imgbtnNearestLoc,imgbtnWantedCriminal,imgbtnMissingPeople,imgbtnHelplineDesk,
            imgbtnReportCrime,imgFeedback;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseFirestore fstore=FirebaseFirestore.getInstance();
    Map<String,Object> contacts = new HashMap<String,Object>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.reportUploadMultiple);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        imgbtnEmergencyCall=(ImageView)findViewById(R.id.imgBtnEmergency);
        imgbtnNearestLoc=(ImageView)findViewById(R.id.imgBtnNearestLoc);
        imgbtnMissingPeople=(ImageView)findViewById(R.id.imgBtnMissing);
        imgbtnWantedCriminal=(ImageView)findViewById(R.id.imgBtnWanted);
        imgbtnNews=(ImageView)findViewById(R.id.imgBtnNews);
        imgbtnHelplineDesk=(ImageView)findViewById(R.id.imgBtnHelpline);
        imgbtnSendSos=(ImageView)findViewById(R.id.imgBtnSendSOS);
        imgbtnReportCrime=(ImageView)findViewById(R.id.imgBtnReportCrime);
        imgFeedback=(ImageView)findViewById(R.id.imgFeedback);
        bVerifyEmail=(Button)findViewById(R.id.btnVerifyEmail);
        tVerifyEmail=(TextView)findViewById(R.id.tvVerifyEmail);

        imgbtnReportCrime.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(getApplicationContext(), "Report Crime", Toast.LENGTH_SHORT).show();
                 Intent intent1 =new Intent(getApplicationContext(), SelectReportOption.class);
                 startActivity(intent1);
             }
        });

        imgFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(getApplicationContext(), Feedback.class);
                startActivity(intent1);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(getApplicationContext(), multipleFileUpload.class);
                startActivity(intent1);
            }
        });
        imgbtnEmergencyCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(getApplicationContext(), EmergencyContacts.class);
                String userID=auth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("Emergency Contacts").document(userID);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists())
                                contacts = document.getData();
                            else
                                Toast.makeText(HomePage.this,"User's Emergency Contacts Does Not Exist",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(HomePage.this,"Unable To Extract Data",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                intent1.putExtra("Contacts Map",(Serializable)contacts);
                startActivity(intent1);
            }
        });

        imgbtnSendSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(getApplicationContext(), SendSosMsg.class);
                String userID=auth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("Emergency Contacts").document(userID);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists())
                                contacts = document.getData();
                            else
                                Toast.makeText(HomePage.this,"User's Emergency Contacts Does Not Exist",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(HomePage.this,"Unable To Extract Data",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                intent1.putExtra("Contacts Map",(Serializable)contacts);
                startActivity(intent1);
            }
        });


        imgbtnNearestLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(getApplicationContext(), NearestEmergency.class);
                startActivity(intent1);
            }
        });
        imgbtnMissingPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(getApplicationContext(), MissingPeople.class);
                startActivity(intent1);
            }
        });
        imgbtnWantedCriminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 =new Intent(getApplicationContext(), WantedCriminal.class);
                startActivity(intent1);
            }
        });
        imgbtnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(getApplicationContext(), NewsApp.class);
                startActivity(intent1);
            }
        });
        imgbtnHelplineDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(getApplicationContext(), ContactHelplineDesk.class);
                startActivity(intent1);
            }
        });

        //Navigation View
        navigationView.setNavigationItemSelectedListener(this);

        if(!auth.getCurrentUser().isEmailVerified()){

            bVerifyEmail.setVisibility(View.VISIBLE);
            tVerifyEmail.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Verify Email Address",Toast.LENGTH_SHORT).show();
        }
        bVerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(HomePage.this,"Verification Email Sent",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LoginPage.class));
                        finish();
                    }
                });
            }
        });
        tVerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(HomePage.this,"Verification Email Sent",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LoginPage.class));
                        finish();
                    }
                });
            }
        });
        View navHeaderView = navigationView.getHeaderView(0);
        TextView tvDisplayName = (TextView) navHeaderView.findViewById(R.id.userName);
        TextView tvDisplayEmail = (TextView) navHeaderView.findViewById(R.id.userEmail);
        String userID=auth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("Users").document(userID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        tvDisplayName.setText(document.get("Name").toString());
                        tvDisplayEmail.setText(document.get("Email").toString());
                    }
                }
                else
                {
                    Toast.makeText(HomePage.this,"Unable To Extract Data",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        MenuItem menuItem = menu.getItem(0);

        SpannableString s = new SpannableString("LOGOUT!");
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        menuItem.setTitle(s);

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
                finish();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent1;
        switch(item.getItemId())
        {
            case R.id.item_contactUs:
                intent1 =new Intent(getApplicationContext(), ContactUs.class);
                startActivity(intent1);
                break;
            case R.id.item_give_feedback:
                intent1 =new Intent(getApplicationContext(), Feedback.class);
                startActivity(intent1);
                break;
            case R.id.helpline_numbers:
                intent1=new Intent(getApplicationContext(),ContactHelplineDesk.class);
                startActivity(intent1);
                break;
            case R.id.item_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
                finish();
                break;
            case R.id.watchNews:
                startActivity(new Intent(getApplicationContext(), NewsApp.class));
                break;
        }
        return true;
    }

}