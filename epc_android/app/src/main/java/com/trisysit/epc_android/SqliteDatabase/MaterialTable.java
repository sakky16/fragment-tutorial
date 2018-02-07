package com.trisysit.epc_android.SqliteDatabase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by trisys on 11/12/17.
 */
@Entity
public class MaterialTable {
    private String id;
    private String name;
    private int qnty;
    private int actualQnty;
    private String unit;
    private String status;
    private String taskId;
    private String materialCount;
    private String projectId;
    private String createdDate;
    private String createdBy;
    private String updatedDate;
    private String updatedBy;
    private String schedule;
    @Id
    private String materialMobileId;
    private String srNo;
    @Generated(hash = 32605528)
    public MaterialTable(String id, String name, int qnty, int actualQnty,
            String unit, String status, String taskId, String materialCount,
            String projectId, String createdDate, String createdBy,
            String updatedDate, String updatedBy, String schedule,
            String materialMobileId, String srNo) {
        this.id = id;
        this.name = name;
        this.qnty = qnty;
        this.actualQnty = actualQnty;
        this.unit = unit;
        this.status = status;
        this.taskId = taskId;
        this.materialCount = materialCount;
        this.projectId = projectId;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.updatedDate = updatedDate;
        this.updatedBy = updatedBy;
        this.schedule = schedule;
        this.materialMobileId = materialMobileId;
        this.srNo = srNo;
    }
    @Generated(hash = 1744803366)
    public MaterialTable() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getQnty() {
        return this.qnty;
    }
    public void setQnty(int qnty) {
        this.qnty = qnty;
    }
    public int getActualQnty() {
        return this.actualQnty;
    }
    public void setActualQnty(int actualQnty) {
        this.actualQnty = actualQnty;
    }
    public String getUnit() {
        return this.unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getTaskId() {
        return this.taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getProjectId() {
        return this.projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    public String getCreatedDate() {
        return this.createdDate;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    public String getCreatedBy() {
        return this.createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getUpdatedDate() {
        return this.updatedDate;
    }
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
    public String getUpdatedBy() {
        return this.updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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
    public String getMaterialMobileId() {
        return this.materialMobileId;
    }
    public void setMaterialMobileId(String materialMobileId) {
        this.materialMobileId = materialMobileId;
    }
    public String getMaterialCount() {
        return this.materialCount;
    }
    public void setMaterialCount(String materialCount) {
        this.materialCount = materialCount;
    }
}
