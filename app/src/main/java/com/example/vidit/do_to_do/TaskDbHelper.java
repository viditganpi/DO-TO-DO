package com.example.vidit.do_to_do;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by vidit on 07-05-2016.
 */
public class TaskDbHelper extends SQLiteOpenHelper{
    private static final String TAG = TaskDbHelper.class.getSimpleName();
    public TaskDbHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"here");
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + "("
                + TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY," + TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT,"
                + TaskContract.TaskEntry.COL_TASK_LOCATION + " TEXT ," + TaskContract.TaskEntry.COL_TASK_YEAR + " INT,"
                + TaskContract.TaskEntry.COL_TASK_MONTH + " INT," + TaskContract.TaskEntry.COL_TASK_DATE + " INT,"
                + TaskContract.TaskEntry.COL_TASK_HOUR + " INT," + TaskContract.TaskEntry.COL_TASK_MIN + " INT," + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
        getDbTableDetails();
        Log.d(TAG, "Database tables created");

    }
    public void getDbTableDetails() {
        SQLiteDatabase mDataBase;
        mDataBase = getReadableDatabase();
        Cursor dbCursor = mDataBase.query(TaskContract.TaskEntry.TABLE, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        for(int i=0;i<columnNames.length;i++){
            Log.d(TAG,columnNames[i]);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }
}
