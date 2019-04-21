package eg.com.iti.triporganizer.screens.dialog;

import eg.com.iti.triporganizer.model.TripDTO;

public interface DialogActivityContract {
    interface DialogView
    {

    }
    interface DialogPrsenter
    {
        void updateTripStatus(TripDTO tripDTO);
    }
}
