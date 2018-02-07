package com.trisysit.epc_android.SqliteDatabase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by trisys on 11/12/17.
 */
@Entity
public class ProjectTable {
    @Id
    private String projectId;
    private String name;
    private Double percentageCompletion;
    private String amount;
    private Integer months;
    private Integer members;
    private String createdBy;
    private String updatedBy;
    private String createdDate;
    private String updatedDate;
    private String projectStartDate;
    private String projectEndDate;
    private String parentProjectId;
    private Double percentToComplete;
    private long criticalTask;
    private long totalTask;
    private long noOfDelayedTask;
    private long onTimeTask;
    private long beforeTimeTask;
    private long noOfCompletedTask;
    private Long noOfProjects;
    private Long delayedCriticalTasks;
    private Long delayedNonCriticalTasks;

    @Generated(hash = 144149768)
    public ProjectTable(String projectId, String name, Double percentageCompletion,
                        String amount, Integer months, Integer members, String createdBy,
                        String updatedBy, String createdDate, String updatedDate,
                        String projectStartDate, String projectEndDate, String parentProjectId,
                        Double percentToComplete, long criticalTask, long totalTask,
                        long noOfDelayedTask, long onTimeTask, long beforeTimeTask,
                        long noOfCompletedTask, Long noOfProjects, Long delayedCriticalTasks,
                        Long delayedNonCriticalTasks) {
        this.projectId = projectId;
        this.name = name;
        this.percentageCompletion = percentageCompletion;
        this.amount = amount;
        this.months = months;
        this.members = members;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.parentProjectId = parentProjectId;
        this.percentToComplete = percentToComplete;
        this.criticalTask = criticalTask;
        this.totalTask = totalTask;
        this.noOfDelayedTask = noOfDelayedTask;
        this.onTimeTask = onTimeTask;
        this.beforeTimeTask = beforeTimeTask;
        this.noOfCompletedTask = noOfCompletedTask;
        this.noOfProjects = noOfProjects;
        this.delayedCriticalTasks = delayedCriticalTasks;
        this.delayedNonCriticalTasks = delayedNonCriticalTasks;
    }

    @Generated(hash = 962464969)
    public ProjectTable() {
    }

    public String getProjectId() {
        return this.projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPercentageCompletion() {
        return this.percentageCompletion;
    }

    public void setPercentageCompletion(Double percentageCompletion) {
        this.percentageCompletion = percentageCompletion;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getMonths() {
        return this.months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public Integer getMembers() {
        return this.members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getParentProjectId() {
        return this.parentProjectId;
    }

    public void setParentProjectId(String parentProjectId) {
        this.parentProjectId = parentProjectId;
    }

    public Double getPercentToComplete() {
        return this.percentToComplete;
    }

    public void setPercentToComplete(Double percentToComplete) {
        this.percentToComplete = percentToComplete;
    }

    public long getCriticalTask() {
        return this.criticalTask;
    }

    public void setCriticalTask(long criticalTask) {
        this.criticalTask = criticalTask;
    }

    public long getTotalTask() {
        return this.totalTask;
    }

    public void setTotalTask(long totalTask) {
        this.totalTask = totalTask;
    }

    public long getNoOfDelayedTask() {
        return this.noOfDelayedTask;
    }

    public void setNoOfDelayedTask(long noOfDelayedTask) {
        this.noOfDelayedTask = noOfDelayedTask;
    }

    public long getOnTimeTask() {
        return this.onTimeTask;
    }

    public void setOnTimeTask(long onTimeTask) {
        this.onTimeTask = onTimeTask;
    }

    public long getBeforeTimeTask() {
        return this.beforeTimeTask;
    }

    public void setBeforeTimeTask(long beforeTimeTask) {
        this.beforeTimeTask = beforeTimeTask;
    }

    public long getNoOfCompletedTask() {
        return this.noOfCompletedTask;
    }

    public void setNoOfCompletedTask(long noOfCompletedTask) {
        this.noOfCompletedTask = noOfCompletedTask;
    }

    public Long getNoOfProjects() {
        return this.noOfProjects;
    }

    public void setNoOfProjects(Long noOfProjects) {
        this.noOfProjects = noOfProjects;
    }

    public Long getDelayedCriticalTasks() {
        return this.delayedCriticalTasks;
    }

    public void setDelayedCriticalTasks(Long delayedCriticalTasks) {
        this.delayedCriticalTasks = delayedCriticalTasks;
    }

    public Long getDelayedNonCriticalTasks() {
        return this.delayedNonCriticalTasks;
    }

    public void setDelayedNonCriticalTasks(Long delayedNonCriticalTasks) {
        this.delayedNonCriticalTasks = delayedNonCriticalTasks;
    }

    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getProjectStartDate() {
        return this.projectStartDate;
    }

    public void setProjectStartDate(String projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public String getProjectEndDate() {
        return this.projectEndDate;
    }

    public void setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
    }
}
