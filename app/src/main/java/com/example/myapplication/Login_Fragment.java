package com.example.myapplication;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.example.myapplication.CommonUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class Login_Fragment extends Fragment implements OnClickListener {
    private static View view;

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    private static TextInputLayout emailIdLayout,passwordLayout;
    private static String LoginURL;
    //private String LoginURL = "http://030cc8c3.ngrok.io/Login";
    private String userId;
    public Login_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
        signUp = (TextView) view.findViewById(R.id.createAccount);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);
        emailIdLayout = (TextInputLayout) view.findViewById(R.id.login_emailid_layout);
        passwordLayout = (TextInputLayout) view.findViewById(R.id.login_password_layout);
        LoginURL = getResources().getString(R.string.url)+"/Login";
        userId = null;

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                if (checkValidation())
                {
                    final String getEmailId = emailid.getText().toString();
                    final String getPassword = password.getText().toString();
                    // String userId = null;
                    /*
                    StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, LoginURL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        System.out.println("Response from server");
                                        JSONObject jsonObject = new JSONObject(response);
                                        String Response = jsonObject.getString("userid");
                                        System.out.println(Response);
                                        userId = Response;
                                        Log.i("Login_fragment","id"+userId);
                                        //Object userId = CommonUtil.getInstance().loginToAccount(getEmailId,getPassword);
                                        if( userId != null ) {
                                            Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                                            startActivity(i);
                                            SaveSharedPreference.setLoggedIn(CommonUtil.getInstance().getAppContext(), true, (String)userId);
                                        }
                                        else
                                        {
                                            //
                                            // CommonUtil.getInstance().showErrorTextLayout(emailIdLayout,"Your Email Id and password combo is wrong");
                                            new CustomToast().Show_Toast(getActivity(), view,
                                                    "Your Email Id or password is Invalid.");
                                        }
                                        //Toast.makeText(getContext(),Response,Toast.LENGTH_SHORT).show();
                                    }
                                    catch (JSONException e){
                                        e.printStackTrace();
                                    }


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.getMessage());

                        }
                    })
                    {
                        protected Map<String,String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("email",getEmailId);
                            params.put("pass",getPassword);
                            return params;
                        }
                    };
                    MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjRequest);
                    */
                    /**
                     *
                     */
                    Object userId = "1234";
                    if( userId != null ) {
                        Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        SaveSharedPreference.setLoggedIn(CommonUtil.getInstance().getAppContext(), true, (String)userId);
                    }
                    else
                    {
                        //
                        // CommonUtil.getInstance().showErrorTextLayout(emailIdLayout,"Your Email Id and password combo is wrong");
                        new CustomToast().Show_Toast(getActivity(), view,
                                "Your Email Id or password is Invalid.");
                    }
                }
                break;

            case R.id.forgot_password:

                // Replace forgot password fragment with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new ForgotPassword_Fragment(),
                                CommonUtil.ForgotPassword_Fragment).commit();
                break;
            case R.id.createAccount:

                // Replace signup frgament with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SignUp_Fragment(),
                                CommonUtil.SignUp_Fragment).commit();
                break;
        }

    }

    // Check Validation before login
    private Boolean checkValidation() {
        Boolean isValid = true;
        // Get email id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(CommonUtil.regEx);

        Matcher m = p.matcher(getEmailId);
        //
        CommonUtil.getInstance().hideErrorTextLayout(emailIdLayout,TextInputLayout.END_ICON_CLEAR_TEXT);
        CommonUtil.getInstance().hideErrorTextLayout(passwordLayout,TextInputLayout.END_ICON_PASSWORD_TOGGLE);

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            //new CustomToast().Show_Toast(getActivity(), view,
            //      "Enter both credentials.");
            if( getEmailId.equals("") || getEmailId.length() == 0 )
            {
                CommonUtil.getInstance().showErrorTextLayout(emailIdLayout,"Please enter emailId");
            }
            //
            if( getPassword.equals("") || getPassword.length() == 0 )
            {
                CommonUtil.getInstance().showErrorTextLayout(passwordLayout,"Please enter password");
            }
            isValid = false;

        }
        // Check if email id is valid or not
        else if (!m.find()) {
            isValid = false;
            CommonUtil.getInstance().showErrorTextLayout(emailIdLayout,"Your Email Id is Invalid");
            //new CustomToast().Show_Toast(getActivity(), view,
            //      "Your Email Id is Invalid.");
        }
        // Else do login and do your stuff
        else {
            Toast.makeText(getActivity(), "Do Login.", Toast.LENGTH_SHORT)
                    .show();
        }
        //
        return isValid;
    }
}

