package com.trisysit.epc_android.SqliteDatabase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by trisys on 4/1/18.
 */
@Entity
public class LocationMaster {
    private String locationL1;
    private String locationL2;
    private String locationL3;
    private String locationL4;
    private String locationL5;
    private String projectId;
    private String status;
    @Generated(hash = 162450508)
    public LocationMaster(String locationL1, String locationL2, String locationL3,
            String locationL4, String locationL5, String projectId, String status) {
        this.locationL1 = locationL1;
        this.locationL2 = locationL2;
        this.locationL3 = locationL3;
        this.locationL4 = locationL4;
        this.locationL5 = locationL5;
        this.projectId = projectId;
        this.status = status;
    }
    @Generated(hash = 547940033)
    public LocationMaster() {
    }
    public String getLocationL1() {
        return this.locationL1;
    }
    public void setLocationL1(String locationL1) {
        this.locationL1 = locationL1;
    }
    public String getLocationL2() {
        return this.locationL2;
    }
    public void setLocationL2(String locationL2) {
        this.locationL2 = locationL2;
    }
    public String getLocationL3() {
        return this.locationL3;
    }
    public void setLocationL3(String locationL3) {
        this.locationL3 = locationL3;
    }
    public String getLocationL4() {
        return this.locationL4;
    }
    public void setLocationL4(String locationL4) {
        this.locationL4 = locationL4;
    }
    public String getLocationL5() {
        return this.locationL5;
    }
    public void setLocationL5(String locationL5) {
        this.locationL5 = locationL5;
    }
    public String getProjectId() {
        return this.projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
