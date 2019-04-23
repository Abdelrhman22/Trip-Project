package eg.com.iti.triporganizer.screens.history;

import com.google.firebase.database.DatabaseReference;

public interface HistoryContract {
    interface HistoryView {
    }

    interface HistoryPresenter {
        DatabaseReference retrieveOldTripsFromFirebase();
    }
}
