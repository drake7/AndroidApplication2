package com.example.lefties;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    final static  String  DATABASE_NAME = "Lefties.db";
    final static int DATABASE_VERSION = 11;


    // TABLE 1: Account_Table
    //Raiyan-added password field
    final static String TABLE1_NAME = "account_table";
    final static String T1COL_1 = "account_Id";
    final static String T1COL_2 = "account_name";
    final static String T1COL_3 = "account_type";
    final static String T1COL_4 = "account_email";
    final static String T1COL_5 = "account_password";
    final static String T1COL_6 = "account_phone";
    final static String T1COL_7 = "account_address";
    final static String T1COL_8 = "account_city";


    //TABLE 2 : Restaurant
    final static String TABLE2_NAME = "restaurant_table";
    final static String T2COL_1 = "restaurant_Id";
    final static String T2COL_2 = "restaurant_type";
    final static String T2COL_3 = "account_Id";

    //TABLE 3: Food Table

    final static String TABLE3_NAME = "food_table";
    final static String T3COL_1 = "food_Id";
    final static String T3COL_2 = "account_Id";  //FK
    final static String T3COL_3 = "food_name";
    final static String T3COL_4 = "food_discount_price";
    final static String T3COL_5 = "food_regular_price";
    final static String T3COL_6 = "food_qty";

    //Table 4 : Order Table

    final static String TABLE4_NAME = "order_table";
    final static String T4COL_1 = "order_Id";
    final static String T4COL_2 = "order_status";  //FK
    final static String T4COL_3 = "order_date";
    final static String T4COL_4 = "order_type";
    final static String T4COL_5 = "order_total";
    final static String T4COL_6 = "order_remind";
    final static String T4COL_7 = "customer_Id";
    final static String T4COL_8 = "restaurant_Id";


    //Table 5 : Cart Table
    final static String TABLE5_NAME = "cart_table";
    final static String T5COL_1 = "cart_Id";  //PK
    final static String T5COL_2 = "order_Id";  //FK
    final static String T5COL_3 = "food_Id";  //FK
    final static String T5COL_4 = "food_qty_ordered";
    final static String T5COL_5 = "checkout_status";
    final static String T5COL_6 = "customer_Id";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase database =this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Raiyan-added Password field, changed name from query1 to queryAccountTable
        String queryAccountTable = "CREATE TABLE " + TABLE1_NAME + "(" + T1COL_1 +
                " INTEGER PRIMARY KEY, " + T1COL_2 + " TEXT, " + T1COL_3 + " TEXT, " +
                T1COL_4 + " TEXT, "+ T1COL_5 + " TEXT, " + T1COL_6 + " TEXT, " + T1COL_7 + " TEXT, " + T1COL_8 + " TEXT)";


        sqLiteDatabase.execSQL(queryAccountTable);

        // Restaurant
        String query1 = "CREATE TABLE " + TABLE2_NAME + "(" + T2COL_1 +
                " INTEGER PRIMARY KEY, " + T2COL_2 + " TEXT," +  T2COL_3 + " TEXT)";

        sqLiteDatabase.execSQL(query1);

        // Food
        String query2 = "CREATE TABLE " + TABLE3_NAME + "(" + T3COL_1 +
                " INTEGER PRIMARY KEY, "  + T3COL_2 + " TEXT, " +
                T3COL_3 + " TEXT, "+ T3COL_4 + " REAL, " + T3COL_5 + " REAL, " + T3COL_6 + " INTEGER)";

        sqLiteDatabase.execSQL(query2);

        // Order
        String query3 = "CREATE TABLE " + TABLE4_NAME + "(" + T4COL_1 +
                " INTEGER PRIMARY KEY, "  + T4COL_2 + " TEXT, " +
                T4COL_3 + " TEXT, "+ T4COL_4 + " TEXT, "
                + T4COL_5 + " TEXT, " + T4COL_6 + " BOOLEAN, " +
                T4COL_7 + " TEXT, " + T4COL_8 + " TEXT )";

        sqLiteDatabase.execSQL(query3);

        // Cart
        String query4 = "CREATE TABLE " + TABLE5_NAME + "(" + T5COL_1 +
                " INTEGER PRIMARY KEY, "  + T5COL_2 + " TEXT, " +
                T5COL_3 + " TEXT, " +
                T5COL_4 + " TEXT, " + T5COL_5 + " BOOLEAN, " + T5COL_6 + " TEXT)";

        sqLiteDatabase.execSQL(query4);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE4_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE5_NAME);

        onCreate(sqLiteDatabase);

    }


    //Adding for account table
    //Minor Changes made by Raiyan, removing aid, it should be generated automatically
    //To see the original one look below
    public long addAccount(String aname, String atype, String aemail, String apassword, String aphone,
                              String aaddress, String acity)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T1COL_2, aname);
        values.put(T1COL_3, atype);
        values.put(T1COL_4, aemail);
        values.put(T1COL_5, apassword);
        values.put(T1COL_6, aphone);
        values.put(T1COL_7, aaddress);
        values.put(T1COL_8, acity);

        long l = sqLiteDatabase.insert(TABLE1_NAME,null,values);
        if(l > 0)
            return l;
        else
            return 0;

    }


    //Adding Restaurant
    public boolean addRestaurant(String Rtype , long aid)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T2COL_2, Rtype);
        values.put(T2COL_3, aid);

        long l = sqLiteDatabase.insert(TABLE2_NAME,null,values);
        if(l > 0)
            return true;
        else
            return false;
    }



    //Adding Food
    public long addFood(long aid, String fname, Double fdiscountprice, Double fregularprice, Integer fqty)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T3COL_2, aid);
        values.put(T3COL_3, fname);
        values.put(T3COL_4, fregularprice);
        values.put(T3COL_5, fdiscountprice);
        values.put(T3COL_6, fqty);

        long l = sqLiteDatabase.insert(TABLE3_NAME,null,values);
        return l;

    }

    public long addOrder(String ostatus, String odate, String otype,
                         Double ototal, boolean oremind,
                         Long custId, long restId)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(T4COL_2, ostatus);
        values.put(T4COL_3, odate);
        values.put(T4COL_4, otype);
        values.put(T4COL_5, ototal);
        values.put(T4COL_6, oremind);;
        values.put(T4COL_7, custId);
        values.put(T4COL_8, restId);


        long l = sqLiteDatabase.insert(TABLE4_NAME,null,values);
        return l;
    }

    public long createOrder(long custId, long restId, double total){
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        long orderId = addOrder(
                "PENDING",
                currentDate,
                "DELIVERY",
                total,
                false,
                custId,
                restId
        );
        return orderId;
    }

    public void updateCartWithOrder(long orderId, long acctId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "UPDATE cart_table SET "
                + " order_id=" + orderId + ", "
                + " checkout_status=1"
                + " WHERE "
                + "( checkout_status=0 AND"
                + " customer_Id=" + acctId + ")";
        sqLiteDatabase.execSQL(query);
    };

    //  :: CART ::

    public Cursor viewDataCart(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE5_NAME;
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }

    public Cursor viewCustomerCart(long acctId){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM cart_table WHERE customer_id=" + acctId + " AND NOT checkout_status";
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }



    // Adding cart
    public long addCart(long orderId, long foodId, int foodQty, boolean checkoutStatus, long acctId)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T5COL_2, orderId);
        values.put(T5COL_3, foodId);
        values.put(T5COL_4, foodQty);
        values.put(T5COL_5, checkoutStatus);
        values.put(T5COL_6, acctId);

        long l = sqLiteDatabase.insert(TABLE5_NAME,null,values);
        return l;
    }

    public boolean validateOnlyOneCart(){
        return false;
    }
    public long addFoodToTempCart(int foodId, long acctId){
        // validate

        return addCart(0, foodId, 1, false,
                Long.parseLong((String.valueOf(acctId))));
    }
    public void applyOrderId(Long orderId){
        // apply order Id to all un-checked out items

    }
    public void editCartQty(){

    }



    // Cart: Delete
    public Cursor resetCart(int id){
        // if previous items are not from same restaurant
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "DELETE FROM " + TABLE5_NAME
                + " WHERE checkout_status = false";
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }
    public boolean deleteCart(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int d = sqLiteDatabase.delete(TABLE5_NAME,"food_Id=?",
                new String[]{Integer.toString(id)});
        if(d>0)
            return true;
        else
            return false;
    }



    // READING DATA

    //Account
    //Raiyan-making changes to viewDataAccount method
    public Cursor viewDataAccount(String userEmail, String userPass){
        SQLiteDatabase database = this.getReadableDatabase();
//        String query = "SELECT * FROM " + TABLE1_NAME;
        String query = "SELECT * FROM " + TABLE1_NAME +
                " WHERE " + T1COL_4 + "=" + '"' + userEmail + '"' + " AND " +
                 T1COL_5 + "=" + '"' + userPass + '"';
        Cursor cursor = database.rawQuery(query,null);
        //String query = "SELECT * FROM " + TABLE1_NAME + " WHERE Id = ?";
        //Cursor cursor = database.rawQuery(query,new String[]{"2"});
        return cursor;
    }
    public Cursor viewAccountByName(String name) {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME +
                " WHERE " + T1COL_2 + " = '" +name + "'";
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }


    //Original viewDataAccount
    /* public Cursor viewDataAccount(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME;
        Cursor cursor = database.rawQuery(query,null);
        //String query = "SELECT * FROM " + TABLE1_NAME + " WHERE Id = ?";
        //Cursor cursor = database.rawQuery(query,new String[]{"2"});
        return cursor;
    }*/

    //Restaurant
    public Cursor viewDataRest(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE2_NAME;
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }

    //Food
    public Cursor viewDataFoodById(long foodId){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE3_NAME + " WHERE food_Id=" + foodId;
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }
    public Cursor viewDataFood(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE3_NAME;
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }
    public Cursor viewDataFoodWithRestaurantName(){
        SQLiteDatabase database = this.getReadableDatabase();

//        String query = "SELECT * FROM " + TABLE3_NAME +
//                " LEFT OUTER JOIN " + TABLE2_NAME ;
        String query = "SELECT * FROM  food_table LEFT OUTER JOIN account_table  WHERE food_table.account_Id = account_table.account_Id";
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }
    public Cursor viewDataFoodByAcctId(long acctId){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE3_NAME
                + " WHERE account_Id = " + String.valueOf(acctId);
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }
    public Cursor viewDataFoodByOrderId(long orderId){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE3_NAME
                + " WHERE order_Id = " + String.valueOf(orderId);
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }
    public Cursor viewDataFoodByRestaurant(long acctId){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE3_NAME
                + " WHERE " + T3COL_2 + "=" + acctId;
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }

    public Cursor viewDataFoodF(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE3_NAME + " WHERE account_Id = 1";
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }

    public Cursor viewDataFoodM(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE3_NAME + " WHERE account_Id = 2";
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }

    public Cursor viewDataFoodP(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE3_NAME + " WHERE account_Id = 3";
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }

    public Cursor viewDataFoodC(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE3_NAME + " WHERE account_Id = 4";
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }

    public Cursor viewDataFoodCh(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE3_NAME + " WHERE account_Id = 5";
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }

    public Cursor viewDataFoodVeg(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE3_NAME + " WHERE account_Id = 6";
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }

