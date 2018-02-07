package com.trisysit.epc_task_android.serverModel;

/**
 * Created by trisys on 6/2/18.
 */

public class LoginResponseModel {
    String status;
    String message;
    LoginDataModel data;

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
}
