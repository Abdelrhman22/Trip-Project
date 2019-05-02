package eg.com.iti.triporganizer.services.floatingWidget;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.Serializable;
import eg.com.iti.triporganizer.model.TripDTO;

public class FireBaseUpdateTrip implements Serializable {
    FirebaseDatabase database;
    DatabaseReference myRef;
    public FireBaseUpdateTrip()
    {
        database = FirebaseDatabase.getInstance();
    }
    public void updateTrip(TripDTO trip) {
        myRef = database.getReference("trips").child(trip.getUserId()).child("history");
        myRef.child(trip.getTripKey()).setValue(trip);
    }
}
