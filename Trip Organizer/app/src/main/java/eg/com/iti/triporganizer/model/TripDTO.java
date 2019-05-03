package eg.com.iti.triporganizer.model;


import java.io.Serializable;


public class TripDTO implements Serializable {


    private String name;

    private String tripKey;

    private String tripStartPoint;

    private String tripEndPoint;

    private Double tripStartPointLongitude;

    private Double tripStartPointLatitude;

    private Double tripEndPointLongitude;

    private Double tripEndPointLatitude;

    private String year;

    private String month;

    private String dayOfMonth;

    private String hourOfDay;

    private String minute;

    private boolean rounded;

    private String repeated;

    private String tripStatus;

    private Notes notes;

    private String userId;

    private int requestCode;

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public TripDTO() {
    }


    public TripDTO(String userId, String name, String tripStartPoint, String tripEndPoint,
                   Double tripStartPointLatitude, Double tripStartPointLongitude,
                   Double tripEndPointLatitude, Double tripEndPointLongitude, int year, int month, int dayOfMonth, int hourOfDay, int minute, String repeated, String tripStatus, Notes notes, boolean rounded) {
        this.userId = userId;
        this.name = name;
        this.tripStartPoint = tripStartPoint;
        this.tripEndPoint = tripEndPoint;
        this.tripStartPointLongitude = tripStartPointLongitude;
        this.tripStartPointLatitude = tripStartPointLatitude;
        this.tripEndPointLongitude = tripEndPointLongitude;
        this.tripEndPointLatitude = tripEndPointLatitude;
        this.year = Integer.toString(year);
        this.month = Integer.toString(month);
        this.dayOfMonth = Integer.toString(dayOfMonth);
        this.hourOfDay = Integer.toString(hourOfDay);
        this.minute = Integer.toString(minute);
        this.repeated = repeated;
        this.tripStatus = tripStatus;
        this.notes = notes;
        this.rounded = rounded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTripKey() {
        return tripKey;
    }

    public void setTripKey(String tripKey) {
        this.tripKey = tripKey;
    }

    public String getTripStartPoint() {
        return tripStartPoint;
    }

    public void setTripStartPoint(String tripStartPoint) {
        this.tripStartPoint = tripStartPoint;
    }

    public String getTripEndPoint() {
        return tripEndPoint;
    }

    public void setTripEndPoint(String tripEndPoint) {
        this.tripEndPoint = tripEndPoint;
    }

    public Double getTripStartPointLongitude() {
        return tripStartPointLongitude;
    }

    public void setTripStartPointLongitude(Double tripStartPointLongitude) {
        this.tripStartPointLongitude = tripStartPointLongitude;
    }

    public Double getTripStartPointLatitude() {
        return tripStartPointLatitude;
    }

    public void setTripStartPointLatitude(Double tripStartPointLatitude) {
        this.tripStartPointLatitude = tripStartPointLatitude;
    }

    public Double getTripEndPointLongitude() {
        return tripEndPointLongitude;
    }

    public void setTripEndPointLongitude(Double tripEndPointLongitude) {
        this.tripEndPointLongitude = tripEndPointLongitude;
    }

    public Double getTripEndPointLatitude() {
        return tripEndPointLatitude;
    }

    public void setTripEndPointLatitude(Double tripEndPointLatitude) {
        this.tripEndPointLatitude = tripEndPointLatitude;
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

    public boolean isRounded() {
        return rounded;
    }

    public void setRounded(boolean rounded) {
        this.rounded = rounded;
    }

    public String getRepeated() {
        return repeated;
    }

    public void setRepeated(String repeated) {
        this.repeated = repeated;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

