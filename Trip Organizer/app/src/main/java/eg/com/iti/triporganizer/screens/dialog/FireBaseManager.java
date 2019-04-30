package eg.com.iti.triporganizer.screens.dialog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import eg.com.iti.triporganizer.model.TripDTO;

public class FireBaseManager {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;

    DialogContract.DialogPrsenter dialogPrsenter;
    FireBaseManager(DialogContract.DialogPrsenter dialogPrsenter)
    {
        this.dialogPrsenter=dialogPrsenter;
    }
    private void deleteTrip(String tripKey, String userID)
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
    public void moveTripFromUpcomingToHistory(TripDTO tripDTO) {
        deleteTrip(tripDTO.getTripKey(),tripDTO.getUserId());
        mDatabaseReference = mFirebaseDatabase.getReference("trips").child(tripDTO.getUserId()).child("history");
        String tripKey = tripDTO.getTripKey();
        mDatabaseReference.child(tripKey).setValue(tripDTO,new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                dialogPrsenter.TripUpdated();
            }
        });
    }
}
