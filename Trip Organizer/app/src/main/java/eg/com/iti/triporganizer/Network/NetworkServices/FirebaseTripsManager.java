package eg.com.iti.triporganizer.Network.NetworkServices;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public void deleteTrip() {
        firebaseAuth = FirebaseAuth.getInstance();
        getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("trips").child(currentUserUID).child("upcoming");

    }
    private void getCurrentUser() {

        user = firebaseAuth.getCurrentUser();

        if (user != null) {
            currentUserUID = user.getUid();
        }
    }
}
