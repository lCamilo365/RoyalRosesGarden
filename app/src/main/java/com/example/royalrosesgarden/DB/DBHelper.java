package com.example.royalrosesgarden.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDataBase;

    public DBHelper(Context context){
        super(context, "RoyalRoseGarden.db", null,1);
        sqLiteDataBase = this.getWritableDatabase();


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE PRODUCTS("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR," +
                "description VARCHAR," +
                "price DOUBLE," +
                "image BLOB" +
                ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PRODUCTS");
    }

    // metodos crud
    public void insertData(String name, String description, Double price, byte[] image){
        String sql = "INSERT INTO PRODUCTS VALUES (null, ?, ?, ?, ?)";
        SQLiteStatement statement = sqLiteDataBase.compileStatement(sql);
        statement.bindString(1,name);
        statement.bindString(2,description);
        statement.bindDouble(3, price);
        statement.bindBlob(4, image);

        statement.executeInsert();

    }

    public Cursor getData(){
        Cursor cursor = sqLiteDataBase.rawQuery( "SELECT * FROM PRODUCTS", null);
        return cursor;
    }

    public Cursor getDataById(String id){
        Cursor cursor = sqLiteDataBase.rawQuery("SELECT * FROM PRODUCTS WHERE id = "+id, null);
        return cursor;
    }

    public void deleteDataById(String id){
        sqLiteDataBase.execSQL("DELETE FROM PRODUCTS WHERE id = " + id);
    }

    public void updateDataById(String id, String name, String description, Double price, byte[] image){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("price", price);
        contentValues.put("image", image);
        sqLiteDataBase.update("PRODUCTS",contentValues,"id = ?",new String[]{id});
    }

}
