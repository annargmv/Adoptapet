package com.example.anna_rgmv.adoptapet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {

    public static final String PREFS_NAME = "MyPrefsFile";
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    Button mEmailLogInButton;
    Button mEmailSignUpButton;

    CheckBox rememberMe;

    EditText emialText;
    EditText password;
    ImageView logo;

    String dogId[];
    String currentUser;
    String userName;
    String userPassword;

    static SQLiteDatabase db;

    public void logIn(View view) {

        if (emialText.getText().length() != 0 && password.getText().length() != 0) {
            try {
                //check id the user is registered
                ParseUser user = ParseUser.logIn(emialText.getText().toString(), password.getText().toString());
                Intent buttonIntent = new Intent(this, FindDogActivity.class);
                startActivity(buttonIntent);
                currentUser=ParseUser.getCurrentUser().getObjectId();
                sqliteUpdate();

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

        //initialize the variables
        logo = (ImageView) findViewById(R.id.logo);
        emialText = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        rememberMe = (CheckBox) findViewById(R.id.rememberMe);
        mEmailLogInButton = (Button) findViewById(R.id.email_login_button);
        mEmailSignUpButton = (Button) findViewById(R.id.email_sign_up_button);


        //remember me checkbox init
        //mEmailLogInButton.setOnClickListener(this);
        rememberMe.setOnClickListener(this);
        mEmailSignUpButton.setOnClickListener(this);

        loginPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            emialText.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            rememberMe.setChecked(true);
        }
    }

    //onclick remember me functionality
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.rememberMe:
            if (view.equals(rememberMe)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(emialText.getWindowToken(), 0);

                userName = emialText.getText().toString();
                userPassword = password.getText().toString();

                if (rememberMe.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", userName);
                    loginPrefsEditor.putString("password", userPassword);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }

                //doSomethingElse();
            }
        }

        if (view.equals(mEmailSignUpButton)) {
            signUp(this.findViewById(R.id.email_sign_up_button));
        }
    }

//    public void doSomethingElse() {
//        startActivity(new Intent(LoginActivity.this, FindDogActivity.class));
////        LoginActivity.this.finish();
//    }


    // update the wish list sql
    public void sqliteUpdate(){
        //download from the server
        ParseQuery<ParseObject> query= ParseQuery.getQuery("Wishlist");
        query.whereEqualTo("userId",currentUser);
        query.selectKeys(Arrays.asList("objectId"));

        try{
            List<ParseObject> test = query.find();
            dogId=new String[test.size()];
            for(int i=0;i<test.size();i++){
                dogId[i]=test.get(i).fetchIfNeeded().getString("dogId");
            }
        }
        catch (com.parse.ParseException e) {
            e.printStackTrace();
        }
        //create or open data base
        db=this.openOrCreateDatabase("AdoptAPat",MODE_PRIVATE,null);
       // db.execSQL("DROP TABLE IF EXISTS wishlist " );
        db.execSQL("CREATE TABLE IF NOT EXISTS wishlist (userId VARCHAR , dogId VARCHAR)");
        db.execSQL("DELETE FROM wishlist where userId = '"+currentUser+"' ");
        for(int i=0;i<dogId.length;i++) {
            db.execSQL("INSERT INTO wishlist (userId, dogId) VALUES ('" + currentUser + "','" + dogId[i] + "')");
        }
        //how to take out from database
        //Cursor c =db.rawQuery("SELECT * FROM wishlist WHERE userId ='"+currentUser+"' ",null);
        //int userIdIndex=c.getColumnIndex("userId");
        //int dogIdIndex=c.getColumnIndex("dogId");
        //c.moveToFirst();
        //while(c!=null){
        //    Log.i("userID:",c.getString(userIdIndex));
        //    c.moveToNext();
        //}
        //how to delete a row
        //db.execSQL("DELETE FORM wishlist where userId = '8nV4BO4t9n' LIMIT 1");

        //how to update
        //db.execSQL("UPDATE wishlist set dogId='d1WrWN83gJ' where userId='8nV4BO4t9n' LIMIT 1");


    }

//    public void onCheckboxClicked(View view) {
//        //String currentUser = String.valueOf(ParseUser.getCurrentUser().get("password"));
//        //String currentUserEmail = String.valueOf(ParseUser.getCurrentUser().get("email"));
//
//        // Is the view now checked?
//        boolean checked = ((CheckBox) view).isChecked();
//
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.rememberMe:
//                if (checked) {
//
//                    getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
//                            .edit()
//                            .putString(PREF_USERNAME, emialText.toString())
//                            .putString(PREF_PASSWORD, password.toString())
//                            .apply();
//
//                    SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
//                    String username = pref.getString(PREF_USERNAME, null);
//                    String password1 = pref.getString(PREF_PASSWORD, null);
//
//                    if (username == null || password1 == null) {
//                        emialText.setText(username);
//                        password.setText(password1);
//
//                        //Prompt for username and password
//                    }
//                }
//
//        }
//    }

}