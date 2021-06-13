package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final String Username = getIntent().getStringExtra("Username");

        Button ProductsBTN = (Button) findViewById(R.id.ProductsBTN);
        ProductsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProductsActivity.class);
                intent.putExtra("Username", Username);
                startActivity(intent);
            }
        });

        Button SearchBTN = (Button) findViewById(R.id.SearchBTN);
        SearchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                intent.putExtra("Username", Username);
                startActivity(intent);
            }
        });

        List<String> CategoriesList = new ArrayList<>();
        DataBaseHelper db = new DataBaseHelper(getApplicationContext());
        Cursor CatCur = db.GetAllCategories();
        while(!CatCur.isAfterLast())
        {
            CategoriesList.add(CatCur.getString(1));
            CatCur.moveToNext();
        }
        ArrayAdapter<String> CategoriesAdapter = new ArrayAdapter<>(HomeActivity.this, android.R.layout.simple_list_item_1, CategoriesList);
        ListView myListView = findViewById(R.id.myListView);
        myListView.setAdapter(CategoriesAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra("Username", Username);
                intent.putExtra("CatID", String.valueOf(i+1));
                startActivity(intent);
            }
        });
    }
}