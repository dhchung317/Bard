package com.hyunki.bard;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Repository {

    private final Database database;
    private static Repository repositoryInstance;
//    LiveData<List<Song>> songList;

    public static Repository getRepositoryInstance(Application application){
        if(repositoryInstance == null){
            repositoryInstance = new Repository(application);
        }
        return repositoryInstance;
    }

    private  Repository (Application application){
//        database = Database.getInstance(application);
        database = new Database(application);
//        this.songList = database.getAllSongs();
    }


//    public void setSongList(){
//        songList.setValue(database.getAllSongs());
//    }

    public void addSong(Song song){
        database.addSong(song);
    }

    public LiveData<List<Song>> getSongList(){
        return database.getAllSongs();
    }


}
