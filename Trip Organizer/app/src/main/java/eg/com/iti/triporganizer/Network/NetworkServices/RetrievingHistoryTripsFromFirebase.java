package eg.com.iti.triporganizer.Network.NetworkServices;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import eg.com.iti.triporganizer.screens.history.HistoryContract;

public class RetrievingHistoryTripsFromFirebase {
    private HistoryContract.HistoryPresenter historyPresenter;
    //firebase database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;

    //firebase user
    FirebaseUser user;
    String currentUserUID;

    public RetrievingHistoryTripsFromFirebase(HistoryContract.HistoryPresenter historyPresenter) {
        this.historyPresenter = historyPresenter;
    }
    public DatabaseReference retrieveHistoryTrips() {
        firebaseAuth = FirebaseAuth.getInstance();
        getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("trips").child(currentUserUID).child("history");
        return mDatabaseReference;
    }
    private void getCurrentUser() {

        user = firebaseAuth.getCurrentUser();

        if (user != null) {
            currentUserUID = user.getUid();
        }
    }
}
