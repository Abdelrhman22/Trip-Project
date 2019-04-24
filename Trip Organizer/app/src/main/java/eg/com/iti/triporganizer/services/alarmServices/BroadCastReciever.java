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
            TripDTO tripDTO = (TripDTO) intent.getSerializableExtra(KeyTags.tripKey);
            if(tripDTO !=null)
            {
                Intent outgoingIntent = new Intent(context, DialogActivity.class);
                outgoingIntent.putExtra(KeyTags.tripKey,tripDTO);
                outgoingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(outgoingIntent);
            }
           else {
                Log.i("mytag","BroadCastReciever trip is null");
                Intent outgoingIntent = new Intent(context, DialogActivity.class);
                outgoingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(outgoingIntent);
                Log.i("mytag","after launch intent");
            }
        }
        else
        {
            Log.i("mytag","BroadCastReciever intent is null");
        }
    }
}