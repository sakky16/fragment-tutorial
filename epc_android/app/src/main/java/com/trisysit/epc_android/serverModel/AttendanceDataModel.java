package com.trisysit.epc_android.serverModel;

import com.trisysit.epc_android.SqliteDatabase.ProjectTask;
import com.trisysit.epc_android.model.AttendanceModel;

import java.util.List;

/**
 * Created by trisys on 21/12/17.
 */

public class AttendanceDataModel {
    List<AttendanceModel> attendanceList;
    String lastSyncTime;
    String token;

    public List<AttendanceModel> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<AttendanceModel> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public String getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(String lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
