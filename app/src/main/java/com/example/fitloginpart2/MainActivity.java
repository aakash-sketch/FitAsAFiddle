package com.example.fitloginpart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;
    int start =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        CountDownTimer c=new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {



                 if(start ==1){
                     mp=MediaPlayer.create(MainActivity.this,R.raw.fineone);
                    mp.start();
                    start++;
                 }
            }

            @Override
            public void onFinish() {
//                mp.stop();
                if(ParseUser.getCurrentUser() != null){
                    Intent i = new Intent(getApplicationContext(),Feed.class);
                    startActivity(i);
                    finish();
                }else
                { Intent intent=new Intent(getApplicationContext(),login.class);
                startActivity(intent);
                finish();}

            }
        }.start();
    }

}