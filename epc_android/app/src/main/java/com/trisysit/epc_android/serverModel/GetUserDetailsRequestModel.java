package com.trisysit.epc_android.serverModel;

/**
 * Created by trisys on 5/2/18.
 */

public class GetUserDetailsRequestModel {
    //String token;
    String userPin;



    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }
}
