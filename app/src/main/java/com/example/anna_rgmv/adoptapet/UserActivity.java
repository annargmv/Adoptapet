package com.example.anna_rgmv.adoptapet;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UserActivity extends AppCompatActivity {

    TextView userName;
    TextView userEmail;
    TextView userPhone;
    SQLiteDatabase db;
    String currentUser;

    GridView grid;
    String[] dogId;

    int[] posId;

    ParseObject dogs = new ParseObject("Dog");
    String objectId = dogs.getObjectId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_us);

        userName = (TextView) findViewById(R.id.userName);
        userEmail = (TextView) findViewById(R.id.emailText);
        userPhone = (TextView) findViewById(R.id.phoneUser);

        db=this.openOrCreateDatabase("AdoptAPat",MODE_PRIVATE,null);
        currentUser=ParseUser.getCurrentUser().getObjectId();

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

        //Retrieving data from the Dog
        Cursor c = db.rawQuery("SELECT dogId FROM wishlist WHERE userId ='"+currentUser+"'",null);
        c.moveToFirst();
        dogId = new String[c.getCount()];
        posId = new int[c.getCount()];
        for(int i=0;i<c.getCount();i++) {
            dogId[i]=(c.getString(c.getColumnIndex("dogId")));
            posId[i] = i;
            c.moveToNext();
        }
        c.close();

        CustomGrid adapter = new CustomGrid(UserActivity.this, dogId, posId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  Toast.makeText(UserActivity.this, "You Clicked at " + dogId[+ position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserActivity.this, DogActivity.class);
                intent.putExtra("dogId",dogId[position]);
                startActivity(intent);
            }
        });

        //Getting the user info
        ParseUser user = new ParseUser();
        user = ParseUser.getCurrentUser();

        userName.setText("Hello " + user.get("Name").toString());
        userEmail.setText("Email: " + user.get("username").toString());
        userPhone.setText("Phone: " + user.get("Phone").toString());

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
