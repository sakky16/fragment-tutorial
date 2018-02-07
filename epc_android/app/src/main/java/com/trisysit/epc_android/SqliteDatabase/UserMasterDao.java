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
 * DAO for table "USER_MASTER".
*/
public class UserMasterDao extends AbstractDao<UserMaster, String> {

    public static final String TABLENAME = "USER_MASTER";

    /**
     * Properties of entity UserMaster.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property FirstName = new Property(0, String.class, "firstName", false, "FIRST_NAME");
        public final static Property LastName = new Property(1, String.class, "lastName", false, "LAST_NAME");
        public final static Property UserId = new Property(2, String.class, "userId", true, "USER_ID");
        public final static Property Username = new Property(3, String.class, "username", false, "USERNAME");
        public final static Property UserPin = new Property(4, String.class, "userPin", false, "USER_PIN");
        public final static Property PunchedIn = new Property(5, Boolean.class, "punchedIn", false, "PUNCHED_IN");
    }


    public UserMasterDao(DaoConfig config) {
        super(config);
    }
    
    public UserMasterDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_MASTER\" (" + //
                "\"FIRST_NAME\" TEXT," + // 0: firstName
                "\"LAST_NAME\" TEXT," + // 1: lastName
                "\"USER_ID\" TEXT PRIMARY KEY NOT NULL ," + // 2: userId
                "\"USERNAME\" TEXT," + // 3: username
                "\"USER_PIN\" TEXT," + // 4: userPin
                "\"PUNCHED_IN\" INTEGER);"); // 5: punchedIn
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_MASTER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserMaster entity) {
        stmt.clearBindings();
 
        String firstName = entity.getFirstName();
        if (firstName != null) {
            stmt.bindString(1, firstName);
        }
 
        String lastName = entity.getLastName();
        if (lastName != null) {
            stmt.bindString(2, lastName);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(4, username);
        }
 
        String userPin = entity.getUserPin();
        if (userPin != null) {
            stmt.bindString(5, userPin);
        }
 
        Boolean punchedIn = entity.getPunchedIn();
        if (punchedIn != null) {
            stmt.bindLong(6, punchedIn ? 1L: 0L);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserMaster entity) {
        stmt.clearBindings();
 
        String firstName = entity.getFirstName();
        if (firstName != null) {
            stmt.bindString(1, firstName);
        }
 
        String lastName = entity.getLastName();
        if (lastName != null) {
            stmt.bindString(2, lastName);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(4, username);
        }
 
        String userPin = entity.getUserPin();
        if (userPin != null) {
            stmt.bindString(5, userPin);
        }
 
        Boolean punchedIn = entity.getPunchedIn();
        if (punchedIn != null) {
            stmt.bindLong(6, punchedIn ? 1L: 0L);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2);
    }    

    @Override
    public UserMaster readEntity(Cursor cursor, int offset) {
        UserMaster entity = new UserMaster( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // firstName
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // lastName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // username
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // userPin
            cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0 // punchedIn
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserMaster entity, int offset) {
        entity.setFirstName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setLastName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUsername(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUserPin(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPunchedIn(cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);
     }
    
    @Override
    protected final String updateKeyAfterInsert(UserMaster entity, long rowId) {
        return entity.getUserId();
    }
    
    @Override
    public String getKey(UserMaster entity) {
        if(entity != null) {
            return entity.getUserId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserMaster entity) {
        return entity.getUserId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
