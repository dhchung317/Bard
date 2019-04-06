package com.hyunki.bard.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.hyunki.bard.R;
import com.hyunki.bard.viewmodel.ViewModel;
import com.hyunki.bard.controller.FragmentInteractionListener;
import com.hyunki.bard.model.Song;

public class MainActivity extends AppCompatActivity implements FragmentInteractionListener {
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModel(getApplication());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .commit();
    }

    @Override
    public void displaySong(Song song) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, SongFragment.newInstance(song))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void displayComposer() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, ComposeFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void displayLibrary() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, LibraryFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }
}
