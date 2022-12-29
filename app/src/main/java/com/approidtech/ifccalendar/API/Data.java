package com.approidtech.ifccalendar.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("selected_date")
    @Expose
    private String selectedDate;
    @SerializedName("is_leap_year")
    @Expose
    private Integer isLeapYear;
    @SerializedName("curr_year")
    @Expose
    private Integer currYear;
    @SerializedName("total_days")
    @Expose
    private Integer totalDays;
    @SerializedName("total_weeks")
    @Expose
    private Integer totalWeeks;
    @SerializedName("numerical_week_day")
    @Expose
    private Integer numericalWeekDay;
    @SerializedName("extra_day")
    @Expose
    private Integer extraDay;
    @SerializedName("curr_month")
    @Expose
    private Integer currMonth;
    @SerializedName("curr_day")
    @Expose
    private Integer currDay;
    @SerializedName("result_date")
    @Expose
    private String resultDate;
    @SerializedName("curr_week_number")
    @Expose
    private Integer currWeekNumber;
    @SerializedName("week_day")
    @Expose
    private String weekDay;
    @SerializedName("alphabetically_curr_month")
    @Expose
    private String alphabeticallyCurrMonth;
    @SerializedName("event_data")
    @Expose
    private List<EventListData> eventData = null;

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public Integer getIsLeapYear() {
        return isLeapYear;
    }

    public void setIsLeapYear(Integer isLeapYear) {
        this.isLeapYear = isLeapYear;
    }

    public Integer getCurrYear() {
        return currYear;
    }

    public void setCurrYear(Integer currYear) {
        this.currYear = currYear;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Integer getTotalWeeks() {
        return totalWeeks;
    }

    public void setTotalWeeks(Integer totalWeeks) {
        this.totalWeeks = totalWeeks;
    }

    public Integer getNumericalWeekDay() {
        return numericalWeekDay;
    }

    public void setNumericalWeekDay(Integer numericalWeekDay) {
        this.numericalWeekDay = numericalWeekDay;
    }

    public Integer getExtraDay() {
        return extraDay;
    }

    public void setExtraDay(Integer extraDay) {
        this.extraDay = extraDay;
    }

    public Integer getCurrMonth() {
        return currMonth;
    }

    public void setCurrMonth(Integer currMonth) {
        this.currMonth = currMonth;
    }

    public Integer getCurrDay() {
        return currDay;
    }

    public void setCurrDay(Integer currDay) {
        this.currDay = currDay;
    }

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }

    public Integer getCurrWeekNumber() {
        return currWeekNumber;
    }

    public void setCurrWeekNumber(Integer currWeekNumber) {
        this.currWeekNumber = currWeekNumber;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getAlphabeticallyCurrMonth() {
        return alphabeticallyCurrMonth;
    }

    public void setAlphabeticallyCurrMonth(String alphabeticallyCurrMonth) {
        this.alphabeticallyCurrMonth = alphabeticallyCurrMonth;
    }

    public List<EventListData> getEventData() {
        return eventData;
    }

    public void setEventData(List<EventListData> eventData) {
        this.eventData = eventData;
    }
}
