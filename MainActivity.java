package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView ForgetPasswordBTN = (TextView) findViewById(R.id.ForgetPasswordBTN);
        ForgetPasswordBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        Button SignUpBTN = (Button) findViewById(R.id.SignUpBTN);
        SignUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        Button LogInBTN = (Button) findViewById(R.id.LogInBTN);
        LogInBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DataBaseHelper db_helper = new DataBaseHelper(getApplicationContext());
                EditText UsernameTXT = (EditText) findViewById(R.id.UsernameTXT_R);
                EditText PasswordTXT = (EditText) findViewById(R.id.PasswordTXT);

                if(!db_helper.CheckUserExistence(UsernameTXT.getText().toString(), PasswordTXT.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Invalid Username Or Password", Toast.LENGTH_LONG).show();
                else
                {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("Username",UsernameTXT.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}