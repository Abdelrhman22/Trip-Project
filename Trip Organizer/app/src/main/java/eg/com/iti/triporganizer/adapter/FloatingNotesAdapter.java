package eg.com.iti.triporganizer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.ArrayList;
import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.model.NoteDTO;

public class FloatingNotesAdapter extends RecyclerView.Adapter<FloatingNotesAdapter.NotesViewHolder>
{
        private ArrayList<NoteDTO> noteDTOArrayList;

        public ArrayList<NoteDTO> getTripNotes()
        {
            return noteDTOArrayList;
        }

        public FloatingNotesAdapter(ArrayList<NoteDTO> noteDTOArrayList) {
            this.noteDTOArrayList = noteDTOArrayList;
        }

        @Override
        public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.floating_note_row, parent, false);
            return new NotesViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NotesViewHolder holder, int position)
        {
            holder.noteName.setText(noteDTOArrayList.get(position).getBody());
            holder.noteStatus.setChecked(noteDTOArrayList.get(position).isDone());
            if (noteDTOArrayList.get(position).isDone())
            {
                holder.noteStatus.setChecked(true);
            }
            holder.noteStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    noteDTOArrayList.get(position).setDone(isChecked);
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return noteDTOArrayList.size();
        }

        class NotesViewHolder extends RecyclerView.ViewHolder
        {
            CheckBox noteStatus;
            TextView noteName;
            NotesViewHolder(View itemView) {
                super(itemView);
                noteStatus = itemView.findViewById(R.id.noteStatus);
                noteName = itemView.findViewById(R.id.noteName);
            }
        }
}