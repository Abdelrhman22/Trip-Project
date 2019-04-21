package eg.com.iti.triporganizer.model;

import java.io.Serializable;

public class TripTimeAndDateDTO implements Serializable {
    private String year;
    private String month;
    private String dayOfMonth;
    private String hourOfDay;
    private String minute;

    public TripTimeAndDateDTO() {
    }

    public TripTimeAndDateDTO(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        this.year = Integer.toString(year);
        this.month = Integer.toString(month);
        this.dayOfMonth = Integer.toString(dayOfMonth);
        this.hourOfDay = Integer.toString(hourOfDay);
        this.minute = Integer.toString(minute);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(String hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }
}
