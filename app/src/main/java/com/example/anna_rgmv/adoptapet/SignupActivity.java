package com.example.anna_rgmv.adoptapet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    Button buttonSubmit;
    EditText etPhone,etName,etPassword,etMail;
    Pattern patternEmail,patternPass;
    Matcher matcherEmail,matcherPass;
    ParseUser user;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,20}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        buttonSubmit=(Button)findViewById(R.id.submitButton);
        etMail=(EditText)findViewById(R.id.emailEditText);
        etPhone=(EditText)findViewById(R.id.phoneEditText);
        etPassword=(EditText)findViewById(R.id.passwordEditText);
        etName=(EditText)findViewById(R.id.nameEditText);

    }

    public void tryToSubmit(View view){
        //Pattern email and password
        patternEmail = Pattern.compile(EMAIL_PATTERN);
        patternPass = Pattern.compile(EMAIL_PATTERN);

        //Match email and password
        matcherEmail = patternEmail.matcher(etMail.getText().toString());
        matcherPass = patternPass.matcher(etMail.getText().toString());

        if(etMail.getText().length() == 0 && etPassword.getText().length() == 0 && etName.getText().length() == 0 && etMail.getText().length() == 0){

            Toast.makeText(this, "All fields are requested, Please fill them",Toast.LENGTH_LONG).show();
        }

        if(etMail.getText().toString().matches("")){

            Toast.makeText(this,"Email is required",Toast.LENGTH_LONG).show();

        }else if(etPassword.getText().toString().matches("")) {

            Toast.makeText(this, "Password is required", Toast.LENGTH_LONG).show();
        }
        else if(etName.getText().toString().matches("")) {
            Toast.makeText(this, "Name is required",Toast.LENGTH_LONG).show();
        }
        else if(etPhone.getText().toString().matches("")) {
            Toast.makeText(this, "Phone is required",Toast.LENGTH_LONG).show();

        }else if(!matcherEmail.matches()) {

            //TODO email check if correct
            Toast.makeText(this,"Invalid email ",Toast.LENGTH_LONG).show();

        }else if (!matcherPass.matches()){

            //TODO password checking
            //?=.*[0-9])# a digit must occur at least once
            //?=.*[a-z])# a lower case letter must occur at least once
            //?=\S+$)   # no whitespace allowed in the entire string
            //.{6,20}   # anything, at least 6 places to 20
            Toast.makeText(this,"Invalid password ",Toast.LENGTH_LONG).show();

        }else{

            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.logOut();

            user =new ParseUser();
            user.setUsername(etMail.getText().toString());
            user.setPassword(etPassword.getText().toString());
            user.put("Name",etName.getText().toString());
            user.put("Phone",etPhone.getText().toString());
            user.signUpInBackground(new SignUpCallback() {

                public void done(ParseException e) {
                    if (e == null) {
                        //success sign up move to login
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        currentUser.logOut();

                        Intent Intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(Intent);
                    } else {
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                        Toast.makeText(SignupActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        Log.i("error signup:",e.getMessage());
                    }
                }
            });

        }

    }


}