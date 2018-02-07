package com.trisysit.epc_android.serverModel;

import com.trisysit.epc_android.model.AttendanceModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by trisys on 9/12/17.
 */

public class AttendanceRequest implements Serializable {
    String token;
    List<AttendanceModel> attendanceList;
    String lastSyncTime;

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

    public List<AttendanceModel> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<AttendanceModel> attendanceList) {
        this.attendanceList = attendanceList;
    }
}
