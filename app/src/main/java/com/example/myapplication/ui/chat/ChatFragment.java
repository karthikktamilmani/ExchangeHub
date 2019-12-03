package com.example.myapplication.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment implements View.OnClickListener{

    public View view;
    ArrayList<String> targetClients = new ArrayList<>();

    ArrayAdapter<String> ListHolderarrayAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chat, container, false);

        ParseUser chatclient = new ParseUser();
        ListView targetClientListView = (ListView) view.findViewById(R.id.TargetClientListview);
        String email="siddharth@gmail.com";
        String password="12345678";



        chatclient.setUsername(email);

        chatclient.setPassword(password);



        ParseUser.logInInBackground(email, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if (e == null) {

                    Log.i("Info", "user logged in");


                } else {

                    String errormessage = e.getMessage();

                    if (errormessage.toLowerCase().contains("java")) {

                        errormessage = e.getMessage().substring(e.getMessage().indexOf(" "));

                    }

                    Toast.makeText(getActivity().getApplicationContext(), errormessage, Toast.LENGTH_SHORT).show();

                }

            }
        });


        targetClientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Intent intent = new Intent(getApplicationContext())
                Intent intent = new Intent(getActivity().getApplicationContext(), ChatModule.class);
                intent.putExtra("targetClient",targetClients.get(i));
                startActivity(intent);
            }
        });
        ListHolderarrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1, targetClients);
        targetClients.clear();
        targetClientListView.setAdapter(ListHolderarrayAdapter);
        //Filter logic to Obtain Other Users
        parseQueryExecutor();

        return view;
    }

    @Override
    public void onClick(View view) {

    }



    void parseQueryExecutor(){
        ParseQuery<ParseUser> ClientFilterquery = ParseUser.getQuery();

        ClientFilterquery.whereNotEqualTo("username", "siddharth@gmail.com");

        ClientFilterquery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {

                        for (ParseUser user : objects) {

                            targetClients.add(user.getUsername());
                            System.out.println(user.getUsername());
                        }

                        ListHolderarrayAdapter.notifyDataSetChanged();

                    }

                }

            }
        });

    }


}