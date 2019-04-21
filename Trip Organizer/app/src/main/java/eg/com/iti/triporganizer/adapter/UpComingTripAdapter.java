package eg.com.iti.triporganizer.adapter;

/* UpComingTripAdapter will be used to fill Recyclerview with All past Trip Details
   which Contains
    _____________________________
   |             |        |  .  |
   |   Image     |  From  |  .  |
   |_____________|________|  .  |
   |name of Note |  TO    |  .  |
   |_____________|________|_____|
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class UpComingTripAdapter extends RecyclerView.Adapter<UpComingTripAdapter.MyViewHolder>
{

    @NonNull
    @Override
    public UpComingTripAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingTripAdapter.MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
