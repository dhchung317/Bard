package com.hyunki.bard;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class Repository {

    private final Database database;
    private static Repository repositoryInstance;
    MutableLiveData<List<Song>> songList;

    public static Repository getRepositoryInstance(Application application){
        if(repositoryInstance == null){
            repositoryInstance = new Repository(application);
        }
        return repositoryInstance;
    }

    private  Repository (Application application){
        database = Database.getInstance(application);
        songList.setValue(database.getAllSongs());
    }




}
