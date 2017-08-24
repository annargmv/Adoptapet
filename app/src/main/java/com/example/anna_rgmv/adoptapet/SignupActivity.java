package com.example.anna_rgmv.adoptapet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    Button bSubmit;
    EditText etPhone,etName,etPassword,etMail;

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

        if((etMail.getText().length() != 0) && (etPhone.getText().length() != 0) && (etName.getText().length() != 0) && (etPassword.getText().length() != 0)){

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if((etMail.getText().length() == 0) && (etPhone.getText().length() == 0) && (etName.getText().length() == 0) && (etPassword.getText().length() == 0)){
            Toast.makeText(getApplicationContext(), "Please fill all of the fields", Toast.LENGTH_LONG).show();
        }
        else if(etMail.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_LONG).show();
        }
        else if (etPhone.getText().length() != 0){
            Toast.makeText(getApplicationContext(), "Please enter your phone", Toast.LENGTH_LONG).show();
        }
        else if(etName.getText().length() != 0){
            Toast.makeText(getApplicationContext(), "Please enter your full name", Toast.LENGTH_LONG).show();
        }
        else if(etPassword.getText().length() != 0){
            Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_LONG).show();
        }

        

    }


}
