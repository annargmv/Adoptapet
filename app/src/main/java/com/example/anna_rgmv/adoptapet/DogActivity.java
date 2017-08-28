package com.example.anna_rgmv.adoptapet;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.Arrays;
import java.util.List;

public class DogActivity extends AppCompatActivity {

    ImageView dogImage;
    ImageView imageWishList;
    TextView dogName;
    TextView dogInfo;
    GridView grid;
    CustomGrid adapter;
    String[] dogId;
    String currentUser;
    String data;
    int[] posId;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dg);

        currentUser=ParseUser.getCurrentUser().getObjectId();
        //for open the data base
        db=this.openOrCreateDatabase("AdoptAPat",MODE_PRIVATE,null);
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

        adapter = new CustomGrid(DogActivity.this, dogId, posId);
        grid=(GridView)findViewById(R.id.grid);
        //the intent info from FindDogActivity
        data = getIntent().getExtras().getString("dogId");

        dogImage = (ImageView) findViewById(R.id.dogImage);
        imageWishList = (ImageView) findViewById(R.id.wishlist);
        if(isInWishlist()) imageWishList.setImageResource(R.drawable.iconwishlist2);

        dogName = (TextView) findViewById(R.id.dogName);
        dogInfo = (TextView) findViewById(R.id.infoDog);

        // Locate the class table named "Dog" in Parse.com
        ParseQuery<ParseObject> query2=ParseQuery.getQuery("Dog");

        // Locate the objectId from the class
        query2.getInBackground(data, new GetCallback<ParseObject>() {
            public void done(ParseObject object,ParseException e) {
                // Locate the column named "ImageDog" and set the string
                ParseFile fileObject = (ParseFile) object.get("ImageDog");

                String name=object.getString("dogName");
                String info=object.getString("notes");

                dogName.setText(name);
                dogInfo.setText(info);

                fileObject.getDataInBackground(new GetDataCallback() {

                    public void done(byte[] data,
                                     ParseException e) {
                        if (e == null) {
                            Log.d("test","We've got data in data.");
                            // Decode the Byte[] into Bitmap
                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
                            // Set the Bitmap into the ImageView
                            dogImage.setImageBitmap(bmp);
                        } else {
                            Log.d("test","There was a problem to download the data.");
                        }
                    }
                });
            }
        });

        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DogActivity.this, "You Clicked at " + dogId[+ position], Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DogActivity.this, DogActivity.class);
                intent.putExtra("dogId", dogId[position]);
                startActivity(intent);

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
                updateParseWishlistTable();
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

    private void updateParseWishlistTable() {
    }

    public void wishlist(View view){
        Log.i("testing: ","i am in wishList");
        if(isInWishlist()) {
            Log.i("testing: ","i am in wishList for take off");
            //take him off from wishlist
            imageWishList.setImageResource(R.drawable.iconwishlist1);
            db.execSQL("DELETE FROM wishlist where userId = '"+currentUser+"' AND dogId='"+data+"'");
        }else {
            Log.i("testing: ","i am in wishList for put in");
            //put him in the wishlist
            imageWishList.setImageResource(R.drawable.iconwishlist2);
            db.execSQL("INSERT INTO wishlist (userId, dogId) VALUES ('"+currentUser+"','"+data+"')");
        }

    }
    //check if the dog is already in wishlist of this user
    private boolean isInWishlist(){
        Cursor c =db.rawQuery("SELECT * FROM wishlist WHERE userId ='"+currentUser+"' AND dogId='"+data+"' LIMIT 1 ",null);
        if(c!=null && c.getCount()>0){ return true;}
        else{ return false; }
     }
}
