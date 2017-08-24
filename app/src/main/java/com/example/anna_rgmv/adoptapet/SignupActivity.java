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

    Button bSubmit;
    EditText etPhone,etName,etPassword,etMail;
    Pattern patternEmail,patternPass;
    Matcher matcherEmail,matcherPass;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,20}$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        bSubmit=(Button)findViewById(R.id.submitButton);
        etMail=(EditText)findViewById(R.id.emailEditText);
        etPhone=(EditText)findViewById(R.id.phoneEditText);
        etPassword=(EditText)findViewById(R.id.passwordEditText);
        etName=(EditText)findViewById(R.id.nameEditText);



    }

    public void tryToSubmit(View view){
        patternEmail = Pattern.compile(EMAIL_PATTERN);
        matcherEmail = patternEmail.matcher(etMail.getText().toString());
        patternPass = Pattern.compile(EMAIL_PATTERN);
        matcherPass = patternPass.matcher(etMail.getText().toString());

        if(etMail.getText().toString().matches("")||etPassword.getText().toString().matches("")){
            Toast.makeText(this,"A email or password are required",Toast.LENGTH_LONG).show();
        }else if(!matcherEmail.matches()) {
            //TODO email check if correct
            Toast.makeText(this,"A invalid email ",Toast.LENGTH_LONG).show();

        }else if (!matcherPass.matches()){
            //TODO password checking
            //?=.*[0-9])# a digit must occur at least once
            //?=.*[a-z])# a lower case letter must occur at least once
            //?=\S+$)   # no whitespace allowed in the entire string
            //.{6,20}   # anything, at least 6 places to 20
            Toast.makeText(this,"A invalid password ",Toast.LENGTH_LONG).show();
        }else{
            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.logOut();
            ParseUser user =new ParseUser();
            user.setUsername(etMail.getText().toString());
            user.setPassword(etPassword.getText().toString());
            user.put("Name",etName.getText().toString());
            user.put("Phone",etPhone.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        //seccess signup move to login
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        currentUser.logOut();//
                        Intent Intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(Intent);
                    } else {
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                        Toast.makeText(SignupActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                        Log.i("error signup:",e.toString());
                    }
                }
            });

        }

    }


}