package com.example.vidit.do_to_do;

import android.provider.BaseColumns;

/**
 * Created by vidit on 07-05-2016.
 */
public class TaskContract {
    public static final String DB_NAME = "com.vidit.do_to_do_db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_LOCATION = "location";
        public static final String COL_TASK_DATE = "day";
        public static final String COL_TASK_MONTH = "month";
        public static final String COL_TASK_YEAR = "year";
        public static final String COL_TASK_HOUR = "hour";
        public static final String COL_TASK_MIN = "minute";
    }
}
