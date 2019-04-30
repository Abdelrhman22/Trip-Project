package eg.com.iti.triporganizer.services.alarmServices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.screens.dialog.DialogActivity;
import eg.com.iti.triporganizer.utils.KeyTags;


public class BroadCastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null)
        {
            // get Trip Data From Alarm
            String key=intent.getStringExtra(KeyTags.tripKey);
            String name=intent.getStringExtra(KeyTags.tripName);
            String userID=intent.getStringExtra(KeyTags.tripUserId);
            double startLat =intent.getDoubleExtra(KeyTags.tripStartLat,0.0);
            double startLong =intent.getDoubleExtra(KeyTags.tripStartLong,0.0);
            double endLat =intent.getDoubleExtra(KeyTags.tripEndLat,0.0);
            double endLon =intent.getDoubleExtra(KeyTags.tripEndLong,0.0);
            // Launching DialogActivity with TripData
                Intent outgoingIntent = new Intent(context, DialogActivity.class);
                outgoingIntent.putExtra(KeyTags.tripKey,key);
                outgoingIntent.putExtra(KeyTags.tripName,name);
                outgoingIntent.putExtra(KeyTags.tripUserId,userID);
                outgoingIntent.putExtra(KeyTags.tripStartLat,startLat);
                outgoingIntent.putExtra(KeyTags.tripStartLong,startLong);
                outgoingIntent.putExtra(KeyTags.tripEndLat,endLat);
                outgoingIntent.putExtra(KeyTags.tripEndLong,endLon);

                outgoingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(outgoingIntent);
        }
        else
        {
            Log.i("mytag","BroadCastReciever intent is null");
        }
    }
}
