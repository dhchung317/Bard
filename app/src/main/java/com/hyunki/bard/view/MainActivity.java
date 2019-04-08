package com.hyunki.bard.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

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
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .commit();
    }

    @Override
    public void displaySong(Song song) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, SongFragment.newInstance(song))
                .addToBackStack("displaySong")
                .commit();
    }

    @Override
    public void displayComposer() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, ComposeFragment.newInstance())
                .addToBackStack("composeSong")
                .commit();
    }

    @Override
    public void displayLibrary() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, LibraryFragment.newInstance())
                .addToBackStack("displayLibrary")
                .commit();
    }
}
