package com.trisysit.epc_android.SqliteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.trisysit.epc_android.model.AttendanceModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by trisys on 7/12/17.
 */

public class DatabaaseUtils {
    public static final String DATABASE_NAME = "db_EPC";
    public static final int DATABASE_VERSION = 4;
    public static final String ATTENDANCE_TB = "attendanceTable";

    public static final String TASK_TB = "attendanceTable";
    public static final String MATERIAL_TB = "attendanceTable";
    public static final String PROJECT_TB = "attendanceTable";

    //Attendace Table Column
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DATE = "date";
    private static final String INTIME = "inTime";
    private static final String INGPS = "inGPS";
    private static final String OUTTIME = "outTime";
    private static final String OUTGPS = "outGPS";
    private static final String STATUS = "status";
    private static final String CREATEDDATE = "createdDate";
    private static final String CREATEDBY = "createdBy";
    private static final String UPDATEDDATE = "updatedDate";
    private static final String UPDATEDBY = "updatedBy";
    private static final String MOBILEID = "mobileId";
    private static final String IMAGE1 = "image1";
    private static final String IMAGE2 = "image2";
    private static final String IMAGE3 = "image3";
    private static final String IMAGE4 = "image4";
    private static final String IMAGE5 = "image5";
    private static final String IMAGE6 = "image6";
    private static final String IMAGE7 = "image7";
    private static final String IMAGE8 = "image8";
    private static final String DEVICE_ID="deviceId";
    private static final String COMMENT1 = "comment1";
    private static final String COMMENT2 = "comment2";
    private static final String COMMENT3 = "comment3";
    private static final String COMMENT4 = "comment4";
    private static final String COMMENT5 = "comment5";
    private static final String COMMENT6 = "comment6";
    private static final String COMMENT7 = "comment7";
    private static final String COMMENT8 = "comment8";
    private static final String IMAGETIME1 = "imageTime1";
    private static final String IMAGETIME2 = "imageTime2";
    private static final String IMAGETIME3 = "imageTime3";
    private static final String IMAGETIME4 = "imageTime4";
    private static final String IMAGETIME5 = "imageTime5";
    private static final String IMAGETIME6 = "imageTime6";
    private static final String IMAGETIME7 = "imageTime7";
    private static final String IMAGETIME8 = "imageTime8";
    private static final String GPS1 = "gps1";
    private static final String GPS2 = "gps2";
    private static final String GPS3 = "gps3";
    private static final String GPS4 = "gps4";
    private static final String GPS5 = "gps5";
    private static final String GPS6 = "gps6";
    private static final String GPS7 = "gps7";
    private static final String GPS8 = "gps8";

    //Task Table column


    public static final String CREATE_TB_ATTENDANCE = "CREATE TABLE IF NOT EXISTS  "
            + ATTENDANCE_TB

            + "("

            + MOBILEID + " TEXT PRIMARY KEY, "

            + ID + " TEXT, "

            + NAME + " TEXT , "

            + INTIME + " TEXT , "

            + INGPS + " TEXT , "

            + OUTTIME + " TEXT , "

            + OUTGPS + " TEXT , "

            + STATUS + " TEXT , "

            + CREATEDDATE + " TEXT , "

            + CREATEDBY + " TEXT , "

            + UPDATEDDATE + " TEXT , "

            + UPDATEDBY + " TEXT , "

            + DATE + " TEXT,"

            + IMAGE1 + " TEXT , "

            + IMAGE2 + " TEXT , "

            + IMAGE3 + " TEXT , "

            + IMAGE4 + " TEXT , "

            + IMAGE5 + " TEXT , "

            + IMAGE6 + " TEXT , "

            + IMAGE7 + " TEXT , "

            + IMAGE8 + " TEXT , "

            + DEVICE_ID + " TEXT , "

            + COMMENT1 + " TEXT , "

            + COMMENT2 + " TEXT, "

            + COMMENT3 + " TEXT, "

            + COMMENT4 + " TEXT , "

            + COMMENT5 + " TEXT , "

            + COMMENT7 + " TEXT , "

            + COMMENT8 + " TEXT , "

            + IMAGETIME1 + " TEXT , "

            + IMAGETIME2 + " TEXT , "

            + IMAGETIME3 + " TEXT , "

            + IMAGETIME4 + " TEXT , "

            + IMAGETIME5 + " TEXT , "

            + IMAGETIME6 + " TEXT , "

