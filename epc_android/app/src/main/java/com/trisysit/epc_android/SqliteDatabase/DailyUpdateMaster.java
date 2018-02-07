package com.trisysit.epc_android.SqliteDatabase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by trisys on 4/1/18.
 */
@Entity
public class DailyUpdateMaster {
    private String schedule;
    private String schedulePart;
    private String subject;
    private String multiplier;
    @Generated(hash = 23853727)
    public DailyUpdateMaster(String schedule, String schedulePart, String subject,
            String multiplier) {
        this.schedule = schedule;
        this.schedulePart = schedulePart;
        this.subject = subject;
        this.multiplier = multiplier;
    }
    @Generated(hash = 1866851133)
    public DailyUpdateMaster() {
    }
    public String getSchedule() {
        return this.schedule;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    public String getSchedulePart() {
        return this.schedulePart;
    }
    public void setSchedulePart(String schedulePart) {
        this.schedulePart = schedulePart;
    }
    public String getSubject() {
        return this.subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getMultiplier() {
        return this.multiplier;
    }
    public void setMultiplier(String multiplier) {
        this.multiplier = multiplier;
    }
}
