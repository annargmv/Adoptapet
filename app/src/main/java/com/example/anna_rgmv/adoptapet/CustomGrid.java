package com.example.anna_rgmv.adoptapet;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CustomGrid extends BaseAdapter{
    private Context mContext;
    private final String[] name;
    private final int[] Imageid;


    public CustomGrid(Context c,String[] name,int[] Imageid ) {
        mContext = c;
        this.Imageid = Imageid;
        this.name = name;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);

            final TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            final ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);


            // Locate the class table named "Dog" in Parse.com
            ParseQuery<ParseObject> query=ParseQuery.getQuery("Dog");
            // Locate the objectId from the class
            query.getInBackground(name[position], new GetCallback<ParseObject>() {
                public void done(ParseObject object,ParseException e) {
                    // TODO Auto-generated method stub
                    // Locate the column named "ImageDog" and set the string
                    //for testing only
                    //Log.i("testing: ",object.toString());
                    //
                    ParseFile fileObject = (ParseFile) object.get("ImageDog");
                    String dogName=object.getString("dogName");
                    textView.setText(dogName);
                    fileObject.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data,
                                         ParseException e) {
                            if (e == null) {
                                Log.d("test",
                                        "We've got data in data.");
                                // Decode the Byte[] into Bitmap
                                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
                                // Set the Bitmap into the ImageView
                                imageView.setImageBitmap(bmp);

                            } else {
                                Log.d("test",
                                        "There was a problem downloading the data.");
                            }
                        }
                    });
                }
            });

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}