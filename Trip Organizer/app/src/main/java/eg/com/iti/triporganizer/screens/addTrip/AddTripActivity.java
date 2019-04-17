package eg.com.iti.triporganizer.screens.addTrip;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;


import java.util.Calendar;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.model.TripDTO;

public class AddTripActivity extends AppCompatActivity {

    PlaceAutocompleteFragment startPlaceAutocompleteFragment, endPlaceAutocompleteFragment;
    String placeStartName, placeEndName,date,time;
    Double startLat, startLng, endLng, endLat;
    //salma
    Button addTripBtn;
    TextInputLayout tripNameWrapper;
    TextInputLayout noteNameWrapper;
    ImageView startDate;
    ImageView startTime;
    ImageView addNote;
    SwitchCompat roundedTrip;
    Spinner repetition;
    DatePickerDialog.OnDateSetListener dateSetListener;
    DatePickerDialog datePickerDialog;
    TextView startDateText;
    TimePickerDialog.OnTimeSetListener timeSetListener;
    TimePickerDialog timePickerDialog;
    TextView startTimeText;


    //---------------------------------------------------------------------------
    TripDTO userTrip;
    private static final String LOG = "log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        initAutoComplete();
        initComponents();
        addListeners();
    }

    private void initComponents() {
        addTripBtn = findViewById(R.id.addTripBtn);
        tripNameWrapper = findViewById(R.id.tripNameWrapper);
        startDate = findViewById(R.id.start_date);
        startTime = findViewById(R.id.start_time);
        roundedTrip = findViewById(R.id.rounded_trip);
        repetition = findViewById(R.id.repeat_spinner);
        addNote = findViewById(R.id.add_notes);
        noteNameWrapper = findViewById(R.id.noteNameWrapper);
        startDateText=findViewById(R.id.start_date_text);
        startTimeText=findViewById(R.id.start_time_text);

    }

    private void addListeners() {
        addTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTrip();
            }
        });
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog= new DatePickerDialog(AddTripActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date=Integer.toString(dayOfMonth)+" / "+Integer.toString(month)+" / "+Integer.toString(year);
                startDateText.setText(date);
            }
        };

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int minute=calendar.get(Calendar.MINUTE);
                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                timePickerDialog= new TimePickerDialog(AddTripActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,timeSetListener,hour,minute,false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();            }
        });

        timeSetListener=new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time=Integer.toString(hourOfDay) +" : "+ Integer.toString(minute);
                startTimeText.setText(time);
            }

        };

    }

    private void createTrip() {
        //trip name
        String tripName;
        if (tripNameWrapper.getEditText() != null)
            tripName = tripNameWrapper.getEditText().getText().toString();

        //trip start point and end point names,lang,lat already handled
        //trip date handled



        userTrip = new TripDTO();
    }


    void initAutoComplete() {

        startPlaceAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager()
                .findFragmentById(R.id.tripSrc);
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
        startPlaceAutocompleteFragment.setFilter(autocompleteFilter);
        // check value of startPlaceAutocompleteFragment
        if (startPlaceAutocompleteFragment != null) {
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
                    Log.e(LOG, status.toString());
                    //Toast.makeText(getApplicationContext(),"ErrorStart "+status.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(AddTripActivity.this, "Problem with loading start", Toast.LENGTH_LONG).show();
        }
        endPlaceAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager()
                .findFragmentById(R.id.tripDestination);
        endPlaceAutocompleteFragment.setFilter(autocompleteFilter);
        // check value of startPlaceAutocompleteFragment
        if (endPlaceAutocompleteFragment != null) {
            endPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    Toast.makeText(getApplicationContext(), place.getName().toString(), Toast.LENGTH_SHORT).show();
                    placeEndName = (String) place.getName();
                    endLng = place.getLatLng().longitude;
                    endLat = place.getLatLng().latitude;
                    Toast.makeText(AddTripActivity.this, placeEndName, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Status status) {
                    Log.e(LOG, status.toString());
                    //Toast.makeText(getApplicationContext(),"Error"+status.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(AddTripActivity.this, "Problem with loading End", Toast.LENGTH_LONG).show();
        }
        AutocompleteFilter typeFilter1 = new AutocompleteFilter.Builder()
                .setCountry("EG")
                .build();
        startPlaceAutocompleteFragment.setFilter(typeFilter1);
        endPlaceAutocompleteFragment.setFilter(typeFilter1);
    }
}
