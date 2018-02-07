package com.trisysit.epc_android.serverModel;

/**
 * Created by trisys on 11/12/17.
 */

public class TaskResponseModel {
    String status;
    String message;
    TaskDataModel data;
    String lastSyncTime;

    public String getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(String lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

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

    public TaskDataModel getData() {
        return data;
    }

    public void setData(TaskDataModel data) {
        this.data = data;
    }
}
