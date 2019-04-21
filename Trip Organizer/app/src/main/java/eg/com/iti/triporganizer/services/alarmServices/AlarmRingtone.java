package eg.com.iti.triporganizer.services.alarmServices;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

//Start & Cancel Sound
public class AlarmRingtone {
    Context context;
    Uri notification;
    Ringtone myRingtone;

    public AlarmRingtone(Context context) {
        this.context = context;
    }

    //Alarm Ringtone
    public void getAlarmSound() {
        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        myRingtone = RingtoneManager.getRingtone(context, notification);
        myRingtone.play();
    }
}
