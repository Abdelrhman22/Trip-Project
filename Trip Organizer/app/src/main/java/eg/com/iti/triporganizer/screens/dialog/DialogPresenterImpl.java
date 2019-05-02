package eg.com.iti.triporganizer.screens.dialog;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import eg.com.iti.triporganizer.Network.NetworkServices.FirebaseTripsManager;
import eg.com.iti.triporganizer.model.TripDTO;

public class DialogPresenterImpl implements DialogContract.DialogPresenter {
    DialogContract.DialogView dialogView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    FirebaseTripsManager firebaseTripsManager;
    DialogPresenterImpl(DialogContract.DialogView dialogView)
    {
        this.dialogView=dialogView;
        firebaseTripsManager=new FirebaseTripsManager(this);
    }


    @Override
    public void getTripByKey(String tripKey, String userId)
    {
        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("trips").child(userId).child("upcoming");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot trip: dataSnapshot.getChildren())
                {
                    if(trip.getKey().equals(tripKey))
                    {
                        TripDTO tripDTO=trip.getValue(TripDTO.class);
                        dialogView.getTripData(tripDTO);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void moveTripFromUpcomingToHistory(TripDTO tripDTO) {
        firebaseTripsManager.moveTripFromUpcomingToHistory(tripDTO);
    }

    @Override
    public void TripUpdated() {
        Log.i("mymessage","Trip has updated");
    }


    @Override
    public void startTrip(TripDTO tripDTO) {
        dialogView.startFloatingWidgetService();
    }

    @Override
    public void canCelTrip(TripDTO tripDTO) {
        firebaseTripsManager.deleteTrip(tripDTO.getTripKey());
    }


}
