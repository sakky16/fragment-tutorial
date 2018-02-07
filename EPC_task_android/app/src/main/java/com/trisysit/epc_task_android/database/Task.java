package com.trisysit.epc_task_android.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by trisys on 7/2/18.
 */
@Entity
public class Task {
    String taskId;
    String comment;
    String priority;
    String subject;
    @Id
    String mobileId;
    long taskCreatedDate;
    String taskFor;
    String taskStatus;
    long taskUpdatedDate;
    String assignedTo;
    String assignedToName;
    String taskCreatedBy;
    String taskUpdatedBy;
    long taskStartDate;
    long taskEndDate;
    String status;
    String taskEffort;
    long taskActualStartDate;
    long taskActualEndDate;
    String taskActualEffort;
    String parentTaskId;
    String projectId;
    String categoryId;
    @Generated(hash = 1082595286)
    public Task(String taskId, String comment, String priority, String subject,
            String mobileId, long taskCreatedDate, String taskFor,
            String taskStatus, long taskUpdatedDate, String assignedTo,
            String assignedToName, String taskCreatedBy, String taskUpdatedBy,
            long taskStartDate, long taskEndDate, String status, String taskEffort,
            long taskActualStartDate, long taskActualEndDate,
            String taskActualEffort, String parentTaskId, String projectId,
            String categoryId) {
        this.taskId = taskId;
        this.comment = comment;
        this.priority = priority;
        this.subject = subject;
        this.mobileId = mobileId;
        this.taskCreatedDate = taskCreatedDate;
        this.taskFor = taskFor;
        this.taskStatus = taskStatus;
        this.taskUpdatedDate = taskUpdatedDate;
        this.assignedTo = assignedTo;
        this.assignedToName = assignedToName;
        this.taskCreatedBy = taskCreatedBy;
        this.taskUpdatedBy = taskUpdatedBy;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.status = status;
        this.taskEffort = taskEffort;
        this.taskActualStartDate = taskActualStartDate;
        this.taskActualEndDate = taskActualEndDate;
        this.taskActualEffort = taskActualEffort;
        this.parentTaskId = parentTaskId;
        this.projectId = projectId;
        this.categoryId = categoryId;
    }
    @Generated(hash = 733837707)
    public Task() {
    }
    public String getTaskId() {
        return this.taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getPriority() {
        return this.priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public String getSubject() {
        return this.subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getMobileId() {
        return this.mobileId;
    }
    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }
    public long getTaskCreatedDate() {
        return this.taskCreatedDate;
    }
    public void setTaskCreatedDate(long taskCreatedDate) {
        this.taskCreatedDate = taskCreatedDate;
    }
    public String getTaskFor() {
        return this.taskFor;
    }
    public void setTaskFor(String taskFor) {
        this.taskFor = taskFor;
    }
    public String getTaskStatus() {
        return this.taskStatus;
    }
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
    public long getTaskUpdatedDate() {
        return this.taskUpdatedDate;
    }
    public void setTaskUpdatedDate(long taskUpdatedDate) {
        this.taskUpdatedDate = taskUpdatedDate;
    }
    public String getAssignedTo() {
        return this.assignedTo;
    }
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
    public String getAssignedToName() {
        return this.assignedToName;
    }
    public void setAssignedToName(String assignedToName) {
        this.assignedToName = assignedToName;
    }
    public String getTaskCreatedBy() {
        return this.taskCreatedBy;
    }
    public void setTaskCreatedBy(String taskCreatedBy) {
        this.taskCreatedBy = taskCreatedBy;
    }
    public String getTaskUpdatedBy() {
        return this.taskUpdatedBy;
    }
    public void setTaskUpdatedBy(String taskUpdatedBy) {
        this.taskUpdatedBy = taskUpdatedBy;
    }
    public long getTaskStartDate() {
        return this.taskStartDate;
    }
    public void setTaskStartDate(long taskStartDate) {
        this.taskStartDate = taskStartDate;
    }
    public long getTaskEndDate() {
        return this.taskEndDate;
    }
    public void setTaskEndDate(long taskEndDate) {
        this.taskEndDate = taskEndDate;
    }
    public String getTaskEffort() {
        return this.taskEffort;
    }
    public void setTaskEffort(String taskEffort) {
        this.taskEffort = taskEffort;
    }
    public long getTaskActualStartDate() {
        return this.taskActualStartDate;
    }
    public void setTaskActualStartDate(long taskActualStartDate) {
        this.taskActualStartDate = taskActualStartDate;
    }
    public long getTaskActualEndDate() {
        return this.taskActualEndDate;
    }
    public void setTaskActualEndDate(long taskActualEndDate) {
        this.taskActualEndDate = taskActualEndDate;
    }
    public String getTaskActualEffort() {
        return this.taskActualEffort;
    }
    public void setTaskActualEffort(String taskActualEffort) {
        this.taskActualEffort = taskActualEffort;
    }
    public String getParentTaskId() {
        return this.parentTaskId;
    }
    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
    }
    public String getProjectId() {
        return this.projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    public String getCategoryId() {
        return this.categoryId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
