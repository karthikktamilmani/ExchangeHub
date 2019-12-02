package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PasswordChangeActivity extends AppCompatActivity {
    String userID;
    private String Password,newpassword;
    private Button UpdateButton;
    private EditText oldPassword,newPassword,ConfirmNewPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        UpdateButton = (Button)findViewById(R.id.updatePasswordButton);
        oldPassword = (EditText)findViewById(R.id.CurrentPassword);
        newPassword = (EditText)findViewById(R.id.NewPassword);
        ConfirmNewPassword = (EditText)findViewById(R.id.ConfirmNewPassword);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            userID = bundle.getString("UserID");
            Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_LONG).show();
            retrievePassword();
        }
        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
            }
        });
    }

    private void updatePassword() {
            if(oldPassword.getText().toString().equals(Password)){

                if(newPassword.getText().toString().equals(ConfirmNewPassword.getText().toString())){
                    //Toast.makeText(getApplicationContext(),"Password can be updated",Toast.LENGTH_LONG).show();
                    updatePasswordInDatabase(newPassword.getText().toString());
                    //newpassword = newPassword.getText().toString();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Password doesn't match",Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(),"you entered wrong Password",Toast.LENGTH_LONG).show();
            }
    }

    private void updatePasswordInDatabase( String Updatedpassword) {
        newpassword = Updatedpassword;
        String UpdatePasswordURL = "http://e8c4adf1.ngrok.io/UpdatePassword";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, UpdatePasswordURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {

                    jsonObject = new JSONObject(response);
                    //NAME = jsonObject.getString("name");
                    //MAIL = jsonObject.getString("email");
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
                        params.put("Password",newpassword);

                        return params;
            }

        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void retrievePassword() {
        String settingURL = "http://e8c4adf1.ngrok.io/RetrievePassword";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, settingURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {

                    jsonObject = new JSONObject(response);
                    Password = jsonObject.getString("Password");
                    //Toast.makeText(getApplicationContext(),MAIL,Toast.LENGTH_LONG).show();
                    //Log.d("STATE",name);
                    Toast.makeText(getApplicationContext(),Password,Toast.LENGTH_LONG).show();
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
                params.put("UserID",userID);

                return params;
            }

        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
