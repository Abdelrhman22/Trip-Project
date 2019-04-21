package eg.com.iti.triporganizer.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Calendar;

import eg.com.iti.triporganizer.model.Notes;


public class TripDTO implements Serializable {


    private String name;

    private String trip_start_point;

    private String trip_end_point;

    private Double trip_start_point_longitude;

    private Double trip_start_point_latitude;

    private Double trip_end_point_longitude;

    private Double trip_end_point_latitude;

    private Calendar tripDateAndTime;

    private boolean rounded;

    private String repeated;

    private String tripStatus;

    private Notes notes;

    private String userId;

    private String duration;

    private String averageSpeed;

    public String getTripStatus() {
        return tripStatus;
    }

    public TripDTO() {
    }


    public TripDTO(String userId, String name, String trip_start_point, String trip_end_point,
                   Double trip_start_point_latitude, Double trip_start_point_longitude,
                   Double trip_end_point_latitude,Double trip_end_point_longitude, Calendar tripDateAndTime, String repeated , String tripStatus, Notes notes ,boolean rounded) {
        this.userId = userId;
        this.name = name;
        this.trip_start_point = trip_start_point;
        this.trip_end_point = trip_end_point;
        this.trip_start_point_longitude = trip_start_point_longitude;
        this.trip_start_point_latitude = trip_start_point_latitude;
        this.trip_end_point_longitude = trip_end_point_longitude;
        this.trip_end_point_latitude = trip_end_point_latitude;
        this.tripDateAndTime=tripDateAndTime;
        this.repeated=repeated;
        this.tripStatus=tripStatus;
        this.notes = notes;
        this.rounded=rounded;
    }




    public String getName() {
        return name;
    }

    public String getTrip_start_point() {
        return trip_start_point;
    }

    public String getTrip_end_point() {
        return trip_end_point;
    }

    public Double getTrip_start_point_longitude() {
        return trip_start_point_longitude;
    }

    public Double getTrip_start_point_latitude() {
        return trip_start_point_latitude;
    }

    public Double getTrip_end_point_longitude() {
        return trip_end_point_longitude;
    }

    public Double getTrip_end_point_latitude() {
        return trip_end_point_latitude;
    }

    @Exclude
    public Calendar getTrip_date() {
        return tripDateAndTime;
    }

    public boolean getRoundStatus() {
        return rounded;
    }

    public Notes getNotes() {
        return notes;
    }


    public String getRepeated() {
        return repeated;
    }

    public String getUserId() {
        return userId;
    }

    public String getDuration() {
        return duration;
    }

    public String getAverageSpeed() {
        return averageSpeed;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setAverageSpeed(String averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public void setRoundStatus(boolean rounded) {
        this.rounded = rounded;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTrip_start_point(String trip_start_point) {
        this.trip_start_point = trip_start_point;
    }

    public void setTrip_end_point(String trip_end_point) {
        this.trip_end_point = trip_end_point;
    }

    public void setTrip_start_point_longitude(Double trip_start_point_longitude) {
        this.trip_start_point_longitude = trip_start_point_longitude;
    }

    public void setTrip_start_point_latitude(Double trip_start_point_latitude) {
        this.trip_start_point_latitude = trip_start_point_latitude;
    }

    public void setTrip_end_point_longitude(Double trip_end_point_longitude) {
        this.trip_end_point_longitude = trip_end_point_longitude;
    }

    public void setTrip_end_point_latitude(Double trip_end_point_latitude) {
        this.trip_end_point_latitude = trip_end_point_latitude;
    }

    public void setTrip_date(Calendar tripDateAndTime) {
        this.tripDateAndTime = tripDateAndTime;
    }

    public void setRepeated(String repeated) {
        this.repeated = repeated;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

