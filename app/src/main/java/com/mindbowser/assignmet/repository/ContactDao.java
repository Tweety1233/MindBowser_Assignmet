package com.mindbowser.assignmet.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mindbowser.assignmet.model.Contacts;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contacts contacts);

    @Query("DELETE from contact_table")
    void deleteAll();

    @Query("SELECT * from contact_table ")
    LiveData<List<Contacts>> getAllContact();

    @Query("SELECT * from contact_table where favourite=:fav")
    LiveData<List<Contacts>> getFavContact(String fav);

    @Query("SELECT * from contact_table where `delete`=:del")
    LiveData<List<Contacts>> getDeleteContact(String del);

    @Update
    void updateFav(Contacts contacts);

    @Delete
    void deleteContact(Contacts contacts);
}
