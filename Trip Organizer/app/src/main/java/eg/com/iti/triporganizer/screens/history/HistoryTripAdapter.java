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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.model.TripDTO;

public class HistoryTripAdapter extends RecyclerView.Adapter<HistoryTripAdapter.MyViewHolder>
{ private Context context;
    private ArrayList<TripDTO> historyTripsList;
    HistoryContract.HistoryPresenter historyPresenter;

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
        TripDTO tripDTO=historyTripsList.get(i);
        String tripDate = tripDTO.getDayOfMonth() + " / " + tripDTO.getMonth() + " / " + tripDTO.getYear();
        String tripTime = tripDTO.getHourOfDay() + " : " + tripDTO.getMinute();
        myViewHolder.tripName.setText(historyTripsList.get(i).getName());
        myViewHolder.tripStartPoint.setText(historyTripsList.get(i).getTripStartPoint());
        myViewHolder.tripEndPoint.setText(historyTripsList.get(i).getTripEndPoint());
        myViewHolder.tripDate.setText(tripDate);
        myViewHolder.tripTime.setText(tripTime);
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