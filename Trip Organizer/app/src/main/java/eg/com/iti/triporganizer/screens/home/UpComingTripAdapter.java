package eg.com.iti.triporganizer.screens.home;

/* UpComingTripAdapter will be used to fill Recyclerview with All past Trip Details
   which Contains
    _____________________________
   |             |        |  .  |
   |   Image     |  From  |  .  |
   |_____________|________|  .  |
   |name of Note |  TO    |  .  |
   |_____________|________|_____|
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.model.Notes;
import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.screens.addTrip.AddTripActivity;
import eg.com.iti.triporganizer.services.alarmServices.AlarmHelper;
import eg.com.iti.triporganizer.services.floatingWidget.FloatingIconService;
import eg.com.iti.triporganizer.utils.KeyTags;
import eg.com.iti.triporganizer.utils.NetworkUtilities;

public class UpComingTripAdapter extends RecyclerView.Adapter<UpComingTripAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<TripDTO> upcomingTripsList;
    HomeContract.HomePresenter homePresenter;
    private PopupMenu popup;

    public UpComingTripAdapter(Context context, HomeContract.HomePresenter homePresenter, ArrayList<TripDTO> upcomingTripsList) {
        this.context = context;
        this.upcomingTripsList = upcomingTripsList;
        this.homePresenter = homePresenter;
    }

    @NonNull
    @Override
    public UpComingTripAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.trip_custom_list_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingTripAdapter.MyViewHolder myViewHolder, int i) {

        TripDTO tripDTO = upcomingTripsList.get(i);
        String tripDate = tripDTO.getDayOfMonth() + " / " + tripDTO.getMonth() + " / " + tripDTO.getYear();
        String tripTime = tripDTO.getHourOfDay() + " : " + tripDTO.getMinute();
        myViewHolder.tripName.setText(upcomingTripsList.get(i).getName());
        myViewHolder.tripStartPoint.setText(upcomingTripsList.get(i).getTripStartPoint());
        myViewHolder.tripEndPoint.setText(upcomingTripsList.get(i).getTripEndPoint());
        myViewHolder.tripDate.setText(tripDate);
        myViewHolder.tripTime.setText(tripTime);

        //set image
        String url="https://maps.googleapis.com/maps/api/staticmap?center=Berkeley,CA&zoom=14&size=400x400&";
        url+="&key="+ context.getString(R.string.google_api_key);
        String photo_url_str ="https://maps.googleapis.com/maps/api/staticmap?";
        photo_url_str+="&zoom=15";
        photo_url_str+="&size=150x150";
        photo_url_str+="&maptype=roadmap";
        photo_url_str+="&markers=color:blue%7Clabel:G%7C"+upcomingTripsList.get(i).getTripEndPointLatitude()+", "+upcomingTripsList.get(i).getTripEndPointLongitude();
        photo_url_str+="&key="+ "AIzaSyDZl8cLWi-yEroZ-m-lSUtoQiaf-oi-rbU";
        Glide.with(context)
                .load(photo_url_str)
                .placeholder(R.drawable.map)
                .error(R.drawable.error)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(myViewHolder.tripImage);



        myViewHolder.tripSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = new PopupMenu(context, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.edit_trip_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editTrip:
                                editTrip(upcomingTripsList.get(i));
                                return true;
                            case R.id.viewNotes:
                                viewNotes(upcomingTripsList.get(i));
                                return true;
                            case R.id.deleteTrip: {
                                AlertDialog deleteDialog = showDeleteDialog();
                                deleteDialog.show();
                                return true;
                            }
                            case R.id.startTrip: {
                                AlarmHelper.cancelAlarm(context.getApplicationContext());
                                homePresenter.moveTripFromUpcomingToHistory(upcomingTripsList.get(i));
                                showDirection(tripDTO);
                                Intent intent = new Intent(context, FloatingIconService.class);
                                Notes notes = tripDTO.getNotes();
                                if (notes.getNotes().size() != 0) {
                                    intent.putExtra(KeyTags.trip, tripDTO);
                                    context.startService(intent);
                                }
                                return true;
                            }
                            default:
                                return false;
                        }
                    }


                });
            }

            private void editTrip(TripDTO tripDTO) {
                //homePresenter.editTrip(tripDTO);
                Intent intent = new Intent(context, AddTripActivity.class);
                intent.putExtra("editedTrip", true);
                intent.putExtra("tripKey", tripDTO.getTripKey());
                intent.putExtra("tripName", tripDTO.getName());
                intent.putExtra("tripRepetition", tripDTO.getRepeated());
                intent.putExtra("tripStartPoint", tripDTO.getTripStartPoint());
                intent.putExtra("tripEndPoint", tripDTO.getTripEndPoint());
                intent.putExtra("tripStartYear", tripDTO.getYear());
                intent.putExtra("tripStartMonth", tripDTO.getMonth());
                intent.putExtra("tripStartDay", tripDTO.getDayOfMonth());
                intent.putExtra("tripStartHour", tripDTO.getHourOfDay());
                intent.putExtra("tripStartMinute", tripDTO.getMinute());
                intent.putExtra("startPlaceLatitude", tripDTO.getTripStartPointLatitude());
                intent.putExtra("startPlaceLongitude", tripDTO.getTripStartPointLongitude());
                intent.putExtra("endPlaceLatitude", tripDTO.getTripEndPointLatitude());
                intent.putExtra("endPlaceLongitude", tripDTO.getTripEndPointLongitude());
                if (tripDTO.getNotes() != null) {
                    intent.putExtra("hasNotes", true);
                    intent.putExtra("notesNum", tripDTO.getNotes().getNotes().size());
                    for (int i = 0; i < tripDTO.getNotes().getNotes().size(); i++) {
                        intent.putExtra("note" + i, tripDTO.getNotes().getNotes().get(i).getBody());
                        intent.putExtra("done" + i, tripDTO.getNotes().getNotes().get(i).isDone());
                    }
                }
                context.startActivity(intent);

            }

            private void viewNotes(TripDTO tripDTO) {
                if (tripDTO.getNotes() == null)
                    Toast.makeText(context, "This trip has no notes", Toast.LENGTH_SHORT).show();
                else {
                    homePresenter.notifyViewToShowNotesDialog(tripDTO);
                }
            }

            private AlertDialog showDeleteDialog() {
                AlertDialog deleteTripDialogBox = new AlertDialog.Builder(context)
                        //.setView(R.layout.dialog_delete_trip)
                        //set message, title, and icon
                        .setTitle("Delete Confirmation")
                        .setMessage("Are you sure you want to delete this trip?")
                        .setIcon(R.drawable.ic_error)

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //deleting code
                                boolean online = NetworkUtilities.isOnline(context);
                                if (online) {
                                    AlarmHelper.cancelAlarm(context.getApplicationContext());
                                    homePresenter.deleteTrip(upcomingTripsList.get(i).getTripKey());
                                } else
                                    Toast.makeText(context, "please check your internet connection", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        })


                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })
                        .create();
                return deleteTripDialogBox;
            }

        });

    }

    public void showDirection(TripDTO tripDTO) {
        Intent mapIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?saddr=" + tripDTO.getTripStartPointLatitude() + "," + tripDTO.getTripStartPointLongitude() + "&daddr=" + tripDTO.getTripEndPointLatitude() + "," + tripDTO.getTripEndPointLongitude()));
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mapIntent);
        } else {
            Toast.makeText(context, "Please install a maps application", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return upcomingTripsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tripName;
        TextView tripDate;
        TextView tripTime;
        TextView tripStartPoint;
        TextView tripEndPoint;
        ImageView tripSettings;
        ImageView tripImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            initializeComponents();
        }

        private void initializeComponents() {
            tripName = itemView.findViewById(R.id.tripName);
            tripDate = itemView.findViewById(R.id.tripDate);
            tripTime = itemView.findViewById(R.id.tripTime);
            tripStartPoint = itemView.findViewById(R.id.tripStartPoint);
            tripEndPoint = itemView.findViewById(R.id.tripEndPoint);
            tripSettings = itemView.findViewById(R.id.tripSettings);
            tripImage = itemView.findViewById(R.id.profile_image);
        }

    }

}
