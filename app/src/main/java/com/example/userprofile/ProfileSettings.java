package com.example.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileSettings extends AppCompatActivity {
    TextView passwordUpdateLink;
    private EditText name,email;
    private String NAME,EMAIL;
    private String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        passwordUpdateLink = (TextView)findViewById(R.id.PasswordTextLink);
        name = (EditText)findViewById(R.id.NameEditText);
        email = (EditText)findViewById(R.id.EmailEditText);
        //Extract UserID from Database
        UserID = "1";
        passwordUpdateLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),PasswordChangeActivity.class);
                i.putExtra("UserID",UserID);
                startActivity(i);
            }
        });
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
             NAME =  extras.getString("name");
             EMAIL = extras.getString("email");

        }



        //name.setText(NAME,TextView.BufferType.EDITABLE);
        name.setText(NAME);
        email.setText(EMAIL);
       // Toast.makeText(getApplicationContext(),NAME,Toast.LENGTH_LONG).show();
    }
}
