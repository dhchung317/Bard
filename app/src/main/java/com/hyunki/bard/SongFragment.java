package com.hyunki.bard;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SongFragment extends Fragment {
    ViewModel viewModel;
    TextView displayNotes;
    TextView songTitle;
    Button playButton;
    SongPlayer player;
//    Database database = Database.getInstance(getActivity());


    public static SongFragment newInstance(Song song) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("song", song);
        SongFragment fragment = new SongFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_song, container, false);
        displayNotes = rootview.findViewById(R.id.all_notes_textview);
        songTitle = rootview.findViewById(R.id.songTitle_textView);
        playButton = rootview.findViewById(R.id.play_button);
        TextToSpeech tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });

        player = new SongPlayer(getActivity(), tts);
        return rootview;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String displayNotesString = null;
        Bundle args = getArguments();
        final Song song = args.getParcelable("song");

        for (Note n : song.songNotes) {
            if (displayNotesString == null) {
                displayNotesString = n.note + " ";
            } else {
                displayNotesString += n.note + " ";
            }
        }
        displayNotes.setText(displayNotesString);
        songTitle.setText(song.songTitle);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.playSong(song);
            }
        });

    }
}
