package com.trisysit.epc_android.serverModel;

/**
 * Created by trisys on 14/12/17.
 */

public class LogoutResponseModel {
    private String status;
    private String message;

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
}
