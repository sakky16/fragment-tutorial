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
 * DAO for table "ACTIVITY_MASTER".
*/
public class ActivityMasterDao extends AbstractDao<ActivityMaster, String> {

    public static final String TABLENAME = "ACTIVITY_MASTER";

    /**
     * Properties of entity ActivityMaster.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Schedule = new Property(0, String.class, "schedule", true, "SCHEDULE");
        public final static Property SrNo = new Property(1, String.class, "srNo", false, "SR_NO");
        public final static Property SchedulePart = new Property(2, String.class, "schedulePart", false, "SCHEDULE_PART");
        public final static Property Unit = new Property(3, String.class, "unit", false, "UNIT");
        public final static Property LongDescription = new Property(4, String.class, "longDescription", false, "LONG_DESCRIPTION");
        public final static Property Particular = new Property(5, String.class, "particular", false, "PARTICULAR");
        public final static Property SubScheduleMultiplier = new Property(6, String.class, "subScheduleMultiplier", false, "SUB_SCHEDULE_MULTIPLIER");
        public final static Property ScheduleMultiplier = new Property(7, String.class, "scheduleMultiplier", false, "SCHEDULE_MULTIPLIER");
        public final static Property MarkRound = new Property(8, String.class, "markRound", false, "MARK_ROUND");
        public final static Property ProjectId = new Property(9, String.class, "projectId", false, "PROJECT_ID");
    }


    public ActivityMasterDao(DaoConfig config) {
        super(config);
    }
    
    public ActivityMasterDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ACTIVITY_MASTER\" (" + //
                "\"SCHEDULE\" TEXT PRIMARY KEY NOT NULL ," + // 0: schedule
                "\"SR_NO\" TEXT," + // 1: srNo
                "\"SCHEDULE_PART\" TEXT," + // 2: schedulePart
                "\"UNIT\" TEXT," + // 3: unit
                "\"LONG_DESCRIPTION\" TEXT," + // 4: longDescription
                "\"PARTICULAR\" TEXT," + // 5: particular
                "\"SUB_SCHEDULE_MULTIPLIER\" TEXT," + // 6: subScheduleMultiplier
                "\"SCHEDULE_MULTIPLIER\" TEXT," + // 7: scheduleMultiplier
                "\"MARK_ROUND\" TEXT," + // 8: markRound
                "\"PROJECT_ID\" TEXT);"); // 9: projectId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ACTIVITY_MASTER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ActivityMaster entity) {
        stmt.clearBindings();
 
        String schedule = entity.getSchedule();
        if (schedule != null) {
            stmt.bindString(1, schedule);
        }
 
        String srNo = entity.getSrNo();
        if (srNo != null) {
            stmt.bindString(2, srNo);
        }
 
        String schedulePart = entity.getSchedulePart();
        if (schedulePart != null) {
            stmt.bindString(3, schedulePart);
        }
 
        String unit = entity.getUnit();
        if (unit != null) {
            stmt.bindString(4, unit);
        }
 
        String longDescription = entity.getLongDescription();
        if (longDescription != null) {
            stmt.bindString(5, longDescription);
        }
 
        String particular = entity.getParticular();
        if (particular != null) {
            stmt.bindString(6, particular);
        }
 
        String subScheduleMultiplier = entity.getSubScheduleMultiplier();
        if (subScheduleMultiplier != null) {
            stmt.bindString(7, subScheduleMultiplier);
        }
 
        String scheduleMultiplier = entity.getScheduleMultiplier();
        if (scheduleMultiplier != null) {
            stmt.bindString(8, scheduleMultiplier);
        }
 
        String markRound = entity.getMarkRound();
        if (markRound != null) {
            stmt.bindString(9, markRound);
        }
 
        String projectId = entity.getProjectId();
        if (projectId != null) {
            stmt.bindString(10, projectId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ActivityMaster entity) {
        stmt.clearBindings();
 
        String schedule = entity.getSchedule();
        if (schedule != null) {
            stmt.bindString(1, schedule);
        }
 
        String srNo = entity.getSrNo();
        if (srNo != null) {
            stmt.bindString(2, srNo);
        }
 
        String schedulePart = entity.getSchedulePart();
        if (schedulePart != null) {
            stmt.bindString(3, schedulePart);
        }
 
        String unit = entity.getUnit();
        if (unit != null) {
            stmt.bindString(4, unit);
        }
 
        String longDescription = entity.getLongDescription();
        if (longDescription != null) {
            stmt.bindString(5, longDescription);
        }
 
        String particular = entity.getParticular();
        if (particular != null) {
            stmt.bindString(6, particular);
        }
 
        String subScheduleMultiplier = entity.getSubScheduleMultiplier();
        if (subScheduleMultiplier != null) {
            stmt.bindString(7, subScheduleMultiplier);
        }
 
        String scheduleMultiplier = entity.getScheduleMultiplier();
        if (scheduleMultiplier != null) {
            stmt.bindString(8, scheduleMultiplier);
        }
 
        String markRound = entity.getMarkRound();
        if (markRound != null) {
            stmt.bindString(9, markRound);
        }
 
        String projectId = entity.getProjectId();
        if (projectId != null) {
            stmt.bindString(10, projectId);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public ActivityMaster readEntity(Cursor cursor, int offset) {
        ActivityMaster entity = new ActivityMaster( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // schedule
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // srNo
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // schedulePart
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // unit
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // longDescription
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // particular
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // subScheduleMultiplier
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // scheduleMultiplier
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // markRound
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // projectId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ActivityMaster entity, int offset) {
        entity.setSchedule(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setSrNo(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSchedulePart(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUnit(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLongDescription(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setParticular(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSubScheduleMultiplier(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setScheduleMultiplier(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setMarkRound(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setProjectId(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final String updateKeyAfterInsert(ActivityMaster entity, long rowId) {
        return entity.getSchedule();
    }
    
    @Override
    public String getKey(ActivityMaster entity) {
        if(entity != null) {
            return entity.getSchedule();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ActivityMaster entity) {
        return entity.getSchedule() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
