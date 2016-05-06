package com.example.vidit.do_to_do;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = Main2Activity.class.getSimpleName();
    private TaskDbHelper mHelper;
    private ListView mlistview;
    private ArrayAdapter<String> mAdapter;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mHelper = new TaskDbHelper(this);
        mlistview = (ListView) findViewById(R.id.list);
        button = (Button) findViewById(R.id.register);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Main2Activity.this,RegisterTask.class);
                        startActivity(i);
                        finish();
                    }
                }
        );
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            Log.d(TAG, "Task: " + cursor.getString(idx));
        }
        cursor.close();
        db.close();
        updateUI();
    }
    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TaskContract.TaskEntry.TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        /*Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE,TaskContract.TaskEntry.COL_TASK_LOCATION
                ,TaskContract.TaskEntry.COL_TASK_YEAR,TaskContract.TaskEntry.COL_TASK_MONTH,TaskContract.TaskEntry.COL_TASK_DATE
                        ,TaskContract.TaskEntry.COL_TASK_HOUR,TaskContract.TaskEntry.COL_TASK_MIN},
                null, null, null, null, null);*/
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            int idx1 = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_LOCATION);
            int idx2 = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_YEAR);
            int idx3 = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_MONTH);
            int idx4 = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DATE);
            int idx5 = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_HOUR);
            int idx6 = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_MIN);
            String ans = cursor.getString(idx) + cursor.getString(idx1) + cursor.getString(idx2) + cursor.getString(idx3)
                    + cursor.getString(idx4) + cursor.getString(idx5) + cursor.getString(idx6);
            taskList.add(ans);
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_todo,
                    R.id.task_title,
                    taskList);
            mlistview.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }
}
