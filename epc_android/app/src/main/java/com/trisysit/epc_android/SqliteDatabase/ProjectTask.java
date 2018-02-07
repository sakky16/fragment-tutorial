package com.trisysit.epc_android.SqliteDatabase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by trisys on 14/12/17.
 */
@Entity
public class ProjectTask {

    private String taskId;
    private String comment;
    private String priority;
    private String reminderText;
    private String reminderUrl;
    private String manPowerReq;
    private String actualManPowerReq;
    private String percentComplete;
    private String subject;
    private Integer material_count;
    private String taskCreatedDate;
    private String taskFor;
    private String type;
    @Id
    private String mobileId;
    private String taskStatus;
    private String taskUpdatedDate;
    private String assignedTo;
    private String assignedToName;
    private String locationL1;
    private String locationL2;
    private String locationL3;
    private String locationL4;
    private String locationL5;
    private String parentL1Id;
    private String contractor;
    private String actualManpower;
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
    private Integer subTaskCount;
    private Integer variance;
    private String userDetails;
    private String taskStartDateNew;
    private String unit;
    private String loa;
    private String schedulePart;
    private String actualLoa;
    private String schedule;
    private String description;
    private String orderBy;
    @Generated(hash = 980722892)
    public ProjectTask(String taskId, String comment, String priority,
            String reminderText, String reminderUrl, String manPowerReq,
            String actualManPowerReq, String percentComplete, String subject,
            Integer material_count, String taskCreatedDate, String taskFor,
            String type, String mobileId, String taskStatus, String taskUpdatedDate,
            String assignedTo, String assignedToName, String locationL1,
            String locationL2, String locationL3, String locationL4,
            String locationL5, String parentL1Id, String contractor,
            String actualManpower, String taskCreatedBy, String taskUpdatedBy,
            Long taskStartDate, Long taskEndDate, String taskEffort,
            String taskActualStartDate, String taskActualEndDate,
            String taskActualEffort, String parentTaskId, String predecessorTaskId,
            String projectId, Boolean critical, String section, Boolean isTrackable,
            Boolean isWorkSchedule, String duration, Integer subTaskCount,
            Integer variance, String userDetails, String taskStartDateNew,
            String unit, String loa, String schedulePart, String actualLoa,
            String schedule, String description, String orderBy) {
        this.taskId = taskId;
        this.comment = comment;
        this.priority = priority;
        this.reminderText = reminderText;
        this.reminderUrl = reminderUrl;
        this.manPowerReq = manPowerReq;
        this.actualManPowerReq = actualManPowerReq;
        this.percentComplete = percentComplete;
        this.subject = subject;
        this.material_count = material_count;
        this.taskCreatedDate = taskCreatedDate;
        this.taskFor = taskFor;
        this.type = type;
        this.mobileId = mobileId;
        this.taskStatus = taskStatus;
        this.taskUpdatedDate = taskUpdatedDate;
        this.assignedTo = assignedTo;
        this.assignedToName = assignedToName;
        this.locationL1 = locationL1;
        this.locationL2 = locationL2;
        this.locationL3 = locationL3;
        this.locationL4 = locationL4;
        this.locationL5 = locationL5;
        this.parentL1Id = parentL1Id;
        this.contractor = contractor;
        this.actualManpower = actualManpower;
        this.taskCreatedBy = taskCreatedBy;
        this.taskUpdatedBy = taskUpdatedBy;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.taskEffort = taskEffort;
        this.taskActualStartDate = taskActualStartDate;
        this.taskActualEndDate = taskActualEndDate;
        this.taskActualEffort = taskActualEffort;
        this.parentTaskId = parentTaskId;
        this.predecessorTaskId = predecessorTaskId;
        this.projectId = projectId;
        this.critical = critical;
        this.section = section;
        this.isTrackable = isTrackable;
        this.isWorkSchedule = isWorkSchedule;
        this.duration = duration;
        this.subTaskCount = subTaskCount;
        this.variance = variance;
        this.userDetails = userDetails;
        this.taskStartDateNew = taskStartDateNew;
        this.unit = unit;
        this.loa = loa;
        this.schedulePart = schedulePart;
        this.actualLoa = actualLoa;
        this.schedule = schedule;
        this.description = description;
        this.orderBy = orderBy;
    }
    @Generated(hash = 1341743950)
    public ProjectTask() {
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
    public String getReminderText() {
        return this.reminderText;
    }
    public void setReminderText(String reminderText) {
        this.reminderText = reminderText;
    }
    public String getReminderUrl() {
        return this.reminderUrl;
    }
    public void setReminderUrl(String reminderUrl) {
        this.reminderUrl = reminderUrl;
    }
    public String getManPowerReq() {
        return this.manPowerReq;
    }
    public void setManPowerReq(String manPowerReq) {
        this.manPowerReq = manPowerReq;
    }
    public String getActualManPowerReq() {
        return this.actualManPowerReq;
    }
    public void setActualManPowerReq(String actualManPowerReq) {
        this.actualManPowerReq = actualManPowerReq;
    }
    public String getPercentComplete() {
        return this.percentComplete;
    }
    public void setPercentComplete(String percentComplete) {
        this.percentComplete = percentComplete;
    }
    public String getSubject() {
        return this.subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public Integer getMaterial_count() {
        return this.material_count;
    }
    public void setMaterial_count(Integer material_count) {
        this.material_count = material_count;
    }
    public String getTaskCreatedDate() {
        return this.taskCreatedDate;
    }
    public void setTaskCreatedDate(String taskCreatedDate) {
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
    public String getTaskUpdatedDate() {
        return this.taskUpdatedDate;
    }
    public void setTaskUpdatedDate(String taskUpdatedDate) {
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
    public Long getTaskStartDate() {
        return this.taskStartDate;
    }
    public void setTaskStartDate(Long taskStartDate) {
        this.taskStartDate = taskStartDate;
    }
    public Long getTaskEndDate() {
        return this.taskEndDate;
    }
    public void setTaskEndDate(Long taskEndDate) {
        this.taskEndDate = taskEndDate;
    }
    public String getTaskEffort() {
        return this.taskEffort;
    }
    public void setTaskEffort(String taskEffort) {
        this.taskEffort = taskEffort;
    }
    public String getTaskActualStartDate() {
        return this.taskActualStartDate;
    }
    public void setTaskActualStartDate(String taskActualStartDate) {
        this.taskActualStartDate = taskActualStartDate;
    }
    public String getTaskActualEndDate() {
        return this.taskActualEndDate;
    }
    public void setTaskActualEndDate(String taskActualEndDate) {
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
    public String getPredecessorTaskId() {
        return this.predecessorTaskId;
    }
    public void setPredecessorTaskId(String predecessorTaskId) {
        this.predecessorTaskId = predecessorTaskId;
    }
    public String getProjectId() {
        return this.projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    public Boolean getCritical() {
        return this.critical;
    }
    public void setCritical(Boolean critical) {
        this.critical = critical;
    }
    public String getSection() {
        return this.section;
    }
    public void setSection(String section) {
        this.section = section;
    }
    public Boolean getIsTrackable() {
        return this.isTrackable;
    }
    public void setIsTrackable(Boolean isTrackable) {
        this.isTrackable = isTrackable;
    }
    public Boolean getIsWorkSchedule() {
        return this.isWorkSchedule;
    }
    public void setIsWorkSchedule(Boolean isWorkSchedule) {
        this.isWorkSchedule = isWorkSchedule;
    }
    public String getDuration() {
        return this.duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public Integer getSubTaskCount() {
        return this.subTaskCount;
    }
    public void setSubTaskCount(Integer subTaskCount) {
        this.subTaskCount = subTaskCount;
    }
    public Integer getVariance() {
        return this.variance;
    }
    public void setVariance(Integer variance) {
        this.variance = variance;
    }
    public String getUserDetails() {
        return this.userDetails;
    }
    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }
    public String getTaskStartDateNew() {
        return this.taskStartDateNew;
    }
    public void setTaskStartDateNew(String taskStartDateNew) {
        this.taskStartDateNew = taskStartDateNew;
    }
    public String getUnit() {
        return this.unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getLoa() {
        return this.loa;
    }
    public void setLoa(String loa) {
        this.loa = loa;
    }
    public String getActualLoa() {
        return this.actualLoa;
    }
    public void setActualLoa(String actualLoa) {
        this.actualLoa = actualLoa;
    }
    public String getSchedule() {
        return this.schedule;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getOrderBy() {
        return this.orderBy;
    }
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getMobileId() {
        return this.mobileId;
    }
    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }
    public String getSchedulePart() {
        return this.schedulePart;
    }
    public void setSchedulePart(String schedulePart) {
        this.schedulePart = schedulePart;
    }
    public String getLocationL1() {
        return this.locationL1;
    }
    public void setLocationL1(String locationL1) {
        this.locationL1 = locationL1;
    }
    public String getLocationL2() {
        return this.locationL2;
    }
    public void setLocationL2(String locationL2) {
        this.locationL2 = locationL2;
    }
    public String getLocationL3() {
        return this.locationL3;
    }
    public void setLocationL3(String locationL3) {
        this.locationL3 = locationL3;
    }
    public String getLocationL4() {
        return this.locationL4;
    }
    public void setLocationL4(String locationL4) {
        this.locationL4 = locationL4;
    }
    public String getLocationL5() {
        return this.locationL5;
    }
    public void setLocationL5(String locationL5) {
        this.locationL5 = locationL5;
    }
    public String getParentL1Id() {
        return this.parentL1Id;
    }
    public void setParentL1Id(String parentL1Id) {
        this.parentL1Id = parentL1Id;
    }
    public String getContractor() {
        return this.contractor;
    }
    public void setContractor(String contractor) {
        this.contractor = contractor;
    }
    public String getActualManpower() {
        return this.actualManpower;
    }
    public void setActualManpower(String actualManpower) {
        this.actualManpower = actualManpower;
    }
}
