package com.dami.dailylights;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button b1;
    EditText username;
    EditText password;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.Username);
        password = (EditText) findViewById(R.id.Password);
        b1 = (Button) findViewById(R.id.LoginButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authentication(username.getText().toString(), password.getText().toString());
            }
        });

    }

    //Login Screen Authentication
    private void Authentication(String UserName, String PassWord){
        Intent intent = new Intent(Login.this, MainActivity.class);
        Login.this.startActivity(intent);
//        if((UserName.equals("a")) && (PassWord.equals("a"))){
//            Log.d("HAMZA_APP","Login Details Matched, Logging in Now");
//            Intent intent = new Intent(Login.this, MainActivity.class);
//            Login.this.startActivity(intent);
//        }
//        else{
//            Toast.makeText(getBaseContext(), "Enter Password Again!", Toast.LENGTH_SHORT).show();
//            Log.d("HAMZA_APP","Verification Failed");
//        }
    }

}
