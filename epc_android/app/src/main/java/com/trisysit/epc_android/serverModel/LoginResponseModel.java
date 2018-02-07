package com.trisysit.epc_android.serverModel;

import com.trisysit.epc_android.model.LoginDataModel;

/**
 * Created by trisys on 14/12/17.
 */

public class LoginResponseModel {
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginDataModel getData() {
        return data;
    }

    public void setData(LoginDataModel data) {
        this.data = data;
    }

    String message;
    LoginDataModel data;
}
