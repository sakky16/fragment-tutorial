package com.trisysit.epc_android.SqliteDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DAILY_UPDATE_MASTER".
*/
public class DailyUpdateMasterDao extends AbstractDao<DailyUpdateMaster, Void> {

    public static final String TABLENAME = "DAILY_UPDATE_MASTER";

    /**
     * Properties of entity DailyUpdateMaster.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Schedule = new Property(0, String.class, "schedule", false, "SCHEDULE");
        public final static Property SchedulePart = new Property(1, String.class, "schedulePart", false, "SCHEDULE_PART");
        public final static Property Subject = new Property(2, String.class, "subject", false, "SUBJECT");
        public final static Property Multiplier = new Property(3, String.class, "multiplier", false, "MULTIPLIER");
    }


    public DailyUpdateMasterDao(DaoConfig config) {
        super(config);
    }
    
    public DailyUpdateMasterDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DAILY_UPDATE_MASTER\" (" + //
                "\"SCHEDULE\" TEXT," + // 0: schedule
                "\"SCHEDULE_PART\" TEXT," + // 1: schedulePart
                "\"SUBJECT\" TEXT," + // 2: subject
                "\"MULTIPLIER\" TEXT);"); // 3: multiplier
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DAILY_UPDATE_MASTER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DailyUpdateMaster entity) {
        stmt.clearBindings();
 
        String schedule = entity.getSchedule();
        if (schedule != null) {
            stmt.bindString(1, schedule);
        }
 
        String schedulePart = entity.getSchedulePart();
        if (schedulePart != null) {
            stmt.bindString(2, schedulePart);
        }
 
        String subject = entity.getSubject();
        if (subject != null) {
            stmt.bindString(3, subject);
        }
 
        String multiplier = entity.getMultiplier();
        if (multiplier != null) {
            stmt.bindString(4, multiplier);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DailyUpdateMaster entity) {
        stmt.clearBindings();
 
        String schedule = entity.getSchedule();
        if (schedule != null) {
            stmt.bindString(1, schedule);
        }
 
        String schedulePart = entity.getSchedulePart();
        if (schedulePart != null) {
            stmt.bindString(2, schedulePart);
        }
 
        String subject = entity.getSubject();
        if (subject != null) {
            stmt.bindString(3, subject);
        }
 
        String multiplier = entity.getMultiplier();
        if (multiplier != null) {
            stmt.bindString(4, multiplier);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public DailyUpdateMaster readEntity(Cursor cursor, int offset) {
        DailyUpdateMaster entity = new DailyUpdateMaster( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // schedule
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // schedulePart
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // subject
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // multiplier
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DailyUpdateMaster entity, int offset) {
        entity.setSchedule(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setSchedulePart(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSubject(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMultiplier(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(DailyUpdateMaster entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(DailyUpdateMaster entity) {
        return null;
    }

    @Override
    public boolean hasKey(DailyUpdateMaster entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}