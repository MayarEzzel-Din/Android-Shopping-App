package com.example.shoppingapp;


public class Product
{
    String ProductName, CategoryName;
    float Price;
    int Quantity;

    public Product(){}
    public Product(String ProductName, String CategoryName, float Price, int Quantity)
    {
        this.ProductName = ProductName;
        this.CategoryName = CategoryName;
        this.Price = Price;
        this.Quantity = Quantity;
    }
}

