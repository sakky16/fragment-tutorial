package com.trisysit.epc_android.utils;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.trisysit.epc_android.EPCApplication;
import com.trisysit.epc_android.SqliteDatabase.ActivityMaster;
import com.trisysit.epc_android.SqliteDatabase.ActivityMasterDao;
import com.trisysit.epc_android.SqliteDatabase.DaoMaster;
import com.trisysit.epc_android.SqliteDatabase.DaoSession;
import com.trisysit.epc_android.SqliteDatabase.ImageSync;
import com.trisysit.epc_android.SqliteDatabase.ImageSyncDao;
import com.trisysit.epc_android.SqliteDatabase.LocationMaster;
import com.trisysit.epc_android.SqliteDatabase.LocationMasterDao;
import com.trisysit.epc_android.SqliteDatabase.MaterialTable;
import com.trisysit.epc_android.SqliteDatabase.MaterialTableDao;
import com.trisysit.epc_android.SqliteDatabase.ProjectTable;
import com.trisysit.epc_android.SqliteDatabase.ProjectTableDao;
import com.trisysit.epc_android.SqliteDatabase.ProjectTask;
import com.trisysit.epc_android.SqliteDatabase.ProjectTaskDao;
import com.trisysit.epc_android.SqliteDatabase.SubActivityMaster;
import com.trisysit.epc_android.SqliteDatabase.SubActivityMasterDao;
import com.trisysit.epc_android.SqliteDatabase.UserMaster;
import com.trisysit.epc_android.SqliteDatabase.UserMasterDao;
import com.trisysit.epc_android.activity.SyncInfoActivity;


import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import de.greenrobot.dao.query.WhereCondition;

