package com.example.anna_rgmv.adoptapet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class UserActivity extends AppCompatActivity {

    TextView userName ;
    TextView userEmail;
    TextView userPhone;

    GridView grid;

    //id of the dogs
    String[] dogId;

    //image per dog
    int[] posId;

    ParseObject dogs = new ParseObject("Dog");
    String objectId = dogs.getObjectId();

//    String[] web = {
//            "Google",
//            "Github",
//            "Instagram",
//            "Facebook",
//            "Flickr",
//            "Pinterest",
//            "Quora",
//            "Twitter",
//            "Vimeo",
//            "WordPress",
//            "Youtube",
//            "Stumbleupon",
//            "SoundCloud",
//            "Reddit",
//            "Blogger"
//
//    } ;
//    int[] imageId = {
//            R.drawable.dog1,
//            R.drawable.dog2,
//            R.drawable.dog3,
//            R.drawable.dog4,
//            R.drawable.dog2,
//            R.drawable.dog3,
//            R.drawable.dog1,
//            R.drawable.dog4,
//            R.drawable.dog3,
//            R.drawable.dog2,
//            R.drawable.dog4,
//            R.drawable.dog1,
//            R.drawable.dog1,
//            R.drawable.dog3,
//            R.drawable.dog4
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_us);

        userName = (TextView) findViewById(R.id.userName);
        userEmail = (TextView) findViewById(R.id.emailText);
        userPhone = (TextView) findViewById(R.id.phone);

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

//        //Retrieving data from the Dog
//        ParseQuery query = new ParseQuery("Dog");
//        query.selectKeys(Arrays.asList("objectId"));
//        {
//            try {
//                List<ParseObject> test = query.find();
//                dogId = new String[test.size()];
//                posId = new int[test.size()];
//                for (int i = 0; i < test.size(); i++) {
//                    dogId[i] = test.get(i).getObjectId();
//                    posId[i] = i;
//                    //String[] str = {test.get(x).getString(uname)};
//                    //text.setText("Username: "+str[x]+"\n");
//                }
//            } catch (com.parse.ParseException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//
//        CustomGrid adapter = new CustomGrid(UserActivity.this, dogId, posId);
//        grid = (GridView) findViewById(R.id.grid);
//        grid.setAdapter(adapter);
//        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(UserActivity.this, "You Clicked at " + dogId[+position], Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//
//
//        //the intent info from FindDogActivity
//        String data = getIntent().getExtras().getString("Name");
//
//        // Locate the class table named "Dog" in Parse.com
//        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("User");
//
//        // Locate the objectId from the class
//        query2.getInBackground(data,new GetCallback<ParseObject>() {
//            public void done(ParseObject object, ParseException e) {
//
//                // TODO Auto-generated method stub
//                // Locate the column named "ImageDog" and set the string
//
//                String name = object.getString("Name");
//                String email = object.getString("username");
//                String phone = object.getString("Phone");
//
//                userName.setText(name);
//                userEmail.setText(email);
//                userPhone.setText(phone);
//            }
//        });
//    }
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
