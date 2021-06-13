package com.example.shoppingapp;

import java.util.ArrayList;

public class Order
{
    User Customer;
    String OrderDate, Address;
    ArrayList<Product> OrderedProducts;

    public Order(User user, String date, String Address, ArrayList<Product> P)
    {
        Customer = user;
        OrderDate = date;
        this.Address = Address;
        OrderedProducts = P;
    }
}
