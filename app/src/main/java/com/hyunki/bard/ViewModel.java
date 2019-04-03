package com.hyunki.bard;

import android.app.Application;

public class ViewModel extends androidx.lifecycle.ViewModel {
    private Repository repository;

    public ViewModel(Application application) {
        repository = Repository.getRepositoryInstance(application);
    }
}
