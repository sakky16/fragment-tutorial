package com.trisysit.epc_android.model;

import java.util.List;

/**
 * Created by trisys on 5/2/18.
 */

public class GetUserDataModel {
    String userId;
    String id;
    String firstName;
    String lastName;
    List<AttendanceModel> attendanceList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<AttendanceModel> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<AttendanceModel> attendanceList) {
        this.attendanceList = attendanceList;
    }
}
