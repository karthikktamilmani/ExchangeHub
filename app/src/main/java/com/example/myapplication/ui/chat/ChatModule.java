package com.example.myapplication.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ChatModule extends AppCompatActivity {

    String targetClientMail = "";

    ArrayList<String> AllChats = new ArrayList<>();

    ArrayAdapter arrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_module);

        Intent intent = getIntent();

         targetClientMail = "Parth";//intent.getStringExtra("targetClient");
        Log.i("message",targetClientMail);

        setTitle(targetClientMail);

        ListView myChatlist = (ListView)findViewById(R.id.messageList);
        arrayAdapter = new ArrayAdapter(ChatModule.this,android.R.layout.simple_list_item_1,AllChats);

        myChatlist.setAdapter(arrayAdapter);
        parseQueryExecutor();

    }


    public void sendMessage(View view) {

        final EditText messageBox = (EditText) findViewById(R.id.messageBox);

        ParseObject message = new ParseObject("Message");

        final String messageContent = messageBox.getText().toString();

        message.put("sender", ParseUser.getCurrentUser().getUsername());
        message.put("recipient", targetClientMail);
        message.put("message", messageContent);

        messageBox.setText("");

        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {

                    AllChats.add(messageContent);

                    arrayAdapter.notifyDataSetChanged();

                }

            }
        });

    }


    public void parseQueryExecutor() {
        ParseQuery<ParseObject> Senderquery = new ParseQuery<ParseObject>("Message");

        Senderquery.whereEqualTo("sender", ParseUser.getCurrentUser().getUsername());
        Senderquery.whereEqualTo("recipient", targetClientMail);

        ParseQuery<ParseObject> Recipientquery = new ParseQuery<ParseObject>("Message");

        Recipientquery.whereEqualTo("recipient", ParseUser.getCurrentUser().getUsername());
        Recipientquery.whereEqualTo("sender", targetClientMail);

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();

        queries.add(Senderquery);
        queries.add(Recipientquery);

        ParseQuery<ParseObject> query = ParseQuery.or(queries);

        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {

                        AllChats.clear();

                        for (ParseObject message : objects) {

                            String messageContent = message.getString("message");

                            Log.i("Message", messageContent);

                            if (!message.getString("sender").equals(ParseUser.getCurrentUser().getUsername())) {

                                messageContent = " > " + messageContent;

                            }

                            Log.i("Info", messageContent);

                            AllChats.add(messageContent);

                        }

                        arrayAdapter.notifyDataSetChanged();

                    }

                }

            }
        });

    }


}
