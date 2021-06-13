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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        final String Username = getIntent().getStringExtra("Username");
        final String CatID = getIntent().getStringExtra("CatID");

        String ProductInfoSTR;
        List<String> ProductsList = new ArrayList<>();
        DataBaseHelper db_helper = new DataBaseHelper(getApplicationContext());
        Cursor ProductsCursor = db_helper.GetAllProducts();
        while(!ProductsCursor.isAfterLast())
        {
            if(CatID.equals(ProductsCursor.getString(4)))
            {ProductInfoSTR = ProductsCursor.getString(1) + ","+ ProductsCursor.getString(0)+ ",  " + ProductsCursor.getString(2) + " $"; //Name + Price
            ProductsList.add(ProductInfoSTR);}
            ProductsCursor.moveToNext();
        }
        ArrayAdapter<String> ProductsAdapter = new ArrayAdapter<>(CategoryActivity.this, android.R.layout.simple_list_item_1, ProductsList);
        GridView myGridView = findViewById(R.id.myGridView);
        myGridView.setAdapter(ProductsAdapter);

        final ArrayList<String> ProductsID_ToCart = new ArrayList<>();
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView vv = (TextView) view;
                String SelectedProduct = vv.getText().toString();
                String[] S = SelectedProduct.split(","); //0: name, 1: id, 2: price
                ProductsID_ToCart.add(S[1]);
                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_LONG).show();
            }
        });

        Button CartBTN = (Button) findViewById(R.id.cartBTN);
        CartBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CartActivity.class);
                intent.putExtra("ProductsIDs", ProductsID_ToCart);
                intent.putExtra("Username", Username);
                startActivity(intent);
            }
        });
    }
}