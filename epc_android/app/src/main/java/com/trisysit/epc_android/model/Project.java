package com.trisysit.epc_android.model;

import java.util.Date;

/**
 * Created by trisys on 11/12/17.
 */

public class Project {
    private String projectId;
    private String name;
    private Double percentageCompletion;
    private String amount;
    private Integer months;
    private Integer members;
    private String createdBy;
    private String updatedBy;
    private Date createdDate;
    private Date updatedDate;
    private Date projectStartDate;
    private Date projectEndDate;
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPercentageCompletion() {
        return percentageCompletion;
    }

    public void setPercentageCompletion(Double percentageCompletion) {
        this.percentageCompletion = percentageCompletion;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public Date getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(Date projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public String getParentProjectId() {
        return parentProjectId;
    }

    public void setParentProjectId(String parentProjectId) {
        this.parentProjectId = parentProjectId;
    }

    public Double getPercentToComplete() {
        return percentToComplete;
    }

    public void setPercentToComplete(Double percentToComplete) {
        this.percentToComplete = percentToComplete;
    }

    public long getCriticalTask() {
        return criticalTask;
    }

    public void setCriticalTask(long criticalTask) {
        this.criticalTask = criticalTask;
    }

    public long getTotalTask() {
        return totalTask;
    }

    public void setTotalTask(long totalTask) {
        this.totalTask = totalTask;
    }

    public long getNoOfDelayedTask() {
        return noOfDelayedTask;
    }

    public void setNoOfDelayedTask(long noOfDelayedTask) {
        this.noOfDelayedTask = noOfDelayedTask;
    }

    public long getOnTimeTask() {
        return onTimeTask;
    }

    public void setOnTimeTask(long onTimeTask) {
        this.onTimeTask = onTimeTask;
    }

    public long getBeforeTimeTask() {
        return beforeTimeTask;
    }

    public void setBeforeTimeTask(long beforeTimeTask) {
        this.beforeTimeTask = beforeTimeTask;
    }

    public long getNoOfCompletedTask() {
        return noOfCompletedTask;
    }

    public void setNoOfCompletedTask(long noOfCompletedTask) {
        this.noOfCompletedTask = noOfCompletedTask;
    }

    public Long getNoOfProjects() {
        return noOfProjects;
    }

    public void setNoOfProjects(Long noOfProjects) {
        this.noOfProjects = noOfProjects;
    }

    public Long getDelayedCriticalTasks() {
        return delayedCriticalTasks;
    }

    public void setDelayedCriticalTasks(Long delayedCriticalTasks) {
        this.delayedCriticalTasks = delayedCriticalTasks;
    }

    public Long getDelayedNonCriticalTasks() {
        return delayedNonCriticalTasks;
    }

    public void setDelayedNonCriticalTasks(Long delayedNonCriticalTasks) {
        this.delayedNonCriticalTasks = delayedNonCriticalTasks;
    }


}
