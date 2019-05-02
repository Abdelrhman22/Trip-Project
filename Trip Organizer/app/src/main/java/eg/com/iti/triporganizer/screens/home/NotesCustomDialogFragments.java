package eg.com.iti.triporganizer.screens.home;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.adapter.ShowDetailsAdapter;
import eg.com.iti.triporganizer.model.NoteDTO;

public class NotesCustomDialogFragments extends DialogFragment {
    RecyclerView notesRecyclerView;
    LinearLayoutManager linearLayoutManager;
    ShowDetailsAdapter notesDetailsAdapter;
    ArrayList<NoteDTO> notesList;

    public static NotesCustomDialogFragments newInstance(int title) {
        return new NotesCustomDialogFragments();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notes_dialog, container, false);
        notesRecyclerView = rootView.findViewById(R.id.tripNotesList);
        Button ok = rootView.findViewById(R.id.Done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .remove(NotesCustomDialogFragments.this).commit();
            }
        });
        notesRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this.getActivity());
        notesRecyclerView.setLayoutManager(linearLayoutManager);
        notesDetailsAdapter = new ShowDetailsAdapter(this.getActivity(), notesList);
        notesRecyclerView.setAdapter(notesDetailsAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    public void setNotes(ArrayList<NoteDTO> notes) {
        this.notesList = notes;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

