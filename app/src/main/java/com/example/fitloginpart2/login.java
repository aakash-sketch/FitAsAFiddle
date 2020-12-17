package com.example.fitloginpart2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.security.Key;

public class login extends AppCompatActivity implements View.OnKeyListener {//,View.OnClickListener{
EditText username1;
EditText pass1;




    public void log(View view){
        ParseUser.logInInBackground(username1.getText().toString(), pass1.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e== null){
                    Toast.makeText(login.this, "Logged in succesfull", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),Feed.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(login.this, "Ops  "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void swi(View view ){
        Intent intent =new Intent(getApplicationContext(),signup.class);
        startActivity(intent);

        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        TextView loginText=findViewById(R.id.loginText);
        ConstraintLayout loginPage =findViewById(R.id.loginPage);
        username1 = findViewById(R.id.usernameforlogin);
        pass1 =findViewById(R.id.pass1);
        pass1.setOnKeyListener(this);
//        loginText.setOnClickListener(this);
//        loginPage.setOnClickListener(this);


    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
    if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
        log(v);
    }
        return false;
    }

//    @Override
//    public void onClick(View v) {
//        if(v.getId() == R.id.loginText || v.getId() == R.id.loginPage){
//        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
//
//    }
//}
}