package com.vagner.agendacontato.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Contact> client);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contact client);

    @Query("SELECT * from contacts")
    LiveData<List<Contact>> all();

    @Query("SELECT * from contacts where id = :id")
    LiveData<Contact> find(long id);

}