/**
 * Created by trisys on 1/8/17.
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
        mHelper = new DaoMaster.DevOpenHelper(EPCApplication.getContext(), "sms-heartbeat", null);
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

    public synchronized void insertMaterialList(List<MaterialTable> materialTableList) {
        List<MaterialTable> mobileIdNotNullMaterialList = new ArrayList<>();
        if (materialTableList != null && materialTableList.size() > 0) {
            try {
                for (MaterialTable materialTable : materialTableList) {
                    if (materialTable.getMaterialMobileId() != null) {
                        mobileIdNotNullMaterialList.add(materialTable);
                    } else {
                        materialTable.setMaterialMobileId(UUID.randomUUID().toString());
                        mobileIdNotNullMaterialList.add(materialTable);
                    }
                }
                if (mobileIdNotNullMaterialList != null) {
                    openWritableDb();
                    MaterialTableDao dao = daoSession.getMaterialTableDao();
                    dao.insertOrReplaceInTx(mobileIdNotNullMaterialList);
                    daoSession.clear();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public synchronized void insertTaskObject(ProjectTask projectTask) {
        if (projectTask != null) {
            try {
                openWritableDb();
                ProjectTaskDao dao = daoSession.getProjectTaskDao();
                dao.insertOrReplaceInTx(projectTask);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public synchronized void insertProjectList(List<ProjectTable> projectTableList) {
        if (projectTableList != null) {
            try {
                openWritableDb();
                ProjectTableDao dao = daoSession.getProjectTableDao();
                dao.insertOrReplaceInTx(projectTableList);
                daoSession.clear();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void insertUserMasterList(List<UserMaster> userMasterList) {
        List<UserMaster> mobileIdNotNullUserList=new ArrayList<>();
        if(userMasterList!=null && userMasterList.size()>0){
            try {
                for(UserMaster userMaster:userMasterList){
                    if(userMaster.getUserId()!=null){
                        mobileIdNotNullUserList.add(userMaster);
                    }
                    else {
                        userMaster.setUserId(UUID.randomUUID().toString());
                        mobileIdNotNullUserList.add(userMaster);
                    }
                }
                if (mobileIdNotNullUserList != null) {
                    openWritableDb();
                    UserMasterDao dao = daoSession.getUserMasterDao();
                    dao.insertOrReplaceInTx(mobileIdNotNullUserList);
                    daoSession.clear();
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public synchronized void insertUsermasterObject(UserMaster userMaster) {
        if (userMaster != null) {
            try {
                openWritableDb();
                UserMasterDao dao = daoSession.getUserMasterDao();
                dao.insertOrReplaceInTx(userMaster);
                daoSession.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void insertLocationMasterList(List<LocationMaster> locationMasters) {
        if (locationMasters != null) {
            try {
                openWritableDb();
                LocationMasterDao dao = daoSession.getLocationMasterDao();
                dao.insertOrReplaceInTx(locationMasters);
                daoSession.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public synchronized void insertActivityList(List<ActivityMaster> activityMasterList) {
        if (activityMasterList != null) {
            try {
                openWritableDb();
                ActivityMasterDao dao = daoSession.getActivityMasterDao();
                dao.insertOrReplaceInTx(activityMasterList);
                daoSession.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void insertSubActivityList(List<SubActivityMaster> subActivityMasterList) {
        if (subActivityMasterList != null) {
            try {
                openWritableDb();
                SubActivityMasterDao dao = daoSession.getSubActivityMasterDao();
                dao.insertOrReplaceInTx(subActivityMasterList);
                daoSession.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized UserMaster getUserMasterByUserPin(String userPin) {
        UserMaster userMaster = null;
        if (userPin != null) {
            try {
                openReadableDb();
                UserMasterDao dao = daoSession.getUserMasterDao();
                org.greenrobot.greendao.query.WhereCondition condition = UserMasterDao.Properties.UserPin.eq(userPin);
                QueryBuilder<UserMaster> queryBuilder = dao.queryBuilder().where(condition);
                userMaster = queryBuilder.unique();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userMaster;
    }

    public synchronized void insertTaskList(List<ProjectTask> projectTaskList) {
        List<ProjectTask> mobileIdNotNullTaskList = new ArrayList<>();
        if (projectTaskList != null && projectTaskList.size() > 0) {
            try {
                for (ProjectTask projectTask : projectTaskList) {
                    if (projectTask.getMobileId() != null) {
                        mobileIdNotNullTaskList.add(projectTask);
                    } else {
                        projectTask.setMobileId(UUID.randomUUID().toString());
                        mobileIdNotNullTaskList.add(projectTask);
                    }
                }
                if (mobileIdNotNullTaskList != null) {
                    openWritableDb();
                    ProjectTaskDao dao = daoSession.getProjectTaskDao();
                    dao.insertOrReplaceInTx(mobileIdNotNullTaskList);
                    daoSession.clear();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void insertImage(ImageSync imageSync) {
        if (imageSync != null) {
            try {
                openWritableDb();
                ImageSyncDao dao = daoSession.getImageSyncDao();
                dao.insertOrReplaceInTx(imageSync);
                daoSession.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public synchronized List<ImageSync> getImageListByStatus() {
        List<ImageSync> imageSyncList = new ArrayList<>();
        try {
            openReadableDb();
            ImageSyncDao dao = daoSession.getImageSyncDao();
            org.greenrobot.greendao.query.WhereCondition condition = ImageSyncDao.Properties.Status.eq(AppUtils.IMAGE_NOT_SYNCED);
            org.greenrobot.greendao.query.WhereCondition condition1 = ImageSyncDao.Properties.Id.isNotNull();
            org.greenrobot.greendao.query.WhereCondition condition2 = ImageSyncDao.Properties.ImageUrl.isNotNull();
            QueryBuilder<ImageSync> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2);
            imageSyncList = queryBuilder.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageSyncList;
    }

    public synchronized ProjectTask getTaskByTaskAndProjecyId(String taskId, String projectId) {
        ProjectTask projectTask = null;
        if (taskId != null && projectId != null) {
            try {
                openReadableDb();
                ProjectTaskDao dao = daoSession.getProjectTaskDao();
                org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.TaskId.eq(taskId);
                org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.ProjectId.eq(projectId);
                QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1);
                projectTask = queryBuilder.unique();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return projectTask;
    }

    public synchronized List<LocationMaster> getLocationL1(String projectId) {
        List<LocationMaster> locationMasterList = new ArrayList<>();
        try {
            openReadableDb();
            LocationMasterDao dao = daoSession.getLocationMasterDao();
            org.greenrobot.greendao.query.WhereCondition condition = LocationMasterDao.Properties.LocationL1.isNotNull();
            org.greenrobot.greendao.query.WhereCondition condition1 = LocationMasterDao.Properties.ProjectId.eq(projectId);
            QueryBuilder<LocationMaster> queryBuilder = dao.queryBuilder().where(condition, condition1);
            locationMasterList = queryBuilder.list();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return locationMasterList;
    }

    public synchronized List<LocationMaster> getLocationL2(String projectId, String locationL1) {
        List<LocationMaster> locationMasterList = new ArrayList<>();
        if (projectId != null && locationL1 != null) {
            try {
                openReadableDb();
                LocationMasterDao dao = daoSession.getLocationMasterDao();
                org.greenrobot.greendao.query.WhereCondition condition = LocationMasterDao.Properties.ProjectId.eq(projectId);
                org.greenrobot.greendao.query.WhereCondition condition1 = LocationMasterDao.Properties.LocationL1.eq(locationL1);
                org.greenrobot.greendao.query.WhereCondition condition2 = LocationMasterDao.Properties.LocationL2.isNotNull();
                QueryBuilder<LocationMaster> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2);
                locationMasterList = queryBuilder.list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return locationMasterList;
    }

    public synchronized List<LocationMaster> getLocationL3(String projectId, String locationL1, String locationL2) {
        List<LocationMaster> locationMasterList = new ArrayList<>();
        if (projectId != null && locationL1 != null && locationL2 != null) {
            try {
                openReadableDb();
                LocationMasterDao dao = daoSession.getLocationMasterDao();
                org.greenrobot.greendao.query.WhereCondition condition = LocationMasterDao.Properties.ProjectId.eq(projectId);
                org.greenrobot.greendao.query.WhereCondition condition1 = LocationMasterDao.Properties.LocationL1.eq(locationL1);
                org.greenrobot.greendao.query.WhereCondition condition2 = LocationMasterDao.Properties.LocationL3.isNotNull();
                org.greenrobot.greendao.query.WhereCondition condition3 = LocationMasterDao.Properties.LocationL2.eq(locationL2);
                QueryBuilder<LocationMaster> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3);
                locationMasterList = queryBuilder.list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return locationMasterList;
    }

    public synchronized List<LocationMaster> getLocationL4(String projectId, String locationL1, String locationL2, String locationL3) {
        List<LocationMaster> locationMasterList = new ArrayList<>();
        if (projectId != null && locationL1 != null && locationL2 != null && locationL3 != null) {
            try {
                openReadableDb();
                LocationMasterDao dao = daoSession.getLocationMasterDao();
                org.greenrobot.greendao.query.WhereCondition condition = LocationMasterDao.Properties.ProjectId.eq(projectId);
                org.greenrobot.greendao.query.WhereCondition condition1 = LocationMasterDao.Properties.LocationL1.eq(locationL1);
                org.greenrobot.greendao.query.WhereCondition condition2 = LocationMasterDao.Properties.LocationL3.eq(locationL3);
                org.greenrobot.greendao.query.WhereCondition condition3 = LocationMasterDao.Properties.LocationL2.eq(locationL2);
                org.greenrobot.greendao.query.WhereCondition condition4 = LocationMasterDao.Properties.LocationL4.isNotNull();
                QueryBuilder<LocationMaster> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3, condition4);
                locationMasterList = queryBuilder.list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return locationMasterList;
    }

    public synchronized List<LocationMaster> getLocationL5List(String projectId, String locationL1, String locationL2, String locationL3, String locationL4) {
        List<LocationMaster> locationMasterList = new ArrayList<>();
        if (projectId != null && locationL1 != null && locationL2 != null && locationL3 != null && locationL4 != null) {
            try {
                openReadableDb();
                LocationMasterDao dao = daoSession.getLocationMasterDao();
                org.greenrobot.greendao.query.WhereCondition condition = LocationMasterDao.Properties.ProjectId.eq(projectId);
                org.greenrobot.greendao.query.WhereCondition condition1 = LocationMasterDao.Properties.LocationL1.eq(locationL1);
                org.greenrobot.greendao.query.WhereCondition condition2 = LocationMasterDao.Properties.LocationL3.eq(locationL3);
                org.greenrobot.greendao.query.WhereCondition condition3 = LocationMasterDao.Properties.LocationL2.eq(locationL2);
                org.greenrobot.greendao.query.WhereCondition condition4 = LocationMasterDao.Properties.LocationL5.isNotNull();
                org.greenrobot.greendao.query.WhereCondition condition5 = LocationMasterDao.Properties.LocationL4.eq(locationL4);
                QueryBuilder<LocationMaster> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3, condition4, condition5);
                locationMasterList = queryBuilder.list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return locationMasterList;
    }

    public synchronized SubActivityMaster getSubActivityByScheduleAndSchedulePart(String schedule, String schedulePart) {
        SubActivityMaster subActivityMaster = null;
        if (schedule != null && schedulePart != null) {
            try {
                openReadableDb();
                SubActivityMasterDao dao = daoSession.getSubActivityMasterDao();
                org.greenrobot.greendao.query.WhereCondition condition = SubActivityMasterDao.Properties.Schedule.eq(schedule);
                org.greenrobot.greendao.query.WhereCondition condition1 = SubActivityMasterDao.Properties.SchedulePart.eq(schedulePart);
                QueryBuilder<SubActivityMaster> queryBuilder = dao.queryBuilder().where(condition, condition1);
                subActivityMaster = queryBuilder.unique();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return subActivityMaster;
    }

    public synchronized ProjectTask getSubActivityTaskByScheduleAndSchedulePart(String schedule, String schedulePart, String projectId) {
        ProjectTask task = null;
        if (schedule != null && schedulePart != null && projectId != null) {
            try {
                openReadableDb();
                ProjectTaskDao dao = daoSession.getProjectTaskDao();
                org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.Schedule.eq(schedule);
                org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.SchedulePart.eq(schedulePart);
                org.greenrobot.greendao.query.WhereCondition condition2 = ProjectTaskDao.Properties.ProjectId.eq(projectId);
                org.greenrobot.greendao.query.WhereCondition condition3 = ProjectTaskDao.Properties.TaskId.isNotNull();
                QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3);
                queryBuilder.orderAsc(ProjectTaskDao.Properties.SchedulePart);
                task = queryBuilder.list().get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return task;
    }

    public synchronized ImageSync getImageSyncByImageUrl(String imageUrl) {
        ImageSync sync = null;
        if (imageUrl != null) {
            try {
                openReadableDb();
                ImageSyncDao dao = daoSession.getImageSyncDao();
                org.greenrobot.greendao.query.WhereCondition condition = ImageSyncDao.Properties.ImageUrl.eq(imageUrl);
                org.greenrobot.greendao.query.WhereCondition condition1 = ImageSyncDao.Properties.Id.isNotNull();
                QueryBuilder<ImageSync> queryBuilder = dao.queryBuilder().where(condition, condition1);
                sync = queryBuilder.unique();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sync;
    }

    public synchronized List<ProjectTask> getTaskByProjectId(String projectId) {
        List<ProjectTask> tasks = new ArrayList<>();
        if (projectId != null) {

            try {
                openReadableDb();
                ProjectTaskDao dao = daoSession.getProjectTaskDao();
                org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.ProjectId.eq(projectId);
                org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.ParentTaskId.isNull();
                org.greenrobot.greendao.query.WhereCondition condition2 = ProjectTaskDao.Properties.TaskId.isNotNull();
                org.greenrobot.greendao.query.WhereCondition condition3 = ProjectTaskDao.Properties.Type.eq("task");
                QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3);
                tasks = queryBuilder.list();

            } catch (Exception e) {

            }
        }
        return tasks;
    }

    public synchronized List<ProjectTask> getL1Tasks(String projectId) {
        List<ProjectTask> l1TaskWithActivity = new ArrayList<ProjectTask>();
        List<ProjectTask> tasks = getTaskByProjectId(projectId);
        HashMap<String, ActivityMaster> activityMap = getActivityMaster(projectId);

        for (ProjectTask task : tasks) {
            String schedule = task.getSchedule();

            if (schedule != null && !"".equals(schedule)) {
                String[] schedules = schedule.split("-");
                for (int i = 0; i < schedules.length; i++) {
                    String scheduleKey = schedules[i];
                    ActivityMaster activity = activityMap.get(scheduleKey);
                    if (activity == null) {
                        System.out.println("Activity is blank::: " + scheduleKey);
                    }

                    ProjectTask taskWithActivity = new ProjectTask();
                    taskWithActivity.setSchedule(scheduleKey);
                    if (activity != null) {
                        if (activity.getParticular() != null) {
                            taskWithActivity.setSubject(activity.getParticular());
                        }
                    }

                    taskWithActivity.setParentL1Id(task.getTaskId());
                    Double taskLoa = 0.0;
                    Double task_loa = 0.0;
                    try {
                        if (task.getLoa() != null) {
                            taskLoa = Double.parseDouble(task.getLoa());
                        } else {
                            taskLoa = 0.0;
                        }
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                    Double multiplier = 0.0;
                    if (activity != null) {
                        try {
                            if (activity.getScheduleMultiplier() != null) {
                                multiplier = Double.parseDouble(activity.getScheduleMultiplier());
                            }
                        } catch (NumberFormatException nfe) {
                            nfe.printStackTrace();

                        }
                    }
                    task_loa = (taskLoa * multiplier);
                    task_loa = (double) Math.round(task_loa * 100) / 100;
                    taskWithActivity.setLoa(task_loa + "");
                    if (activity != null) {
                        if (activity.getUnit() != null) {
                            taskWithActivity.setUnit(activity.getUnit());
                        }
                    }
                    taskWithActivity.setProjectId(task.getProjectId());
                    l1TaskWithActivity.add(taskWithActivity);

                }
            } else {
                ProjectTask projectTask = new ProjectTask();
                Double taskLoa = 0.0;
                try {
                    taskLoa = Double.parseDouble(task.getLoa());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                projectTask.setSchedule("");
                projectTask.setSubject(task.getSubject());
                projectTask.setLoa(task.getLoa());
                if (task.getUnit() != null) {
                    projectTask.setUnit(task.getUnit());
                }
                projectTask.setParentL1Id(task.getTaskId());
                //l1TaskWithActivity.add(projectTask);
            }
        }
        Collections.sort(l1TaskWithActivity, new Comparator<ProjectTask>() {

            public int compare(ProjectTask o1, ProjectTask o2) {
                return o1.getSchedule().compareTo(o2.getSchedule());
            }
        });
        return l1TaskWithActivity;
    }

    public synchronized HashMap<String, ActivityMaster> getActivityMaster(String projectId) {
        HashMap<String, ActivityMaster> activityMastersMap = new HashMap<String, ActivityMaster>();
        List<ActivityMaster> activityMasters = new ArrayList<>();
        if (projectId != null) {
            try {
                openReadableDb();
                ActivityMasterDao dao = daoSession.getActivityMasterDao();
                org.greenrobot.greendao.query.WhereCondition condition = ActivityMasterDao.Properties.ProjectId.eq(projectId);
                QueryBuilder<ActivityMaster> queryBuilder = dao.queryBuilder().where(condition);
                activityMasters = queryBuilder.list();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (activityMasters.size() > 0) {
            for (ActivityMaster activityMaster : activityMasters) {
                activityMastersMap.put(activityMaster.getSchedule(), activityMaster);
            }
        }
        return activityMastersMap;

    }

    public synchronized List<ProjectTask> getActivityByScheduleAndParentL1Id(String schedule, String parentL1Id) {
        ProjectTask projectTask = null;
        List<ProjectTask> projectTaskList = new ArrayList<>();
        if (schedule != null && parentL1Id != null) {
            try {
                openReadableDb();
                ProjectTaskDao dao = daoSession.getProjectTaskDao();
                org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.Schedule.eq(schedule);
                org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.ParentL1Id.eq(parentL1Id);
                org.greenrobot.greendao.query.WhereCondition condition2 = ProjectTaskDao.Properties.TaskId.isNotNull();
                org.greenrobot.greendao.query.WhereCondition condition3 = ProjectTaskDao.Properties.Type.eq("activity");
                QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3);
                queryBuilder.orderAsc(ProjectTaskDao.Properties.OrderBy);
                projectTaskList = queryBuilder.list();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return projectTaskList;
    }

    public synchronized List<ProjectTask> getSubActivityByScheduleAndSchedulePart(String schedule, String schedulePart, String parentL1Id) {
        List<ProjectTask> subActivityList = new ArrayList<>();
        if (schedule != null && schedulePart != null && parentL1Id != null) {
            try {
                openReadableDb();
                ProjectTaskDao dao = daoSession.getProjectTaskDao();
                org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.Schedule.eq(schedule);
                org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.ParentL1Id.eq(parentL1Id);
                org.greenrobot.greendao.query.WhereCondition condition2 = ProjectTaskDao.Properties.TaskId.isNotNull();
                org.greenrobot.greendao.query.WhereCondition condition3 = ProjectTaskDao.Properties.Type.eq("subActivity");
                org.greenrobot.greendao.query.WhereCondition condition4 = ProjectTaskDao.Properties.SchedulePart.eq(schedulePart);
                QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3, condition4);
                queryBuilder.orderAsc(ProjectTaskDao.Properties.OrderBy);
                subActivityList = queryBuilder.list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return subActivityList;
    }

    public synchronized List<ProjectTask> getSubTaskByTaskId(String taskId, String projectId) {
        List<ProjectTask> taskTableList = new ArrayList<>();
        if (taskId != null && projectId != null) {
            try {
                openReadableDb();
                ProjectTaskDao dao = daoSession.getProjectTaskDao();
                org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.ParentTaskId.eq(taskId);
                org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.ProjectId.eq(projectId);
                org.greenrobot.greendao.query.WhereCondition condition2 = ProjectTaskDao.Properties.TaskId.isNotNull();
                org.greenrobot.greendao.query.WhereCondition condition3 = ProjectTaskDao.Properties.Type.eq("task");
                QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3);
                taskTableList = queryBuilder.list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return taskTableList;
    }

    public synchronized List<ProjectTask> getDailyTaskUpdateDescOfCreatedDate(String Schedule, String projectId, String schedulePart) {
        List<ProjectTask> taskList = new ArrayList<>();
        if (Schedule != null && projectId != null) {
            try {
                openReadableDb();
                ProjectTaskDao dao = daoSession.getProjectTaskDao();
                org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.Schedule.eq(Schedule);
                org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.ProjectId.eq(projectId);
                org.greenrobot.greendao.query.WhereCondition condition2 = ProjectTaskDao.Properties.MobileId.isNotNull();
                org.greenrobot.greendao.query.WhereCondition condition3 = ProjectTaskDao.Properties.Type.eq(AppUtils.DAILY_UPDATE_TYPE);
                org.greenrobot.greendao.query.WhereCondition condition4 = ProjectTaskDao.Properties.SchedulePart.eq(schedulePart);
                QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3, condition4);
                queryBuilder.orderDesc(ProjectTaskDao.Properties.TaskCreatedDate);
                taskList = queryBuilder.list();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return taskList;
    }

    public synchronized List<ProjectTask> getTaskHistoryDescendingOrderOfTaskDate(String taskId, String projectId) {
        List<ProjectTask> taskList = new ArrayList<>();
        if (taskId != null && projectId != null) {
            try {
                openReadableDb();
                ProjectTaskDao dao = daoSession.getProjectTaskDao();
                org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.ParentTaskId.eq(taskId);
                org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.ProjectId.eq(projectId);
                org.greenrobot.greendao.query.WhereCondition condition2 = ProjectTaskDao.Properties.MobileId.isNotNull();
                org.greenrobot.greendao.query.WhereCondition condition3 = ProjectTaskDao.Properties.Type.eq(AppUtils.DAILY_UPDATE_TYPE);
                QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3);
                queryBuilder.orderDesc(ProjectTaskDao.Properties.TaskStartDate);
                taskList = queryBuilder.list();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return taskList;
    }

    public synchronized boolean isImagePresentInDataBase(String imageUrl) {
        boolean isPresent = false;
        ImageSync sync = null;
        if (imageUrl != null) {
            try {
                openReadableDb();
                ImageSyncDao dao = daoSession.getImageSyncDao();
                org.greenrobot.greendao.query.WhereCondition condition = ImageSyncDao.Properties.ImageUrl.eq(imageUrl);
                QueryBuilder<ImageSync> queryBuilder = dao.queryBuilder().where(condition);
                sync = queryBuilder.unique();
                if (sync != null) {
                    isPresent = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isPresent;
    }

    public synchronized List<ProjectTask> getDailyTaskUpdateBySchedule(String schedule, String projectId, String schedulePart) {
        List<ProjectTask> taskList = new ArrayList<>();
        if (schedule != null && projectId != null && schedulePart != null) {
            try {
                openReadableDb();
                ProjectTaskDao dao = daoSession.getProjectTaskDao();
                org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.Schedule.eq(schedule);
                org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.ProjectId.eq(projectId);
                org.greenrobot.greendao.query.WhereCondition condition2 = ProjectTaskDao.Properties.MobileId.isNotNull();
                org.greenrobot.greendao.query.WhereCondition condition3 = ProjectTaskDao.Properties.Type.eq(AppUtils.DAILY_UPDATE_TYPE);
                org.greenrobot.greendao.query.WhereCondition condition4 = ProjectTaskDao.Properties.SchedulePart.eq(schedulePart);
                QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3, condition4);
                queryBuilder.orderDesc(ProjectTaskDao.Properties.TaskStartDate);
                taskList = queryBuilder.list();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return taskList;
    }

    public synchronized List<ProjectTask> getTaskListForSync() {
        List<ProjectTask> tasks = new ArrayList<>();
        try {
            openReadableDb();
            ProjectTaskDao dao = daoSession.getProjectTaskDao();
            org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.TaskStatus.eq(AppUtils.IN_PROGRESS);
            QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition);
            tasks = queryBuilder.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public synchronized List<ProjectTask> getDailyTaskListPendingForSync() {
        List<ProjectTask> tasks = new ArrayList<>();
        try {
            openReadableDb();
            ProjectTaskDao dao = daoSession.getProjectTaskDao();
            org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.TaskStatus.eq(AppUtils.IN_PROGRESS);
            org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.Type.eq(AppUtils.DAILY_UPDATE_TYPE);
            QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1);
            tasks = queryBuilder.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public synchronized List<ProjectTask> getSubActivityTaskListPendingForSync() {
        List<ProjectTask> tasks = new ArrayList<>();
        try {
            openReadableDb();
            ProjectTaskDao dao = daoSession.getProjectTaskDao();
            org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.TaskStatus.eq(AppUtils.IN_PROGRESS);
            org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.Type.eq(AppUtils.SUB_ACTIVITY);
            QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1);
            tasks = queryBuilder.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public synchronized List<ProjectTask> getActivityTaskListPendingForSync() {
        List<ProjectTask> tasks = new ArrayList<>();
        try {
            openReadableDb();
            ProjectTaskDao dao = daoSession.getProjectTaskDao();
            org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.TaskStatus.eq(AppUtils.IN_PROGRESS);
            org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.Type.eq(AppUtils.ACTIVITY);
            QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1);
            tasks = queryBuilder.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public synchronized int getSubtaskCount(String taskId, String projectId) {
        int subTaskCount = 0;
        if (taskId != null && projectId != null) {
            try {
                openReadableDb();
                ProjectTaskDao dao = daoSession.getProjectTaskDao();
                org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.ParentTaskId.eq(taskId);
                org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.ProjectId.eq(projectId);
                org.greenrobot.greendao.query.WhereCondition condition2 = ProjectTaskDao.Properties.TaskId.isNotNull();
                org.greenrobot.greendao.query.WhereCondition condition3 = ProjectTaskDao.Properties.Type.eq("task");
                QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3);
                subTaskCount = queryBuilder.list().size();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return subTaskCount;
    }

    public synchronized int getActivityCount(String taskId, String projectId) {
        int activityCount = 0;
        if (taskId != null && projectId != null) {
            try {
                openReadableDb();
                ProjectTaskDao dao = daoSession.getProjectTaskDao();
                org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.ParentTaskId.eq(taskId);
                org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.ProjectId.eq(projectId);
                org.greenrobot.greendao.query.WhereCondition condition2 = ProjectTaskDao.Properties.TaskId.isNotNull();
                org.greenrobot.greendao.query.WhereCondition condition3 = ProjectTaskDao.Properties.Type.eq("activity");
                QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3);
                activityCount = queryBuilder.list().size();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return activityCount;
    }

    public synchronized List<ProjectTask> getActivityByTaskIdAndProjectId(String taskId, String projectId) {
        List<ProjectTask> projectTaskList = new ArrayList<>();
        if (taskId != null && projectId != null) {
            try {
                openReadableDb();
                ProjectTaskDao dao = daoSession.getProjectTaskDao();
                org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.ParentTaskId.eq(taskId);
                org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.ProjectId.eq(projectId);
                org.greenrobot.greendao.query.WhereCondition condition2 = ProjectTaskDao.Properties.TaskId.isNotNull();
                org.greenrobot.greendao.query.WhereCondition condition3 = ProjectTaskDao.Properties.Type.eq("activity");
                QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3);
                projectTaskList = queryBuilder.list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return projectTaskList;
    }

    public synchronized int getSubActivityCount(String taskId, String projectId) {
        int subActivityCount = 0;
        List<ProjectTask> taskList = new ArrayList<>();
        if (taskId != null && projectId != null) {
            try {
                openReadableDb();
                ProjectTaskDao dao = daoSession.getProjectTaskDao();
                org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.ParentTaskId.eq(taskId);
                org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.ProjectId.eq(projectId);
                org.greenrobot.greendao.query.WhereCondition condition2 = ProjectTaskDao.Properties.TaskId.isNotNull();
                org.greenrobot.greendao.query.WhereCondition condition3 = ProjectTaskDao.Properties.Type.eq("subActivity");
                QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3);
                taskList = queryBuilder.list();
                subActivityCount = taskList.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return subActivityCount;
    }

    public synchronized List<ProjectTask> getSubActivityListByTaskIdAndProjectId(String taskId, String projectId) {
        List<ProjectTask> projectTaskList = new ArrayList<>();
        if (taskId != null && projectId != null) {
            try {
                openReadableDb();
                ProjectTaskDao dao = daoSession.getProjectTaskDao();
                org.greenrobot.greendao.query.WhereCondition condition = ProjectTaskDao.Properties.ParentTaskId.eq(taskId);
                org.greenrobot.greendao.query.WhereCondition condition1 = ProjectTaskDao.Properties.ProjectId.eq(projectId);
                org.greenrobot.greendao.query.WhereCondition condition2 = ProjectTaskDao.Properties.TaskId.isNotNull();
                org.greenrobot.greendao.query.WhereCondition condition3 = ProjectTaskDao.Properties.Type.eq("subActivity");
                QueryBuilder<ProjectTask> queryBuilder = dao.queryBuilder().where(condition, condition1, condition2, condition3);
                projectTaskList = queryBuilder.list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return projectTaskList;
    }

    public synchronized List<SubActivityMaster> getSubActivityBySchedule(String schedule) {
        List<SubActivityMaster> subActivityMasters = new ArrayList<SubActivityMaster>();

        try {
            openReadableDb();
            SubActivityMasterDao dao = daoSession.getSubActivityMasterDao();
            org.greenrobot.greendao.query.WhereCondition condition = SubActivityMasterDao.Properties.Schedule.eq(schedule);
            QueryBuilder<SubActivityMaster> queryBuilder = dao.queryBuilder().where(condition);
            subActivityMasters = queryBuilder.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subActivityMasters;
    }

    public synchronized List<ProjectTable> getProjectByProjectName(String projectName) {
        List<ProjectTable> projectTableList = new ArrayList<>();
        if (projectName != null) {
            try {
                openReadableDb();
                ProjectTableDao dao = daoSession.getProjectTableDao();
                org.greenrobot.greendao.query.WhereCondition condition = ProjectTableDao.Properties.Name.like("%" + projectName + "%");
                QueryBuilder<ProjectTable> queryBuilder = dao.queryBuilder().where(condition);
                projectTableList = queryBuilder.list();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return projectTableList;
    }

    public synchronized List<ProjectTable> getAllProject() {
        List<ProjectTable> projectTableList = new ArrayList<>();
        try {
            openReadableDb();
            ProjectTableDao dao = daoSession.getProjectTableDao();
            org.greenrobot.greendao.query.WhereCondition condition = ProjectTableDao.Properties.ProjectId.isNotNull();
            QueryBuilder<ProjectTable> queryBuilder = dao.queryBuilder().where(condition);
            projectTableList = queryBuilder.list();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectTableList;
    }

    public synchronized void deleteAllImages() {
        try {
            openWritableDb();
            ImageSyncDao dao = daoSession.getImageSyncDao();
            org.greenrobot.greendao.query.WhereCondition condition = ImageSyncDao.Properties.Id.isNotNull();
            QueryBuilder<ImageSync> queryBuilder = dao.queryBuilder().where(condition);
            queryBuilder.buildDelete().executeDeleteWithoutDetachingEntities();
            daoSession.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearTable() {
        openReadableDb();
        daoSession.getProjectTableDao().deleteAll();
        daoSession.getProjectTaskDao().deleteAll();
        daoSession.getActivityMasterDao().deleteAll();
        daoSession.getSubActivityMasterDao().deleteAll();
        daoSession.getLocationMasterDao().deleteAll();
        daoSession.getUserMasterDao().deleteAll();
        daoSession.clear();
    }

    public void clearImageTable() {
        openReadableDb();
        daoSession.getImageSyncDao().deleteAll();
    }


}