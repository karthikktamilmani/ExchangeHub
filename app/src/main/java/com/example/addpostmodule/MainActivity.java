
//Source References: https://demonuts.com/pick-image-gallery-camera-android/ : used this source for picking Image from the source(Camera or Gallery)

package com.example.addpostmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button SelectPicturebutton;
    private ImageView imageview;

    private int GALLERYOPTION = 1, CAMERAOPTION = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SelectPicturebutton = (Button) findViewById(R.id.btn);
        imageview = (ImageView) findViewById(R.id.iv);

        SelectPicturebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromSource();
            }
        });
    }
    private void selectImageFromSource(){
        AlertDialog.Builder DialogBuilder = new AlertDialog.Builder(this);
        DialogBuilder.setTitle("Select Action");
        String[] DialogBuilderItems = {
                "Gallery",
                "Camera" };
        DialogBuilder.setItems(DialogBuilderItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Gallery();
                                break;
                            case 1:
                                Camera();
                                break;
                        }
                    }
                });
        DialogBuilder.show();
    }
    public void Gallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, GALLERYOPTION);
    }

    private void Camera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, CAMERAOPTION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERYOPTION) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);


                    ByteArrayOutputStream bytestreamFormatData = new ByteArrayOutputStream();
                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, bytestreamFormatData);
                    byte[] byteArrayData = bytestreamFormatData.toByteArray();
                    Intent intent = new Intent(this, AddPost.class);
                    intent.putExtra("image",byteArrayData);
                    startActivity(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERAOPTION) {
            Bitmap bitmapImage = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, bytestream);
            byte[] byteArrayData = bytestream.toByteArray();
            Intent intent = new Intent(this, AddPost.class);
            intent.putExtra("image",byteArrayData);
            startActivity(intent);

        }
    }





}
