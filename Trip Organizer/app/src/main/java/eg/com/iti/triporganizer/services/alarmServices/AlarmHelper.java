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
    public static Integer codesArray []={0,0,0,0,0,0,0,0,0,0};
    private static int requestCode=10;
    public static void startAlarm(TripDTO tripDTO, Calendar calendar, Context context )
    {
        TripDTO receivedTrip = tripDTO;
        requestCode = 10;
        getRequestCode();
        Log.i("requestCode","is"+requestCode);
        tripDTO.setRequestCode(requestCode);
        Intent serviceIntent = new Intent(context, BroadCastReciever.class);
        serviceIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        serviceIntent.putExtra(KeyTags.tripKey,tripDTO.getTripKey());    // tripKey
        serviceIntent.putExtra(KeyTags.tripName,tripDTO.getName());     // tripName
        serviceIntent.putExtra(KeyTags.tripUserId,tripDTO.getUserId()); // tripUserId
        serviceIntent.putExtra(KeyTags.tripStartLat,tripDTO.getTripStartPointLatitude());   // tripStartLat
        serviceIntent.putExtra(KeyTags.tripStartLong,tripDTO.getTripStartPointLongitude());  // tripStartLong
        serviceIntent.putExtra(KeyTags.tripEndLat,tripDTO.getTripEndPointLatitude());        // tripEndLat
        serviceIntent.putExtra(KeyTags.tripEndLong,tripDTO.getTripEndPointLongitude());  // tripEndLong
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(context, requestCode, serviceIntent, 0);

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
    public static void cancelAlarm(Context context,TripDTO tripDTO) {
        Intent intent = new Intent(context, BroadCastReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), tripDTO.getRequestCode(),
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }
    private static void getRequestCode()
    {
        for(int i=0;i<codesArray.length;i++)
        {
            if(codesArray[i]!=1)
            {
                requestCode=i;
                codesArray[i]=1;
                break;
            }
        }
    }
}
