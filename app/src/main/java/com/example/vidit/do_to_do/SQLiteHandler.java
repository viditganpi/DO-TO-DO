package com.example.vidit.do_to_do;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vidit on 07-05-2016.
 */
public class SQLiteHandler extends SQLiteOpenHelper{
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "task_db";

    private static final String TABLE = "tasks";
    private static final String KEY_ID = "id";
    private static final String COL_TASK_TITLE = "title";
    private static final String COL_TASK_LOCATION = "location";
    private static final String COL_TASK_DATE = "day";
    private static final String COL_TASK_MONTH = "month";
    private static final String COL_TASK_YEAR = "year";
    private static final String COL_TASK_HOUR = "hour";
    private static final String COL_TASK_MIN = "minute";
    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + COL_TASK_TITLE + " TEXT,"
                + COL_TASK_LOCATION + " TEXT ," + COL_TASK_DATE + " INT,"
                + COL_TASK_MONTH  + " INT," + COL_TASK_YEAR + " INT," + COL_TASK_HOUR + " INT," + COL_TASK_MIN + " INT"+ ")";
        db.execSQL(CREATE_EVENT_TABLE);

        Log.d(TAG, "Database tables created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // Create tables again
        onCreate(db);
    }
    public void addEvent(String title, String location, int day, int month,int year,  int hour, int min) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( COL_TASK_TITLE, title);
        values.put( COL_TASK_LOCATION, location);
        values.put( COL_TASK_DATE, day);
        values.put( COL_TASK_MONTH, month);
        values.put( COL_TASK_YEAR, year);
        values.put( COL_TASK_HOUR, hour);
        values.put( COL_TASK_MIN, min);

        // Inserting Row
        long id1 = db.insert(TABLE, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New task inserted into sqlite: " + id1);
    }

    public ArrayList<String> getEventsDetails() {
        HashMap<String, String> events = new HashMap<String, String>();
        ArrayList<String> data = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE;
        String temp = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                //events.put("id", cursor.getString(0));
                temp += cursor.getString(1) +" at"+ cursor.getString(2)+" on " + cursor.getString(3)+"-" +
                        cursor.getString(4)+"-" + cursor.getString(5)+" at " + cursor.getString(6)+":" + cursor.getString(7) + "\n" + "\n";
                data.add(temp);
            }
        }
        cursor.close();
        db.close();
        Log.d(TAG,data.toString());
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + events.toString());

        return data;
    }

    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }


    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
