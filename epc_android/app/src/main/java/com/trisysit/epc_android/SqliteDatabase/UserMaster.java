package com.trisysit.epc_android.SqliteDatabase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by trisys on 31/1/18.
 */
@Entity
public class UserMaster {
    private String firstName;
    private String lastName;
    @Id
    private String userId;
    private String username;
    private String userPin;
    private Boolean punchedIn;
    @Generated(hash = 118017179)
    public UserMaster(String firstName, String lastName, String userId,
            String username, String userPin, Boolean punchedIn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.username = username;
        this.userPin = userPin;
        this.punchedIn = punchedIn;
    }
    @Generated(hash = 1859733052)
    public UserMaster() {
    }
    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUserPin() {
        return this.userPin;
    }
    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }
    public Boolean getPunchedIn() {
        return this.punchedIn;
    }
    public void setPunchedIn(Boolean punchedIn) {
        this.punchedIn = punchedIn;
    }
}
