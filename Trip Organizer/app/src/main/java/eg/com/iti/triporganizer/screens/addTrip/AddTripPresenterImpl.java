package eg.com.iti.triporganizer.screens.addTrip;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;

import eg.com.iti.triporganizer.Network.NetworkServices.AddTripsToFireBaseDataBase;
import eg.com.iti.triporganizer.Network.NetworkServices.FirebaseTripsManager;
import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.services.alarmServices.AlarmHelper;

public class AddTripPresenterImpl implements AddTripContract.AddTripPresenter
{
FirebaseTripsManager firebaseTripsManager;
    AddTripContract.AddTripView addTripView;
    AddTripsToFireBaseDataBase addTripsToFireBaseDataBase;
    Context context;

    public AddTripPresenterImpl(AddTripContract.AddTripView addTripView) {
        this.addTripView=addTripView;
        this.context = (Context)addTripView;
        addTripsToFireBaseDataBase= new AddTripsToFireBaseDataBase(this);
        firebaseTripsManager=new FirebaseTripsManager(this);
    }


    @Override
    public void addTrip(TripDTO tripDTO, Calendar calendar) {
        addTripsToFireBaseDataBase.addTrip(tripDTO);
        Log.i("Calender",""+calendar.getTime());
        AlarmHelper.startAlarm(tripDTO,calendar,context);
    }

    @Override
    public void notifyViewWithSuccessfulInsertion() {
        addTripView.respondToSuccessfulInsertion();
    }

    @Override
    public void notifyViewWithFailedInsertion() {
        addTripView.respondToFailedInsertion();
    }

    @Override
    public void deleteTrip(String editedTripKey) {
        firebaseTripsManager.deleteTrip(editedTripKey);
    }
}
