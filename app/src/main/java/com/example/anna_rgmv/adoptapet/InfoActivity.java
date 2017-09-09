package com.example.anna_rgmv.adoptapet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.parse.ParseUser;

import static com.example.anna_rgmv.adoptapet.LoginActivity.db;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_activiy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Action bar back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Intent intent;
        switch (item.getItemId()){
            case R.id.menuUser:
                intent=new Intent(getApplicationContext(),UserActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuFind:
                intent=new Intent(getApplicationContext(),FindDogActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuLogout:
                DogActivity.updateParseWishlistTable(db);
                ParseUser.logOut();
                intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.contactUs:
                intent = new Intent(getApplicationContext(),ContactUs.class);
                startActivity(intent);
                return true;
            case R.id.menuInfo:
                intent=new Intent(getApplicationContext(),InfoActivity.class);
                startActivity(intent);
                return true;
            default: return false;
        }
    }
}