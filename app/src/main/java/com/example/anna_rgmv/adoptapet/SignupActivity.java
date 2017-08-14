package com.example.anna_rgmv.adoptapet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    }


}
