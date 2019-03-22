package com.vagner.agendacontato.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.vagner.agendacontato.database.AppDatabase;

import java.util.Arrays;
import java.util.List;

public class ContatctRepository {

    private ContactDao dao;

    public ContatctRepository(Application application){
        this.dao = AppDatabase.getAppDatabase(application).contactDao();
    }

    public LiveData<List<Contact>> all(){
        return this.dao.all();
    }

    public LiveData<Contact> find(long id){
        return this.dao.find(id);
    }
    public void insert(Contact contact){
        new InsertAsyncTask(this.dao).execute(Arrays.asList(contact));
    }

    private static class InsertAsyncTask extends AsyncTask<List<Contact>, Void, Void>{

        private ContactDao dao;

        public InsertAsyncTask(ContactDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(List<Contact>... clients) {
            this.dao.insert(clients[0]);
            return null;
        }
    }

}
