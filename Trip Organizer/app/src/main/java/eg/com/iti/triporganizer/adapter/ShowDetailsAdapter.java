package eg.com.iti.triporganizer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.model.NoteDTO;
import eg.com.iti.triporganizer.screens.home.UpComingTripAdapter;

/* ShowDetailsAdapter will help me to know state of notes of specific OLD Trip
 [Note1 Specific Trip]  √
 [Note2 Specific Trip]  ✖
 [Note3 Specific Trip]  √
 */
//beside each trip in the history (...)
public class ShowDetailsAdapter extends RecyclerView.Adapter<ShowDetailsAdapter.NotesViewHolder>
{
    private Context context;
    private ArrayList<NoteDTO> notes;

    public ShowDetailsAdapter(Context context, ArrayList<NoteDTO> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public ShowDetailsAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ShowDetailsAdapter.NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.note_details, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShowDetailsAdapter.NotesViewHolder notesViewHolder, int i) {
        notesViewHolder.note.setText(notes.get(i).getBody());
        notesViewHolder.note.setChecked(notes.get(i).isDone());
        notesViewHolder.note.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {
        CheckBox note;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            note=itemView.findViewById(R.id.noteDetails);
        }
    }
}
