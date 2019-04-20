package eg.com.iti.triporganizer.Network.NetworkServices;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.screens.addTrip.AddTripContract;

public class AddTripsToFireBaseDataBase {
    AddTripContract.AddTripPresenter addTripPresenter;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    public AddTripsToFireBaseDataBase(AddTripContract.AddTripPresenter addTripPresenter) {
        this.addTripPresenter = addTripPresenter;
        database = FirebaseDatabase.getInstance();
    }

    public void addTrip(TripDTO tripDTO) {
        databaseReference = database.getReference("trips").child(tripDTO.getUserId());
        databaseReference.child(databaseReference.push().getKey()).setValue(tripDTO, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                addTripPresenter.notifyViewWithSuccessfulInsertion();
            }
        });

    }
}
