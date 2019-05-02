package eg.com.iti.triporganizer.screens.dialog;

import eg.com.iti.triporganizer.model.TripDTO;

public interface DialogContract
{
    interface DialogView
    {
          void getTripData(TripDTO tripDTO);
          void startFloatingWidgetService();
    }
    interface DialogPrsenter
    {
       public void getTripByKey(String key, String UserId);
        void moveTripFromUpcomingToHistory(TripDTO tripDTO);
        void TripUpdated();
        public void startTrip(TripDTO tripDTO);
        public void canCelTrip(TripDTO tripDTO);
    }
}
