package com.trisysit.epc_android.model;

import com.trisysit.epc_android.SqliteDatabase.MaterialTable;

import java.util.Date;
import java.util.List;

/**
 * Created by trisys on 11/12/17.
 */

public class Task {
    private String taskId;
    private String comment;
    private String priority;

    public Integer getMaterial_count() {
        return material_count;
    }

    public void setMaterial_count(Integer material_count) {
        this.material_count = material_count;
    }

    private String reminderText;
    private String reminderUrl;
    private String manPowerReq;
    private String actualManPowerReq;
    private String percentComplete;
    private String subject;
    private Integer material_count;
    private String taskCreatedDate;
    private String taskFor;
    private String taskStatus;
    private String taskUpdatedDate;
    private String assignedTo;
    private String assignedToName;
    private String taskCreatedBy;
    private String taskUpdatedBy;
    private Long taskStartDate;
    private Long taskEndDate;
    private String taskEffort;
    private String taskActualStartDate;
    private String taskActualEndDate;
    private String taskActualEffort;
    private String parentTaskId;
    private String predecessorTaskId;
    private String projectId;
    private Boolean critical;
    private String section;
    private Boolean isTrackable;
    private Boolean isWorkSchedule;
    private String duration;
    private Task predecessorTask;
    //private UserDetail assignedUser;
    private List<Task> subTasks;
    private List<MaterialTable> materials;
    private Integer subTaskCount;
    private Integer variance;
    private String userDetails;
    private String taskStartDateNew;
    private String unit;
    private String loa;
    private String actualLoa;
    private String schedule;
    private String description;
    private String orderBy;

    public String getTaskCreatedDate() {
        return taskCreatedDate;
    }

    public void setTaskCreatedDate(String taskCreatedDate) {
        this.taskCreatedDate = taskCreatedDate;
    }

    public String getTaskUpdatedDate() {
        return taskUpdatedDate;
    }

    public void setTaskUpdatedDate(String taskUpdatedDate) {
        this.taskUpdatedDate = taskUpdatedDate;
    }

    public Long getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(Long taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public Long getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(Long taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    public String getTaskActualStartDate() {
        return taskActualStartDate;
    }

    public void setTaskActualStartDate(String taskActualStartDate) {
        this.taskActualStartDate = taskActualStartDate;
    }

    public String getTaskActualEndDate() {
        return taskActualEndDate;
    }

    public void setTaskActualEndDate(String taskActualEndDate) {
        this.taskActualEndDate = taskActualEndDate;
    }


    public List<MaterialTable> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialTable> materials) {
        this.materials = materials;
    }


    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getAssignedToName() {
        return assignedToName;
    }

    public void setAssignedToName(String assignedToName) {
        this.assignedToName = assignedToName;
    }

    public String getTaskCreatedBy() {
        return taskCreatedBy;
    }

    public void setTaskCreatedBy(String taskCreatedBy) {
        this.taskCreatedBy = taskCreatedBy;
    }

    public String getTaskUpdatedBy() {
        return taskUpdatedBy;
    }

    public void setTaskUpdatedBy(String taskUpdatedBy) {
        this.taskUpdatedBy = taskUpdatedBy;
    }


    public String getTaskEffort() {
        return taskEffort;
    }

    public void setTaskEffort(String taskEffort) {
        this.taskEffort = taskEffort;
    }


    public String getTaskActualEffort() {
        return taskActualEffort;
    }

    public void setTaskActualEffort(String taskActualEffort) {
        this.taskActualEffort = taskActualEffort;
    }

    public String getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public String getPredecessorTaskId() {
        return predecessorTaskId;
    }

    public void setPredecessorTaskId(String predecessorTaskId) {
        this.predecessorTaskId = predecessorTaskId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Boolean getCritical() {
        return critical;
    }

    public void setCritical(Boolean critical) {
        this.critical = critical;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Boolean getTrackable() {
        return isTrackable;
    }

    public void setTrackable(Boolean trackable) {
        isTrackable = trackable;
    }

    public Boolean getWorkSchedule() {
        return isWorkSchedule;
    }

    public void setWorkSchedule(Boolean workSchedule) {
        isWorkSchedule = workSchedule;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Task getPredecessorTask() {
        return predecessorTask;
    }

    public void setPredecessorTask(Task predecessorTask) {
        this.predecessorTask = predecessorTask;
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<Task> subTasks) {
        this.subTasks = subTasks;
    }

    public Integer getSubTaskCount() {
        return subTaskCount;
    }

    public void setSubTaskCount(Integer subTaskCount) {
        this.subTaskCount = subTaskCount;
    }

    public Integer getVariance() {
        return variance;
    }

    public void setVariance(Integer variance) {
        this.variance = variance;
    }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }

    public String getTaskStartDateNew() {
        return taskStartDateNew;
    }

    public void setTaskStartDateNew(String taskStartDateNew) {
        this.taskStartDateNew = taskStartDateNew;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLoa() {
        return loa;
    }

    public void setLoa(String loa) {
        this.loa = loa;
    }

    public String getActualLoa() {
        return actualLoa;
    }

    public void setActualLoa(String actualLoa) {
        this.actualLoa = actualLoa;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getReminderText() {
        return reminderText;
    }

    public void setReminderText(String reminderText) {
        this.reminderText = reminderText;
    }

    public String getReminderUrl() {
        return reminderUrl;
    }

    public void setReminderUrl(String reminderUrl) {
        this.reminderUrl = reminderUrl;
    }

    public String getManPowerReq() {
        return manPowerReq;
    }

    public void setManPowerReq(String manPowerReq) {
        this.manPowerReq = manPowerReq;
    }

    public String getActualManPowerReq() {
        return actualManPowerReq;
    }

    public void setActualManPowerReq(String actualManPowerReq) {
        this.actualManPowerReq = actualManPowerReq;
    }

    public String getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete(String percentComplete) {
        this.percentComplete = percentComplete;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getTaskFor() {
        return taskFor;
    }

    public void setTaskFor(String taskFor) {
        this.taskFor = taskFor;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }


}