            + IMAGETIME7 + " TEXT , "

            + IMAGETIME8 + " TEXT , "

            + GPS1 + " TEXT , "

            + GPS2 + " TEXT , "

            + GPS3 + " TEXT , "

            + GPS4 + " TEXT , "

            + GPS5 + " TEXT , "

            + GPS6 + " TEXT , "

            + GPS7 + " TEXT , "

            + GPS8 + " TEXT , "


            + COMMENT6 + " TEXT);";

    private static final String TAG = DatabaaseUtils.class.getSimpleName();

    public static SQLiteDatabase getDatabaseInstance(Context context) {

        return new SqlHelper(context)
                .getWritableDatabase();
    }

    public static void insertAttendance(Context context, AttendanceModel model) {SQLiteDatabase db = getDatabaseInstance(context);
        boolean status = false;
        try {
            ContentValues values = new ContentValues();
            values.put(ID, "" + model.getId());
            values.put(MOBILEID, "" +model.getMobileId() );
            values.put(NAME, "" +model.getName() );
            values.put(DATE, "" +model.getDate() );
            values.put(INTIME, "" +model.getInTime() );
            values.put(INGPS, "" +model.getInGPS() );
            values.put(OUTGPS, "" +model.getOutGPS() );
            values.put(OUTTIME,""+model.getOutTime());
            values.put(STATUS,""+model.getStatus());
            values.put(CREATEDBY,""+model.getCreatedBy());
            values.put(CREATEDDATE,""+model.getCreatedDate());
            values.put(UPDATEDDATE,""+model.getUpdatedDate());
            values.put(UPDATEDBY,""+model.getUpdatedBy());
            values.put(IMAGE1,""+model.getImage1());
            values.put(IMAGE2,""+model.getImage2());
            values.put(IMAGE3,""+model.getImage3());
            values.put(IMAGE4,""+model.getImage4());
            values.put(IMAGE5,""+model.getImage5());
            values.put(IMAGE6,""+model.getImage6());
            values.put(COMMENT1,""+model.getComment1());
            values.put(COMMENT2,""+model.getComment2());
            values.put(COMMENT3,""+model.getComment3());
            values.put(COMMENT4,""+model.getComment4());
            values.put(COMMENT5,""+model.getComment5());
            values.put(COMMENT6,""+model.getComment6());
            values.put(GPS1,""+model.getGps1());
            values.put(GPS2,""+model.getGps2());
            values.put(GPS3,""+model.getGps3());
            values.put(GPS4,""+model.getGps4());
            values.put(GPS5,""+model.getGps5());
            values.put(GPS6,""+model.getGps6());
            values.put(DEVICE_ID,""+model.getDeviceId());
            //values.put(GPS7,""+model.getGps7());
            //values.put(GPS8,""+model.getGps8());
            values.put(IMAGETIME1,""+model.getImageTime1());
            values.put(IMAGETIME2,""+model.getImageTime2());
            values.put(IMAGETIME3,""+model.getImageTime3());
            values.put(IMAGETIME4,""+model.getImageTime4());
            values.put(IMAGETIME5,""+model.getImageTime5());
            values.put(IMAGETIME6,""+model.getImageTime6());

            long insertStatus = db.insertOrThrow(ATTENDANCE_TB, null, values);
            if (insertStatus > -1) {
                status = true;
                Log.i(TAG, "Inserted Feedback");
            } else {
                Log.i(TAG, "Not inserted Feedback");
                status = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
    public static void replaceAttendance(Context context, AttendanceModel model) {
        SQLiteDatabase db = getDatabaseInstance(context);
        boolean status = false;
        try {
            ContentValues values = new ContentValues();
            values.put(ID, "" + model.getId());
            values.put(MOBILEID, "" +model.getMobileId() );
            values.put(NAME, "" +model.getName() );
            values.put(DATE, "" +model.getDate() );
            values.put(INTIME, "" +model.getInTime() );
            values.put(INGPS, "" +model.getInGPS() );
            values.put(OUTGPS, "" +model.getOutGPS() );
            values.put(OUTTIME,""+model.getOutTime());
            values.put(STATUS,""+model.getStatus());
            values.put(CREATEDBY,""+model.getCreatedBy());
            values.put(CREATEDDATE,""+model.getCreatedDate());
            values.put(UPDATEDDATE,""+model.getUpdatedDate());
            values.put(UPDATEDBY,""+model.getUpdatedBy());
            values.put(IMAGE1,""+model.getImage1());
            values.put(IMAGE2,""+model.getImage2());
            values.put(IMAGE3,""+model.getImage3());
            values.put(IMAGE4,""+model.getImage4());
            values.put(IMAGE5,""+model.getImage5());
            values.put(IMAGE6,""+model.getImage6());
            values.put(COMMENT1,""+model.getComment1());
            values.put(COMMENT2,""+model.getComment2());
            values.put(COMMENT3,""+model.getComment3());
            values.put(COMMENT4,""+model.getComment4());
            values.put(COMMENT5,""+model.getComment5());
            values.put(COMMENT6,""+model.getComment6());
            values.put(GPS1,""+model.getGps1());
            values.put(GPS2,""+model.getGps2());
            values.put(GPS3,""+model.getGps3());
            values.put(GPS4,""+model.getGps4());
            values.put(GPS5,""+model.getGps5());
            values.put(GPS6,""+model.getGps6());
            values.put(DEVICE_ID,""+model.getDeviceId());
            //values.put(GPS7,""+model.getGps7());
            //values.put(GPS8,""+model.getGps8());
            values.put(IMAGETIME1,""+model.getImageTime1());
            values.put(IMAGETIME2,""+model.getImageTime2());
            values.put(IMAGETIME3,""+model.getImageTime3());
            values.put(IMAGETIME4,""+model.getImageTime4());
            values.put(IMAGETIME5,""+model.getImageTime5());
            values.put(IMAGETIME6,""+model.getImageTime6());

            long insertStatus = db.replaceOrThrow(ATTENDANCE_TB, null, values);
            if (insertStatus > -1) {
                status = true;
                Log.i(TAG, "Inserted Feedback");
            } else {
                Log.i(TAG, "Not inserted Feedback");
                status = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public static boolean isAttendanceExist(Context context, String id) {
        SQLiteDatabase db = getDatabaseInstance(context);

        String selectQuery = "SELECT  *  FROM " + ATTENDANCE_TB + " WHERE " + MOBILEID + " ='" + id + "';";

        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor != null && cursor.getCount() > 0;
    }

    public static ArrayList<AttendanceModel> getAttendanceList(Context context){
        final ArrayList<AttendanceModel> attendanceModel=new ArrayList<>();
        SQLiteDatabase db=getDatabaseInstance(context);
        String sql="SELECT * FROM "+ ATTENDANCE_TB +" ORDER BY "+CREATEDDATE+" DESC";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor!=null){
            if(cursor.moveToNext()){
                do {
                    try {
                        AttendanceModel model=new AttendanceModel();
                        model.setMobileId(cursor.getString(cursor.getColumnIndex(MOBILEID)));
                        model.setId(cursor.getString(cursor.getColumnIndex(ID)));
                        model.setInTime(cursor.getString(cursor.getColumnIndex(INTIME)));
                        model.setOutTime(cursor.getString(cursor.getColumnIndex(OUTTIME)));
                        model.setInGPS(cursor.getString(cursor.getColumnIndex(INGPS)));
                        model.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                        model.setOutGPS(cursor.getString(cursor.getColumnIndex(OUTGPS)));
                        model.setImage1(cursor.getString(cursor.getColumnIndex(IMAGE1)));
                        model.setImage2(cursor.getString(cursor.getColumnIndex(IMAGE2)));
                        model.setImage3(cursor.getString(cursor.getColumnIndex(IMAGE3)));
                        model.setImage4(cursor.getString(cursor.getColumnIndex(IMAGE4)));
                        model.setImage5(cursor.getString(cursor.getColumnIndex(IMAGE5)));
                        model.setImage6(cursor.getString(cursor.getColumnIndex(IMAGE6)));
                        model.setComment1(cursor.getString(cursor.getColumnIndex(COMMENT1)));
                        model.setComment2(cursor.getString(cursor.getColumnIndex(COMMENT2)));
                        model.setComment3(cursor.getString(cursor.getColumnIndex(COMMENT3)));
                        model.setComment4(cursor.getString(cursor.getColumnIndex(COMMENT4)));
                        model.setComment5(cursor.getString(cursor.getColumnIndex(COMMENT5)));
                        model.setComment6(cursor.getString(cursor.getColumnIndex(COMMENT6)));
                        model.setGps1(cursor.getString(cursor.getColumnIndex(GPS1)));
                        model.setGps2(cursor.getString(cursor.getColumnIndex(GPS2)));
                        model.setGps3(cursor.getString(cursor.getColumnIndex(GPS3)));
                        model.setGps4(cursor.getString(cursor.getColumnIndex(GPS4)));
                        model.setGps5(cursor.getString(cursor.getColumnIndex(GPS5)));
                        model.setGps6(cursor.getString(cursor.getColumnIndex(GPS6)));
                        //model.setGps7(cursor.getString(cursor.getColumnIndex(GPS7)));
                        //model.setGps8(cursor.getString(cursor.getColumnIndex(GPS8)));
                        model.setImageTime1(cursor.getString(cursor.getColumnIndex(IMAGETIME1)));
                        model.setImageTime2(cursor.getString(cursor.getColumnIndex(IMAGETIME2)));
                        model.setImageTime3(cursor.getString(cursor.getColumnIndex(IMAGETIME3)));
                        model.setImageTime4(cursor.getString(cursor.getColumnIndex(IMAGETIME4)));
                        model.setImageTime5(cursor.getString(cursor.getColumnIndex(IMAGETIME5)));
                        model.setImageTime6(cursor.getString(cursor.getColumnIndex(IMAGETIME6)));
                        model.setDeviceId(cursor.getString(cursor.getColumnIndex(DEVICE_ID)));

                        attendanceModel.add(model);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }while (cursor.moveToNext());
            }
        }
        if(cursor!=null){
            cursor.close();
        }
        db.close();
        return attendanceModel;
    }
    public static List<AttendanceModel> getAttendanceByNameAndDate(Context context,String name){
        final ArrayList<AttendanceModel> attendanceModel=new ArrayList<>();
        SQLiteDatabase db=getDatabaseInstance(context);
        String sql="SELECT * FROM "+ ATTENDANCE_TB +" WHERE " + NAME + " = '" + name + "' ORDER BY "+CREATEDDATE+" DESC";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor!=null){
            if(cursor.moveToNext()){
                do {
                    try {
                        AttendanceModel model=new AttendanceModel();
                        model.setMobileId(cursor.getString(cursor.getColumnIndex(MOBILEID)));
                        model.setId(cursor.getString(cursor.getColumnIndex(ID)));
                        model.setInTime(cursor.getString(cursor.getColumnIndex(INTIME)));
                        model.setOutTime(cursor.getString(cursor.getColumnIndex(OUTTIME)));
                        model.setInGPS(cursor.getString(cursor.getColumnIndex(INGPS)));
                        model.setOutGPS(cursor.getString(cursor.getColumnIndex(OUTGPS)));
                        model.setImage1(cursor.getString(cursor.getColumnIndex(IMAGE1)));
                        model.setImage2(cursor.getString(cursor.getColumnIndex(IMAGE2)));
                        model.setImage3(cursor.getString(cursor.getColumnIndex(IMAGE3)));
                        model.setImage4(cursor.getString(cursor.getColumnIndex(IMAGE4)));
                        model.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                        model.setImage5(cursor.getString(cursor.getColumnIndex(IMAGE5)));
                        model.setImage6(cursor.getString(cursor.getColumnIndex(IMAGE6)));
                        model.setComment1(cursor.getString(cursor.getColumnIndex(COMMENT1)));
                        model.setComment2(cursor.getString(cursor.getColumnIndex(COMMENT2)));
                        model.setComment3(cursor.getString(cursor.getColumnIndex(COMMENT3)));
                        model.setComment4(cursor.getString(cursor.getColumnIndex(COMMENT4)));
                        model.setComment5(cursor.getString(cursor.getColumnIndex(COMMENT5)));
                        model.setComment6(cursor.getString(cursor.getColumnIndex(COMMENT6)));
                        model.setGps1(cursor.getString(cursor.getColumnIndex(GPS1)));
                        model.setGps2(cursor.getString(cursor.getColumnIndex(GPS2)));
                        model.setGps3(cursor.getString(cursor.getColumnIndex(GPS3)));
                        model.setGps4(cursor.getString(cursor.getColumnIndex(GPS4)));
                        model.setGps5(cursor.getString(cursor.getColumnIndex(GPS5)));
                        model.setGps6(cursor.getString(cursor.getColumnIndex(GPS6)));
                        model.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                        model.setImageTime1(cursor.getString(cursor.getColumnIndex(IMAGETIME1)));
                        model.setImageTime2(cursor.getString(cursor.getColumnIndex(IMAGETIME2)));
                        model.setImageTime3(cursor.getString(cursor.getColumnIndex(IMAGETIME3)));
                        model.setImageTime4(cursor.getString(cursor.getColumnIndex(IMAGETIME4)));
                        model.setImageTime5(cursor.getString(cursor.getColumnIndex(IMAGETIME5)));
                        model.setImageTime6(cursor.getString(cursor.getColumnIndex(IMAGETIME6)));
                        model.setDeviceId(cursor.getString(cursor.getColumnIndex(DEVICE_ID)));
                        attendanceModel.add(model);
                        //model.setGps7(cursor.getString(cursor.getColumnIndex(GPS7)));
                        //model.setGps8(cursor.getString(cursor.getColumnIndex(GPS8)));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }while (cursor.moveToNext());
            }
        }
        if(cursor!=null){
            cursor.close();
        }
        db.close();
        return attendanceModel;
    }
    public static AttendanceModel getAttendanceDetailsByMobileId(Context context,String mobileId){
        SQLiteDatabase db=getDatabaseInstance(context);
        AttendanceModel model=new AttendanceModel();
        String sql="SELECT * FROM "+ ATTENDANCE_TB +" WHERE " + MOBILEID + " = '" + mobileId + "'";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor!=null){
            if(cursor.moveToNext()){
                do {
                    try {

                        model.setMobileId(cursor.getString(cursor.getColumnIndex(MOBILEID)));
                        model.setId(cursor.getString(cursor.getColumnIndex(ID)));
                        model.setInTime(cursor.getString(cursor.getColumnIndex(INTIME)));
                        model.setOutTime(cursor.getString(cursor.getColumnIndex(OUTTIME)));
                        model.setInGPS(cursor.getString(cursor.getColumnIndex(INGPS)));
                        model.setOutGPS(cursor.getString(cursor.getColumnIndex(OUTGPS)));
                        model.setImage1(cursor.getString(cursor.getColumnIndex(IMAGE1)));
                        model.setImage2(cursor.getString(cursor.getColumnIndex(IMAGE2)));
                        model.setImage3(cursor.getString(cursor.getColumnIndex(IMAGE3)));
                        model.setImage4(cursor.getString(cursor.getColumnIndex(IMAGE4)));
                        model.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                        model.setImage5(cursor.getString(cursor.getColumnIndex(IMAGE5)));
                        model.setImage6(cursor.getString(cursor.getColumnIndex(IMAGE6)));
                        model.setComment1(cursor.getString(cursor.getColumnIndex(COMMENT1)));
                        model.setComment2(cursor.getString(cursor.getColumnIndex(COMMENT2)));
                        model.setComment3(cursor.getString(cursor.getColumnIndex(COMMENT3)));
                        model.setComment4(cursor.getString(cursor.getColumnIndex(COMMENT4)));
                        model.setComment5(cursor.getString(cursor.getColumnIndex(COMMENT5)));
                        model.setComment6(cursor.getString(cursor.getColumnIndex(COMMENT6)));
                        model.setGps1(cursor.getString(cursor.getColumnIndex(GPS1)));
                        model.setGps2(cursor.getString(cursor.getColumnIndex(GPS2)));
                        model.setGps3(cursor.getString(cursor.getColumnIndex(GPS3)));
                        model.setGps4(cursor.getString(cursor.getColumnIndex(GPS4)));
                        model.setGps5(cursor.getString(cursor.getColumnIndex(GPS5)));
                        model.setGps6(cursor.getString(cursor.getColumnIndex(GPS6)));
                        model.setImageTime1(cursor.getString(cursor.getColumnIndex(IMAGETIME1)));
                        model.setImageTime2(cursor.getString(cursor.getColumnIndex(IMAGETIME2)));
                        model.setImageTime3(cursor.getString(cursor.getColumnIndex(IMAGETIME3)));
                        model.setImageTime4(cursor.getString(cursor.getColumnIndex(IMAGETIME4)));
                        model.setImageTime5(cursor.getString(cursor.getColumnIndex(IMAGETIME5)));
                        model.setImageTime6(cursor.getString(cursor.getColumnIndex(IMAGETIME6)));
                        model.setDeviceId(cursor.getString(cursor.getColumnIndex(DEVICE_ID)));
                        //model.setGps7(cursor.getString(cursor.getColumnIndex(GPS7)));
                        //model.setGps8(cursor.getString(cursor.getColumnIndex(GPS8)));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }while (cursor.moveToNext());
            }
        }
        if(cursor!=null){
            cursor.close();
        }
        db.close();
        return model;
    }
    public static void deleteAllTableData(Context context){
        SQLiteDatabase db=getDatabaseInstance(context);
        db.delete(ATTENDANCE_TB,null,null);
    }
    public static List<AttendanceModel> getAttendListForSync(Context context,String status){
        SQLiteDatabase db=getDatabaseInstance(context);
        final ArrayList<AttendanceModel> attendanceModels=new ArrayList<>();
        AttendanceModel model=new AttendanceModel();
        String sql="SELECT * FROM "+ ATTENDANCE_TB +" WHERE " + STATUS + " = '" + status + "'";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor!=null){
            if(cursor.moveToNext()){
                do {
                    try {
                        model.setMobileId(cursor.getString(cursor.getColumnIndex(MOBILEID)));
                        model.setId(cursor.getString(cursor.getColumnIndex(ID)));
                        model.setInTime(cursor.getString(cursor.getColumnIndex(INTIME)));
                        model.setOutTime(cursor.getString(cursor.getColumnIndex(OUTTIME)));
                        model.setInGPS(cursor.getString(cursor.getColumnIndex(INGPS)));
                        model.setOutGPS(cursor.getString(cursor.getColumnIndex(OUTGPS)));
                        model.setImage1(cursor.getString(cursor.getColumnIndex(IMAGE1)));
                        model.setImage2(cursor.getString(cursor.getColumnIndex(IMAGE2)));
                        model.setImage3(cursor.getString(cursor.getColumnIndex(IMAGE3)));
                        model.setImage4(cursor.getString(cursor.getColumnIndex(IMAGE4)));
                        model.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                        model.setCreatedDate(cursor.getString(cursor.getColumnIndex(CREATEDDATE)));
                        model.setImage5(cursor.getString(cursor.getColumnIndex(IMAGE5)));
                        model.setImage6(cursor.getString(cursor.getColumnIndex(IMAGE6)));
                        model.setComment1(cursor.getString(cursor.getColumnIndex(COMMENT1)));
                        model.setComment2(cursor.getString(cursor.getColumnIndex(COMMENT2)));
                        model.setComment3(cursor.getString(cursor.getColumnIndex(COMMENT3)));
                        model.setComment4(cursor.getString(cursor.getColumnIndex(COMMENT4)));
                        model.setComment5(cursor.getString(cursor.getColumnIndex(COMMENT5)));
                        model.setComment6(cursor.getString(cursor.getColumnIndex(COMMENT6)));
                        model.setGps1(cursor.getString(cursor.getColumnIndex(GPS1)));
                        model.setGps2(cursor.getString(cursor.getColumnIndex(GPS2)));
                        model.setGps3(cursor.getString(cursor.getColumnIndex(GPS3)));
                        model.setGps4(cursor.getString(cursor.getColumnIndex(GPS4)));
                        model.setGps5(cursor.getString(cursor.getColumnIndex(GPS5)));
                        model.setGps6(cursor.getString(cursor.getColumnIndex(GPS6)));
                        //model.setGps7(cursor.getString(cursor.getColumnIndex(GPS7)));
                        //model.setGps8(cursor.getString(cursor.getColumnIndex(GPS8)));
                        model.setImageTime1(cursor.getString(cursor.getColumnIndex(IMAGETIME1)));
                        model.setImageTime2(cursor.getString(cursor.getColumnIndex(IMAGETIME2)));
                        model.setImageTime3(cursor.getString(cursor.getColumnIndex(IMAGETIME3)));
                        model.setImageTime4(cursor.getString(cursor.getColumnIndex(IMAGETIME4)));
                        model.setImageTime5(cursor.getString(cursor.getColumnIndex(IMAGETIME5)));
                        model.setImageTime6(cursor.getString(cursor.getColumnIndex(IMAGETIME6)));
                        model.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                        model.setCreatedBy(cursor.getString(cursor.getColumnIndex(CREATEDBY)));
                        model.setDeviceId(cursor.getString(cursor.getColumnIndex(DEVICE_ID)));
                        attendanceModels.add(model);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }while (cursor.moveToNext());
            }
        }
        if(cursor!=null){
            cursor.close();
        }
        db.close();
        return attendanceModels;
    }




}
