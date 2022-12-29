package com.approidtech.ifccalendar.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventListData {
    @SerializedName("calender_event_id")
    @Expose
    private String calenderEventId;
    @SerializedName("is_365")
    @Expose
    private String is365;
    @SerializedName("is_365_date")
    @Expose
    private String is365Date;
    @SerializedName("is_364_date")
    @Expose
    private String is364Date;
    @SerializedName("is_time")
    @Expose
    private String isTime;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("device_id")
    @Expose
    private String deviceId;

    public String getCalenderEventId() {
        return calenderEventId;
    }

    public void setCalenderEventId(String calenderEventId) {
        this.calenderEventId = calenderEventId;
    }

    public String getIs365() {
        return is365;
    }

    public void setIs365(String is365) {
        this.is365 = is365;
    }

    public String getIs365Date() {
        return is365Date;
    }

    public void setIs365Date(String is365Date) {
        this.is365Date = is365Date;
    }

    public String getIs364Date() {
        return is364Date;
    }

    public void setIs364Date(String is364Date) {
        this.is364Date = is364Date;
    }

    public String getIsTime() {
        return isTime;
    }

    public void setIsTime(String isTime) {
        this.isTime = isTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
