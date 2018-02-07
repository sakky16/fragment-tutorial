package com.trisysit.epc_android.SqliteDatabase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by trisys on 2/1/18.
 */
@Entity
public class ImageSync {
    String imageUrl;
    long imageParam;
    String status;
    @Id
    String id;
    @Generated(hash = 311911220)
    public ImageSync(String imageUrl, long imageParam, String status, String id) {
        this.imageUrl = imageUrl;
        this.imageParam = imageParam;
        this.status = status;
        this.id = id;
    }
    @Generated(hash = 1749074507)
    public ImageSync() {
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public long getImageParam() {
        return this.imageParam;
    }
    public void setImageParam(long imageParam) {
        this.imageParam = imageParam;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
