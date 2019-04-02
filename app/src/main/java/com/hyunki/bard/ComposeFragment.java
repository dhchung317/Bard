package com.hyunki.bard;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComposeFragment extends Fragment implements View.OnClickListener {
    FragmentInteractionListener listener;
    Song song;
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
    Database database;
    int rawId;

    String noteName;

    public static ComposeFragment newInstance() {
        return new ComposeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        database = Database.getInstance(getActivity());

        View view = inflater.inflate(R.layout.fragment_compose, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        for (Note n : song.songNotes) {
            if (onDeleteCurrentNotesDisplay == null) {
                onDeleteCurrentNotesDisplay = n.note;
            } else {
                onDeleteCurrentNotesDisplay += " " + n.note;
            }
        }
        currentNotes.setText(onDeleteCurrentNotesDisplay);
    }

    private void addNotes() {
        song.addNote(new Note(
                rawId,
                syllable.getText().toString(),
                Integer.parseInt(durationInput.getText().toString()),
                noteName)
        );
        currentNotes.append(noteName + " ");
    }

    private void addSong() {
        if (songTitle == null) {
            Toast.makeText(getActivity(), "enter a title!", Toast.LENGTH_SHORT).show();
        } else {
            song.setSongTitle(songTitle.getText().toString());
            database.addSong(song);
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
