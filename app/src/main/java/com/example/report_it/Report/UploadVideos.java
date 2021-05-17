package com.example.report_it.Report;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.report_it.R;
import com.example.report_it.Registrations.HomePage;
import com.example.report_it.SendSOS.SendSosMsg;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class UploadVideos extends AppCompatActivity {
    private ActionBar actionBar;
    private EditText videoTitle;
    private VideoView videoView;
    private Button bUploadPickedVideo;
    private FloatingActionButton fabPickVideo;
    private static final int VIDEO_PICK_GALLERY_CODE = 100;
    private static final int VIDEO_PICK_CAMERA_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final String status="Under Investigation";
    private String[] cameraPermissions;
    private Uri videoUri = null;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    private String name, email, title, locationLtLng,phone;
    ;
    private ProgressDialog progressDialog;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_videos);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Record And Upload Videos");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
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
                        phone=document.get("Mobile").toString();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Empty Title Field", Toast.LENGTH_SHORT).show();
            }
        });
        videoTitle = findViewById(R.id.titleVideo);
        videoView = findViewById(R.id.videoView);
        bUploadPickedVideo = findViewById(R.id.btnUploadPickedVideo);
        fabPickVideo = findViewById(R.id.pickVideoFab);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait!");
        progressDialog.setMessage("Uploading Video");
        progressDialog.setCanceledOnTouchOutside(false);
        //Camera permission
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        bUploadPickedVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoTitle.setText(name);
                title = videoTitle.getText().toString().trim();
                if (TextUtils.isEmpty(title))
                    Toast.makeText(UploadVideos.this, "Enter Title!!", Toast.LENGTH_SHORT).show();
                else if (videoUri == null)
                    Toast.makeText(UploadVideos.this, "Pick A Video!!", Toast.LENGTH_SHORT).show();
                else
                    uploadVideoToFirebase();
            }
        });
        fabPickVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPickDialog();
            }
        });
    }

    private void uploadVideoToFirebase() {
        progressDialog.show();
        String timestamp = "" + System.currentTimeMillis();
        Date date= new Timestamp(Long.parseLong(timestamp));
        String filePathAndName = "Evidence Videos/" + "video_" + timestamp;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(videoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadUri = uriTask.getResult();
                        locationLtLng=locationLatLng();
                        //Toast.makeText(getApplicationContext(), "Inside-"+locationLtLng, Toast.LENGTH_SHORT).show();
                        if (uriTask.isSuccessful()) {

                            HashMap<String, Object> hashMap = new HashMap<String, Object>();
                            hashMap.put("Title", "" + title+" at "+date.toString());
                            hashMap.put("Uploaded By", "" + name);
                            hashMap.put("Email", "" + email);
                            hashMap.put("Location", "" +locationLtLng);
                            hashMap.put("TimeStamp", "" + timestamp);
                            hashMap.put("VideoUrl", "" + downloadUri);
                            hashMap.put("Status",""+status);
                            hashMap.put("Date",""+date.toString());
                            hashMap.put("Phone",""+phone);
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Evidence Videos");
                            reference.child(title+" "+timestamp)
                                    .setValue(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            Toast.makeText(UploadVideos.this,"Video Uploaded..",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(UploadVideos.this,"Failed To Upload Video",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadVideos.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void videoPickDialog() {
        locationLtLng=locationLatLng();
        String[] options = {"Camera", "Gallery","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Video From")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (i == 0) {
                            //Camera selected
                            if (!checkCameraPermission()) {
                                requestCameraPermission();
                            } else {
                                videoPickCamera();
                            }
                        } else if (i == 1) {
                            //Gallery Selected
                            videoPickGallery();
                        }
                        else{
                            dialog.dismiss();
                        }
                    }
                }).show();

    }

    private void requestCameraPermission() {
        //Request permission
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED;
        return result1 && result2;
    }

    private void videoPickGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*");
        startActivityForResult(Intent.createChooser(intent, "Select Videos"), VIDEO_PICK_GALLERY_CODE);
    }

    private void videoPickCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_PICK_CAMERA_CODE);
    }

    private void setVideoToVideoView() {
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(videoUri);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.pause();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        videoPickCamera();
                    } else {
                        Toast.makeText(this, "CAMERA & STORAGE PERMISSION ARE REQUIRED", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == VIDEO_PICK_GALLERY_CODE) {
                videoUri = data.getData();
                setVideoToVideoView();
            } else if (requestCode == VIDEO_PICK_CAMERA_CODE) {
                videoUri = data.getData();
                setVideoToVideoView();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String locationLatLng() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(UploadVideos.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(UploadVideos.this,
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
            ActivityCompat.requestPermissions(UploadVideos.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        //Toast.makeText(getApplicationContext(), locationLtLng, Toast.LENGTH_SHORT).show();
        return locationLtLng;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}