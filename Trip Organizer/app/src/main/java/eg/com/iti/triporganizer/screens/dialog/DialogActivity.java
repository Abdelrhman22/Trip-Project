package eg.com.iti.triporganizer.screens.dialog;

import android.app.PendingIntent;
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
import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.services.alarmServices.NotificationHelper;
import eg.com.iti.triporganizer.utils.KeyTags;

import static eg.com.iti.triporganizer.services.alarmServices.AlarmHelper.stopAlarmService;

public class DialogActivity extends AppCompatActivity implements DialogActivityContract.DialogView {

    DialogActivityContract.DialogPrsenter dialogPrsenter;
    private AlertDialog.Builder alertBuilder;
    private MediaPlayer player;
    TripDTO tripDTO;
    String tripName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        dialogPrsenter=new DialogPresenterImpl(this);
        runMediaPlayer();
        setFinishOnTouchOutside(false);
        if(getIntent()!=null)
        {
            tripDTO=(TripDTO) getIntent().getSerializableExtra(KeyTags.tripKey);
            if(tripDTO!=null)
            {

                tripName = tripDTO.getName();
                Log.i("lat",""+tripDTO.getTrip_start_point_latitude());
            }
           else
               tripName="empty";
        }

        alertBuilder=new AlertDialog.Builder(this);
        alertBuilder.setTitle("Trip "+tripName)
                .setMessage("Do yo want to Start ?")
                .setPositiveButton("start",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        player.stop();
                        player.release();
//                        launchGoogleMap(tripDTO.getTrip_start_point_latitude(),tripDTO.getTrip_start_point_longitude(),
//                                tripDTO.getTrip_end_point_latitude(),tripDTO.getTrip_end_point_longitude());
                        launchGoogleMap(29.973137 ,31.017820 ,
                                30.019712 ,31.210248);
                        //Start trip
                        dialogPrsenter.updateTripStatus(tripDTO);

                        //Call Method that starts widget service
                        //tripDTO.getNotes()
                        finish();
                    }
                }).setNeutralButton("snooze", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player.stop();
                player.release();

                //Snooze trip

                //Notification in the tray
                NotificationHelper notificationHelper = new NotificationHelper(DialogActivity.this);
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
                stopAlarmService();
                finish();
            }
        }).setIcon(getResources().getDrawable(R.drawable.ic_notification)).setCancelable(false).show();

    }

    private void launchGoogleMap(Double trip_start_point_latitude, Double trip_start_point_longitude, Double trip_end_point_latitude, Double trip_end_point_longitude)
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
}
