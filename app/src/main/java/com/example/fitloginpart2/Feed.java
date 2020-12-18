package com.example.fitloginpart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.Inflater;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;


public class Feed extends AppCompatActivity {
    //Intent declaration
    Intent subFeeds;
    //Layout Initialization
    LinearLayout feedLayout;
    LinearLayout hairs;
    LinearLayout skin;
    ScrollView mainScroll;
    LinearLayout Accupresure;
    LinearLayout Dailydose;
    LinearLayout View;
    //ends
//Other declaratrios::
//TextView tv;




        public void logout(View view){
        ParseUser.logOut();
        Intent intent =new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void feedUI(final String text, int linearLayoutID/*,int textViewID*/){

        ScrollView sv =new ScrollView(this);
        final TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(30);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //tv.setId(textViewID);
        tv.setPadding(0,0,0,5);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subFeeds = new Intent(getApplicationContext(),subFeed.class);
                subFeeds.putExtra("feedChild",text);
                startActivity(subFeeds);
            }
        });
        sv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500));
        LinearLayout LL =new LinearLayout(this);
        LL.setOrientation(LinearLayout.HORIZONTAL);

        LL.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        LL.setId(linearLayoutID);
        sv.addView(LL);

        feedLayout.addView(tv);
        feedLayout.addView(sv);

    }



    int temp=0;

    public void feedCreation(final LinearLayout layout, String categoryName){


        ParseQuery<ParseObject> q=new ParseQuery<ParseObject>("Gargi");
        q.orderByDescending("createdAt");
        q.whereEqualTo("Category",categoryName);
        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null && objects.size()>0){
                    for (final ParseObject o :objects){
                        o.put("Isnew","Yes");
                        o.saveInBackground();

                        ParseFile file = (ParseFile)o.get("imagess");
//                        final String oid = o.getObjectId();
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(e==null && data!=null){
                                    Bitmap bitmap=BitmapFactory.decodeByteArray(data,0,data.length);


                                    final ImageView imageView=new ImageView(getApplicationContext());
                                    imageView.setLayoutParams(new ViewGroup.LayoutParams(500,500));
                                    imageView.setImageBitmap(bitmap);
                                    imageView.setPadding(15,0,15,0);
                                    imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
//                                            o.put("isClicked","yes");
//                                            temp;
                                           ImageView imageView1=findViewById(R.id.Zoomimage);
                                           imageView1.setImageResource(R.drawable.bg2);
                                            View.setVisibility(android.view.View.VISIBLE);
                                        }

                                    });
                                    layout.addView(imageView);
                                    final Button button=new Button(getApplicationContext());
                                    button.setText(" ");
                                    button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            o.saveInBackground();
                                            if(o.get("Isnew")=="Yes"){
                                                button.setBackgroundResource(R.drawable.like1);
                                                o.put("Isnew","No");
                                            }else{
                                                if(o.get("Isliked")=="Yes"){
                                                    button.setBackgroundResource(R.drawable.like1);
                                                    o.put("Isliked","No");
                                                }else{
                                                    button.setBackgroundResource(R.drawable.likeg);
                                                    o.put("Isliked","Yes");
                                                }
                                            }

                                        }
                                    });
                                    button.setBackgroundResource(R.drawable.likeg);
                                    button.setLayoutParams(new ViewGroup.LayoutParams(80, 80));
                                    layout.addView(button);



                                }
                            }
                        });
                    }
                }
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        getSupportActionBar().hide();
        setTitle("Fit As A Fiddle ");
        mainScroll = findViewById(R.id.mainScroll);
        feedLayout=findViewById(R.id.feedLayout);
        hairs=findViewById(R.id.hairsLayout);
        skin=findViewById(R.id.skinlayout);
        Dailydose = findViewById(R.id.dailyDoseLayout);
        View=findViewById(R.id.view);
        View.setVisibility(android.view.View.INVISIBLE);



//create FEEDUI
        feedUI("Accupresure",R.id.Accupresure/*,R.id.AccupresureText*/);
//LINK?FIND ui
        Accupresure  =findViewById(R.id.Accupresure);
//POPUlate FEED
        feedCreation(hairs,"hair");
        feedCreation(skin,"skin");
        feedCreation(Accupresure,"accupresure");
        feedCreation(Dailydose,"dailydose");

        //Automation...Work..from Admin App


    }

    public void SubFeedsHair(View view){
        subFeeds = new Intent(getApplicationContext(),subFeed.class);
        subFeeds.putExtra("feedChild","Hair");
        startActivity(subFeeds);

    }

    public void SubFeedsSkin(View view){
        subFeeds = new Intent(getApplicationContext(),subFeed.class);
        subFeeds.putExtra("feedChild","Skin");
        startActivity(subFeeds);

    }

    public void SubFeedsDailyDose(View view){
        subFeeds = new Intent(getApplicationContext(),subFeed.class);
        subFeeds.putExtra("feedChild","DailyDose");
        startActivity(subFeeds);

    }
}