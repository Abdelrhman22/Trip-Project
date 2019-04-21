package eg.com.iti.triporganizer.services.alarmServices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.utils.KeyTags;

public class AlarmHelper {

    public static void startAlarm(TripDTO tripDTO, Context context ) {
        TripDTO receivedTrip = tripDTO;
        Calendar calender = receivedTrip.getTrip_date();
        Intent serviceIntent = new Intent(context, BroadCastReciever.class);
        //serviceIntent.addFlags()
        serviceIntent.putExtra(KeyTags.tripKey,receivedTrip);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, serviceIntent, 0);

        if (receivedTrip.getRepeated().equals("Daily"))
        {
            calender.add(Calendar.DATE, 1);
        }
        else if(receivedTrip.getRepeated().equals("Weekly"))
        {
            calender.add(Calendar.DATE, 7);
        }
        else if(receivedTrip.getRepeated().equals("Monthly"))
        {
            calender.add(Calendar.DATE, 30);
        }
        else {
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);
        }
    }
}
