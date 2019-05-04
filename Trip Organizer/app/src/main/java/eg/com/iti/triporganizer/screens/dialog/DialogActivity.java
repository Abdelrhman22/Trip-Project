package eg.com.iti.triporganizer.screens.dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.model.Notes;
import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.screens.home.UpComingTripAdapter;
import eg.com.iti.triporganizer.services.alarmServices.NotificationHelper;
import eg.com.iti.triporganizer.services.floatingWidget.FloatingIconService;
import eg.com.iti.triporganizer.utils.KeyTags;

import static eg.com.iti.triporganizer.services.alarmServices.AlarmHelper.stopAlarmService;

public class DialogActivity extends AppCompatActivity implements DialogContract.DialogView {

    DialogContract.DialogPresenter dialogPresenter;
    private AlertDialog.Builder alertBuilder;
    private MediaPlayer player;
    TripDTO receivedTrip=new TripDTO();
    UpComingTripAdapter upComingTripAdapter;
    String key, name , userID;
    double startLat,startLong,endLat,endLon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        dialogPresenter =new DialogPresenterImpl(this);
        runMediaPlayer();
        setFinishOnTouchOutside(false);
        Intent intent=getIntent();
        if(intent!=null)
        {
             key=intent.getStringExtra(KeyTags.tripKey);
             name=intent.getStringExtra(KeyTags.tripName);
             userID=intent.getStringExtra(KeyTags.tripUserId);
             startLat =intent.getDoubleExtra(KeyTags.tripStartLat,0.0);
             startLong =intent.getDoubleExtra(KeyTags.tripStartLong,0.0);
             endLat =intent.getDoubleExtra(KeyTags.tripEndLat,0.0);
             endLon =intent.getDoubleExtra(KeyTags.tripEndLong,0.0);
            dialogPresenter.getTripByKey(key,userID);
        }

        alertBuilder=new AlertDialog.Builder(this);
        alertBuilder.setTitle("Trip "+name)
                .setMessage("Do yo want to Start ?")
                .setPositiveButton("start",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        player.stop();
                        player.release();
                        launchGoogleMap(startLat,startLong,endLat,endLon);
                        dialogPresenter.moveTripFromUpcomingToHistory(receivedTrip);
                        dialogPresenter.startTrip(receivedTrip);
                        finishAffinity();
                    }
                }).setNeutralButton("snooze", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player.stop();
                player.release();

                //Snooze trip
                //Notification in the tray
                NotificationHelper notificationHelper = new NotificationHelper(DialogActivity.this);
                notificationHelper.getTripDetails(receivedTrip);
                NotificationCompat.Builder builder = notificationHelper.getChannelNotification();
                notificationHelper.getManager().notify(1, builder.build());
                finish();
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player.stop();
                player.release();
                //Cancel trip
                dialogPresenter.canCelTrip(receivedTrip);
                stopAlarmService();
                finish();
            }
        }).setIcon(getResources().getDrawable(R.drawable.ic_notification)).setCancelable(false).show();

    }

    private void launchGoogleMap(double trip_start_point_latitude, double trip_start_point_longitude, double trip_end_point_latitude, double trip_end_point_longitude)
    {
        Intent mapIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?saddr=" + trip_start_point_latitude + "," + trip_start_point_longitude + "&daddr=" + trip_end_point_latitude + "," + trip_end_point_longitude));
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(mapIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Please install a maps application", Toast.LENGTH_SHORT).show();
        }

    }

    private void runMediaPlayer()
    {
        player=MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        player.setLooping(true);
        player.setVolume(100.0f,100.0f);
        player.start();
    }

    @Override
    public void getTripData(TripDTO tripDTO) {
        receivedTrip=tripDTO;
        Log.i("tripValue","received"+receivedTrip.getName());
    }

    @Override
    public void startFloatingWidgetService() {
        Intent intent = new Intent(this, FloatingIconService.class);
        Notes notes = receivedTrip.getNotes();
        if (notes.getNotes().size() != 0)
        {
            intent.putExtra(KeyTags.trip, receivedTrip);
            startService(intent);
        }
    }


}
