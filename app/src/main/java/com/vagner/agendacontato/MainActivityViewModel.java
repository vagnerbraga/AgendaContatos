package com.vagner.agendacontato;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.vagner.agendacontato.model.Contact;
import com.vagner.agendacontato.model.ContatctRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private ContatctRepository repository;
    public LiveData<List<Contact>> listLiveData;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContatctRepository(application);

        this.listLiveData = Transformations.map(this.repository.all(), list -> list);
    }
}
