package com.mindbowser.assignmet.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mindbowser.assignmet.model.Contacts;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert
    void insert(Contacts contacts);

    @Query("DELETE from contact_table")
    void deleteAll();

    @Query("SELECT * from contact_table")
    LiveData<List<Contacts>> getAllContact();

    @Query("SELECT * from contact_table where favourite=:fav")
    LiveData<List<Contacts>> getFavContact(String fav);

    @Update
    void updateFav(Contacts contacts);
}
