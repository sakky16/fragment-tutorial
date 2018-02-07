package com.trisysit.epc_android.model;

import com.trisysit.epc_android.SqliteDatabase.UserMaster;

import java.util.List;

/**
 * Created by trisys on 8/12/17.
 */

public class LoginDataModel {
    private String roleName;
    private String firstName;
    private String lastName;
    private String token;
    private String roleDescription;

    public String getUserMultipleAttendance() {
        return userMultipleAttendance;
    }

    public void setUserMultipleAttendance(String userMultipleAttendance) {
        this.userMultipleAttendance = userMultipleAttendance;
    }

    private String userMultipleAttendance;


    private String id;

    public Boolean getPunchedIn() {
        return punchedIn;
    }

    public void setPunchedIn(Boolean punchedIn) {
        this.punchedIn = punchedIn;
    }

    private Boolean punchedIn;

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
