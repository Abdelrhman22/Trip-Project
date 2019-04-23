package eg.com.iti.triporganizer.screens.history;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.model.TripDTO;

public class HistoryActivity extends AppCompatActivity implements HistoryContract.HistoryView {
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String userID;
    HistoryContract.HistoryPresenter historyPresenter;
    HistoryTripAdapter historyTripAdapter;
    DatabaseReference databaseReference;
    ArrayList<TripDTO> historyTripsList;
    RecyclerView historyTripsRecyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyTripsList = new ArrayList<>();
        historyPresenter=new HistoryPresenterImpl (this);
        databaseReference=historyPresenter.retrieveOldTripsFromFirebase();
        mAuth=FirebaseAuth.getInstance();
        initializeComponents();
        addingListeners();
    }

    private void addingListeners() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshotItr: dataSnapshot.getChildren())
                {
                    TripDTO trip = dataSnapshotItr.getValue(TripDTO.class);
                    historyTripsList.add(trip);
                }
                historyTripAdapter = new HistoryTripAdapter(HistoryActivity.this,historyPresenter,historyTripsList);
                historyTripsRecyclerView.setAdapter(historyTripAdapter);
                historyTripAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HistoryActivity.this, "Your Trips are unavailable", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeComponents() {
        historyTripsRecyclerView =findViewById(R.id.oldTripsListRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        historyTripsRecyclerView.setLayoutManager(linearLayoutManager);
    }
}
