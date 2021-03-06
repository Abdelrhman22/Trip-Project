package eg.com.iti.triporganizer.screens.history;

import com.google.firebase.database.DatabaseReference;

import eg.com.iti.triporganizer.Network.NetworkServices.RetrievingHistoryTripsFromFirebase;
import eg.com.iti.triporganizer.model.TripDTO;

public class HistoryPresenterImpl implements HistoryContract.HistoryPresenter{
    HistoryContract.HistoryView historyView;
    RetrievingHistoryTripsFromFirebase retrievingHistoryTripsFromFirebase;
    public HistoryPresenterImpl(HistoryContract.HistoryView historyView) {
        this.historyView=historyView;
        retrievingHistoryTripsFromFirebase=new RetrievingHistoryTripsFromFirebase(this);
    }

    @Override
    public DatabaseReference retrieveOldTripsFromFirebase() {
        return retrievingHistoryTripsFromFirebase.retrieveHistoryTrips();
    }

    @Override
    public void notifyViewToShowNotesDialog(TripDTO tripDTO) {
        historyView.showNotesDialog(tripDTO);

    }
}
