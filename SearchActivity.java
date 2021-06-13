package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button Search = (Button) findViewById(R.id.SearchBTN_S);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText SearchTXT = (EditText) findViewById(R.id.SearchTXT);

                DataBaseHelper db = new DataBaseHelper(getApplicationContext());
                Cursor ProductsCursor = db.FindProduct_ByName(SearchTXT.getText().toString());
                String ProductInfoSTR;
                List<String> ProductsList = new ArrayList<>();
                while(!ProductsCursor.isAfterLast())
                {
                    ProductInfoSTR = ProductsCursor.getString(1) + ","+ ProductsCursor.getString(0)+ ",  " + ProductsCursor.getString(2) + " $"; //Name + Price
                    ProductsList.add(ProductInfoSTR);
                    ProductsCursor.moveToNext();
                }
                ArrayAdapter<String> ProductsAdapter = new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_list_item_1, ProductsList);
                GridView searchGrid = findViewById(R.id.searchGrid);
                searchGrid.setAdapter(ProductsAdapter);
            }
        });
        Button VoiceBTN = (Button) findViewById(R.id.VoiceBTN);
        VoiceBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, VoiceActivity.class);
                startActivity(intent);
            }
        });
    }
}