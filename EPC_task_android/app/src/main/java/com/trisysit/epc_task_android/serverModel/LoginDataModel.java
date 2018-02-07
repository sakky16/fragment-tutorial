package com.trisysit.epc_task_android.serverModel;

/**
 * Created by trisys on 6/2/18.
 */

public class LoginDataModel {
    private String firstName;
    private String lastName;
    private String token;

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
}
