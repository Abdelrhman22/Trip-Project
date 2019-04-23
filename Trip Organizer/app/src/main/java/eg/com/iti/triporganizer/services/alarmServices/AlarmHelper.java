package eg.com.iti.triporganizer.services.alarmServices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.utils.KeyTags;

public class AlarmHelper {

    public static void startAlarm(TripDTO tripDTO, Calendar calendar, Context context ) {
        TripDTO receivedTrip = tripDTO;
        Intent serviceIntent = new Intent(context, BroadCastReciever.class);
        serviceIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        serviceIntent.putExtra(KeyTags.tripKey,receivedTrip);
        Log.i("mytag","AlarmHelper "+tripDTO.getName());
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, serviceIntent, 0);

        if (receivedTrip.getRepeated().equals("Repeat Daily"))
        {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
        else if(receivedTrip.getRepeated().equals("Repeat Weekly"))
        {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
        }
        else if(receivedTrip.getRepeated().equals("Repeat Monthly"))
        {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY*30,pendingIntent);
        }
        else {
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}
