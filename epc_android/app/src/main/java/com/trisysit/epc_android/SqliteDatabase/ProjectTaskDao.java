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
 * DAO for table "PROJECT_TASK".
*/
public class ProjectTaskDao extends AbstractDao<ProjectTask, String> {

    public static final String TABLENAME = "PROJECT_TASK";

    /**
     * Properties of entity ProjectTask.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property TaskId = new Property(0, String.class, "taskId", false, "TASK_ID");
        public final static Property Comment = new Property(1, String.class, "comment", false, "COMMENT");
        public final static Property Priority = new Property(2, String.class, "priority", false, "PRIORITY");
        public final static Property ReminderText = new Property(3, String.class, "reminderText", false, "REMINDER_TEXT");
        public final static Property ReminderUrl = new Property(4, String.class, "reminderUrl", false, "REMINDER_URL");
        public final static Property ManPowerReq = new Property(5, String.class, "manPowerReq", false, "MAN_POWER_REQ");
        public final static Property ActualManPowerReq = new Property(6, String.class, "actualManPowerReq", false, "ACTUAL_MAN_POWER_REQ");
        public final static Property PercentComplete = new Property(7, String.class, "percentComplete", false, "PERCENT_COMPLETE");
        public final static Property Subject = new Property(8, String.class, "subject", false, "SUBJECT");
        public final static Property Material_count = new Property(9, Integer.class, "material_count", false, "MATERIAL_COUNT");
        public final static Property TaskCreatedDate = new Property(10, String.class, "taskCreatedDate", false, "TASK_CREATED_DATE");
        public final static Property TaskFor = new Property(11, String.class, "taskFor", false, "TASK_FOR");
        public final static Property Type = new Property(12, String.class, "type", false, "TYPE");
        public final static Property MobileId = new Property(13, String.class, "mobileId", true, "MOBILE_ID");
        public final static Property TaskStatus = new Property(14, String.class, "taskStatus", false, "TASK_STATUS");
        public final static Property TaskUpdatedDate = new Property(15, String.class, "taskUpdatedDate", false, "TASK_UPDATED_DATE");
        public final static Property AssignedTo = new Property(16, String.class, "assignedTo", false, "ASSIGNED_TO");
        public final static Property AssignedToName = new Property(17, String.class, "assignedToName", false, "ASSIGNED_TO_NAME");
        public final static Property LocationL1 = new Property(18, String.class, "locationL1", false, "LOCATION_L1");
        public final static Property LocationL2 = new Property(19, String.class, "locationL2", false, "LOCATION_L2");
        public final static Property LocationL3 = new Property(20, String.class, "locationL3", false, "LOCATION_L3");
        public final static Property LocationL4 = new Property(21, String.class, "locationL4", false, "LOCATION_L4");
        public final static Property LocationL5 = new Property(22, String.class, "locationL5", false, "LOCATION_L5");
        public final static Property ParentL1Id = new Property(23, String.class, "parentL1Id", false, "PARENT_L1_ID");
        public final static Property Contractor = new Property(24, String.class, "contractor", false, "CONTRACTOR");
        public final static Property ActualManpower = new Property(25, String.class, "actualManpower", false, "ACTUAL_MANPOWER");
        public final static Property TaskCreatedBy = new Property(26, String.class, "taskCreatedBy", false, "TASK_CREATED_BY");
        public final static Property TaskUpdatedBy = new Property(27, String.class, "taskUpdatedBy", false, "TASK_UPDATED_BY");
        public final static Property TaskStartDate = new Property(28, Long.class, "taskStartDate", false, "TASK_START_DATE");
        public final static Property TaskEndDate = new Property(29, Long.class, "taskEndDate", false, "TASK_END_DATE");
        public final static Property TaskEffort = new Property(30, String.class, "taskEffort", false, "TASK_EFFORT");
        public final static Property TaskActualStartDate = new Property(31, String.class, "taskActualStartDate", false, "TASK_ACTUAL_START_DATE");
        public final static Property TaskActualEndDate = new Property(32, String.class, "taskActualEndDate", false, "TASK_ACTUAL_END_DATE");
        public final static Property TaskActualEffort = new Property(33, String.class, "taskActualEffort", false, "TASK_ACTUAL_EFFORT");
        public final static Property ParentTaskId = new Property(34, String.class, "parentTaskId", false, "PARENT_TASK_ID");
        public final static Property PredecessorTaskId = new Property(35, String.class, "predecessorTaskId", false, "PREDECESSOR_TASK_ID");
        public final static Property ProjectId = new Property(36, String.class, "projectId", false, "PROJECT_ID");
        public final static Property Critical = new Property(37, Boolean.class, "critical", false, "CRITICAL");
        public final static Property Section = new Property(38, String.class, "section", false, "SECTION");
        public final static Property IsTrackable = new Property(39, Boolean.class, "isTrackable", false, "IS_TRACKABLE");
        public final static Property IsWorkSchedule = new Property(40, Boolean.class, "isWorkSchedule", false, "IS_WORK_SCHEDULE");
        public final static Property Duration = new Property(41, String.class, "duration", false, "DURATION");
        public final static Property SubTaskCount = new Property(42, Integer.class, "subTaskCount", false, "SUB_TASK_COUNT");
        public final static Property Variance = new Property(43, Integer.class, "variance", false, "VARIANCE");
        public final static Property UserDetails = new Property(44, String.class, "userDetails", false, "USER_DETAILS");
        public final static Property TaskStartDateNew = new Property(45, String.class, "taskStartDateNew", false, "TASK_START_DATE_NEW");
        public final static Property Unit = new Property(46, String.class, "unit", false, "UNIT");
        public final static Property Loa = new Property(47, String.class, "loa", false, "LOA");
        public final static Property SchedulePart = new Property(48, String.class, "schedulePart", false, "SCHEDULE_PART");
        public final static Property ActualLoa = new Property(49, String.class, "actualLoa", false, "ACTUAL_LOA");
        public final static Property Schedule = new Property(50, String.class, "schedule", false, "SCHEDULE");
        public final static Property Description = new Property(51, String.class, "description", false, "DESCRIPTION");
        public final static Property OrderBy = new Property(52, String.class, "orderBy", false, "ORDER_BY");
    }


    public ProjectTaskDao(DaoConfig config) {
        super(config);
    }
    
    public ProjectTaskDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PROJECT_TASK\" (" + //
                "\"TASK_ID\" TEXT," + // 0: taskId
                "\"COMMENT\" TEXT," + // 1: comment
                "\"PRIORITY\" TEXT," + // 2: priority
                "\"REMINDER_TEXT\" TEXT," + // 3: reminderText
                "\"REMINDER_URL\" TEXT," + // 4: reminderUrl
                "\"MAN_POWER_REQ\" TEXT," + // 5: manPowerReq
                "\"ACTUAL_MAN_POWER_REQ\" TEXT," + // 6: actualManPowerReq
                "\"PERCENT_COMPLETE\" TEXT," + // 7: percentComplete
                "\"SUBJECT\" TEXT," + // 8: subject
                "\"MATERIAL_COUNT\" INTEGER," + // 9: material_count
                "\"TASK_CREATED_DATE\" TEXT," + // 10: taskCreatedDate
                "\"TASK_FOR\" TEXT," + // 11: taskFor
                "\"TYPE\" TEXT," + // 12: type
                "\"MOBILE_ID\" TEXT PRIMARY KEY NOT NULL ," + // 13: mobileId
                "\"TASK_STATUS\" TEXT," + // 14: taskStatus
                "\"TASK_UPDATED_DATE\" TEXT," + // 15: taskUpdatedDate
                "\"ASSIGNED_TO\" TEXT," + // 16: assignedTo
                "\"ASSIGNED_TO_NAME\" TEXT," + // 17: assignedToName
                "\"LOCATION_L1\" TEXT," + // 18: locationL1
                "\"LOCATION_L2\" TEXT," + // 19: locationL2
                "\"LOCATION_L3\" TEXT," + // 20: locationL3
                "\"LOCATION_L4\" TEXT," + // 21: locationL4
                "\"LOCATION_L5\" TEXT," + // 22: locationL5
                "\"PARENT_L1_ID\" TEXT," + // 23: parentL1Id
                "\"CONTRACTOR\" TEXT," + // 24: contractor
                "\"ACTUAL_MANPOWER\" TEXT," + // 25: actualManpower
                "\"TASK_CREATED_BY\" TEXT," + // 26: taskCreatedBy
                "\"TASK_UPDATED_BY\" TEXT," + // 27: taskUpdatedBy
                "\"TASK_START_DATE\" INTEGER," + // 28: taskStartDate
                "\"TASK_END_DATE\" INTEGER," + // 29: taskEndDate
                "\"TASK_EFFORT\" TEXT," + // 30: taskEffort
                "\"TASK_ACTUAL_START_DATE\" TEXT," + // 31: taskActualStartDate
                "\"TASK_ACTUAL_END_DATE\" TEXT," + // 32: taskActualEndDate
                "\"TASK_ACTUAL_EFFORT\" TEXT," + // 33: taskActualEffort
                "\"PARENT_TASK_ID\" TEXT," + // 34: parentTaskId
                "\"PREDECESSOR_TASK_ID\" TEXT," + // 35: predecessorTaskId
                "\"PROJECT_ID\" TEXT," + // 36: projectId
                "\"CRITICAL\" INTEGER," + // 37: critical
                "\"SECTION\" TEXT," + // 38: section
                "\"IS_TRACKABLE\" INTEGER," + // 39: isTrackable
                "\"IS_WORK_SCHEDULE\" INTEGER," + // 40: isWorkSchedule
                "\"DURATION\" TEXT," + // 41: duration
                "\"SUB_TASK_COUNT\" INTEGER," + // 42: subTaskCount
                "\"VARIANCE\" INTEGER," + // 43: variance
                "\"USER_DETAILS\" TEXT," + // 44: userDetails
                "\"TASK_START_DATE_NEW\" TEXT," + // 45: taskStartDateNew
                "\"UNIT\" TEXT," + // 46: unit
                "\"LOA\" TEXT," + // 47: loa
                "\"SCHEDULE_PART\" TEXT," + // 48: schedulePart
                "\"ACTUAL_LOA\" TEXT," + // 49: actualLoa
                "\"SCHEDULE\" TEXT," + // 50: schedule
                "\"DESCRIPTION\" TEXT," + // 51: description
                "\"ORDER_BY\" TEXT);"); // 52: orderBy
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PROJECT_TASK\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ProjectTask entity) {
        stmt.clearBindings();
 
        String taskId = entity.getTaskId();
        if (taskId != null) {
            stmt.bindString(1, taskId);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(2, comment);
        }
 
        String priority = entity.getPriority();
        if (priority != null) {
            stmt.bindString(3, priority);
        }
 
        String reminderText = entity.getReminderText();
        if (reminderText != null) {
            stmt.bindString(4, reminderText);
        }
 
        String reminderUrl = entity.getReminderUrl();
        if (reminderUrl != null) {
            stmt.bindString(5, reminderUrl);
        }
 
        String manPowerReq = entity.getManPowerReq();
        if (manPowerReq != null) {
            stmt.bindString(6, manPowerReq);
        }
 
        String actualManPowerReq = entity.getActualManPowerReq();
        if (actualManPowerReq != null) {
            stmt.bindString(7, actualManPowerReq);
        }
 
        String percentComplete = entity.getPercentComplete();
        if (percentComplete != null) {
            stmt.bindString(8, percentComplete);
        }
 
        String subject = entity.getSubject();
        if (subject != null) {
            stmt.bindString(9, subject);
        }
 
        Integer material_count = entity.getMaterial_count();
        if (material_count != null) {
            stmt.bindLong(10, material_count);
        }
 
        String taskCreatedDate = entity.getTaskCreatedDate();
        if (taskCreatedDate != null) {
            stmt.bindString(11, taskCreatedDate);
        }
 
        String taskFor = entity.getTaskFor();
        if (taskFor != null) {
            stmt.bindString(12, taskFor);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(13, type);
        }
 
        String mobileId = entity.getMobileId();
        if (mobileId != null) {
            stmt.bindString(14, mobileId);
        }
 
        String taskStatus = entity.getTaskStatus();
        if (taskStatus != null) {
            stmt.bindString(15, taskStatus);
        }
 
        String taskUpdatedDate = entity.getTaskUpdatedDate();
        if (taskUpdatedDate != null) {
            stmt.bindString(16, taskUpdatedDate);
        }
 
        String assignedTo = entity.getAssignedTo();
        if (assignedTo != null) {
            stmt.bindString(17, assignedTo);
        }
 
        String assignedToName = entity.getAssignedToName();
        if (assignedToName != null) {
            stmt.bindString(18, assignedToName);
        }
 
        String locationL1 = entity.getLocationL1();
        if (locationL1 != null) {
            stmt.bindString(19, locationL1);
        }
 
        String locationL2 = entity.getLocationL2();
        if (locationL2 != null) {
            stmt.bindString(20, locationL2);
        }
 
        String locationL3 = entity.getLocationL3();
        if (locationL3 != null) {
            stmt.bindString(21, locationL3);
        }
 
        String locationL4 = entity.getLocationL4();
        if (locationL4 != null) {
            stmt.bindString(22, locationL4);
        }
 
        String locationL5 = entity.getLocationL5();
        if (locationL5 != null) {
            stmt.bindString(23, locationL5);
        }
 
        String parentL1Id = entity.getParentL1Id();
        if (parentL1Id != null) {
            stmt.bindString(24, parentL1Id);
        }
 
        String contractor = entity.getContractor();
        if (contractor != null) {
            stmt.bindString(25, contractor);
        }
 
        String actualManpower = entity.getActualManpower();
        if (actualManpower != null) {
            stmt.bindString(26, actualManpower);
        }
 
        String taskCreatedBy = entity.getTaskCreatedBy();
        if (taskCreatedBy != null) {
            stmt.bindString(27, taskCreatedBy);
        }
 
        String taskUpdatedBy = entity.getTaskUpdatedBy();
        if (taskUpdatedBy != null) {
            stmt.bindString(28, taskUpdatedBy);
        }
 
        Long taskStartDate = entity.getTaskStartDate();
        if (taskStartDate != null) {
            stmt.bindLong(29, taskStartDate);
        }
 
        Long taskEndDate = entity.getTaskEndDate();
        if (taskEndDate != null) {
            stmt.bindLong(30, taskEndDate);
        }
 
        String taskEffort = entity.getTaskEffort();
        if (taskEffort != null) {
            stmt.bindString(31, taskEffort);
        }
 
        String taskActualStartDate = entity.getTaskActualStartDate();
        if (taskActualStartDate != null) {
            stmt.bindString(32, taskActualStartDate);
        }
 
        String taskActualEndDate = entity.getTaskActualEndDate();
        if (taskActualEndDate != null) {
            stmt.bindString(33, taskActualEndDate);
        }
 
        String taskActualEffort = entity.getTaskActualEffort();
        if (taskActualEffort != null) {
            stmt.bindString(34, taskActualEffort);
        }
 
        String parentTaskId = entity.getParentTaskId();
        if (parentTaskId != null) {
            stmt.bindString(35, parentTaskId);
        }
 
        String predecessorTaskId = entity.getPredecessorTaskId();
        if (predecessorTaskId != null) {
            stmt.bindString(36, predecessorTaskId);
        }
 
        String projectId = entity.getProjectId();
        if (projectId != null) {
            stmt.bindString(37, projectId);
        }
 
        Boolean critical = entity.getCritical();
        if (critical != null) {
            stmt.bindLong(38, critical ? 1L: 0L);
        }
 
        String section = entity.getSection();
        if (section != null) {
            stmt.bindString(39, section);
        }
 
        Boolean isTrackable = entity.getIsTrackable();
        if (isTrackable != null) {
            stmt.bindLong(40, isTrackable ? 1L: 0L);
        }
 
        Boolean isWorkSchedule = entity.getIsWorkSchedule();
        if (isWorkSchedule != null) {
            stmt.bindLong(41, isWorkSchedule ? 1L: 0L);
        }
 
        String duration = entity.getDuration();
        if (duration != null) {
            stmt.bindString(42, duration);
        }
 
        Integer subTaskCount = entity.getSubTaskCount();
        if (subTaskCount != null) {
            stmt.bindLong(43, subTaskCount);
        }
 
        Integer variance = entity.getVariance();
        if (variance != null) {
            stmt.bindLong(44, variance);
        }
 
        String userDetails = entity.getUserDetails();
        if (userDetails != null) {
            stmt.bindString(45, userDetails);
        }
 
        String taskStartDateNew = entity.getTaskStartDateNew();
        if (taskStartDateNew != null) {
            stmt.bindString(46, taskStartDateNew);
        }
 
        String unit = entity.getUnit();
        if (unit != null) {
            stmt.bindString(47, unit);
        }
 
        String loa = entity.getLoa();
        if (loa != null) {
            stmt.bindString(48, loa);
        }
 
        String schedulePart = entity.getSchedulePart();
        if (schedulePart != null) {
            stmt.bindString(49, schedulePart);
        }
 
        String actualLoa = entity.getActualLoa();
        if (actualLoa != null) {
            stmt.bindString(50, actualLoa);
        }
 
        String schedule = entity.getSchedule();
        if (schedule != null) {
            stmt.bindString(51, schedule);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(52, description);
        }
 
        String orderBy = entity.getOrderBy();
        if (orderBy != null) {
            stmt.bindString(53, orderBy);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ProjectTask entity) {
        stmt.clearBindings();
 
        String taskId = entity.getTaskId();
        if (taskId != null) {
            stmt.bindString(1, taskId);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(2, comment);
        }
 
        String priority = entity.getPriority();
        if (priority != null) {
            stmt.bindString(3, priority);
        }
 
        String reminderText = entity.getReminderText();
        if (reminderText != null) {
            stmt.bindString(4, reminderText);
        }
 
        String reminderUrl = entity.getReminderUrl();
        if (reminderUrl != null) {
            stmt.bindString(5, reminderUrl);
        }
 
        String manPowerReq = entity.getManPowerReq();
        if (manPowerReq != null) {
            stmt.bindString(6, manPowerReq);
        }
 
        String actualManPowerReq = entity.getActualManPowerReq();
        if (actualManPowerReq != null) {
            stmt.bindString(7, actualManPowerReq);
        }
 
        String percentComplete = entity.getPercentComplete();
        if (percentComplete != null) {
            stmt.bindString(8, percentComplete);
        }
 
        String subject = entity.getSubject();
        if (subject != null) {
            stmt.bindString(9, subject);
        }
 
        Integer material_count = entity.getMaterial_count();
        if (material_count != null) {
            stmt.bindLong(10, material_count);
        }
 
        String taskCreatedDate = entity.getTaskCreatedDate();
        if (taskCreatedDate != null) {
            stmt.bindString(11, taskCreatedDate);
        }
 
        String taskFor = entity.getTaskFor();
        if (taskFor != null) {
            stmt.bindString(12, taskFor);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(13, type);
        }
 
        String mobileId = entity.getMobileId();
        if (mobileId != null) {
            stmt.bindString(14, mobileId);
        }
 
        String taskStatus = entity.getTaskStatus();
        if (taskStatus != null) {
            stmt.bindString(15, taskStatus);
        }
 
        String taskUpdatedDate = entity.getTaskUpdatedDate();
        if (taskUpdatedDate != null) {
            stmt.bindString(16, taskUpdatedDate);
        }
 
        String assignedTo = entity.getAssignedTo();
        if (assignedTo != null) {
            stmt.bindString(17, assignedTo);
        }
 
        String assignedToName = entity.getAssignedToName();
        if (assignedToName != null) {
            stmt.bindString(18, assignedToName);
        }
 
        String locationL1 = entity.getLocationL1();
        if (locationL1 != null) {
            stmt.bindString(19, locationL1);
        }
 
        String locationL2 = entity.getLocationL2();
        if (locationL2 != null) {
            stmt.bindString(20, locationL2);
        }
 
        String locationL3 = entity.getLocationL3();
        if (locationL3 != null) {
            stmt.bindString(21, locationL3);
        }
 
        String locationL4 = entity.getLocationL4();
        if (locationL4 != null) {
            stmt.bindString(22, locationL4);
        }
 
        String locationL5 = entity.getLocationL5();
        if (locationL5 != null) {
            stmt.bindString(23, locationL5);
        }
 
        String parentL1Id = entity.getParentL1Id();
        if (parentL1Id != null) {
            stmt.bindString(24, parentL1Id);
        }
 
        String contractor = entity.getContractor();
        if (contractor != null) {
            stmt.bindString(25, contractor);
        }
 
        String actualManpower = entity.getActualManpower();
        if (actualManpower != null) {
            stmt.bindString(26, actualManpower);
        }
 
        String taskCreatedBy = entity.getTaskCreatedBy();
        if (taskCreatedBy != null) {
            stmt.bindString(27, taskCreatedBy);
        }
 
        String taskUpdatedBy = entity.getTaskUpdatedBy();
        if (taskUpdatedBy != null) {
            stmt.bindString(28, taskUpdatedBy);
        }
 
        Long taskStartDate = entity.getTaskStartDate();
        if (taskStartDate != null) {
            stmt.bindLong(29, taskStartDate);
        }
 
        Long taskEndDate = entity.getTaskEndDate();
        if (taskEndDate != null) {
            stmt.bindLong(30, taskEndDate);
        }
 
        String taskEffort = entity.getTaskEffort();
        if (taskEffort != null) {
            stmt.bindString(31, taskEffort);
        }
 
        String taskActualStartDate = entity.getTaskActualStartDate();
        if (taskActualStartDate != null) {
            stmt.bindString(32, taskActualStartDate);
        }
 
        String taskActualEndDate = entity.getTaskActualEndDate();
        if (taskActualEndDate != null) {
            stmt.bindString(33, taskActualEndDate);
        }
 
        String taskActualEffort = entity.getTaskActualEffort();
        if (taskActualEffort != null) {
            stmt.bindString(34, taskActualEffort);
        }
 
        String parentTaskId = entity.getParentTaskId();
        if (parentTaskId != null) {
            stmt.bindString(35, parentTaskId);
        }
 
        String predecessorTaskId = entity.getPredecessorTaskId();
        if (predecessorTaskId != null) {
            stmt.bindString(36, predecessorTaskId);
        }
 
        String projectId = entity.getProjectId();
        if (projectId != null) {
            stmt.bindString(37, projectId);
        }
 
        Boolean critical = entity.getCritical();
        if (critical != null) {
            stmt.bindLong(38, critical ? 1L: 0L);
        }
 
        String section = entity.getSection();
        if (section != null) {
            stmt.bindString(39, section);
        }
 
        Boolean isTrackable = entity.getIsTrackable();
        if (isTrackable != null) {
            stmt.bindLong(40, isTrackable ? 1L: 0L);
        }
 
        Boolean isWorkSchedule = entity.getIsWorkSchedule();
        if (isWorkSchedule != null) {
            stmt.bindLong(41, isWorkSchedule ? 1L: 0L);
        }
 
        String duration = entity.getDuration();
        if (duration != null) {
            stmt.bindString(42, duration);
        }
 
        Integer subTaskCount = entity.getSubTaskCount();
        if (subTaskCount != null) {
            stmt.bindLong(43, subTaskCount);
        }
 
        Integer variance = entity.getVariance();
        if (variance != null) {
            stmt.bindLong(44, variance);
        }
 
        String userDetails = entity.getUserDetails();
        if (userDetails != null) {
            stmt.bindString(45, userDetails);
        }
 
        String taskStartDateNew = entity.getTaskStartDateNew();
        if (taskStartDateNew != null) {
            stmt.bindString(46, taskStartDateNew);
        }
 
        String unit = entity.getUnit();
        if (unit != null) {
            stmt.bindString(47, unit);
        }
 
        String loa = entity.getLoa();
        if (loa != null) {
            stmt.bindString(48, loa);
        }
 
        String schedulePart = entity.getSchedulePart();
        if (schedulePart != null) {
            stmt.bindString(49, schedulePart);
        }
 
        String actualLoa = entity.getActualLoa();
        if (actualLoa != null) {
            stmt.bindString(50, actualLoa);
        }
 
        String schedule = entity.getSchedule();
        if (schedule != null) {
            stmt.bindString(51, schedule);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(52, description);
        }
 
        String orderBy = entity.getOrderBy();
        if (orderBy != null) {
            stmt.bindString(53, orderBy);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13);
    }    

    @Override
    public ProjectTask readEntity(Cursor cursor, int offset) {
        ProjectTask entity = new ProjectTask( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // taskId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // comment
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // priority
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // reminderText
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // reminderUrl
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // manPowerReq
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // actualManPowerReq
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // percentComplete
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // subject
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // material_count
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // taskCreatedDate
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // taskFor
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // type
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // mobileId
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // taskStatus
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // taskUpdatedDate
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // assignedTo
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // assignedToName
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // locationL1
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // locationL2
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // locationL3
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // locationL4
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // locationL5
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // parentL1Id
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // contractor
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // actualManpower
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // taskCreatedBy
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // taskUpdatedBy
            cursor.isNull(offset + 28) ? null : cursor.getLong(offset + 28), // taskStartDate
            cursor.isNull(offset + 29) ? null : cursor.getLong(offset + 29), // taskEndDate
            cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30), // taskEffort
            cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31), // taskActualStartDate
            cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32), // taskActualEndDate
            cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33), // taskActualEffort
            cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34), // parentTaskId
            cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35), // predecessorTaskId
            cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36), // projectId
            cursor.isNull(offset + 37) ? null : cursor.getShort(offset + 37) != 0, // critical
            cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38), // section
            cursor.isNull(offset + 39) ? null : cursor.getShort(offset + 39) != 0, // isTrackable
            cursor.isNull(offset + 40) ? null : cursor.getShort(offset + 40) != 0, // isWorkSchedule
            cursor.isNull(offset + 41) ? null : cursor.getString(offset + 41), // duration
            cursor.isNull(offset + 42) ? null : cursor.getInt(offset + 42), // subTaskCount
            cursor.isNull(offset + 43) ? null : cursor.getInt(offset + 43), // variance
            cursor.isNull(offset + 44) ? null : cursor.getString(offset + 44), // userDetails
            cursor.isNull(offset + 45) ? null : cursor.getString(offset + 45), // taskStartDateNew
            cursor.isNull(offset + 46) ? null : cursor.getString(offset + 46), // unit
            cursor.isNull(offset + 47) ? null : cursor.getString(offset + 47), // loa
            cursor.isNull(offset + 48) ? null : cursor.getString(offset + 48), // schedulePart
            cursor.isNull(offset + 49) ? null : cursor.getString(offset + 49), // actualLoa
            cursor.isNull(offset + 50) ? null : cursor.getString(offset + 50), // schedule
            cursor.isNull(offset + 51) ? null : cursor.getString(offset + 51), // description
            cursor.isNull(offset + 52) ? null : cursor.getString(offset + 52) // orderBy
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ProjectTask entity, int offset) {
        entity.setTaskId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setComment(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPriority(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setReminderText(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setReminderUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setManPowerReq(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setActualManPowerReq(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPercentComplete(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSubject(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setMaterial_count(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setTaskCreatedDate(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setTaskFor(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setType(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setMobileId(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setTaskStatus(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setTaskUpdatedDate(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setAssignedTo(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setAssignedToName(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setLocationL1(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setLocationL2(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setLocationL3(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setLocationL4(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setLocationL5(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setParentL1Id(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setContractor(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setActualManpower(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setTaskCreatedBy(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setTaskUpdatedBy(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setTaskStartDate(cursor.isNull(offset + 28) ? null : cursor.getLong(offset + 28));
        entity.setTaskEndDate(cursor.isNull(offset + 29) ? null : cursor.getLong(offset + 29));
        entity.setTaskEffort(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
        entity.setTaskActualStartDate(cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31));
        entity.setTaskActualEndDate(cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32));
        entity.setTaskActualEffort(cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33));
        entity.setParentTaskId(cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34));
        entity.setPredecessorTaskId(cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35));
        entity.setProjectId(cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36));
        entity.setCritical(cursor.isNull(offset + 37) ? null : cursor.getShort(offset + 37) != 0);
        entity.setSection(cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38));
        entity.setIsTrackable(cursor.isNull(offset + 39) ? null : cursor.getShort(offset + 39) != 0);
        entity.setIsWorkSchedule(cursor.isNull(offset + 40) ? null : cursor.getShort(offset + 40) != 0);
        entity.setDuration(cursor.isNull(offset + 41) ? null : cursor.getString(offset + 41));
        entity.setSubTaskCount(cursor.isNull(offset + 42) ? null : cursor.getInt(offset + 42));
        entity.setVariance(cursor.isNull(offset + 43) ? null : cursor.getInt(offset + 43));
        entity.setUserDetails(cursor.isNull(offset + 44) ? null : cursor.getString(offset + 44));
        entity.setTaskStartDateNew(cursor.isNull(offset + 45) ? null : cursor.getString(offset + 45));
        entity.setUnit(cursor.isNull(offset + 46) ? null : cursor.getString(offset + 46));
        entity.setLoa(cursor.isNull(offset + 47) ? null : cursor.getString(offset + 47));
        entity.setSchedulePart(cursor.isNull(offset + 48) ? null : cursor.getString(offset + 48));
        entity.setActualLoa(cursor.isNull(offset + 49) ? null : cursor.getString(offset + 49));
        entity.setSchedule(cursor.isNull(offset + 50) ? null : cursor.getString(offset + 50));
        entity.setDescription(cursor.isNull(offset + 51) ? null : cursor.getString(offset + 51));
        entity.setOrderBy(cursor.isNull(offset + 52) ? null : cursor.getString(offset + 52));
     }
    
    @Override
    protected final String updateKeyAfterInsert(ProjectTask entity, long rowId) {
        return entity.getMobileId();
    }
    
    @Override
    public String getKey(ProjectTask entity) {
        if(entity != null) {
            return entity.getMobileId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ProjectTask entity) {
        return entity.getMobileId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
