package com.example.shoppingapp;

public class User
{
    public String CustomerName, Username, Password, Gender, Job, BD;
    public User()
    {

    }
    public User(String CustomerName, String Username, String Password, String Gender, String Job, String BD)
    {
        this.CustomerName = CustomerName;
        this.Username = Username;
        this.Password = Password;
        this.Gender = Gender;
        this.Job = Job;
        this.BD = BD;
    }
}

