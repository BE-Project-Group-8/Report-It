package com.example.report_it.Report;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.report_it.R;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class UploadPhotos extends AppCompatActivity {
    private ActionBar actionBar;
    private EditText imageTitle;
    private ImageView imageView;
    private Button bUploadPickedImage;
    private FloatingActionButton fabPickImage;
    private Uri image_uri=null;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    private String name, email, title, locationLtLng;
    private ProgressDialog progressDialog;
    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photos);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Capture And Upload Photos");
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
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Empty Title Field", Toast.LENGTH_SHORT).show();
            }
        });
        imageTitle = findViewById(R.id.titleImage);
        imageView = findViewById(R.id.imageView);
        bUploadPickedImage = findViewById(R.id.btnUploadPickedImage);
        fabPickImage = findViewById(R.id.pickImageFab);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait!");
        progressDialog.setMessage("Uploading Image");
        progressDialog.setCanceledOnTouchOutside(false);
        fabPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });
        bUploadPickedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationLtLng=locationLatLng();
                title = imageTitle.getText().toString().trim();
                if (TextUtils.isEmpty(title))
                    Toast.makeText(UploadPhotos.this, "Enter Title!!", Toast.LENGTH_SHORT).show();
                else if (image_uri == null)
                    Toast.makeText(UploadPhotos.this, "Pick An Image!!", Toast.LENGTH_SHORT).show();
                else
                    uploadImageToFirebase();
            }
        });

    }

    private void imagePickDialog() {
        String[] options = {"Camera", "Gallery","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Photo From")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (i == 0) {
                            if(checkAndRequestCameraPermission())
                            {
                                openCamera();
                            }
                        } else if (i == 1) {
                            pickImageFromGallery();
                        }
                        else{
                            dialog.dismiss();
                        }
                    }
                }).show();
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,2);
    }
    private void uploadImageToFirebase() {
        progressDialog.show();
        String timestamp = "" + System.currentTimeMillis();
        String filePathAndName = "Evidence Photos/" + "photo_" + timestamp;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(image_uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadUri = uriTask.getResult();
                        if (uriTask.isSuccessful()) {

                            HashMap<String, Object> hashMap = new HashMap<String, Object>();
                            hashMap.put("Title", "" + title);
                            hashMap.put("Uploaded By", "" + name);
                            hashMap.put("Email", "" + email);
                            hashMap.put("Location", "" +locationLtLng);
                            hashMap.put("TimeStamp", "" + timestamp);
                            hashMap.put("ImageUrl", "" + downloadUri);
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Evidence Photos");
                            reference.child(title+" "+timestamp)
                                    .setValue(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            Toast.makeText(UploadPhotos.this,"Image Uploaded..",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(UploadPhotos.this,"Failed To Upload Image",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadPhotos.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==20 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            openCamera();
        }
        else
        {
            Toast.makeText(UploadPhotos.this,"Permission Denied",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case 1:
                if(resultCode==RESULT_OK)
                {
                    Uri selectedImageUri=data.getData();
                    imageView.setImageURI(selectedImageUri);
                    image_uri=selectedImageUri;
                }
                break;
            case 2:
                if(resultCode==RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Bitmap finalPhoto = (Bitmap) bundle.get("data");

                    Uri capturedImageUri=getImageUri(UploadPhotos.this,finalPhoto);
                    imageView.setImageBitmap(finalPhoto);
                    image_uri=capturedImageUri;
                }
                break;
        }
    }

    private void pickImageFromGallery()
    {
        Intent pickImage=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,1);
    }
    private boolean checkAndRequestCameraPermission(){
        if(Build.VERSION.SDK_INT>=23){
            int cameraPermission= ActivityCompat.checkSelfPermission(UploadPhotos.this, Manifest.permission.CAMERA);
            if(cameraPermission==PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(UploadPhotos.this,new String[]{Manifest.permission.CAMERA}, 20);
                return false;
            }
        }
        return true;
    }
    private String locationLatLng() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(UploadPhotos.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(UploadPhotos.this,
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
            ActivityCompat.requestPermissions(UploadPhotos.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        //Toast.makeText(getApplicationContext(), locationLtLng, Toast.LENGTH_SHORT).show();
        return locationLtLng;
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}