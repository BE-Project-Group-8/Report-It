package com.example.report_it.Report;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.report_it.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.grpc.Context;

public class multipleFileUpload extends AppCompatActivity {
    private ActionBar actionBar;
    private Button bchooseFiles,buploadFiles;
    private static final int PICK_FILE=1;
    private String name,email,locationLtLng;
    ArrayList<Uri> fileLinks=new ArrayList<Uri>();
    ArrayList<Uri> FileList=new ArrayList<Uri>();
    ProgressDialog progressDialog;
    private boolean isSuccessful=false;
    int k=0;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    public static final String TAG="TAGG",status="Under Investigation";
    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_file_upload);
        FileList.clear();
        actionBar = getSupportActionBar();
        actionBar.setTitle("Upload Files");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        bchooseFiles=findViewById(R.id.btnChooseFiles);
        buploadFiles=findViewById(R.id.btnUploadFiles);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Uploading Please Wait...");
        String userId = auth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("Users").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        name = document.get("Name").toString();
                        email = document.get("Email").toString();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "ERROR CONNECTING TO FIREBASE!", Toast.LENGTH_SHORT).show();
            }
        });
        bchooseFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"GETTING location");
                locationLtLng=locationLatLng();
                Log.e(TAG,"GOT location");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent,PICK_FILE);
            }
        });
        buploadFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String timestamp = "" + System.currentTimeMillis();
                StorageReference folder = FirebaseStorage.getInstance().getReference().child("Multiple Files/"+name+"/"+timestamp);
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("Title", "multiple " + name + " " + timestamp);
                hashMap.put("Uploaded By", "" + name);
                hashMap.put("Email", "" + email);
                hashMap.put("Location", "" + locationLtLng);
                hashMap.put("TimeStamp", "" + timestamp);
                hashMap.put("Status",""+status);
                for(int j=0;j<FileList.size();j++) {
                    Uri PerFile = FileList.get(j);
                    StorageReference filename = folder.child("file" + PerFile.getLastPathSegment());
                    filename.putFile(PerFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadUri = uriTask.getResult();
                            if (uriTask.isSuccessful()) {
                                hashMap.put("File " + Integer.toString(k + 1), String.valueOf(downloadUri));
                                k += 1;
                                if(k==FileList.size())
                                {
                                    progressDialog.dismiss();
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Evidence Photos");
                                    reference.child("multiple " + name + " " + timestamp)
                                            .setValue(hashMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(multipleFileUpload.this, "File Uploaded..", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(multipleFileUpload.this, "Failed To Upload Files", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Error Uploading Files", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //progressDialog.dismiss();
                }
            }
        });
    }

    private String locationLatLng() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(multipleFileUpload.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(multipleFileUpload.this,
                                    Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );
                            Toast.makeText(getApplicationContext(), "Getting Location", Toast.LENGTH_SHORT).show();
                            locationLtLng = "https://maps.google.com/?q=" + addresses.get(0).getLatitude() + "," + addresses.get(0).getLongitude();

                            Toast.makeText(getApplicationContext(), locationLtLng, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            return locationLtLng;
        }
        else {
            ActivityCompat.requestPermissions(multipleFileUpload.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        //Toast.makeText(getApplicationContext(), locationLtLng, Toast.LENGTH_SHORT).show();
        return locationLtLng;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FileList.clear();
        if(requestCode==PICK_FILE){
            if(resultCode==RESULT_OK){
                if(data.getClipData()!=null){
                    int count=data.getClipData().getItemCount();
                    int i=0;
                    while(i<count){
                        Uri File=data.getClipData().getItemAt(i).getUri();
                        FileList.add(File);
                        i++;
                    }
                }
            }
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}