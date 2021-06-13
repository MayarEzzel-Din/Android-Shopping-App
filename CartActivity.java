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

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        final String Username = getIntent().getStringExtra("Username");

        Cursor ProductCursor;
        DataBaseHelper db = new DataBaseHelper(getApplicationContext());
        final ArrayList<String> ProductsList = new ArrayList<>();
        ArrayList<String> IDsList = getIntent().getStringArrayListExtra("ProductsIDs");
        for(int i=0; i<IDsList.size(); i++)
        {
            ProductCursor = db.FindProduct_ByID(Integer.valueOf(IDsList.get(i)));
            String ProductInfoSTR = ProductCursor.getString(1) + ","+ ProductCursor.getString(0)+ "," + ProductCursor.getString(2) + " $"; //Name + Price
            ProductsList.add(ProductInfoSTR);
        }
        final ArrayAdapter<String> ProductsAdapter = new ArrayAdapter<>(CartActivity.this, android.R.layout.simple_list_item_1, ProductsList);
        final GridView MyGridView = findViewById(R.id.MyGridView);
        MyGridView.setAdapter(ProductsAdapter);

        MyGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                TextView vv = (TextView) view;
                String SelectedProduct = vv.getText().toString();
                int index = FindProduct(ProductsList, SelectedProduct);
                ProductsList.remove(index);
                ProductsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        Button CheckOutBTN = (Button) findViewById(R.id.CheckOutBTN);
        CheckOutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                intent.putExtra("ProductsList", ProductsList);
                intent.putExtra("Username", Username);
                startActivity(intent);
            }
        });
    }

    private int FindProduct(ArrayList<String>L, String Product)
    {
        for(int i=0; i<L.size(); i++)
            if(L.get(i).equals(Product))
                return i;
        return -1;
    }
}