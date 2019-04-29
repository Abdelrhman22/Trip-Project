package eg.com.iti.triporganizer.screens.home;

import com.google.firebase.database.DatabaseReference;

import eg.com.iti.triporganizer.model.TripDTO;

public interface HomeContract {
    interface HomeView {
        void respondToSuccessfulSignOut();


        void respondToSuccessfulTripDeletion();

        void notifyHomeToShowMap(TripDTO tripDTO);
    }

    interface HomePresenter {
        void signOut();

        void notifyViewWithSuccessfulSignOut();

        DatabaseReference retrieveUpcomingTripsFromFirebase();

        void deleteTrip(String tripKey);

        void notifyWithSuccessfulTripDeletion();

        void moveTripFromUpcomingToHistory(TripDTO tripDTO);

        void showMap(TripDTO tripDTO);
    }
}
