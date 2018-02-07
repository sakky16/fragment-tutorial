package com.trisysit.epc_android.serverModel;

import com.trisysit.epc_android.model.AttendanceModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by trisys on 9/12/17.
 */

public class AttendanceResponse implements Serializable {
    String status;
    String message;
    String lastSyncTime;


    public String getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(String lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }
    AttendanceDataModel data;

    public AttendanceDataModel getData() {
        return data;
    }

    public void setData(AttendanceDataModel data) {
        this.data = data;
    }
    //List<AttendanceModel> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /*//public List<AttendanceModel> getData() {
        return data;
    }*/

    /*public void setData(List<AttendanceModel> data) {
        this.data = data;
    }*/
}
