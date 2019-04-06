package com.hyunki.bard.controller;

import com.hyunki.bard.model.Song;

public interface FragmentInteractionListener {
    void displaySong(Song song);

    void displayComposer();

    void displayLibrary();
}
