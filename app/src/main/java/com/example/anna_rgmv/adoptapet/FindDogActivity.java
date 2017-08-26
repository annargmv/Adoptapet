package com.example.anna_rgmv.adoptapet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

public class FindDogActivity extends AppCompatActivity{

    //Initializing variables
    EditText searchByName;
    Spinner typeSpinner;
    Spinner genderSpinner;
    GridView grid;


    //array for the adapters
    ArrayList<String> typeArray = new ArrayList<String>();
    ArrayList<String> genderArray = new ArrayList<String>();

    //names of the dogs
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

    ParseObject dogs = new ParseObject("Dog");
    String objectId = dogs.getObjectId();
    //ParseFile imageOfDog = new ParseFile("Dog");

    //image per dog
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

                Intent intent = new Intent(FindDogActivity.this, DogActivity.class);
                intent.putExtra("web[+position]",imageId);
                startActivity(intent);

            }
        });

        searchByName = (EditText) findViewById(R.id.searchByname);
        typeSpinner = (Spinner)findViewById(R.id.typeSpinner);
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);

        //Type Spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinerAdapter = ArrayAdapter.createFromResource (this,R.array.type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        typeSpinner.setAdapter(spinerAdapter);

        //Gender
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinerGenderAdapter = ArrayAdapter.createFromResource (this,R.array.gender, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinerGenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        genderSpinner.setAdapter(spinerGenderAdapter);

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

