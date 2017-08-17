package com.example.anna_rgmv.adoptapet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class FindDogActivity extends AppCompatActivity {

    GridView grid;
    String[] web = {
            "Google",
            "Github",
            "Instagram",
            "Facebook",
            "Flickr",
            "Pinterest",
            "Quora",
            "Twitter",
            "Vimeo",
            "WordPress",
            "Youtube",
            "Stumbleupon",
            "SoundCloud",
            "Reddit",
            "Blogger"

    } ;
    int[] imageId = {
            R.drawable.dog1,
            R.drawable.dog2,
            R.drawable.dog3,
            R.drawable.dog4,
            R.drawable.dog2,
            R.drawable.dog3,
            R.drawable.dog1,
            R.drawable.dog4,
            R.drawable.dog3,
            R.drawable.dog2,
            R.drawable.dog4,
            R.drawable.dog1,
            R.drawable.dog1,
            R.drawable.dog3,
            R.drawable.dog4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_dog);

        CustomGrid adapter = new CustomGrid(FindDogActivity.this, web, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FindDogActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

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
                //TODO what hepend when logout
            case R.id.menuInfo:
                intent=new Intent(getApplicationContext(),InfoActivity.class);
                startActivity(intent);
                return true;
            default: return false;


        }
    }
}

