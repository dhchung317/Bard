package com.hyunki.bard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Database database = Database.getInstance(getApplicationContext());
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
