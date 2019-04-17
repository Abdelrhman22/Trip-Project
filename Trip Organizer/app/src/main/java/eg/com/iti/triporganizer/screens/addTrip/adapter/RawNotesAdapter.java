package eg.com.iti.triporganizer.screens.addTrip.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.model.NoteDTO;

/*RawNotesAdapter will be used after you have added note you will see
   -------------------------------
   | Note Name      | Delete Image|
   -------------------------------
   if you want to delete this note
 */
//expandable recyclerview  in which we add notes
public class RawNotesAdapter extends RecyclerView.Adapter<RawNotesAdapter.RawNoteHolder> {

    private List<NoteDTO> mNotes;

    public List<NoteDTO> getNotes() {
        return mNotes;
    }

    public RawNotesAdapter(List<NoteDTO> tripNotes) {
        mNotes = tripNotes;
    }

    @Override
    public RawNoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_note_raw, parent, false);
        return new RawNoteHolder(v);
    }

    @Override
    public void onBindViewHolder(RawNoteHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    class RawNoteHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView button;

        RawNoteHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.note_content);
            button = v.findViewById(R.id.btn_delete);
        }

        void bind(final int index) {
            textView.setText(mNotes.get(index).getBody());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNotes.remove(index);
                    notifyDataSetChanged();
                }
            });
        }
    }

}