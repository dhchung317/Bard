package com.hyunki.bard.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.hyunki.bard.controller.FragmentInteractionListener;
import com.hyunki.bard.model.Note;
import com.hyunki.bard.model.Song;
import com.hyunki.bard.viewmodel.ViewModel;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComposeFragment extends Fragment implements View.OnClickListener {
    private FragmentInteractionListener listener;
    private ViewModel viewModel;
    private Song song;
    @BindView(R.id.song_title_edit_text)
    EditText songTitle;
    @BindView(R.id.syllable_editText)
    EditText syllable;
    @BindView(R.id.duration_editText)
    EditText durationInput;
    @BindView(R.id.current_textView)
    TextView currentNotes;
    @BindView(R.id.notes_spinner)
    Spinner notes;
    @BindView(R.id.addNote_button)
    Button addNotes;
    @BindView(R.id.deleteNote_button)
    Button deleteNotes;
    @BindView(R.id.add_song_button)
    Button addSong;
    @BindView(R.id.compose_to_library_button)
    Button gotoLibrary;

    private int rawId;
    private String noteName;

    public static ComposeFragment newInstance() {
        return new ComposeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            listener = (FragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentInteractionListener");
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
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        getActivity(), R.array.spinner_array, R.layout.support_simple_spinner_dropdown_item);
        notes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rawId = getActivity().getResources().getIdentifier(
                        parent.getItemAtPosition(position).toString().toLowerCase(),
                        "raw",
                        getActivity().getPackageName()
                );

                Log.d("rawid", String.valueOf(rawId));
                noteName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        notes.setAdapter(adapter);
    }

    private void deleteNotes() {
        song.deleteNote();
        String onDeleteCurrentNotesDisplay = null;
        for (Note n : song.getSongNotes()) {
            if (onDeleteCurrentNotesDisplay == null) {
                onDeleteCurrentNotesDisplay = n.getNote();
            } else {
                onDeleteCurrentNotesDisplay += " " + n.getNote();
            }
        }
        currentNotes.setText(onDeleteCurrentNotesDisplay);
    }

    private void addNotes() {
        String durationI = durationInput.getText().toString();

        if(durationI.isEmpty()) {
            durationI = "1000";
            Toast.makeText(getActivity(), "no duration entered. default is 1000 ms", Toast.LENGTH_SHORT).show();
        }
        song.addNote(new Note(
                rawId,
                syllable.getText().toString(),
                Integer.parseInt(durationI),
                noteName)
        );
        currentNotes.append(noteName + " ");
    }

    private void addSong() {
        if (songTitle.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "enter a title!", Toast.LENGTH_SHORT).show();
        } else {
            song.setSongTitle(songTitle.getText().toString());
            viewModel.addSong(song);
            Toast.makeText(getActivity(), "Song Added!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.add_song_button,
            R.id.deleteNote_button,
            R.id.compose_to_library_button,
            R.id.addNote_button}
    )

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_song_button:
                addSong();
                break;
            case R.id.addNote_button:
                addNotes();
                break;
            case R.id.deleteNote_button:
                deleteNotes();
                break;
            case R.id.compose_to_library_button:
                listener.displayLibrary();
                break;
        }
    }
}