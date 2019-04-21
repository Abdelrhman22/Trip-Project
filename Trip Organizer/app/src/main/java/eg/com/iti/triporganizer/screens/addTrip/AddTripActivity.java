package eg.com.iti.triporganizer.screens.addTrip;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.model.NoteDTO;
import eg.com.iti.triporganizer.model.Notes;
import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.screens.addTrip.adapter.RawNotesAdapter;


public class AddTripActivity extends AppCompatActivity implements AddTripContract.AddTripView {


    String currentUserUID, tripName, placeStartName, placeEndName, startDateString, startTimeString, returnDateString, returnTimeString, repeated;
    Double startLat, startLng, endLng, endLat;
    boolean startTimeSet = false;
    boolean startDateSet = false;
    boolean returnTimeSet = false;
    boolean returnDateSet = false;
    boolean startPlaceSet = false;
    boolean endPlaceSet = false;


    ArrayList<NoteDTO> notes;
    TripDTO userTrip;
    private static final String LOG = "log";
    static Boolean isTouched = false;
    static boolean rounded = false;
    FirebaseUser user;
    Notes userNotes;
    AddTripContract.AddTripPresenter addTripPresenter;
    Calendar currentDateAndTime, startDateAndTime, returnDateAndTime;

    //------------------Components----------------------------------------
    PlaceAutocompleteFragment startPlaceAutocompleteFragment, endPlaceAutocompleteFragment;
    Button addTripBtn;
    TextInputLayout tripNameWrapper, noteNameWrapper;
    ImageView startDate, startTime, returnDate, returnTime, addNote;
    TextView startDateText, startTimeText, returnDateText, returnTimeText;
    SwitchCompat roundedTrip;
    Spinner repetition;
    DatePickerDialog.OnDateSetListener startDateSetListener;
    DatePickerDialog.OnDateSetListener returnDateSetListener;
    DatePickerDialog datePickerDialog;
    TimePickerDialog.OnTimeSetListener startTimeSetListener;
    TimePickerDialog.OnTimeSetListener returnTimeSetListener;
    TimePickerDialog timePickerDialog;
    RecyclerView notesRecyclerView;
    RawNotesAdapter rawNotesAdapter;
    LinearLayout backTripDetails;
    //---------------------------------------------------------------------------


    public AddTripActivity() {
        startDateAndTime=Calendar.getInstance();
        startDateAndTime.clear();
        returnDateAndTime=Calendar.getInstance();
        returnDateAndTime.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        addTripPresenter = new AddTripPresenterImpl(this);
        notes = new ArrayList<>();
        initComponents();
        initAutoComplete();
        addListeners();
    }


