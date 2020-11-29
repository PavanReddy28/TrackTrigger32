package com.example.tracktrigger32;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraSelectHH extends AppCompatActivity  {

    public static final int IMAG_PICK_CODE = 999;
    public static final int PERMISSION_CODE = 1009;

    ImageView ivCamera;
    Button btnCamGallery,btnCamSave;
    String currentPhotoPath;
    public static Uri ur;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_select_h_h);

        ivCamera = findViewById(R.id.ivCameraWork);


        btnCamGallery = findViewById(R.id.btnCamGalleryWork);
        btnCamSave = findViewById(R.id.btnCamSaveWork);

        btnCamSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendUri = new Intent();
                sendUri.putExtra("ImgUri", ur);
                sendUri.putExtra("posi",getIntent().getIntExtra("posit",0));
                setResult(RESULT_OK, sendUri);

                CameraSelectHH.this.finish();
            }
        });
        btnCamGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }else{
                        pickImageFromGallery();
                    }
                }else{
                    pickImageFromGallery();
                }
            }
        });

    }


    private void pickImageFromGallery() {
        Intent intent = new Intent((Intent.ACTION_PICK));
        intent.setType("image/*");
        startActivityForResult(intent,IMAG_PICK_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                }else{
                    Toast.makeText(this, "Permission denied..Provide access for this feature", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode == IMAG_PICK_CODE){
            ivCamera.setImageURI(data.getData());
            ur=data.getData();
        }

    }
}











