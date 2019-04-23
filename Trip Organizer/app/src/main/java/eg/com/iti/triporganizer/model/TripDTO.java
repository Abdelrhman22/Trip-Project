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

    private TripTimeAndDateDTO tripDateAndTime;

    private boolean rounded;

    private String repeated;

    private String tripStatus;

    private Notes notes;

    private String userId;


    public String getTripStatus() {
        return tripStatus;
    }

    public TripDTO() {
    }


    public TripDTO(String userId, String name, String tripStartPoint, String tripEndPoint,
                   Double tripStartPointLatitude, Double tripStartPointLongitude,
                   Double tripEndPointLatitude,Double tripEndPointLongitude, TripTimeAndDateDTO tripDateAndTime, String repeated , String tripStatus, Notes notes ,boolean rounded) {
        this.userId = userId;
        this.name = name;
        this.tripStartPoint = tripStartPoint;
        this.tripEndPoint = tripEndPoint;
        this.tripStartPointLongitude = tripStartPointLongitude;
        this.tripStartPointLatitude = tripStartPointLatitude;
        this.tripEndPointLongitude = tripEndPointLongitude;
        this.tripEndPointLatitude = tripEndPointLatitude;
        this.tripDateAndTime=tripDateAndTime;
        this.repeated=repeated;
        this.tripStatus=tripStatus;
        this.notes = notes;
        this.rounded=rounded;
    }




    public String getName() {
        return name;
    }

    public String getTripKey() {
        return tripKey;
    }


    public String getTripStartPoint() {
        return tripStartPoint;
    }

    public String getTripEndPoint() {
        return tripEndPoint;
    }

    public Double getTrip_start_point_longitude() {
        return tripStartPointLongitude;
    }

    public Double getTrip_start_point_latitude() {
        return tripStartPointLatitude;
    }

    public Double getTrip_end_point_longitude() {
        return tripEndPointLongitude;
    }

    public Double getTrip_end_point_latitude() {
        return tripEndPointLatitude;
    }

    public TripTimeAndDateDTO getTrip_date() {
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

    public void setNotes(Notes notes) {
        this.notes = notes;
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

    public void setTripKey(String tripKey) {
        this.tripKey = tripKey;
    }

    public void setTripStartPoint(String tripStartPoint) {
        this.tripStartPoint = tripStartPoint;
    }

    public void setTripEndPoint(String tripEndPoint) {
        this.tripEndPoint = tripEndPoint;
    }

    public void setTripStartPointLongitude(Double tripStartPointLongitude) {
        this.tripStartPointLongitude = tripStartPointLongitude;
    }

    public void setTripStartPointLatitude(Double tripStartPointLatitude) {
        this.tripStartPointLatitude = tripStartPointLatitude;
    }

    public void setTripEndPointLongitude(Double tripEndPointLongitude) {
        this.tripEndPointLongitude = tripEndPointLongitude;
    }

    public void setTripEndPointLatitude(Double tripEndPointLatitude) {
        this.tripEndPointLatitude = tripEndPointLatitude;
    }

    public void setTripDate(TripTimeAndDateDTO tripDateAndTime) {
        this.tripDateAndTime = tripDateAndTime;
    }

    public void setRepeated(String repeated) {
        this.repeated = repeated;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

