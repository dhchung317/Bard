package com.hyunki.bard.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.hyunki.bard.Animations;
import com.hyunki.bard.R;
import com.hyunki.bard.viewmodel.ViewModel;
import com.hyunki.bard.controller.FragmentInteractionListener;
import com.hyunki.bard.model.Song;

public class MainActivity extends AppCompatActivity implements FragmentInteractionListener {
    private ViewModel viewModel;
    private ImageView splash;
    public static final String SONG_FRAGMENT_KEY = "displaySong";
    public static final String COMPOSE_FRAGMENT_KEY = "composeSong";
    public static final String LIBRARY_FRAGMENT_KEY = "displayLibrary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        splash = findViewById(R.id.mainActivity_splash_imageView);

        Animation drop = Animations.getDropImageAnimation(splash);

        drop.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                splash.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, MainFragment.newInstance())
                        .commit();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        splash.startAnimation(drop);
    }

    @Override
    public void displaySong(Song song) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, SongFragment.newInstance(song))
                .addToBackStack(SONG_FRAGMENT_KEY)
                .commit();
    }

    @Override
    public void displayComposer() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, ComposeFragment.newInstance())
                .addToBackStack(COMPOSE_FRAGMENT_KEY)
                .commit();
    }

    @Override
    public void displayLibrary() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, LibraryFragment.newInstance())
                .addToBackStack(LIBRARY_FRAGMENT_KEY)
                .commit();
    }
}
