package com.mindbowser.assignmet.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mindbowser.assignmet.model.Contacts;
import com.mindbowser.assignmet.model.DeleteContact;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Contacts.class, DeleteContact.class}, version = 1, exportSchema = false)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract DeleteDao deleteDao();

    private static volatile ContactDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ContactDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ContactDatabase.class,
                            "contact_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
