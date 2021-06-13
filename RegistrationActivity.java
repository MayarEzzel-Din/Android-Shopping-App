package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final String[] BirthDateArr = {""};
        CalendarView calendarView = (CalendarView) findViewById(R.id.CalenderView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                BirthDateArr[0] = dayOfMonth + "/" + (month + 1) + "/" + year;
            }
        });

        Button RegisterBTN = (Button) findViewById(R.id.RegisterBTN);
        RegisterBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String BirthDate = BirthDateArr[0];
                EditText FullNameTXT = (EditText) findViewById(R.id.FullNameTXT);
                EditText UsernameTXT = (EditText) findViewById(R.id.UsernameTXT_R);
                EditText PasswordTXT = (EditText) findViewById(R.id.PasswordTXT);
                EditText JobTXT = (EditText) findViewById(R.id.JobTXT);

                RadioGroup GroupRBTN = (RadioGroup) findViewById(R.id.GroupRBTN);
                RadioButton SelectedGender = (RadioButton) findViewById(GroupRBTN.getCheckedRadioButtonId());
                String Gender = SelectedGender.getText().toString();

                if(FullNameTXT.getText().toString().equals("") || UsernameTXT.getText().toString().equals("") || PasswordTXT.getText().toString().equals("") ||
                        JobTXT.getText().toString().equals("") || BirthDate.equals("") || Gender.equals(""))
                    Toast.makeText(getApplicationContext(), "Please Fill The Missed Entries", Toast.LENGTH_LONG).show();
                else
                {
                    DataBaseHelper db_helper = new DataBaseHelper(getApplicationContext());
                    if(!db_helper.CheckUsername(UsernameTXT.getText().toString()))
                        Toast.makeText(getApplicationContext(), "This Username Is Already In Use", Toast.LENGTH_LONG).show();
                    else
                    {
                        User newUser = new User(FullNameTXT.getText().toString(), UsernameTXT.getText().toString(), PasswordTXT.getText().toString(),
                                Gender, JobTXT.getText().toString(), BirthDate);
                        db_helper.AddCustomer(newUser);
                        Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}