package eg.com.iti.triporganizer.Network.NetworkServices;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.screens.home.HomeContract;

public class FirebaseTripsManager {
    HomeContract.HomePresenter homePresenter;
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

    public void deleteTrip(String tripKey) {
        firebaseAuth = FirebaseAuth.getInstance();
        getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("trips").child(currentUserUID).child("upcoming").child(tripKey);
        mDatabaseReference.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
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

    public void editTrip(TripDTO tripDTO) {
        deleteTrip(tripDTO.getTripKey());
        homePresenter.notifyViewWithEditTripDone(tripDTO);
    }
}
