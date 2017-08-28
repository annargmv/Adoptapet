package com.example.anna_rgmv.adoptapet;

import android.content.Intent;
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

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    TextView userName;
    TextView userEmail;
    TextView userPhone;

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
        ParseQuery query = new ParseQuery("Dog");
        query.selectKeys(Arrays.asList("objectId"));
        {
            try {
                List<ParseObject> test = query.find();
                dogId = new String[test.size()];
                posId = new int[test.size()];
                for (int i = 0; i < test.size(); i++) {
                    dogId[i] = test.get(i).getObjectId();
                    posId[i] = i;
                    //String[] str = {test.get(x).getString(uname)};
                    //text.setText("Username: "+str[x]+"\n");
                }
            } catch (com.parse.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        CustomGrid adapter = new CustomGrid(UserActivity.this, dogId, posId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(UserActivity.this, "You Clicked at " + dogId[+ position], Toast.LENGTH_SHORT).show();


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
