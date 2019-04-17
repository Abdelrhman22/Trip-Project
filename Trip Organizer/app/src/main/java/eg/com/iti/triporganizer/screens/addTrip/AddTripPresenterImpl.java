package eg.com.iti.triporganizer.screens.addTrip;

import java.util.Calendar;

import eg.com.iti.triporganizer.Network.NetworkServices.AddTripsToFireBaseDataBase;
import eg.com.iti.triporganizer.model.roomdb.TripDTO;

public class AddTripPresenterImpl implements AddTripContract.AddTripPresenter
{

    AddTripContract.AddTripView addTripView;
    AddTripsToFireBaseDataBase addTripsToFireBaseDataBase;

    public AddTripPresenterImpl(AddTripContract.AddTripView addTripView) {
        this.addTripView=addTripView;
        addTripsToFireBaseDataBase= new AddTripsToFireBaseDataBase(this);
    }


    @Override
    public void addTrip(TripDTO tripDTO) {
        addTripsToFireBaseDataBase.addTrip(tripDTO);
    }

    @Override
    public void notifyViewWithSuccessfulInsertion() {
        addTripView.respondToSuccessfulInsertion();
    }

    @Override
    public void notifyViewWithFailedInsertion() {
        addTripView.respondToFailedInsertion();
    }
}
