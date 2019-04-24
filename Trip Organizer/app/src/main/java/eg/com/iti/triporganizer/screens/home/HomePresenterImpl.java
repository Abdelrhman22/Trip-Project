package eg.com.iti.triporganizer.screens.home;

import com.google.firebase.database.DatabaseReference;
import eg.com.iti.triporganizer.Network.NetworkServices.FirebaseLogout;
import eg.com.iti.triporganizer.Network.NetworkServices.FirebaseTripsManager;
import eg.com.iti.triporganizer.Network.NetworkServices.RetrievingUpcomingTripsFromFirebase;
import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.utils.NetworkUtilities;
import eg.com.iti.triporganizer.utils.SharedPreferencesManager;

public class HomePresenterImpl implements HomeContract.HomePresenter {
    HomeContract.HomeView homeView;
    FirebaseLogout firebaseLogout;
    SharedPreferencesManager sharedPreferencesManager;
    RetrievingUpcomingTripsFromFirebase retrievingUpcomingTripsFromFirebase;
    FirebaseTripsManager firebaseTripsManager;

    public HomePresenterImpl(HomeContract.HomeView homeView) {
        this.homeView=homeView;
        firebaseLogout=new FirebaseLogout(this);
        retrievingUpcomingTripsFromFirebase=new RetrievingUpcomingTripsFromFirebase(this);
        firebaseTripsManager=new FirebaseTripsManager(this);

    }

    @Override
    public void signOut() {
        firebaseLogout.signOut();
    }

    @Override
    public void notifyViewWithSuccessfulSignOut() {
        homeView.respondToSuccessfulSignOut();
    }

    @Override
    public DatabaseReference retrieveUpcomingTripsFromFirebase() {
        return retrievingUpcomingTripsFromFirebase.retrieveUpcomingTrips();
    }

    @Override
    public void deleteTrip(String tripKey) {
        firebaseTripsManager.deleteTrip(tripKey);
    }

    @Override
    public void notifyWithSuccessfulTripDeletion() {
        homeView.respondToSuccessfulTripDeletion();
    }






}

