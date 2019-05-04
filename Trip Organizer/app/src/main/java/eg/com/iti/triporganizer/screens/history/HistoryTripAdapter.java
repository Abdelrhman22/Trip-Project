package eg.com.iti.triporganizer.screens.history;

/* HistoryTripAdapter will be used to fill Recyclerview with All past Trip Details
   which Contains
    _____________________________
   |             |        |  .  |
   |   Image     |  From  |  .  |
   |_____________|________|  .  |
   |name of Note |  TO    |  .  |
   |_____________|________|_____|
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.model.TripDTO;

public class HistoryTripAdapter extends RecyclerView.Adapter<HistoryTripAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<TripDTO> historyTripsList;
    HistoryContract.HistoryPresenter historyPresenter;
    private PopupMenu popup;

    public HistoryTripAdapter(Context context, HistoryContract.HistoryPresenter historyPresenter, ArrayList<TripDTO> historyTripsList) {
        this.context = context;
        this.historyTripsList = historyTripsList;
        this.historyPresenter = historyPresenter;
    }

    @NonNull
    @Override
    public HistoryTripAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HistoryTripAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.trip_custom_list_row, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        TripDTO tripDTO = historyTripsList.get(i);
        String tripDate = tripDTO.getDayOfMonth() + " / " + tripDTO.getMonth() + " / " + tripDTO.getYear();
        String tripTime = tripDTO.getHourOfDay() + " : " + tripDTO.getMinute();
        myViewHolder.tripName.setText(historyTripsList.get(i).getName());
        myViewHolder.tripStartPoint.setText(historyTripsList.get(i).getTripStartPoint());
        myViewHolder.tripEndPoint.setText(historyTripsList.get(i).getTripEndPoint());
        myViewHolder.tripDate.setText(tripDate);
        myViewHolder.tripTime.setText(tripTime);
        //set image
        String url="https://maps.googleapis.com/maps/api/staticmap?center=Berkeley,CA&zoom=14&size=400x400&";
        url+="&key="+ context.getString(R.string.google_api_key);
        String photo_url_str ="https://maps.googleapis.com/maps/api/staticmap?";
        photo_url_str+="&zoom=15";
        photo_url_str+="&size=150x150";
        photo_url_str+="&maptype=roadmap";
        photo_url_str+="&markers=color:blue%7Clabel:G%7C"+historyTripsList.get(i).getTripEndPointLatitude()+", "+historyTripsList.get(i).getTripEndPointLongitude();
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
                inflater.inflate(R.menu.edit_oldtrip_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.viewNotes:
                                viewNotes(historyTripsList.get(i));
                                return true;

                            default:
                                return false;
                        }
                    }
                });
            }
        });

    }
    private void viewNotes(TripDTO tripDTO) {
        if(tripDTO.getNotes()==null)
            Toast.makeText(context, "This trip has no notes", Toast.LENGTH_SHORT).show();
        else {
            historyPresenter.notifyViewToShowNotesDialog(tripDTO);
        }
    }

    @Override
    public int getItemCount() {
        return historyTripsList.size();
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
