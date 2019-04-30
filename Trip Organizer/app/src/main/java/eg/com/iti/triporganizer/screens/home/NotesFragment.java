package eg.com.iti.triporganizer.screens.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.adapter.ShowDetailsAdapter;
import eg.com.iti.triporganizer.model.NoteDTO;

@SuppressLint("ValidFragment")
public class NotesFragment extends DialogFragment {
    RecyclerView notesRecyclerView;
    LinearLayoutManager linearLayoutManager;
    ShowDetailsAdapter notesDetailsAdapter;
    ArrayList<NoteDTO> notesList;

    @SuppressLint("ValidFragment")
    public NotesFragment(ArrayList<NoteDTO> notesList) {
        this.notesList = notesList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notes_dialog, container);
        notesRecyclerView = rootView.findViewById(R.id.tripNotesList);
        notesRecyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this.getActivity());
        notesRecyclerView.setLayoutManager(linearLayoutManager);
        notesDetailsAdapter = new ShowDetailsAdapter(this.getActivity(), notesList);
        notesRecyclerView.setAdapter(notesDetailsAdapter);
        //Log.i("notes","size"+notesList.size());
        this.getDialog().setTitle("Notes");
        return rootView;
    }
}
