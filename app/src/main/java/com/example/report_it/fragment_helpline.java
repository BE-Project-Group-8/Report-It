package com.example.report_it;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class fragment_helpline extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String num;
    private static final int REQUEST_CALL=1;
    private Button bNationalCall,bPolice,bAmbulance,bFire,bSenior,bWomen,bDisaster;
    public fragment_helpline() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static fragment_helpline newInstance(String param1, String param2) {
        fragment_helpline fragment = new fragment_helpline();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_helpline, container,
                false);
        bNationalCall=(Button)rootView.findViewById(R.id.btnNationalHelpCall);
        bPolice=(Button)rootView.findViewById(R.id.btnPoliceCall);
        bAmbulance=(Button)rootView.findViewById(R.id.btnAmbulanceCall);
        bFire=(Button)rootView.findViewById(R.id.btnFireCall);
        bSenior=(Button)rootView.findViewById(R.id.btnSeniorCtzCall);
        bWomen=(Button)rootView.findViewById(R.id.btnWomensCall);
        bDisaster=(Button)rootView.findViewById(R.id.btnDisasterCall);


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

        return rootView;
    }
    private void makePhoneCall(String number){
        if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
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
                Toast.makeText(getContext(),"Permission DENIED",Toast.LENGTH_SHORT).show();
            }
        }
    }
}