package eg.com.iti.triporganizer.screens.mapHistory;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.screens.mapHistory.mapParser.DirectionsJSONParser;
import eg.com.iti.triporganizer.utils.KeyTags;
import eg.com.iti.triporganizer.utils.MapColors;

import static eg.com.iti.triporganizer.utils.MapColors.markerColor;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<LatLng> MarkerPoints;
    List<TripDTO> tripDTOArrayList;
    List<TripDTO> receivedTrip;
    //firebase database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    LatLng startPoint;
    LatLng endPoint;
    String userId;
    MapsContract.MapsPresenter mapsPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        tripDTOArrayList=new ArrayList<>();
        receivedTrip=new ArrayList<>();
        mapsPresenter=new MapsPresenterImpl(MapsActivity.this);
        Intent intent = getIntent();
        if(intent!=null)
        {
            userId=intent.getStringExtra(KeyTags.UUIDKey); // get UUID from Homw Page
            Toast.makeText(this, ""+userId, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }
        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("trips").child(userId).child("history");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        MarkerPoints = new ArrayList<>();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshotItr : dataSnapshot.getChildren()) {
                    TripDTO trip = dataSnapshotItr.getValue(TripDTO.class);
                    tripDTOArrayList.add(trip);
                }
                receivedTrip=tripDTOArrayList;
                Toast.makeText(MapsActivity.this, "size= "+receivedTrip.size(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < receivedTrip.size(); i++) {

                    ///LatLng startPoint=new LatLng(tripDTOArrayList.get(i).getTrip_start_point_latitude(),tripDTOArrayList.get(i).getTrip_start_point_longitude());
                    //LatLng endPoint  =new LatLng(tripDTOArrayList.get(i).getTrip_end_point_latitude(),tripDTOArrayList.get(i).getTrip_end_point_longitude());
                    startPoint=new LatLng(receivedTrip.get(i).getTripStartPointLatitude(),receivedTrip.get(i).getTripStartPointLongitude());
                    endPoint  =new LatLng(receivedTrip.get(i).getTripEndPointLatitude(),receivedTrip.get(i).getTripEndPointLongitude());

                    MarkerPoints.clear();
                    mMap.addMarker(new MarkerOptions().position(startPoint).title("Start Point")
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(markerColor[i])));
                    MarkerPoints.add(startPoint);
                    MarkerPoints.add(endPoint);

                    // Creating MarkerOptions
                    MarkerOptions options = new MarkerOptions();
                    // Setting the position of the marker
                    options.position(endPoint);
                    if (MarkerPoints.size() == 1) {
                        options.icon(BitmapDescriptorFactory.defaultMarker(markerColor[i]));
                    } else if (MarkerPoints.size() == 2) {
                        options.icon(BitmapDescriptorFactory.defaultMarker(markerColor[i]));
                    }
                    mMap.addMarker(options);
                    // Checks, whether start and end locations are captured
                    if (MarkerPoints.size() >= 2) {
                        LatLng origin = MarkerPoints.get(0);
                        LatLng dest = MarkerPoints.get(1);

                        // Getting URL to the Google Directions API
                        String url = mapsPresenter.getRequestedUrl(origin, dest);
                        Log.d("onMapClick", url);
                        ReadTask  readTask = new ReadTask ();

                        // Start downloading json data from Google Directions API
                        readTask.execute(url);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // PrefManager prefManager;
        // prefManager=new PrefManager(getContext());
        // tripDTOArrayList= MyAppDB.getAppDatabase(getContext()).tripDao().getAllTrips("started", prefManager.getUserId());

    }


    // Fetches data from url passed
    private class ReadTask  extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            // For storing data from web service
            String data = "";
            try {
                // Fetching the data from web service
                data = mapsPresenter.downloadUrl(url[0]);
            } catch (Exception e) {

            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                // Starts parsing data
                routes = parser.parse(jObject);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions polylineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                polylineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);
                // Fetching all the points in i-th ic_route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                polylineOptions.addAll(points);
                polylineOptions.width(10);
                if (MapColors.colorIndex > 3) {
                    MapColors.colorIndex = 0;
                }
                polylineOptions.color(MapColors.color[MapColors.colorIndex]);
                MapColors.colorIndex++;
            }
            if (polylineOptions != null) {
                mMap.addPolyline(polylineOptions);
            } else
            {
            }
        }
    }
}
