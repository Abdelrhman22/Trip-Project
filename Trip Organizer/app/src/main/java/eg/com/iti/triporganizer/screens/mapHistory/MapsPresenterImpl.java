package eg.com.iti.triporganizer.screens.mapHistory;

import com.google.android.gms.maps.model.LatLng;

public class MapsPresenterImpl implements MapsContract.MapsPresenter {
    MapsActivity mapsActivity;

    public MapsPresenterImpl(MapsActivity mapsActivity)
    {
        this.mapsActivity=mapsActivity;
    }

    @Override
    public String getRequestedUrl(LatLng origin, LatLng dest)
    {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&key=AIzaSyCeYHDhDctqGmb5APIdyWrd-imDO2DkQHc";
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }

}
