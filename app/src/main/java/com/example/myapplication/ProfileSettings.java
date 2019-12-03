package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileSettings extends AppCompatActivity {
    TextView passwordUpdateLink;
    private EditText name,email;
    private String NAME,EMAIL;
    private String UserID;
    private Button logOutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);



        passwordUpdateLink = (TextView)findViewById(R.id.PasswordTextLink);
        name = (EditText)findViewById(R.id.NameEditText);
        email = (EditText)findViewById(R.id.EmailEditText);
        logOutBtn = (Button) findViewById(R.id.LogoutLink);
        //Extract UserID from Database
        UserID = "1";
        passwordUpdateLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PasswordChangeActivity.class);
                i.putExtra("UserID",UserID);
                startActivity(i);
            }
        });
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
             NAME =  extras.getString("name");
             EMAIL = extras.getString("email");

        }

        //
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtil.getInstance().logoutApp();
                //
                Intent i = new Intent(getApplicationContext(), MainActivityStart.class);
                startActivity(i);
            }
        });



        //name.setText(NAME,TextView.BufferType.EDITABLE);
        name.setText(NAME);
        email.setText(EMAIL);
       // Toast.makeText(getApplicationContext(),NAME,Toast.LENGTH_LONG).show();
    }
}
