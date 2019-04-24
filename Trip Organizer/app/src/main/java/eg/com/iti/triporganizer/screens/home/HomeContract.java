package eg.com.iti.triporganizer.screens.home;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import eg.com.iti.triporganizer.model.TripDTO;

public interface HomeContract {
    interface HomeView {
        void respondToSuccessfulSignOut();


        void respondToSuccessfulTripDeletion();

        void respondToSuccessfulEditTrip(TripDTO tripDTO);
    }

    interface HomePresenter {
        void signOut();

        void notifyViewWithSuccessfulSignOut();

        DatabaseReference retrieveUpcomingTripsFromFirebase();

        void deleteTrip(String tripKey);

        void notifyWithSuccessfulTripDeletion();

        void editTrip(TripDTO tripDTO);
        
        void notifyViewWithEditTripDone(TripDTO tripDTO);
    }
}
