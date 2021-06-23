package com.mindbowser.assignmet.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mindbowser.assignmet.model.DeleteContact;

import java.util.List;

@Dao
public interface DeleteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DeleteContact contacts);

    @Query("SELECT * from delete_contact")
    LiveData<List<DeleteContact>> getDeleteContact();

    @Delete
    void delete(DeleteContact contacts);
}
