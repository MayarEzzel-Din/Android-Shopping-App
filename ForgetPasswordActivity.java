package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Button ConfirmBTN = (Button) findViewById(R.id.ConfirmBTN);
        ConfirmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText NewUsernameTXT = (EditText) findViewById(R.id.NewUsernameTXT);
                DataBaseHelper db = new DataBaseHelper(getApplicationContext());
                if(db.CheckUsername(NewUsernameTXT.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Wrong Username", Toast.LENGTH_LONG).show();
                else
                {
                    Cursor user = db.FindUser_ByUsername(NewUsernameTXT.getText().toString());
                    EditText newPass = (EditText) findViewById(R.id.NewPasswordTXT);
                    newPass.setText(user.getString(2));
                }
            }
        });

    }
}