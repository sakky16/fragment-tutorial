package com.trisysit.epc_android.serverModel;

import com.trisysit.epc_android.SqliteDatabase.ProjectTask;
import com.trisysit.epc_android.model.Material;

import java.util.List;

/**
 * Created by trisys on 11/12/17.
 */

public class TaskRequestModel {
    String token;
    List<ProjectTask> tasks;
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

    public List<ProjectTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<ProjectTask> tasks) {
        this.tasks = tasks;
    }

    public void setToken(String token) {
        this.token = token;

    }

}
