package com.hyunki.bard.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hyunki.bard.R;
import com.hyunki.bard.controller.ClickableNoteListener;
import com.hyunki.bard.controller.FragmentInteractionListener;
import com.hyunki.bard.controller.NotesAdapter;
import com.hyunki.bard.model.ClickableNote;
import com.hyunki.bard.model.Note;
import com.hyunki.bard.model.Song;
import com.hyunki.bard.viewmodel.ViewModel;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComposeFragment extends Fragment implements View.OnClickListener {
    private FragmentInteractionListener listener;
    private ViewModel viewModel;
    private Song song;
    private int rawId;
    private String noteName;
    private String defaultDuration;
    private NotesAdapter adapter;
    private ArrayList<ClickableNote> clickableNotes = new ArrayList<>();

    @BindView(R.id.composeFragment_songTitle_editText)
    EditText songTitle;
    @BindView(R.id.composeFragment_syllable_editText)
    EditText syllable;
    @BindView(R.id.composeFragment_duration_editText)
    EditText durationInput;
    @BindView(R.id.composeFragment_displayCurrentNotes_textView)
    TextView currentNotes;
//    @BindView(R.id.composeFragment_notes_spinner)
//    Spinner notes;
    @BindView(R.id.compose_recyclerview)
    RecyclerView noteRecycler;
    @BindView(R.id.composeFragment_addNote_button)
    Button addNotes;
    @BindView(R.id.composeFragment_deleteNote_button)
    Button deleteNotes;
    @BindView(R.id.composeFragment_addSong_button)
    Button addSong;
    @BindView(R.id.composeFragment_library_button)
    Button gotoLibrary;

    public static ComposeFragment newInstance() {
        return new ComposeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultDuration = "1000";
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener){
            listener = (FragmentInteractionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        song = new Song();
        adapter = new NotesAdapter(clickableNotes);
        noteRecycler.setAdapter(adapter);
        noteRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        List<String> noteRes = Arrays.asList(getResources().getStringArray(R.array.spinner_array));
        for (int i = 0; i < noteRes.size(); i++) {
            clickableNotes.add(new ClickableNote(
                  noteRes.get(i),
                    getActivity()
                            .getResources()
                            .getIdentifier(
                                    noteRes.get(i).toLowerCase(),
                                    "raw",
                                    getActivity().getPackageName()
                            ),
                    getActivity()
                            .getResources()
                            .getIdentifier(
                                    "alto_" + noteRes.get(i).toLowerCase(),
                                    "drawable",
                                    getContext().getPackageName())

            ));
        }
        adapter.setNotesList(clickableNotes);
//        ArrayAdapter<CharSequence> adapter =
//                ArrayAdapter.createFromResource(
//                        getActivity(), R.array.spinner_array, R.layout.support_simple_spinner_dropdown_item);
//        notes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                rawId = getActivity().getResources().getIdentifier(
//                        parent.getItemAtPosition(position).toString().toLowerCase(),
//                        "raw",
//                        getActivity().getPackageName()
//                );
//                noteName = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        notes.setAdapter(adapter);
    }

    private void deleteNotes() {
        if (song.getSongNotes().size() > 0) {
            song.deleteNote();
        }

        String onDeleteCurrentNotesDisplay = "";
        for (Note n : song.getSongNotes()) {
            if (currentNotes.getText().toString().isEmpty()) {
                onDeleteCurrentNotesDisplay = n.getNote() + " ";
            } else {
                onDeleteCurrentNotesDisplay += n.getNote() + " ";
            }
        }
        currentNotes.setText(onDeleteCurrentNotesDisplay);
    }

    private void addNotes() {
        ClickableNote currentNote = viewModel.getCurrentNote();
        rawId = currentNote.getRawNote();
        noteName = currentNote.getNote();

        String durationI = defaultDuration;

        if (durationInput.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.no_duration_entered_message), Toast.LENGTH_SHORT).show();
        }else{
            durationI = durationInput.getText().toString();
        }
        song.addNote(new Note(
                rawId,
                syllable.getText().toString(),
                Integer.parseInt(durationI),
                noteName)
        );
        currentNotes.append(String.format("%s ", noteName));
    }

    private void addSong() {
        if (songTitle.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.no_title_entered_message), Toast.LENGTH_SHORT).show();
        } else if (!viewModel.getSong(songTitle.getText().toString()).getSongTitle().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.title_exists_message), Toast.LENGTH_SHORT).show();
        } else {
            song.setSongTitle(songTitle.getText().toString());
            viewModel.addSong(song);
            Toast.makeText(getActivity(), getString(R.string.song_added_message), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.composeFragment_addSong_button,
            R.id.composeFragment_deleteNote_button,
            R.id.composeFragment_library_button,
            R.id.composeFragment_addNote_button}
    )

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.composeFragment_addSong_button:
                addSong();
                break;
            case R.id.composeFragment_addNote_button:
                addNotes();
                break;
            case R.id.composeFragment_deleteNote_button:
                deleteNotes();
                break;
            case R.id.composeFragment_library_button:
                listener.displayLibrary();
                break;
        }
    }

//    @Override
//    public void setCurrentNote(String note, int rawNote) {
//        rawId = rawNote;
//        noteName = note;
//    }
}
