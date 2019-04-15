package eg.com.iti.triporganizer.screens.addTrip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;


import eg.com.iti.triporganizer.R;

public class AddTripActivity extends AppCompatActivity {

    PlaceAutocompleteFragment startPlaceAutocompleteFragment, endPlaceAutocompleteFragment;
    String placeStartName ,placeEndName;
    Double startLat ,startLng ,endLng ,endLat;
    private static final String LOG="log";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        initAutoComplete();
    }

    void initAutoComplete(){

        startPlaceAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager()
                .findFragmentById(R.id.place_autocomplete_fragment_from);
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
        startPlaceAutocompleteFragment.setFilter(autocompleteFilter);
        // check value of startPlaceAutocompleteFragment
        if(startPlaceAutocompleteFragment!=null)
        {
            startPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    //Toast.makeText(getApplicationContext(),place.getName().toString(),Toast.LENGTH_SHORT).show();
                    placeStartName = (String) place.getName();
                    startLng = place.getLatLng().longitude;
                    startLat = place.getLatLng().latitude;
                    Toast.makeText(AddTripActivity.this, placeStartName, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Status status) {
                    Log.e(LOG,status.toString());
                    //Toast.makeText(getApplicationContext(),"ErrorStart "+status.toString(),Toast.LENGTH_SHORT).show();
                }
        });
        }
        else
        {
            Toast.makeText(AddTripActivity.this, "Problem with loading start", Toast.LENGTH_LONG).show();
        }
        endPlaceAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager()
                .findFragmentById(R.id.place_autocomplete_fragment_to);
        endPlaceAutocompleteFragment.setFilter(autocompleteFilter);
        // check value of startPlaceAutocompleteFragment
        if(endPlaceAutocompleteFragment!=null)
        {
            endPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    Toast.makeText(getApplicationContext(),place.getName().toString(),Toast.LENGTH_SHORT).show();
                    placeEndName = (String) place.getName();
                    endLng = place.getLatLng().longitude;
                    endLat = place.getLatLng().latitude;
                    Toast.makeText(AddTripActivity.this, placeEndName, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Status status) {
                    Log.e(LOG,status.toString());
                    //Toast.makeText(getApplicationContext(),"Error"+status.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(AddTripActivity.this, "Problem with loading End", Toast.LENGTH_LONG).show();
        }
        AutocompleteFilter typeFilter1 = new AutocompleteFilter.Builder()
                .setCountry("EG")
                .build();
        startPlaceAutocompleteFragment.setFilter(typeFilter1);
        endPlaceAutocompleteFragment.setFilter(typeFilter1);
    }
}
