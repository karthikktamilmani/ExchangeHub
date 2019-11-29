package com.example.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button settingButton,saveChanges,settings;
    private ImageView imageView;
    private final int IMG_REQUEST = 1;
    Bitmap bitmap;
    private EditText description;
    private String UploadURL = " http://7d49c34b.ngrok.io/uploadImage";
    private String desc;
    private String NAME,MAIL;
    private String UserID,DESC,IMAGE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingButton = (Button)findViewById(R.id.SettingsButton);
        saveChanges = (Button)findViewById(R.id.saveChanges);
        imageView = (ImageView)findViewById(R.id.ProfileimageView);
        description = (EditText)findViewById(R.id.DescriptionEditText);

        populateData();
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveSettings();

                Intent i = new Intent(getApplicationContext(),ProfileSettings.class);
                Toast.makeText(getApplicationContext(),MAIL,Toast.LENGTH_LONG).show();
                i.putExtra("email",MAIL);
                i.putExtra("name",NAME);
                String userID = "1";
                i.putExtra("userID",userID);
                startActivity(i);

            }
        });
        //Retrieve UserID from SQLite
        UserID = "1";

        imageView.setOnClickListener(this);
        saveChanges.setOnClickListener(this);

    }

    private void populateData() {
        String populateDataURL = " http://7d49c34b.ngrok.io/populateData";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, populateDataURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {

                    jsonObject = new JSONObject(response);
                    IMAGE = jsonObject.getString("image");
                    DESC = jsonObject.getString("desc");
                    //Toast.makeText(getApplicationContext(),MAIL,Toast.LENGTH_LONG).show();
                    //Log.d("STATE",name);
                    System.out.println(IMAGE);
                    Bitmap image  = StringToImage(IMAGE);
                  //  imageView.setImageBitmap(image);
                    description.setText(DESC);
                    //imageView.setVisibility(View.VISIBLE);
                    Picasso.get().load("http://7d49c34b.ngrok.io/"+IMAGE).into(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                //OBTAIN id from Database
                String userID = "1";
                //System.out.println(desc);
                params.put("userID",userID);



                return params;
            }

        };
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);

    }

    @Override
    public void onClick(View v){

        switch (v.getId())
        {
            case R.id.ProfileimageView:
                selectImage();
                break;

            case R.id.saveChanges:
                updateChanges();
                break;


        }

    }

    private void retrieveSettings() {
        String settingURL = " http://7d49c34b.ngrok.io/retrieveSettings";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, settingURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {

                    jsonObject = new JSONObject(response);
                    NAME = jsonObject.getString("name");
                    MAIL = jsonObject.getString("email");
                    //Toast.makeText(getApplicationContext(),MAIL,Toast.LENGTH_LONG).show();
                    //Log.d("STATE",name);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                //OBTAIN id from Database
                String userID = "1";
                //System.out.println(desc);
                params.put("userID",userID);



                return params;
            }

        };
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }


    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    private void updateChanges(){
        desc = description.getText().toString();
        if(desc.isEmpty()){
            Toast.makeText(getApplicationContext(),"Empty string",Toast.LENGTH_LONG).show();
            desc = "Empty String";
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UploadURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("response");
                    Toast.makeText(MainActivity.this,Response,Toast.LENGTH_LONG).show();
                    imageView.setImageResource(0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                System.out.println(desc);
                params.put("desc",desc);
                params.put("name","Image1");
                params.put("UserID",UserID);
                params.put("image",imageToString(bitmap));
                return params;
            }

        };
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }

    private String imageToString(Bitmap bitmap) {
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
         byte[] imgBytes = byteArrayOutputStream.toByteArray();
         return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }

    private Bitmap StringToImage(String image){
        try {
            byte[] decodedimage = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(decodedimage, 0, decodedimage.length);
            return bitmapImage;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}



