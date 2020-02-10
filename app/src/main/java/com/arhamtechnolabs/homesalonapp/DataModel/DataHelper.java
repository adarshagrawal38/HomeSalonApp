package com.arhamtechnolabs.homesalonapp.DataModel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataHelper {


    Context context;
    SQLiteDatabase database;
    String tableName = "cart4";

    public DataHelper(Context c) {
        context  = c;
        database = context.openOrCreateDatabase("Billing.db", Context.MODE_PRIVATE, null);
        Log.i("Database","Created");
        database.execSQL("Create table if NOT EXISTS "+tableName+" (id int, category_name varchar, features varchar, amount varchar, image varchar, time int, numberOfTimes int, numberOfProducts int, amountOfProduct int)");
        Log.i("Databse", "Table created");

    }

    public void insertData(CartData data) {
        try{

            String insertQuery = "Insert into "+tableName+" values("+data.getId()+", '"+data.getCategoryName()+"','"+data.getFeatures()+"','"+data.getAmount()+"','"+data.getImg()+"',"+data.getTimeRequired()+","+ data.getNumberOfTime()+"," +data.getNumberOfProducts()+","+data.getAmountOfProducts() +")";
            database.execSQL(insertQuery);
            Log.i("Database", "Data inserted");



        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public  List<CartData> fetchRequest() {
        List<CartData> list = new ArrayList<>();
        List<CartData> newList = new ArrayList<>();
        try{
            Cursor c = database.rawQuery("Select * from "+tableName, null);
            int id_index = c.getColumnIndex("id");
            int category_index = c.getColumnIndex("category_name");
            int feature_index = c.getColumnIndex("features");
            int amount_index = c.getColumnIndex("amount");
            int image_index = c.getColumnIndex("image");
            int time_index = c.getColumnIndex("time");
            int numberOf_TimeIndex= c.getColumnIndex("numberOfTimes");
            int numofProductIndex = c.getColumnIndex("numberOfProducts");
            int productAmountIndex = c.getColumnIndex("amountOfProduct");

            c.moveToFirst();
            while (c!= null) {
                int a = c.getInt(id_index);
                String b = c.getString(category_index);
                String c1 = c.getString(feature_index);
                String d = c.getString(amount_index);
                String e = c.getString(image_index);
                int f = c.getInt(time_index);
                int g = c.getInt(numberOf_TimeIndex);
                int h = c.getInt(numofProductIndex);
                int i = c.getInt(productAmountIndex);

                CartData cartData = new CartData(a,b,c1,d,e,f, g, h, i);

                list.add(cartData);
                Log.i("Database", c.getString(0));
                c.moveToNext();
            }
            Log.i("Database", "OUT");


        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;

    }
    public String categoryList() {
        List<CartData>  data = fetchRequest();
        String result = "";
        for (CartData cartData: data) {
            result+=cartData.getCategoryName()+"%2C%20";
        }
        return result;
    }

    public void flushTable() {
        database.execSQL("Delete from "+tableName);
        Log.i("Database", "Cart item deleted");
    }
    public void deleteItem(CartData data) {
        database.execSQL("Delete from "+tableName+" where id = "+data.getId());
        Log.i("Database", "Item deleted");
    }

    public void deleteItem(int id) {
        database.execSQL("Delete from "+tableName+" where id = "+id);
        Log.i("Database", "Item deleted");
    }

    public void updateNumberOfTimes(int id, int n) {
        try{
            String updateQuery = "update " + tableName + " set numberOfTimes="+n +" where id="+id;
            database.execSQL(updateQuery);
            Log.i("Database", "Data Updated");
        }catch (Exception e) {
            Log.i("UpdateExection", e.getMessage());
        }
    }


    public void updateNumberOfProduct(int id, int n) {
        try{
            String updateQuery = "update " + tableName + " set numberOfProducts="+n +" where id="+id;
            database.execSQL(updateQuery);
            Log.i("Database", "Number of product Updated " +n);
        }catch (Exception e) {
            Log.i("UpdateExection", e.getMessage());
        }
    }



    public int getNumberOfTime(int id) {
        int n=0;
        try{
            Cursor c = database.rawQuery("Select * from "+tableName +" where id = "+id, null);

            int numberOf_TimeIndex= c.getColumnIndex("numberOfTimes");

            c.moveToFirst();
            while (c!= null) {
                n = c.getInt(numberOf_TimeIndex);
                c.moveToNext();
            }
            Log.i("Database", "Returned NumberOftimeItemPurchased");


        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return n;
    }

    public int getNumberOfProduct(int id) {
        int n=0;
        try{
            Cursor c = database.rawQuery("Select * from "+tableName +" where id = "+id, null);

            int productIndex= c.getColumnIndex("numberOfProducts");

            c.moveToFirst();
            while (c!= null) {
                n = c.getInt(productIndex);
                c.moveToNext();
            }
            Log.i("Database", "Returned NumberOftimeProductPurchased");


        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return n;
    }

    public int getTotalProductCost() {
        List<CartData> cartList = fetchRequest();

        /*Calculate product balance*/

        int total = 0;
        for (CartData data: cartList) {
            total+=data.amountOfProducts*data.numberOfProducts;

        }
        return total;
    }
    public int getTotalServiceCost() {
        List<CartData> cartList = fetchRequest();

        /*Calculate product balance*/

        int total = 0;
        for (CartData data: cartList) {
            total+=Integer.parseInt(data.getAmount())*data.getNumberOfTime();
        }
        return total;
    }

    public int getItemCount() {
        List<CartData> cartList = fetchRequest();
        if (cartList == null) return 0;
        int total = 0;
        for (CartData cart: cartList) {
            total+=cart.getNumberOfTime();
        }
        return  total;


    }

}
