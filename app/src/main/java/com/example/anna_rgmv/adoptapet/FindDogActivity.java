package com.example.anna_rgmv.adoptapet;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

public class FindDogActivity extends AppCompatActivity implements OnItemSelectedListener, TextWatcher {

    //Initializing variables
    EditText searchByName;
    Spinner typeSpinner;
    Spinner genderSpinner;
    GridView grid;

    //array for the adapters
    //ArrayList<String> typeArray = new ArrayList<String>();
    //ArrayList<String> genderArray = new ArrayList<String>();

    //id of the dogs
    String[] dogId;
    //image per dog
    int[] posId;

    String[] dogName;
    String[] dogGenderType;
    //String[] dogTypes;

    //String currentUser;
    //String data;
    String itemDogType;
    String dogTypeName;
    String dogGender;

    SQLiteDatabase db;
    public void retrieveData(){

        ///////////////////////Retrieving dogs id from parse//////////////////////////

        ParseQuery query = new ParseQuery("Dog");
        query.selectKeys(Arrays.asList("objectId"));
        {
            try{
                List<ParseObject> test = query.find();
                dogId=new String[test.size()];
                posId =new int[test.size()];
                for(int i=0;i<test.size();i++){
                    dogId[i]=test.get(i).getObjectId();
                    posId[i]=i;
                }
            }
            catch (com.parse.ParseException e) {
                e.printStackTrace();
            }
        }

        CustomGrid adapter = new CustomGrid(FindDogActivity.this, dogId, posId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FindDogActivity.this, "You Clicked at " +dogId[+ position], Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(FindDogActivity.this, DogActivity.class);
                intent.putExtra("dogId",dogId[position]);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fd);

        retrieveData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CustomGrid adapter = new CustomGrid(FindDogActivity.this, dogId, posId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FindDogActivity.this, "You Clicked at " +dogId[+ position], Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(FindDogActivity.this, DogActivity.class);
                intent.putExtra("dogId",dogId[position]);
                startActivity(intent);
            }
        });



        searchByName = (EditText) findViewById(R.id.searchByname);
        typeSpinner = (Spinner)findViewById(R.id.typeSpinner);
        typeSpinner.setOnItemSelectedListener(this);

        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        genderSpinner.setOnItemSelectedListener(this);

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

        //setContentView(R.layout.activity_fd);
        searchByName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                String dogName = String.valueOf(s);

                if(dogName.equals("")){
                    retrieveData();
                }
                else {
                    ParseQuery query = new ParseQuery("Dog");
                    query.whereEqualTo("dogName", String.valueOf(s));
                    query.selectKeys(Arrays.asList("objectId"));
                    {
                        try {
                            List<ParseObject> test = query.find();
                            dogId = new String[test.size()];
                            posId = new int[test.size()];
                            for (int i = 0; i < test.size(); i++) {
                                dogId[i] = test.get(i).getObjectId();
                                posId[i] = i;
                            }
                        } catch (com.parse.ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    CustomGrid adapter = new CustomGrid(FindDogActivity.this, dogId, posId);
                    grid = (GridView) findViewById(R.id.grid);
                    grid.setAdapter(adapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(FindDogActivity.this, "You Clicked at " + dogId[+position], Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(FindDogActivity.this, DogActivity.class);
                            intent.putExtra("dogId", dogId[position]);
                            startActivity(intent);
                        }
                    });
                }


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
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



    public void searchByType() {

        //download from the server
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Dog");
        query.whereEqualTo("dogType", dogTypeName);
        query.selectKeys(Arrays.asList("objectId"));
        try {
            List<ParseObject> test = query.find();
            dogId = new String[test.size()];
            dogName = new String[test.size()];
            posId =new int[test.size()];
            for (int i = 0; i < test.size(); i++) {
                //dogId[i] = test.get(i).fetchIfNeeded().getString("objectId");
                dogId[i] = test.get(i).getObjectId();
                dogName[i] = test.get(i).fetchIfNeeded().getString("dogType");
                posId[i] = i;
            }

        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }
        //create or open data base
        db = this.openOrCreateDatabase("AdoptAPet", MODE_PRIVATE, null);
        // db.execSQL("DROP TABLE IF EXISTS wishlist " );
        db.execSQL("CREATE TABLE IF NOT EXISTS dogsForSearch (name VARCHAR ,objectId VARCHAR ,dogType VARCHAR)");
        //db.execSQL("DELETE FROM wishlist where userId = '"+currentUser+"' ");
        for (int i = 0; i < dogId.length; i++) {
            db.execSQL("INSERT INTO dogsForSearch (name ,objectId ,dogType) VALUES ('" + dogName + "','" + dogId[i] + "','" + dogTypeName + "')");
            System.out.println("The list of Dog Id" + dogId[i]);
            System.out.println("The list of Dog Name Id" + dogName[i]);
        }

        CustomGrid adapter = new CustomGrid(FindDogActivity.this, dogId, posId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FindDogActivity.this, "You Clicked at " +dogId[+ position], Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(FindDogActivity.this, DogActivity.class);
                intent.putExtra("dogId",dogId[position]);
                startActivity(intent);
            }
        });

    }

    public void searchByGender(){

        //download from the server
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Dog");
        query.whereEqualTo("gender", dogGender);
        query.selectKeys(Arrays.asList("objectId"));
        try {

            List<ParseObject> test = query.find();
            dogId = new String[test.size()];
            dogGenderType = new String[test.size()];
            posId = new int[test.size()];

            for (int i = 0; i < test.size(); i++) {

                dogId[i] = test.get(i).getObjectId();
                dogGenderType[i] = test.get(i).fetchIfNeeded().getString("gender");
                posId[i] = i;

                System.out.println("the bool is " + dogGenderType[i]);
            }

        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }
        //create or open data base
        db = this.openOrCreateDatabase("AdoptAPet", MODE_PRIVATE, null);
        // db.execSQL("DROP TABLE IF EXISTS wishlist " );
        db.execSQL("CREATE TABLE IF NOT EXISTS dogsForSearch (name VARCHAR ,objectId VARCHAR ,isMale VARCHAR)");
        //db.execSQL("DELETE FROM wishlist where userId = '"+currentUser+"' ");
        for (int i = 0; i < dogId.length; i++) {
            db.execSQL("INSERT INTO dogsForSearch (name ,objectId ,dogType) VALUES ('" + dogName + "','" + dogId[i] + "','" + dogGender + "')");
            System.out.println("The list of Dog Id" + dogId[i]);
            System.out.println("The list of Dog Name Id" + dogGenderType[i]);
        }


        CustomGrid adapter = new CustomGrid(FindDogActivity.this, dogId, posId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FindDogActivity.this, "You Clicked at " +dogId[+ position], Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(FindDogActivity.this, DogActivity.class);
                intent.putExtra("dogId",dogId[position]);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        String itemDogType = parent.getItemAtPosition(position).toString();
        String itemDogGender =  parent.getItemAtPosition(position).toString();

        if(itemDogType.equals("גזעי")){


            Toast.makeText(parent.getContext(), "Selected: " + itemDogType, Toast.LENGTH_LONG).show();
            dogTypeName = itemDogType; // Type of dog
            searchByType();

        }
        else if(itemDogType.equals("מעורב")){

            Toast.makeText(parent.getContext(), "Selected: " + itemDogType, Toast.LENGTH_LONG).show();
            dogTypeName = itemDogType; // Type of dog
            searchByType();
        }

        else if(itemDogGender.equals("Male")){

            Toast.makeText(parent.getContext(), "Selected: " + itemDogType, Toast.LENGTH_LONG).show();
            dogGender = itemDogGender ;// Gender of the dog
            searchByGender();
        }
        else if(itemDogGender.equals("Female")){

            Toast.makeText(parent.getContext(), "Selected: " + itemDogType, Toast.LENGTH_LONG).show();
            dogGender = itemDogGender ; //Gender of the dog
            searchByGender();

        }
        else if(itemDogType.equals("סוג")){

            retrieveData();

        }
        else if(itemDogType.equals("Gender")) {

            retrieveData();
        }

    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Another interface callback

    }

    @Override
    public void afterTextChanged(Editable arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}