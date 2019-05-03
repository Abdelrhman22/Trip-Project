package eg.com.iti.triporganizer.Network.NetworkServices;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.screens.addTrip.AddTripContract;
import eg.com.iti.triporganizer.screens.dialog.DialogContract;
import eg.com.iti.triporganizer.screens.home.HomeContract;

public class FirebaseTripsManager {
    HomeContract.HomePresenter homePresenter;
    AddTripContract.AddTripPresenter addTripPresenter;
    DialogContract.DialogPresenter dialogPresenter;

    boolean edittrip = false;
    boolean moveTrip = false;
    boolean startMapFromAlarm = false;
    //firebase database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;


    //firebase user
    FirebaseUser user;
    String currentUserUID;

    public FirebaseTripsManager(HomeContract.HomePresenter homePresenter) {
        this.homePresenter = homePresenter;
    }

    public FirebaseTripsManager(AddTripContract.AddTripPresenter addTripPresenter) {
        this.addTripPresenter = addTripPresenter;
        edittrip = true;
    }

    public FirebaseTripsManager(DialogContract.DialogPresenter dialogPresenter) {
        this.dialogPresenter = dialogPresenter;
        startMapFromAlarm = true;
    }

    public void deleteTrip(String tripKey) {
        firebaseAuth = FirebaseAuth.getInstance();
        getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("trips").child(currentUserUID).child("upcoming").child(tripKey);
        mDatabaseReference.removeValue(new DatabaseReference.CompletionListener() {
           @Override
           public void onComplete(DatabaseError databaseError,DatabaseReference databaseReference)
           {
                    if (!edittrip && !moveTrip)
                   homePresenter.notifyWithSuccessfulTripDeletion();
           }
       });
    }

    private void getCurrentUser() {

        user = firebaseAuth.getCurrentUser();

        if (user != null) {
            currentUserUID = user.getUid();
        }
    }

    public void moveTripFromUpcomingToHistory(TripDTO tripDTO) {
        moveTrip = true;
        deleteTrip(tripDTO.getTripKey());
        mDatabaseReference = mFirebaseDatabase.getReference("trips").child(currentUserUID).child("history");
        String tripKey = tripDTO.getTripKey();
        mDatabaseReference.child(tripKey).setValue(tripDTO, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (startMapFromAlarm) {
                    dialogPresenter.TripUpdated();
                } else {
                    homePresenter.showMap(tripDTO);
                }
            }
        });
    }
    public void deleteTrip(String tripKey, String userID)
    {
        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("trips").child(userID).child("upcoming").child(tripKey);
        mDatabaseReference.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
            {
                Log.i("mymessage","deleted");
            }
        });
    } // end of Delete Method
}

