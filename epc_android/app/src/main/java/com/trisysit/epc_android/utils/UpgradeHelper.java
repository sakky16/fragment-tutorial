package com.trisysit.epc_android.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.trisysit.epc_android.database.DaoMaster;


/**
 * Created by trisys on 1/8/17.
 */

public class UpgradeHelper extends DaoMaster.OpenHelper {
    public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {

        }
    }
}

