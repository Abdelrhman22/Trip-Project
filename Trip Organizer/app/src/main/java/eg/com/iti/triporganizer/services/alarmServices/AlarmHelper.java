package eg.com.iti.triporganizer.services.alarmServices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.utils.KeyTags;

public class AlarmHelper {

    public static void startAlarm(TripDTO tripDTO, Calendar calendar, Context context ) {
        TripDTO receivedTrip = tripDTO;
        Intent serviceIntent = new Intent(context, BroadCastReciever.class);
        serviceIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        serviceIntent.putExtra(KeyTags.tripKey,receivedTrip);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, serviceIntent, 0);

        if (receivedTrip.getRepeated().equals("Repeat Daily"))
        {
            calendar.add(Calendar.DATE, 1);
            //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
        else if(receivedTrip.getRepeated().equals("Repeat Weekly"))
        {
            calendar.add(Calendar.DATE, 7);
        }
        else if(receivedTrip.getRepeated().equals("Repeat Monthly"))
        {
            calendar.add(Calendar.DATE, 30);
        }
        else {
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}
