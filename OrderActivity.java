package com.example.shoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        final ArrayList<String> ProductsList = getIntent().getStringArrayListExtra("ProductsList");
        TextView TotalCostTXT = (TextView) findViewById(R.id.TotalCostTXT);
        TotalCostTXT.setText(String.valueOf(CalculateCost(P_List(ProductsList))) + "  $");

        Button OrderBTN = findViewById(R.id.OrderBTN);
        OrderBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper db_helper = new DataBaseHelper(getApplicationContext());
                String Username = getIntent().getStringExtra("Username");
                Cursor c = db_helper.FindUser_ByUsername(Username);
                EditText AddressTXT = (EditText) findViewById(R.id.AddressTXT);
                Date Today = Calendar.getInstance().getTime();

                User user = new User(c.getString(0), c.getString(1), c.getString(2), c.getString(3), "Engineer", c.getString(4));
                Order myOrder = new Order(user, String.valueOf(Today), AddressTXT.getText().toString(), P_List(ProductsList));
                db_helper.CreateOrder(myOrder);
                Toast.makeText(getApplicationContext(), "Ordered", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(OrderActivity.this, HomeActivity.class);
                intent.putExtra("Username", Username);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Product> P_List(ArrayList<String> LP)
    {
        ArrayList<Product> L = new ArrayList<>();
        DataBaseHelper db_helper = new DataBaseHelper(getApplicationContext());

        for(int i=0; i<LP.size(); i++)
        {
            String[] P = LP.get(i).split(","); //0-> Name, 1-> ID, 2->
            Cursor c = db_helper.FindProduct_ByID(Integer.valueOf(P[1]));
            Product prod = new Product(P[0], c.getString(4), Float.valueOf(c.getString(2)), Integer.valueOf(c.getShort(3)));
            L.add(prod);

            db_helper.UpdateProductQuantity(Integer.valueOf(P[1]), prod.Quantity-1);
        }
        return L;
    }
    private float CalculateCost(ArrayList<Product> PL)
    {
        float total = 0;
        for(int i=0; i<PL.size(); i++)
            total += PL.get(i).Price;
        return total;
    }
}