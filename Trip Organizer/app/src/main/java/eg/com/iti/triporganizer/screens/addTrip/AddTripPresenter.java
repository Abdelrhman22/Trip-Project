package eg.com.iti.triporganizer.screens.addTrip;

import java.util.Calendar;
import eg.com.iti.triporganizer.model.helpers.roomdb.TripDTO;

public interface AddTripPresenter
{
    void updateFirebaseAndCreateAlarms(TripDTO tripDTO, Calendar myCalendar) ;
    void addTrip();
    void updateTrip(TripDTO trip,Calendar myCalendar) ;
}
