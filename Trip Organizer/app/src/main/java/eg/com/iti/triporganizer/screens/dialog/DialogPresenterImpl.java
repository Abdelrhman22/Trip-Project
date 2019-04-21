package eg.com.iti.triporganizer.screens.dialog;

import eg.com.iti.triporganizer.model.TripDTO;

public class DialogPresenterImpl implements DialogActivityContract.DialogPrsenter {
    DialogActivityContract.DialogView view;

    DialogPresenterImpl(DialogActivityContract.DialogView view)
    {
        this.view=view;
    }

    @Override
    public void updateTripStatus(TripDTO tripDTO) {

    }
}
