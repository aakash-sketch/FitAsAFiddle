package com.example.fitloginpart2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class SavedView extends AppCompatActivity {
Bitmap bitmap;
LinearLayout hori;
LinearLayout spaceL;
@Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_view);
        setTitle("Saved Items.");
        hori =findViewById(R.id.hori);
        spaceL=findViewById(R.id.space);
        ParseQuery<ParseObject> q=new ParseQuery<ParseObject>("Saved");
        q.orderByDescending("createdAt");
        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null && objects.size()>0){
                    for (final ParseObject o :objects){


                        ParseFile file = (ParseFile)o.get("savedFeed");
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(e==null && data!=null){
                                    bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                                    final ImageView imageView=new ImageView(getApplicationContext());
                                    imageView.setLayoutParams(new ViewGroup.LayoutParams((ViewGroup.LayoutParams.MATCH_PARENT)-200,1000));
                                    imageView.setImageBitmap(bitmap);
                                    imageView.setPadding(15,0,15,20
                                    );
//
//

                                    final Button Savebutton1=new Button(getApplicationContext());
                                    Savebutton1.setText(" ");
                                    Savebutton1.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
                                    Savebutton1.setPadding(20,00,20,0);


                                    Savebutton1.setBackgroundResource(R.drawable.unsave);
                                    Savebutton1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            o.deleteInBackground();
                                            Toast.makeText(getApplicationContext(), "Unsaved", Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(getIntent());
                                        }



                                    }

                                    );

//                                    spaceL.addView(space);
                                    hori.addView(imageView);
//
                                    hori.addView(Savebutton1);


                                }}});}}}});}
    }
