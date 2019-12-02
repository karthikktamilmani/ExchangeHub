package com.example.myapplication.ui.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.MySingleton;
import com.example.myapplication.ProfileSettings;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment implements View.OnClickListener
{

    private Button settingButton,saveChanges,settings;
    private ImageView imageView;
    private final int IMG_REQUEST = 1;
    Bitmap bitmap;
    private EditText description;
    private String UploadURL = "http://e8c4adf1.ngrok.io/uploadImage";
    private String desc;
    private String NAME,MAIL;
    private String UserID;
    public View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);

        settingButton = view.findViewById(R.id.SettingsButton);
        saveChanges = view.findViewById(R.id.saveChanges);
        imageView = view.findViewById(R.id.ProfileimageView);
        description = view.findViewById(R.id.DescriptionEditText);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveSettings();

                Intent i = new Intent(getActivity().getApplicationContext(), ProfileSettings.class);
                Toast.makeText(getActivity().getApplicationContext(),MAIL,Toast.LENGTH_LONG).show();
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

        return view;
    }


    @Override
    public void onClick(View v){

        switch (v.getId())
        {
            /*case R.id.ProfileimageView:
                selectImage();
                break;

            case R.id.saveChanges:
                updateChanges();
                break;*/


        }

    }

    private void retrieveSettings() {
        String settingURL = "http://e8c4adf1.ngrok.io/retrieveSettings";
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
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==IMG_REQUEST && resultCode==getActivity().RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path);
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
            Toast.makeText(getActivity().getApplicationContext(),"Empty string",Toast.LENGTH_LONG).show();
            desc = "Empty String";
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UploadURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("response");
                    Toast.makeText(getActivity(),Response,Toast.LENGTH_LONG).show();
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
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
}
