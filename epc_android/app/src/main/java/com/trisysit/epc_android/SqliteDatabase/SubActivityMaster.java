package com.trisysit.epc_android.SqliteDatabase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by trisys on 3/1/18.
 */
@Entity
public class SubActivityMaster {
    private String schedule;
    private String srNo;
    private String schedulePart;
    private String unit;
    private String longDescription;
    private String particular;
    private String subScheduleMultiplier;
    private String scheduleMultiplier;
    private String markRound;
    private String projectId;
    @Generated(hash = 1533538936)
    public SubActivityMaster(String schedule, String srNo, String schedulePart,
            String unit, String longDescription, String particular,
            String subScheduleMultiplier, String scheduleMultiplier,
            String markRound, String projectId) {
        this.schedule = schedule;
        this.srNo = srNo;
        this.schedulePart = schedulePart;
        this.unit = unit;
        this.longDescription = longDescription;
        this.particular = particular;
        this.subScheduleMultiplier = subScheduleMultiplier;
        this.scheduleMultiplier = scheduleMultiplier;
        this.markRound = markRound;
        this.projectId = projectId;
    }
    @Generated(hash = 712426634)
    public SubActivityMaster() {
    }
    public String getSchedule() {
        return this.schedule;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    public String getSrNo() {
        return this.srNo;
    }
    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }
    public String getSchedulePart() {
        return this.schedulePart;
    }
    public void setSchedulePart(String schedulePart) {
        this.schedulePart = schedulePart;
    }
    public String getUnit() {
        return this.unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getLongDescription() {
        return this.longDescription;
    }
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
    public String getParticular() {
        return this.particular;
    }
    public void setParticular(String particular) {
        this.particular = particular;
    }
    public String getSubScheduleMultiplier() {
        return this.subScheduleMultiplier;
    }
    public void setSubScheduleMultiplier(String subScheduleMultiplier) {
        this.subScheduleMultiplier = subScheduleMultiplier;
    }
    public String getScheduleMultiplier() {
        return this.scheduleMultiplier;
    }
    public void setScheduleMultiplier(String scheduleMultiplier) {
        this.scheduleMultiplier = scheduleMultiplier;
    }
    public String getMarkRound() {
        return this.markRound;
    }
    public void setMarkRound(String markRound) {
        this.markRound = markRound;
    }
    public String getProjectId() {
        return this.projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
