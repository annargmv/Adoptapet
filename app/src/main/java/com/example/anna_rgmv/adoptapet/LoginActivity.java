package com.example.anna_rgmv.adoptapet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {


    Button mEmailLogInButton;
    Button mEmailSignUpButton;
    AutoCompleteTextView emialText;
    EditText password;
    ImageView logo;

    public void logIn(View view) {

        if (emialText.getText().length() != 0 && password.getText().length() != 0) {

            Intent buttonIntent = new Intent(this, FindDogActivity.class);
            startActivity(buttonIntent);

        } else if ((emialText.getText().length() == 0) && (password.getText().length() == 0)) {

            Toast.makeText(getApplicationContext(), "Please enter your email and password", Toast.LENGTH_LONG).show();

        } else if ((emialText.getText().length() == 0) && (password.getText().length() != 0)) {

            Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_LONG).show();

        } else if ((emialText.getText().length() != 0) && (password.getText().length() == 0)) {

            Toast.makeText(getApplicationContext(), "Please enter your email and password", Toast.LENGTH_LONG).show();

        }


    }

    public void signUp(View view) {
        mEmailSignUpButton = (Button) findViewById(R.id.email_sign_up_button);
        Intent buttonIntent = new Intent(this, SignupActivity.class);
        startActivity(buttonIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Add setOnClickListener for the buttons

        logo = (ImageView) findViewById(R.id.logo);
        emialText = (AutoCompleteTextView) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        mEmailLogInButton = (Button) findViewById(R.id.email_login_button);

        //mEmailLogInButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Please enter your email and password", Toast.LENGTH_LONG);
////                logIn(findViewById(R.id.email_login_button));
//
//            }
//        });

        //logIn(findViewById(R.id.email_login_button));
        //signUp(findViewById(R.id.email_sign_up_button));

//        if(mEmailLogInButton.callOnClick()) {
//            logIn(findViewById(R.id.email_login_button));
//        }
//        else if(mEmailSignUpButton.callOnClick()){
//            signUp(findViewById(R.id.email_sign_up_button));
//        }
    }

    @Override
    public void onClick(View v) {

    }
}

