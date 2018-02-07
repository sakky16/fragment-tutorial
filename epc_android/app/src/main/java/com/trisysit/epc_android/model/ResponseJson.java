package com.trisysit.epc_android.model;

/**
 * Created by tejeshkumardalai on 8/14/17.
 */

public class ResponseJson {


    private String status;
    private String data;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseJson() {

    }

    public ResponseJson(String status, String data) {
        this.status = status;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
