package com.example.shoppingapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper
{
    private static String dbName = "E-CommerceDataBase";
    SQLiteDatabase myDB;

    public DataBaseHelper(Context context) {
        super(context, dbName, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String command = "create table Customers(CustID integer primary key autoincrement, CustName text not null, Username Text not null, Password text not null," +
                "Gender text not null, Birthdate text not null, Job text not null)";
        db.execSQL(command);

        command = "create table Categories(CatID integer primary key autoincrement, CatName text not null)";
        db.execSQL(command);

        command = "create table Orders(OrdID integer primary key autoincrement, OrdDate text not null, " +
                "Address Text not null, CustID integer not null, FOREIGN KEY(CustID) REFERENCES Customers(CustID))";
        db.execSQL(command);

        command = "create table Products(ProID integer primary key autoincrement, ProName text not null, Price float not null, Quantity integer," +
                "CatID integer not null, FOREIGN KEY(CatID) REFERENCES Categories(CatID))";
        db.execSQL(command);

        command = "create table OrderDetails(OrdID integer not null, ProdID integer not null, Quantity integer not null, " +
                "FOREIGN KEY(OrdID) REFERENCES Orders(OrdID), FOREIGN KEY(ProdID) REFERENCES Products(ProID))";
        db.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists Customers");
        db.execSQL("drop table if exists Categories");
        db.execSQL("drop table if exists Orders");
        db.execSQL("drop table if exists Products");
        db.execSQL("drop table if exists OrderDetails");

        onCreate(db);
    }


    public void AddCustomer(User user)
    {
        ContentValues row = new ContentValues();
        row.put("CustName", user.CustomerName);
        row.put("Username", user.Username);
        row.put("Password", user.Password);
        row.put("Gender", user.Gender);
        row.put("Birthdate", user.BD);
        row.put("Job", user.Job);

        myDB = getWritableDatabase();
        myDB.insert("Customers", null, row);
        myDB.close();
    }
    public void AddProduct(Product prod)
    {
        ContentValues row = new ContentValues();
        row.put("ProName", prod.ProductName);
        row.put("Price", prod.Price);
        row.put("Quantity", prod.Quantity);
        row.put("CatID", 2);

        myDB = getWritableDatabase();
        myDB.insert("Products", null, row);
        myDB.close();
    }
    public void AddCategory(String CategoryName)
    {
        ContentValues row = new ContentValues();
        row.put("CatName", CategoryName);

        myDB = getWritableDatabase();
        myDB.insert("Categories", null, row);
        myDB.close();
    }
    public Cursor FindUser_ByUsername(String Username)
    {
        myDB = getReadableDatabase();
        String[] RowDetails = {"CustName", "Username", "Password", "Gender", "Birthdate"};
        Cursor cursor = myDB.query("Customers", RowDetails, "Username=?", new String[]{Username}, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        myDB.close();
        return cursor;
    }
    public boolean CheckUserExistence(String Username, String Password)
    {
        myDB = getReadableDatabase();
        String[] RowDetails = {"CustName", "Username", "Password", "Gender", "Birthdate"};
        Cursor cursor = myDB.query("Customers", RowDetails, "Username=? and Password=?", new String[]{Username, Password}, null, null, null);
        if(cursor.getCount() == 0)
            return false;
        return true;
    }

    public boolean CheckUsername(String Username)
    {
        myDB = getReadableDatabase();
        String[] RowDetails = {"CustName", "Username", "Password", "Gender", "Birthdate"};
        Cursor cursor = myDB.query("Customers", RowDetails, "Username=?", new String[]{Username}, null, null, null);
        if(cursor.getCount() == 0)
            return true;
        return false;
    }

    public Cursor GetAllCategories()
    {
        myDB = getReadableDatabase();
        Cursor cursor = myDB.rawQuery("select * from Categories", null);
        if(cursor != null)
            cursor.moveToFirst();
        myDB.close();
        return cursor;
    }

    public Cursor GetAllProducts()
    {
        myDB = getReadableDatabase();
        Cursor cursor = myDB.rawQuery("select * from Products", null);
        if(cursor != null)
            cursor.moveToFirst();
        myDB.close();
        return cursor;
    }

    public Cursor FindProduct_ByID(int ID)
    {
        myDB = getReadableDatabase();
        Cursor cursor = myDB.rawQuery("select * from Products where ProID=" + ID, null);
        if(cursor != null)
            cursor.moveToFirst();
        myDB.close();
        return cursor;
    }

    public Cursor FindProduct_ByName(String Name)
    {
        myDB = getReadableDatabase();
        String[] RowDetails = {"ProName", "Price", "Quantity", "CatID"};
        Cursor cursor = myDB.query("Products", RowDetails, "ProName=?", new String[]{Name}, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        myDB.close();
        return cursor;
    }


    public void UpdateProductQuantity(Integer ID , Integer newQuantity)
    {
        myDB = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("Quantity" , newQuantity);
        myDB.update("Products" , row , "ProID like '"+ID+"' " , null);
        myDB.close();
    }


    public void CreateOrder(Order order)
    {
        ContentValues row = new ContentValues();
        row.put("OrdDate", order.OrderDate);
        row.put("Address", order.Address);
        row.put("CustID", 1);

        myDB = getWritableDatabase();
        myDB.insert("Orders", null, row);

        row = new ContentValues();

        myDB.close();
    }
}