    private void initComponents() {
        addTripBtn = findViewById(R.id.addTripButton);
        tripNameWrapper = findViewById(R.id.tripNameWrapper);
        startDate = findViewById(R.id.startDate);
        startTime = findViewById(R.id.startTime);
        returnDate = findViewById(R.id.returnDate);
        returnTime = findViewById(R.id.returnTime);
        roundedTrip = findViewById(R.id.twoWayTrip);
        repetition = findViewById(R.id.spinnerRepeateType);
        addNote = findViewById(R.id.addNotes);
        noteNameWrapper = findViewById(R.id.noteNameWrapper);
        startDateText = findViewById(R.id.startDateTextView);
        startTimeText = findViewById(R.id.startTimeTextView);
        returnDateText = findViewById(R.id.returnDateTextView);
        returnTimeText = findViewById(R.id.returnTimeTextView);
        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setNestedScrollingEnabled(false);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(AddTripActivity.this));
        rawNotesAdapter = new RawNotesAdapter(notes);
        notesRecyclerView.setAdapter(rawNotesAdapter);
        backTripDetails = findViewById(R.id.twoWayTripLayout);
    }

    @SuppressLint("ClickableViewAccessibility")
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
                currentDateAndTime = Calendar.getInstance();
                int year = currentDateAndTime.get(Calendar.YEAR);
                int month = currentDateAndTime.get(Calendar.MONTH);
                int day = currentDateAndTime.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddTripActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, startDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startDateSet = true;
                startDateAndTime.set(Calendar.YEAR,year);
                startDateAndTime.set(Calendar.MONTH,month);
                startDateAndTime.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                startDateString = Integer.toString(dayOfMonth) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(year);
                startDateText.setText(startDateString);
            }
        };

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDateAndTime = Calendar.getInstance();
                int minute = currentDateAndTime.get(Calendar.MINUTE);
                int hour = currentDateAndTime.get(Calendar.HOUR_OF_DAY);
                timePickerDialog = new TimePickerDialog(AddTripActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, startTimeSetListener, hour, minute, false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        startTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startDateAndTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                startDateAndTime.set(Calendar.MINUTE,minute);
                startTimeSet = true;
                startTimeString = Integer.toString(hourOfDay) + " : " + Integer.toString(minute);
                startTimeText.setText(startTimeString);
            }

        };

        //roundTrip
        roundedTrip.setOnTouchListener((v, event) -> {
            isTouched = true;
            return false;
        });

        roundedTrip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isTouched) {
                isTouched = false;
                if (isChecked) {
                    //Log.i(LOG,"checked");
                    backTripDetails.setVisibility(View.VISIBLE);
                    rounded = true;
                } else {
                    backTripDetails.setVisibility(View.GONE);
                    rounded = false;
                }
            }
        });

        //set back trip
        returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDateAndTime = Calendar.getInstance();
                int year = currentDateAndTime.get(Calendar.YEAR);
                int month = currentDateAndTime.get(Calendar.MONTH);
                int day = currentDateAndTime.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddTripActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, returnDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });


        returnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                returnDateAndTime.set(Calendar.YEAR,year);
                returnDateAndTime.set(Calendar.MONTH,month);
                returnDateAndTime.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                returnDateSet = true;
                returnDateString = Integer.toString(dayOfMonth) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(year);
                returnDateText.setText(returnDateString);
            }
        };

        returnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDateAndTime = Calendar.getInstance();
                int minute = currentDateAndTime.get(Calendar.MINUTE);
                int hour = currentDateAndTime.get(Calendar.HOUR_OF_DAY);
                timePickerDialog = new TimePickerDialog(AddTripActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, returnTimeSetListener, hour, minute, false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        returnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                returnDateAndTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                returnDateAndTime.set(Calendar.MINUTE,minute);
                returnTimeSet = true;
                returnTimeString = Integer.toString(hourOfDay) + " : " + Integer.toString(minute);
                returnTimeText.setText(returnTimeString);
            }

        };

        tripNameWrapper.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tripNameWrapper.setError(null);
                }
            }
        });

        //adding notes
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notesRecyclerView.getVisibility() == View.GONE)
                    notesRecyclerView.setVisibility(View.VISIBLE);
                if (noteNameWrapper.getEditText() != null) {
                    String noteContent = noteNameWrapper.getEditText().getText().toString().trim();
                    noteNameWrapper.getEditText().getText().clear();
                    if (!noteContent.equals("")) {
                        NoteDTO note = new NoteDTO(false, noteContent);
                        notes.add(note);
                        rawNotesAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        addTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTrip();
            }
        });
    }

    //trip name
    void getTripName() {
        if (tripNameWrapper.getEditText() != null) {
            tripName = tripNameWrapper.getEditText().getText().toString();
            Log.i("log", tripName);
        }
    }

    void initAutoComplete() {
        startPlaceAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager()
                .findFragmentById(R.id.tripStartPlace);
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
        startPlaceAutocompleteFragment.setFilter(autocompleteFilter);
        // check value of startPlaceAutocompleteFragment
        if (startPlaceAutocompleteFragment != null) {
            startPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    //Toast.makeText(getApplicationContext(),place.getName().toString(),Toast.LENGTH_SHORT).show();
                    startPlaceSet = true;
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
                .findFragmentById(R.id.tripEndPlace);
        endPlaceAutocompleteFragment.setFilter(autocompleteFilter);
        // check value of startPlaceAutocompleteFragment
        if (endPlaceAutocompleteFragment != null) {
            endPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    endPlaceSet = true;
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

    private void setUserData() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUserUID = user.getUid();
            Log.i("log", currentUserUID);
        }
    }

    private void setNotes() {
        userNotes = new Notes(notes);
    }

    private void checkRepetition() {
        repeated = repetition.getSelectedItem().toString();
    }

    private void createTrip() {
        getTripName();
        setUserData();
        setNotes();
        checkRepetition();
        checkFieldsNotMissedThenInsertTrip();
    }


    private void checkFieldsNotMissedThenInsertTrip() {
        if (rounded) {
            if ((tripName.trim().equals("")) || (!startPlaceSet) || (!endPlaceSet) || (!startDateSet) || (!startTimeSet) || (!returnDateSet) || (!returnTimeSet)) {
                if (tripName.trim().equals(""))
                    tripNameWrapper.setError("Enter trip name please");
                Toast.makeText(this, "Fill in all fields please", Toast.LENGTH_SHORT).show();
            } else {
                Calendar now=Calendar.getInstance();
                if (startDateAndTime.before(now)) {
                    Toast.makeText(this, "You cannot select passed time", Toast.LENGTH_SHORT).show();
                }
                else if (returnDateAndTime.before(now)){
                    Toast.makeText(this, "You cannot select passed time", Toast.LENGTH_SHORT).show();
                }
                else if(returnDateAndTime.before(startDateAndTime)){
                    Toast.makeText(this, "you cannot return before going", Toast.LENGTH_SHORT).show();
                }
                else {
                    userTrip = new TripDTO(currentUserUID, tripName, placeStartName, placeEndName, startLat, startLng, endLat, endLng, startDateAndTime, repeated, "upcoming", userNotes, rounded);
                    addTripPresenter.addTrip(userTrip);
                    TripDTO backTrip = new TripDTO(currentUserUID, tripName, placeEndName, placeStartName, endLat, endLng, startLat, startLng, returnDateAndTime, repeated, "upcoming", userNotes, false);
                    addTripPresenter.addTrip(backTrip);
                }
            }
        } else {
            if ((tripName.trim().equals("")) || (!startPlaceSet) || (!endPlaceSet) || (!startDateSet) || (!startTimeSet)) {
                if (tripName.trim().equals(""))
                    tripNameWrapper.setError("Enter trip name please");
                Toast.makeText(this, "Fill in all fields please", Toast.LENGTH_SHORT).show();
            } else {
                Calendar now=Calendar.getInstance();
                if (startDateAndTime.before(now)) {
                    Toast.makeText(this, "You cannot select passed time", Toast.LENGTH_SHORT).show();
                } else {
                    userTrip = new TripDTO(currentUserUID, tripName, placeStartName, placeEndName, startLat, startLng, endLat, endLng, startDateAndTime, repeated, "upcoming", userNotes, rounded);
                    addTripPresenter.addTrip(userTrip);
                }
            }
        }
    }


    @Override
    public void respondToSuccessfulInsertion() {
        Toast.makeText(this, "Your trip inserted successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void respondToFailedInsertion() {
        Toast.makeText(this, "Failed to insert your trip", Toast.LENGTH_SHORT).show();
    }

}