//    public Cursor viewDataFoodByState(){
//        SQLiteDatabase database = this.getReadableDatabase();
//        String query = "SELECT * FROM " + TABLE3_NAME + " JOIN " +
//                TABLE1_NAME + " ON " + TABLE3_NAME + ".account_Id = " + TABLE1_NAME + ".account_Id WHERE " +
//                TABLE1_NAME + ".CITY = 'SURREY'";
//        Cursor cursor = database.rawQuery(query, null);
//        return cursor;
//    }


    //Order
    public Cursor viewDataOrder(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE4_NAME;
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }

    //Deleting the data

    //Account
    public boolean deleteRecAccount(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int d = sqLiteDatabase.delete(TABLE1_NAME,"account_Id=?",
                new String[]{Integer.toString(id)});
        if(d>0)
            return true;
        else
            return false;
    }

    //Restaurant
    public boolean deleteRecRest(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int d = sqLiteDatabase.delete(TABLE2_NAME,"restaurant_Id=?",
                new String[]{Integer.toString(id)});
        if(d>0)
            return true;
        else
            return false;
    }

    //Food
    public boolean deleteRecFood(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int d = sqLiteDatabase.delete(TABLE3_NAME,T3COL_1 + "=?",
                new String[]{Integer.toString(id)});
        if(d>0)
            return true;
        else
            return false;
    }

    //Order
    public boolean deleteRecOrder(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int d = sqLiteDatabase.delete(TABLE4_NAME,"order_Id=?",
                new String[]{Integer.toString(id)});
        if(d>0)
            return true;
        else
            return false;
    }



    ///Updating the tables

    //updating account type
    public boolean updateRecAccount(int id,String c){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T1COL_3,c);
        int u = sqLiteDatabase.update(TABLE1_NAME,contentValues,"id=?",
                new String[]{Integer.toString(id)});
        if(u>0)
            return true;
        else
            return false;
    }



}
