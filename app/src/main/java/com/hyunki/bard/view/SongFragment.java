package com.hyunki.bard.view;

import android.content.Context;
import android.media.MediaPlayer;
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
import android.widget.Toast;

import com.hyunki.bard.R;
import com.hyunki.bard.SongPlayer;
import com.hyunki.bard.controller.FragmentInteractionListener;
import com.hyunki.bard.viewmodel.ViewModel;
import com.hyunki.bard.model.Note;
import com.hyunki.bard.model.Song;

public class SongFragment extends Fragment {
    private FragmentInteractionListener listener;
    private ViewModel viewModel;
    private TextView displayNotes;
    private TextView songTitle;
    private Button playButton;
    private Button deleteButton;
    private SongPlayer player;
    private TextToSpeech tts;
    private Button exitButton;

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
        View rootview = inflater.inflate(R.layout.fragment_song, container, false);
        displayNotes = rootview.findViewById(R.id.all_notes_textview);
        songTitle = rootview.findViewById(R.id.songTitle_textView);
        playButton = rootview.findViewById(R.id.play_button);
        deleteButton = rootview.findViewById(R.id.delete_button);
        exitButton = rootview.findViewById(R.id.exit_song_button);
        tts = new TextToSpeech(getActivity(), status -> {});
        player = new SongPlayer(getActivity(), tts);
        return rootview;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String displayNotesString = null;
        Bundle args = getArguments();
        final Song song = args.getParcelable("song");

        for (Note n : song.getSongNotes()) {
            if (displayNotesString == null) {
                displayNotesString = n.getNote() + " ";
            } else {
                displayNotesString += n.getNote() + " ";
            }
        }
        displayNotes.setText(displayNotesString);
        songTitle.setText(song.getSongTitle());
        playButton.setOnClickListener(v -> {
            player.playSong(viewModel.getSong(song));
            while(player.getMp().isPlaying()){
                playButton.setEnabled(false);
            }
            playButton.setEnabled(true);

        });
        exitButton.setOnClickListener(v -> getActivity().onBackPressed());
        deleteButton.setOnClickListener(v -> {
            viewModel.deleteSong(song);
            Toast.makeText(getActivity(), "Song deleted", Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack("displayLibrary",1);
            getFragmentManager().popBackStack("displaySong",0);
            listener.displayLibrary();
        });
    }

    @Override
    public void onDestroy() {
        tts.shutdown();
        player.getMp().release();
        super.onDestroy();
    }
}

