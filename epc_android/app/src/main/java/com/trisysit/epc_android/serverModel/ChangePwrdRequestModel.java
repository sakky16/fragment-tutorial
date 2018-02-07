package com.trisysit.epc_android.serverModel;

/**
 * Created by trisys on 27/12/17.
 */

public class ChangePwrdRequestModel {
    String token;
    String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    String newPassword;
}
