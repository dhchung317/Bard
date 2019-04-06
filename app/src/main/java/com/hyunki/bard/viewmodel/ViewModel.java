package com.hyunki.bard.viewmodel;

import android.app.Application;

import com.hyunki.bard.model.Song;
import com.hyunki.bard.repository.Repository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ViewModel extends AndroidViewModel {
    private Repository repository;

    public LiveData<List<Song>> getAllSongs() {
        return allSongs;
    }

    private LiveData<List<Song>> allSongs;

    public ViewModel(Application application) {
        super(application);
        this.repository = Repository.getRepositoryInstance(application);
        this.allSongs = repository.getSongList();
    }

    public void addSong(Song song) {
        repository.addSong(song);
    }
}