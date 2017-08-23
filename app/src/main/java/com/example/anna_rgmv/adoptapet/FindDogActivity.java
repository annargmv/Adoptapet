package com.example.anna_rgmv.adoptapet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

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
        ////////////////////////////temp for fill the table////////////////
        //ParseObject dog=new ParseObject("Dog");
        //dog.put("dogName","לני");
        //dog.put("dogType","רועה בלגי");
        //dog.put("notes","לני שלנו מסתדר עם כלבים שהוא מכיר, רק צריך רגע להכיר (כולנו לא?), מסתדר נהדר עם חברתו לתא מילי היפה. לני לא סומך על כל בן אדם מהתחלה, אך ברגע שמכיר הוא מפונק כמו הגור שהוא \uD83D\uDE42 אוהב מגע וליטופים וייתן את כל האהבה שיש לו לתת. ישמח למישהו שיוציא איתו אנרגיה ויראה לו שהעולם מלא בטוב! פחות מתאים לרביצה על הספה כל היום \uD83D\uDE42\n" +
        //        "\n" +
        //        "לני רועה בלגי בן שנה, גדול ומהמם.\n" +
         //       "\n" +
        //        "לפרטים נוספים צרו קשר בטלפון 054-4951748 אם אין מענה שלחו sms ונחזור בהקדם.");
        //dog.put("isKidsDog",true);
        //dog.put("isHomeDog",false);
        //dog.put("isDangerDog",true);
        //dog.put("isTrainingDog",true);
        //dog.put("isMale",true);
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.lenny);
        // Convert it to byte
        //ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        //bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        //byte[] image = stream.toByteArray();
        //ParseFile file = new ParseFile("lenny.png", image);
        // Upload the image into Parse Cloud
        //file.saveInBackground();
        // Create a column named "ImageFile" and insert the image
        //dog.put("ImageDog", file);
        // Create the class and the columns
        //dog.saveInBackground();



        ///////////////////////////////////////////////////////////////////

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
                //TODO testing log out
                ParseUser.logOut();
                intent=new Intent(getApplicationContext(),LoginActivity.class);
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

