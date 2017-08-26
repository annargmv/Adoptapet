package com.example.anna_rgmv.adoptapet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {


    Button mEmailLogInButton;
    Button mEmailSignUpButton;
    EditText emialText;
    EditText password;
    ImageView logo;

    public void logIn(View view) {
        mEmailLogInButton = (Button) findViewById(R.id.email_login_button);

        if (emialText.getText().length() != 0 && password.getText().length() != 0) {
            try {

                ParseUser user = ParseUser.logIn(emialText.getText().toString(), password.getText().toString());
                System.out.println("info for the user is " + user);
                Intent buttonIntent = new Intent(this, FindDogActivity.class);
                startActivity(buttonIntent);

            } catch (ParseException e) {
                Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                Log.i("error Login:",e.toString());
            }

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

        //initialize pasre service
        Parse.initialize(new Parse.Configuration.Builder(this)
            .applicationId("86d41a93f1bd5d33ae9bba0c7ac97da4c326eafd")
            .server("http://ec2-34-201-149-100.compute-1.amazonaws.com:80/parse")
            .build()
        );
        /*ParseObject object=new ParseObject("Exemple");
          object.put("myNumber","123");
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Log.i("testing:","Failed");
                }else{
                    Log.i("testing:","Successful");
                }
            }
        });
        */

//Add setOnClickListener for the buttons
        logo = (ImageView) findViewById(R.id.logo);
        emialText = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
//        mEmailLogInButton = (Button) findViewById(R.id.email_login_button);

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