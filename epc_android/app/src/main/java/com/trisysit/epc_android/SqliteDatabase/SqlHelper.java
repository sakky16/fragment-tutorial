package com.trisysit.epc_android.SqliteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by trisys on 7/12/17.
 */

public class SqlHelper extends SQLiteOpenHelper {

    private static final String TAG = SqlHelper.class.getSimpleName();

    public SqlHelper(Context context) {
        super(context, DatabaaseUtils.DATABASE_NAME, null, DatabaaseUtils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaaseUtils.CREATE_TB_ATTENDANCE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaaseUtils.ATTENDANCE_TB);
        db.execSQL(DatabaaseUtils.CREATE_TB_ATTENDANCE);


    }
}
