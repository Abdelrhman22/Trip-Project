package eg.com.iti.triporganizer.screens.home;

import android.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
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
import eg.com.iti.triporganizer.screens.addTrip.AddTripActivity;
import eg.com.iti.triporganizer.screens.history.HistoryActivity;
import eg.com.iti.triporganizer.screens.login.LoginActivity;
import eg.com.iti.triporganizer.screens.mapHistory.MapsActivity;
import eg.com.iti.triporganizer.utils.KeyTags;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeContract.HomeView {
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String userID;
    HomeContract.HomePresenter homePresenter;
    UpComingTripAdapter upComingTripAdapter;
    DatabaseReference databaseReference;
    ArrayList<TripDTO> upcomingTripsList;
    TextView userName,userEmail;

    //--------------------------------------------------------------
    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    RecyclerView upcomingTripsRecyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        upcomingTripsList = new ArrayList<>();
        homePresenter = new HomePresenterImpl(this);
        databaseReference = homePresenter.retrieveUpcomingTripsFromFirebase();
        mAuth = FirebaseAuth.getInstance();
        initializeComponents();
        addingListeners();
    }

    private void initializeComponents() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.addTripBtn);
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        upcomingTripsRecyclerView = findViewById(R.id.upcomingTripsList);
        linearLayoutManager = new LinearLayoutManager(this);
        upcomingTripsRecyclerView.setLayoutManager(linearLayoutManager);
        currentUser = mAuth.getCurrentUser();
        userName=navigationView.getHeaderView(0).findViewById(R.id.userName);
        userEmail=navigationView.getHeaderView(0).findViewById(R.id.userEmail);
        userID = currentUser.getUid();
        Log.i("name",currentUser.getDisplayName());
        userName.setText(currentUser.getDisplayName());
        userEmail.setText(currentUser.getEmail());


    }


    private void addingListeners() {
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AddTripActivity.class);
            startActivity(intent);
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshotItr : dataSnapshot.getChildren()) {
                    TripDTO trip = dataSnapshotItr.getValue(TripDTO.class);
                    upcomingTripsList.add(trip);
                }
                upComingTripAdapter = new UpComingTripAdapter(HomeActivity.this, homePresenter, upcomingTripsList);
                upcomingTripsRecyclerView.setAdapter(upComingTripAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Your Trips are unavailable", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Toast.makeText(this, "You are in Home Page", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(HomeActivity.this, HistoryActivity.class));
        } else if (id == R.id.nav_mapHistory) {
            Intent mapIntent = new Intent(HomeActivity.this, MapsActivity.class);
            mapIntent.putExtra(KeyTags.UUIDKey, userID);
            startActivity(mapIntent);
        } else if (id == R.id.nav_signout) {
            homePresenter.signOut();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void respondToSuccessfulSignOut() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.putExtra("signedOut", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void respondToSuccessfulTripDeletion() {
        Toast.makeText(this, "Trip deleted successfully", Toast.LENGTH_LONG).show();
       // upComingTripAdapter.notifyDataSetChanged();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void notifyHomeToShowMap(TripDTO tripDTO) {
        startActivity(getIntent());
        upComingTripAdapter.showDirection(tripDTO);

    }

    @Override
    public void showNotesDialog(TripDTO tripDTO) {
        NotesFragment dialogFragment = new NotesFragment(tripDTO.getNotes().getNotes());
        dialogFragment.show(getSupportFragmentManager(), "dialog");
    }


}
