package eg.com.iti.triporganizer.screens.history;

import com.google.firebase.database.DatabaseReference;

import eg.com.iti.triporganizer.model.TripDTO;

public interface HistoryContract {
    interface HistoryView {
        void showNotesDialog(TripDTO tripDTO);
    }

    interface HistoryPresenter {
        DatabaseReference retrieveOldTripsFromFirebase();

        void notifyViewToShowNotesDialog(TripDTO tripDTO);
    }
}
