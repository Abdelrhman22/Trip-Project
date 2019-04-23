package eg.com.iti.triporganizer.Network.NetworkServices;

import android.provider.ContactsContract;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.screens.home.HomeContract;

public class RetrievingUpcomingTripsFromFirebase {
    private HomeContract.HomePresenter homePresenter;
    //firebase database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;

    //firebase user
    FirebaseUser user;
    String currentUserUID;

    public RetrievingUpcomingTripsFromFirebase(HomeContract.HomePresenter homePresenter) {
        this.homePresenter = homePresenter;
    }

    public DatabaseReference retrieveUpcomingTrips() {
        firebaseAuth = FirebaseAuth.getInstance();
        getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("trips").child(currentUserUID).child("upcoming");
        return mDatabaseReference;
    }

    private void getCurrentUser() {

        user = firebaseAuth.getCurrentUser();

        if (user != null) {
            currentUserUID = user.getUid();
        }
    }
}
