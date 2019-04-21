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
import android.widget.Toast;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.services.alarmServices.NotificationHelper;

public class DialogActivity extends AppCompatActivity {

    private AlertDialog.Builder alertBuilder;
    private MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        setFinishOnTouchOutside(false);
        player=MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        player.setLooping(true);
        player.setVolume(100.0f,100.0f);
        player.start();
        final int tripId=getIntent().getIntExtra("tripId",0);
        alertBuilder=new AlertDialog.Builder(this);
        alertBuilder.setTitle("Road Trip")
                .setMessage("Do yo want to Start Trip ?")
                .setPositiveButton("start",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        player.stop();
                        player.release();
                        //Start trip
                        Intent mapIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?saddr=" + 29.973137 + "," + 31.017820 + "&daddr=" + 30.019712 + "," + 31.210248));
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(mapIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Please install a maps application", Toast.LENGTH_SHORT).show();
                        }
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

            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player.stop();
                player.release();
                //Cancel trip
                finish();
            }
        }).setIcon(getResources().getDrawable(R.drawable.ic_notification)).setCancelable(false).show();

    }
}
