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

import java.util.ArrayList;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.model.TripDTO;
import eg.com.iti.triporganizer.model.TripTimeAndDateDTO;
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
                                Toast.makeText(context, "view notes", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.deleteTrip: {
                                AlertDialog deleteDialog = showDeleteDialog();
                                deleteDialog.show();
                                return true;
                            }
                            case R.id.startTrip: {
                                showDirection(tripDTO);
                                Log.i("aaaaa",""+upcomingTripsList.get(i).getRepeated());
                                return true;
                            }
                            default:
                                return false;
                        }
                    }


                });
            }

            private void editTrip(TripDTO tripDTO) {
                homePresenter.editTrip(tripDTO);
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
                                if (online)
                                    homePresenter.deleteTrip(upcomingTripsList.get(i).getTripKey());
                                else
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

    private void showDirection(TripDTO tripDTO)
    {
        Intent mapIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?saddr=" + 29.392691 + "," + 30.828360 + "&daddr=" + 30.044420 + "," + 31.235712));
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
        }

    }

}
