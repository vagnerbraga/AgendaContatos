package com.vagner.agendacontato.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.vagner.agendacontato.model.Contact;
import com.vagner.agendacontato.model.ContatctRepository;

public class CreateEditViewModel extends AndroidViewModel {

    private ContatctRepository repository;
    public CreateEditViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContatctRepository(application);
    }

    public void insert(Contact contact){
        this.repository.insert(contact);
    }
}
