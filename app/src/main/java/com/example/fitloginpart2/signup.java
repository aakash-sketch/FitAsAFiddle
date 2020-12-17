package com.example.fitloginpart2;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

public class signup extends AppCompatActivity implements View.OnKeyListener{//, View.OnClickListener {
ParseUser user  = new ParseUser();
EditText username ;
EditText pass;
EditText email;
EditText phone;
    InputMethodManager inputMethodManager;
    ConstraintLayout signupPage;
    TextView signupText;

    public void sinup(View view) {
        if(username.getText().toString().matches("") ||pass.getText().toString().matches("") ||email.getText().toString().matches("") ||phone.getText().toString().matches("")){
            Toast.makeText(this, "Submit The Details", Toast.LENGTH_SHORT).show();
        }
        else{
            user.setUsername(username.getText().toString());
            user.setPassword(pass.getText().toString());
            user.setEmail(email.getText().toString());
            user.put("Phone",phone.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null) {
                        Toast.makeText(signup.this, "Signed up", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(signup.this, "sign up failed  "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                
            });
        }


    }
    public void login(View view){
        Intent intent =new Intent(getApplicationContext(),login.class);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
       signupPage = findViewById(R.id.signupPage);
       signupText = findViewById(R.id.signupText);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.Pass);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        phone.setOnKeyListener(this);
//
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


//        signupPage.setOnClickListener(this);
//        signupText.setOnClickListener(this);


    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            sinup(v);
        }


        return false;}



//    @Override
//    public void onClick(View v) {
//
//        if(v.getId() == R.id.signupPage || v.getId() == R.id.signupText){
//             {
//
//                inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//            }
//        }}
}