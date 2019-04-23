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

    private static AlarmManager alarmManager;
    private  static PendingIntent pendingIntent;

    public static void startAlarm(TripDTO tripDTO, Calendar calendar, Context context ) {
        TripDTO receivedTrip = tripDTO;
        Intent serviceIntent = new Intent(context, BroadCastReciever.class);
        serviceIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        serviceIntent.putExtra(KeyTags.tripKey,receivedTrip);
        Log.i("mytag","AlarmHelper "+tripDTO.getName());
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(context, 1, serviceIntent, 0);

        switch (receivedTrip.getRepeated()) {
            case "Repeat Daily":
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                break;
            case "Repeat Weekly":
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                break;
            case "Repeat Monthly":
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 30, pendingIntent);
                break;
            default:
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                break;
        }
    }

    public  static  void stopAlarmService()
    {
        alarmManager.cancel(pendingIntent);
    }
}
