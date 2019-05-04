package eg.com.iti.triporganizer.services.alarmServices;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.screens.dialog.DialogActivity;
import eg.com.iti.triporganizer.screens.home.HomeActivity;
import eg.com.iti.triporganizer.utils.KeyTags;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    private NotificationManager mManager;
    private TripDTO tripDTO;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public void getTripDetails(TripDTO tripDTO) {
        this.tripDTO = tripDTO;
    }

    public NotificationCompat.Builder getChannelNotification() {

        Intent notifyIntent = new Intent(this, BroadCastReciever.class);
        notifyIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        //notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if(tripDTO != null) {
            Log.i("MyMessage", "tripdto not null");
            Log.i("MyMessage", ""+tripDTO.getName());
            Log.i("MyMessage", ""+tripDTO.getTripStartPointLatitude());
            Log.i("MyMessage", ""+tripDTO.getTripEndPointLatitude());
        }

        notifyIntent.putExtra(KeyTags.tripKey, tripDTO.getTripKey());
        notifyIntent.putExtra(KeyTags.tripName, tripDTO.getName());
        notifyIntent.putExtra(KeyTags.tripUserId, tripDTO.getUserId());
        notifyIntent.putExtra(KeyTags.tripStartLat, tripDTO.getTripStartPointLatitude());
        notifyIntent.putExtra(KeyTags.tripStartLong, tripDTO.getTripStartPointLongitude());
        notifyIntent.putExtra(KeyTags.tripEndLat, tripDTO.getTripEndPointLatitude());
        notifyIntent.putExtra(KeyTags.tripEndLong, tripDTO.getTripStartPointLongitude());
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getBroadcast(this,
                tripDTO.getRequestCode(), notifyIntent, 0);

        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Road Trip Alarm!")
                .setContentText("Your trip is about to start.")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentIntent(contentIntent);

    }
}
