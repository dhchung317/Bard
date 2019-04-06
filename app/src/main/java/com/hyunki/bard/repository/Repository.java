package com.hyunki.bard.repository;

import android.app.Application;

import com.hyunki.bard.database.Database;
import com.hyunki.bard.model.Song;

import java.util.List;

import androidx.lifecycle.LiveData;

public class Repository {
    private final Database database;
    private static Repository repositoryInstance;

    public static Repository getRepositoryInstance(Application application){
        if(repositoryInstance == null){
            repositoryInstance = new Repository(application);
        }
        return repositoryInstance;
    }

    private  Repository (Application application){
        database = new Database(application);
    }

    public void addSong(Song song){
        database.addSong(song);
    }

    public LiveData<List<Song>> getSongList(){
        return database.getAllSongs();
    }
}
