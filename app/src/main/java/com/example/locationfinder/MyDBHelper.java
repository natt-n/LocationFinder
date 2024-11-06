package com.example.locationfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDBHelper extends SQLiteOpenHelper {
    private Context context;

    private static final String DB_NAME = "locationFinder.db";
    private static final int DB_VER = 2;

    private static final String TABLE_NAME = "myadds";
    private static final String ID_COL = "id";
    private static final String ADDRESS_COL = "address";
    private static final String LAT_COL = "latitude";
    private static final String LON_COL = "longitude";

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ADDRESS_COL + " TEXT, "
                + LAT_COL + " REAL, "
                + LON_COL + " REAL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addAddress(String address, Double latitude, Double longitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ADDRESS_COL, address);
        cv.put(LAT_COL, latitude);
        cv.put(LON_COL, longitude);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed to insert", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, "Successfully added new address", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            db.rawQuery(query, null);
        }

        return cursor;
    }

}
