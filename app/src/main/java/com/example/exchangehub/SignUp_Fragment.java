package com.example.exchangehub;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp_Fragment extends Fragment implements OnClickListener {
    private static View view;
    private static EditText fullName, emailId,password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static Animation shakeAnimation;
    private static LinearLayout signupLayout;
    private FirebaseAuth firebaseAuth;
    Context thisContext;
    public SignUp_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);
        thisContext = container.getContext();
        initViews();
        setListeners();
        return view;
    }

    // Initialize all views
    private void initViews() {
        fullName = (EditText) view.findViewById(R.id.fullName);
        emailId = (EditText) view.findViewById(R.id.userEmailId);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(R.id.signUpBtn);
        login = (TextView) view.findViewById(R.id.already_user);
        signupLayout = (LinearLayout) view.findViewById(R.id.signup_layout);
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);
        firebaseAuth = FirebaseAuth.getInstance();

        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

           // login.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:

                // Call checkValidation method
                if(checkValidation())
                {
                    CommonUtil.getInstance().showHomePageFragment();
                }
                break;

            case R.id.already_user:

                // Replace login fragment
                new MainActivity().replaceLoginFragment();
                break;
        }

    }

    // Check Validation Method
    private Boolean checkValidation() {
        //
        Boolean isValid = false;

        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(CommonUtil.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0){
            signupLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required.");}

        // Check if email id valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");

            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match.");

        /*
            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please select Terms and Conditions.");
*/
            // Else do signup or do your stuff
        else {
            Toast.makeText(getActivity(), "Do SignUp.", Toast.LENGTH_SHORT)
                    .show();
            firebaseAuth.createUserWithEmailAndPassword(getEmailId,getPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(getActivity(),"Register Succesfully",Toast.LENGTH_SHORT).show();

                }
            });
            //
            isValid = true;
        }
        return isValid;
    }
}
