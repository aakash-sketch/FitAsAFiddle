package com.example.fitloginpart2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class subFeed extends AppCompatActivity {
//    ScrollView subFeedScroll;
    androidx.gridlayout.widget.GridLayout grid;
    Intent intent ;
    LinearLayout feedUiLayout ;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_feed);

//        subFeedScroll =findViewById(R.id.subFeedMainScrollView);
          grid =findViewById(R.id.grid);
          intent=getIntent();
        String cat=intent.getStringExtra("feedChild");
        setTitle(cat+" 's Posts..");
        
        ParseQuery<ParseObject> q=new ParseQuery<ParseObject>("Gargi");
        q.orderByDescending("createdAt");
        q.whereEqualTo("Category",cat.toLowerCase());
        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null && objects.size()>0){
                    for (final ParseObject o :objects){
                        o.put("Isnew","Yes");
                        o.saveInBackground();

                        final ParseFile file = (ParseFile)o.get("imagess");
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(final byte[] data, ParseException e) {
                                if(e==null && data!=null){
                                     bitmap= BitmapFactory.decodeByteArray(data,0,data.length);


                                    final ImageView imageView=new ImageView(getApplicationContext());
                                    imageView.setLayoutParams(new ViewGroup.LayoutParams(425,800));
                                    imageView.setImageBitmap(bitmap);
                                    imageView.setPadding(15,0,15,0);
                                    feedUiLayout = new LinearLayout(getApplicationContext());
                                    feedUiLayout.setOrientation(LinearLayout.VERTICAL);
                                    feedUiLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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
                                    button.setLayoutParams(new ViewGroup.LayoutParams(110, 110));

                                    Space space=new Space(getApplicationContext());
                                    space.setMinimumHeight(230);

                                    Button Savebutton= new Button(getApplicationContext());
                                    Savebutton.setText(" ");
                                    Savebutton.setLayoutParams(new ViewGroup.LayoutParams(110, 110));
                                    Savebutton.setBackgroundResource(R.drawable.pirsave);
                                    Savebutton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

//                                            ImageDecoder.Source source = ImageDecoder.createSource(this,imageView);
                                                Bitmap bitmap1 =((BitmapDrawable)imageView.getDrawable()).getBitmap();
//                                            try {
//                                                bitmap1= ImageDecoder.decodeBitmap(source);
//                                            } catch (IOException ex) {
//                                                ex.printStackTrace();
//                                            }
                                            ByteArrayOutputStream strem1= new ByteArrayOutputStream();
                                            bitmap1.compress(Bitmap.CompressFormat.PNG,100,strem1);
                                            byte[] arri =strem1.toByteArray();
                                            ParseFile file= new ParseFile("savedimg",arri);
                                            ParseObject oject= new ParseObject("Saved");
                                            oject.put("savedFeed",file);
                                            oject.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    Toast.makeText(subFeed.this, "Saved", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });

                                    grid.addView(imageView);

                                    feedUiLayout.addView(button);
                                    feedUiLayout.addView(space);
                                    feedUiLayout.addView(Savebutton);

                                    grid.addView(feedUiLayout);



                                }
                            }
                        });
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id ==R.id.logout){
            ParseUser.logOut();
            Intent intentL = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentL);
            finish();
        }
        if(id==R.id.viewSavedImg) {
            Intent saveI = new Intent(getApplicationContext(), SavedView.class);
            startActivity(saveI);
        }
    return super.onOptionsItemSelected(item);
    }
}