package eg.com.iti.triporganizer.screens.addTrip;

import eg.com.iti.triporganizer.model.TripDTO;

public interface AddTripContract {
    interface AddTripView {
        void respondToSuccessfulInsertion();
        void respondToFailedInsertion();
    }
    interface AddTripPresenter{
        void addTrip (TripDTO tripDTO);
        void notifyViewWithSuccessfulInsertion();
        void notifyViewWithFailedInsertion();
    }
}
