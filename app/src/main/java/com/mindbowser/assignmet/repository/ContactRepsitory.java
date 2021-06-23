package com.mindbowser.assignmet.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mindbowser.assignmet.model.Contacts;
import com.mindbowser.assignmet.ui.Constants;

import java.util.List;

public class ContactRepsitory {
    private ContactDao contactDao;
    private LiveData<List<Contacts>> contactLiveData;
    private LiveData<List<Contacts>> contactFavLiveData;
    private LiveData<List<Contacts>> contactDeleteLiveData;

    public ContactRepsitory(Application application) {
        ContactDatabase db = ContactDatabase.getDatabase(application);
        contactDao = db.contactDao();
        contactLiveData = contactDao.getAllContact();
        contactFavLiveData = contactDao.getFavContact("yes");
        contactDeleteLiveData = contactDao.getDeleteContact("yes");
        Constants.log("contactRepository", "" + contactDao.getAllContact());

    }

    public LiveData<List<Contacts>> getContacts() {
        return contactLiveData;
    }


    public void insert(Contacts contacts) {
        Constants.log("contactRepository", "" + contacts.getName());

        ContactDatabase.databaseWriteExecutor.execute(() ->
                contactDao.insert(contacts));
    }

    public void delete() {

        ContactDatabase.databaseWriteExecutor.execute(() ->
                contactDao.deleteAll());
    }

    public void updateFav(Contacts contacts) {
        ContactDatabase.databaseWriteExecutor.execute(() ->
                contactDao.updateFav(contacts));
    }


    public LiveData<List<Contacts>> getFavContacts() {
        return contactFavLiveData;
    }

    public LiveData<List<Contacts>> getDeleteContacts() {
        return contactDeleteLiveData;
    }

    public void deleteContact(Contacts contacts) {
        ContactDatabase.databaseWriteExecutor.execute(() ->
                contactDao.deleteContact(contacts));
    }
}


