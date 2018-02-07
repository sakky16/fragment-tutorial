package com.trisysit.epc_android.serverModel;

import com.trisysit.epc_android.SqliteDatabase.ActivityMaster;
import com.trisysit.epc_android.SqliteDatabase.LocationMaster;
import com.trisysit.epc_android.SqliteDatabase.ProjectTable;
import com.trisysit.epc_android.SqliteDatabase.ProjectTask;
import com.trisysit.epc_android.SqliteDatabase.SubActivityMaster;
import com.trisysit.epc_android.model.Material;
import com.trisysit.epc_android.model.Project;
import com.trisysit.epc_android.model.Task;

import java.util.List;

/**
 * Created by trisys on 11/12/17.
 */

public class TaskDataModel {
    List<ProjectTask> tasks;
    List<ProjectTable> projects;
    List<SubActivityMaster> subActivities;
    List<ActivityMaster> activities;
    List<LocationMaster> locationMasters;

    public List<LocationMaster> getLocationMasters() {
        return locationMasters;
    }

    public void setLocationMasters(List<LocationMaster> locationMasters) {
        this.locationMasters = locationMasters;
    }

    public List<SubActivityMaster> getSubActivities() {
        return subActivities;
    }

    public void setSubActivities(List<SubActivityMaster> subActivities) {
        this.subActivities = subActivities;
    }

    public List<ActivityMaster> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityMaster> activities) {
        this.activities = activities;
    }

    String token;


    public List<ProjectTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<ProjectTask> tasks) {
        this.tasks = tasks;
    }

    public List<ProjectTable> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectTable> projects) {
        this.projects = projects;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
