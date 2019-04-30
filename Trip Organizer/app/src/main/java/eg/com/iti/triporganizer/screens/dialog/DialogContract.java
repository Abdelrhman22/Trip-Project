package eg.com.iti.triporganizer.screens.dialog;

import eg.com.iti.triporganizer.model.TripDTO;

public interface DialogContract
{
    interface DialogView
    {
        public  void getTripData(TripDTO tripDTO);

    }
    interface DialogPrsenter
    {
       public void getTripByKey(String key, String UserId);
        void moveTripFromUpcomingToHistory(TripDTO tripDTO);
        void TripUpdated();
    }
}
