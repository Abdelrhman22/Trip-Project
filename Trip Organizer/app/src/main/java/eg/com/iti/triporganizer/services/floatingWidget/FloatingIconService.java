package eg.com.iti.triporganizer.services.floatingWidget;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.adapter.FloatingNotesAdapter;
import eg.com.iti.triporganizer.model.NoteDTO;
import eg.com.iti.triporganizer.model.Notes;
import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.utils.KeyTags;

public class FloatingIconService extends Service {

    private WindowManager mWindowManager;
    private View mAlarmView ,layoutCollapsed,layoutExpanded;
    private WindowManager.LayoutParams params;
    RelativeLayout parentLayout;
    ImageView closeButton;
    RecyclerView recyclerView;
    Button confirmButton;
    // these variables according to inflate recyclerView with Notes.
    private ArrayList<NoteDTO> notes = new ArrayList<>(); // notes of this trip
    private TripDTO receivedTrip;
    private FloatingNotesAdapter adapter;
    FireBaseUpdateTrip fireBaseUpdateTrip = new FireBaseUpdateTrip();

    public FloatingIconService()
    {}
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // getTrip from DialogActivity
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        receivedTrip = (TripDTO) intent.getSerializableExtra(KeyTags.trip);
        notes = receivedTrip.getNotes().getNotes();
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAlarmView = LayoutInflater.from(this).inflate(R.layout.floating_icon, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        } else {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

        params.gravity = Gravity.CENTER_VERTICAL | Gravity.TOP;
        params.x = 0;
        params.y = 100;
        // add View
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mAlarmView, params);

        layoutCollapsed = mAlarmView.findViewById(R.id.layoutCollapsed);
        layoutExpanded = mAlarmView.findViewById(R.id.layoutExpanded);

        closeButton = mAlarmView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });
        parentLayout=(RelativeLayout)mAlarmView.findViewById(R.id.parentLayout);
        parentLayout.setOnTouchListener(new View.OnTouchListener()
        {
            private int lastAction;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            layoutCollapsed.setVisibility(View.GONE);
                            layoutExpanded.setVisibility(View.VISIBLE);

                            recyclerView = mAlarmView.findViewById(R.id.recyclerViewTripNotes);

                            if (adapter == null) {
                                adapter = new FloatingNotesAdapter(notes);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            recyclerView.setAdapter(adapter);
                            confirmButton = mAlarmView.findViewById(R.id.confirmButton);

                            confirmButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    layoutCollapsed.setVisibility(View.VISIBLE);
                                    layoutExpanded.setVisibility(View.GONE);
                                    notes = ((FloatingNotesAdapter)recyclerView.getAdapter()).getTripNotes();
                                    receivedTrip.setNotes(new Notes(notes));
                                    fireBaseUpdateTrip.updateTrip(receivedTrip);

                                }
                            });
                            v.performClick();
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mAlarmView, params);
                        lastAction = event.getAction();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAlarmView != null) mWindowManager.removeView(mAlarmView);
    }
}
