package eg.com.iti.triporganizer.screens.home;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import eg.com.iti.triporganizer.model.TripDTO;

public interface HomeContract {
    interface HomeView {
        void respondToSuccessfulSignOut();
    }

    interface HomePresenter {
        void signOut();

        void notifyViewWithSuccessfulSignOut();

        DatabaseReference retrieveUpcomingTripsFromFirebase();

        void deleteTrip();
    }
}
