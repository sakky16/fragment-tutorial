package com.trisysit.epc_android.serverModel;

import com.trisysit.epc_android.model.GetUserDataModel;

/**
 * Created by trisys on 5/2/18.
 */

public class GetUserDetailsResponseModel {
    String status;
    String message;
    GetUserDataModel data;

    public GetUserDataModel getData() {
        return data;
    }

    public void setData(GetUserDataModel data) {
        this.data = data;
    }

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
