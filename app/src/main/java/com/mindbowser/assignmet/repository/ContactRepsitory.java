package com.mindbowser.assignmet.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mindbowser.assignmet.model.Contacts;
import com.mindbowser.assignmet.model.DeleteContact;
import com.mindbowser.assignmet.ui.Constants;

import java.util.List;

public class ContactRepsitory {
    private ContactDao contactDao;
    private DeleteDao deleteDao;
    private LiveData<List<Contacts>> contactLiveData;
    private LiveData<List<Contacts>> contactFavLiveData;
    private LiveData<List<DeleteContact>> contactDeleteLiveData;

    public ContactRepsitory(Application application) {
        ContactDatabase db = ContactDatabase.getDatabase(application);
        contactDao = db.contactDao();
        deleteDao = db.deleteDao();
        contactLiveData = contactDao.getAllContact();
        contactFavLiveData = contactDao.getFavContact("yes");
        contactDeleteLiveData = deleteDao.getDeleteContact();
        Constants.log("contactRepository", "" + contactDao.getAllContact());
        Constants.log("contactRepository delete", "" + deleteDao.getDeleteContact());

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

    public LiveData<List<DeleteContact>> getDeleteContacts() {
        return contactDeleteLiveData;
    }

    public void deleteContact(Contacts contacts) {
        Constants.log("repository", "inseter delete" + contacts.getName());

        ContactDatabase.databaseWriteExecutor.execute(() ->
                contactDao.deleteContact(contacts));
        Constants.log("repository", "remove contct" + contacts.getName());

    }

    public void insertDelete(DeleteContact contacts) {
        ContactDatabase.databaseWriteExecutor.execute(() ->
                deleteDao.insert(contacts));
    }

    public void deleteAgain(DeleteContact contacts) {
        ContactDatabase.databaseWriteExecutor.execute(() -> deleteDao.delete(contacts));
    }
}


