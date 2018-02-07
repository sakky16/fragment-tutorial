package com.trisysit.epc_task_android.utils;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.trisysit.epc_task_android.EPCTaskUpdateAppication;
import com.trisysit.epc_task_android.database.DaoMaster;
import com.trisysit.epc_task_android.database.DaoSession;
import com.trisysit.epc_task_android.database.Task;
import com.trisysit.epc_task_android.database.TaskDao;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trisys on 7/2/18.
 */

public class DataBaseManager {
    private static DataBaseManager instance;
    /**
     * The Android activity reference for access to DatabaseManager.
     */

    private DaoMaster.OpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private DataBaseManager() {
        mHelper = new DaoMaster.DevOpenHelper(EPCTaskUpdateAppication.getContext(), "sms-heartbeat", null);
    }


    public static DataBaseManager getInstance() {
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }


    public void closeDbConnections() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
    }


    public void openReadableDb() throws SQLiteException {
        database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }


    public void openWritableDb() throws SQLiteException {
        database = mHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();

    }
    public synchronized List<Task> getTaskListForSync(){
        List<Task> taskList=new ArrayList<>();
        try {
            openReadableDb();
            TaskDao dao=daoSession.getTaskDao();
            WhereCondition condition=TaskDao.Properties.Status.eq(AppUtils.IN_PROGRESS);
            QueryBuilder<Task> queryBuilder=dao.queryBuilder().where(condition);
            taskList=queryBuilder.list();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return taskList;
    }
    public void clearTable(){
        openReadableDb();
        daoSession.getTaskDao().deleteAll();
        daoSession.clear();
    }

}
