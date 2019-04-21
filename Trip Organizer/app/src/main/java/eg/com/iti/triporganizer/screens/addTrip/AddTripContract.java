package eg.com.iti.triporganizer.screens.addTrip;

import android.content.Context;

import java.util.Calendar;

import eg.com.iti.triporganizer.model.TripDTO;

public interface AddTripContract {
    interface AddTripView {
        void respondToSuccessfulInsertion();
        void respondToFailedInsertion();
    }
    interface AddTripPresenter{
        void addTrip (TripDTO tripDTO, Calendar calendar);
        void notifyViewWithSuccessfulInsertion();
        void notifyViewWithFailedInsertion();
    }
}
