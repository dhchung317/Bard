package com.hyunki.bard.view;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

import java.util.Objects;

public class SongFragment extends Fragment implements View.OnClickListener {
    private FragmentInteractionListener listener;
    private ViewModel viewModel;
    private TextToSpeech tts;
    private SongPlayer player;
    private Song song;
    private static final String SONG_FRAGMENT_BUNDLE_KEY = "song";

    @BindView(R.id.songFragment_displayNotes_textview)
    TextView displayNotes;
    @BindView(R.id.songFragment_songTitle_textview)
    TextView songTitle;
    @BindView(R.id.songFragment_play_button)
    Button playButton;
    @BindView(R.id.songFragment_exit_button)
    Button deleteButton;
    @BindView(R.id.songFragment_delete_button)
    Button exitButton;


    public static SongFragment newInstance(Song song) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SONG_FRAGMENT_BUNDLE_KEY, song);
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
        tts = new TextToSpeech(getActivity(), status -> {});
        player = new SongPlayer(getActivity(), tts);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        String displayNotesString = null;
        Bundle args = getArguments();

        assert args != null;
        song = args.getParcelable(SONG_FRAGMENT_BUNDLE_KEY);

        assert song != null;
        for (Note n : song.getSongNotes()) {
            if (displayNotesString == null) {
                displayNotesString = n.getNote() + " ";
            } else {
                displayNotesString += n.getNote() + " ";
            }
        }

        displayNotes.setText(displayNotesString);
        songTitle.setText(song.getSongTitle());
    }

    private void deleteSong(Song song) {
        viewModel.deleteSong(song);
        Toast.makeText(getActivity(), getString(R.string.song_deleted_message), Toast.LENGTH_SHORT).show();
        assert getFragmentManager() != null;
        getFragmentManager().popBackStack(MainActivity.LIBRARY_FRAGMENT_KEY,1);
        getFragmentManager().popBackStack(MainActivity.SONG_FRAGMENT_KEY,0);
        listener.displayLibrary();
    }

    private void exitSongFragment() {
        Objects.requireNonNull(getActivity()).onBackPressed();
    }

    private void playSong(Song song) {

        player.playSong(viewModel.getSong(song.getSongTitle()));
        if(player.getMp() != null) {
            while (player.getMp().isPlaying()) {
                playButton.setEnabled(false);
            }
        }
        playButton.setEnabled(true);
    }

    @OnClick({R.id.songFragment_play_button,
            R.id.songFragment_delete_button,
            R.id.songFragment_exit_button})

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.songFragment_play_button:
                playSong(song);
                break;
            case R.id.songFragment_delete_button:
                deleteSong(song);
                break;
            case R.id.songFragment_exit_button:
                exitSongFragment();
                break;
        }
    }

    @Override
    public void onDestroy() {
        tts.shutdown();
        if(player.getMp() != null) {
            player.getMp().release();
        }
        super.onDestroy();
    }
}

