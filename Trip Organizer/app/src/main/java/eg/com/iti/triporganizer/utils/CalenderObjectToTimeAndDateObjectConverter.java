package eg.com.iti.triporganizer.utils;

import java.util.Calendar;

import eg.com.iti.triporganizer.model.TripTimeAndDateDTO;

public class CalenderObjectToTimeAndDateObjectConverter {

    public static TripTimeAndDateDTO getTimeAndDateObject(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TripTimeAndDateDTO tripTimeAndDateDTO = new TripTimeAndDateDTO(year, month, dayOfMonth, hourOfDay, minute);
        return tripTimeAndDateDTO;
    }
}
