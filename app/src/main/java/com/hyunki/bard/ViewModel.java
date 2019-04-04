package com.hyunki.bard;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ViewModel extends AndroidViewModel {
    private Repository repository;
    LiveData<List<Song>> allSongs;



    public ViewModel(Application application) {
        super(application);
        this.repository = Repository.getRepositoryInstance(application);
        this.allSongs = repository.getSongList();
    }


    public void addSong(Song song) {
        repository.addSong(song);
    }



}
