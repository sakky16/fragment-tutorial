package com.trisysit.epc_android.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.trisysit.epc_android.activity.AttendaneAdminRecordAttendanceActivity;
import com.trisysit.epc_android.activity.RecordAttendanceActivity;

import java.util.Arrays;

/**
 * Created by trisys on 11/12/17.
 */

public class LocationDetector implements LocationListener {
    private Context context;
    public boolean canGetLocation=false;
    private String locationType;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 3; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    public LocationDetector (Context context,String locationType){
        this.context=context;
        this.locationType=locationType;
        startLocationListener();
    }

    public void startLocationListener() {
        try {
            SharedPrefHelper sharedPrefHelper = SharedPrefHelper.getInstance(null);
            LocationManager locationManager = (LocationManager)context
                    .getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // We are not going to request permissions. It should have been setup as part of installation
                sharedPrefHelper.saveInSharedPrefs("callGps", "true");
                return;
            }
            try {

                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                //locationManager.removeUpdates(this);
                Log.d("Network", "Network");
            } catch (Exception ex) {
                Log.d("epc", "Network provider did not work:" + ex.toString());
            }

            try {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES * 2, //slower updates from GPS to save battery
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location!=null){
                    String latitude=String.valueOf(location.getLatitude());
                    String longitude=String.valueOf(location.getLongitude());
                    //locationManager.removeUpdates(this);
                }
                Log.d("GPS Enabled", "GPS Enabled");
            } catch (Exception ex) {
                Log.d("epc", "GPS provider did not work:" + ex);
            }
        } catch (Exception e) {
            Log.v("epc", Arrays.toString(e.getStackTrace()));
        }

    }
    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            String latitude=String.valueOf(location.getLatitude());
            String longitude=String.valueOf(location.getLongitude());
            SharedPrefHelper.getInstance(context).saveInSharedPrefs(locationType,latitude+","+longitude);

            if(locationType!=null && locationType.equalsIgnoreCase("outGps")){
                if(context!=null){
                    if(context instanceof RecordAttendanceActivity) {
                        RecordAttendanceActivity checkinActivity = (RecordAttendanceActivity) context;
                        checkinActivity.saveIntoDb(latitude+","+longitude,AppUtils.PUNCH_OUT,locationType);
                        this.locationType="";

                    }
                    else if(context instanceof AttendaneAdminRecordAttendanceActivity){
                        AttendaneAdminRecordAttendanceActivity checkinActivity = (AttendaneAdminRecordAttendanceActivity) context;
                        checkinActivity.saveIntoDb(latitude+","+longitude,AppUtils.PUNCH_OUT,locationType);
                        this.locationType="";
                    }
                }
            }
            else if(locationType!=null && !locationType.equalsIgnoreCase("inGps") && !locationType.equalsIgnoreCase("")){
                if(context!=null){
                    if(context instanceof RecordAttendanceActivity) {
                        RecordAttendanceActivity checkinActivity = (RecordAttendanceActivity) context;
                        checkinActivity.saveIntoDb(latitude+","+longitude,AppUtils.SAVE,locationType);
                        this.locationType="";
                    }

                }
            }
            else if(locationType!=null && locationType.equalsIgnoreCase("inGps")){
                if(context!=null){
                    if(context instanceof RecordAttendanceActivity){
                        RecordAttendanceActivity checkinActivity = (RecordAttendanceActivity) context;
                        checkinActivity.saveIntoDb(latitude+","+longitude,AppUtils.PUNCH_IN,locationType);
                        this.locationType="";
                    }
                    else if(context instanceof AttendaneAdminRecordAttendanceActivity){
                        AttendaneAdminRecordAttendanceActivity recordAttendanceActivity=(AttendaneAdminRecordAttendanceActivity)context;
                        recordAttendanceActivity.saveIntoDb(latitude+","+longitude,AppUtils.PUNCH_IN,locationType);
                        this.locationType="";
                    }
                }
            }
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}