package com.mindbowser.assignmet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mindbowser.assignmet.model.Contacts;
import com.mindbowser.assignmet.repository.ContactRepsitory;
import com.mindbowser.assignmet.ui.Constants;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    ContactRepsitory repository;
    private LiveData<List<Contacts>> contacts;
    private LiveData<List<Contacts>> favcontacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepsitory(application);
        contacts = repository.getContacts();
        favcontacts=repository.getFavContacts();
    }

    public LiveData<List<Contacts>> getContacts() {
        return contacts;
    }
    public LiveData<List<Contacts>> getFavContacts() {
        return favcontacts;
    }

    public void insert(Contacts contacts) {
        Constants.log("contactVm", "" + contacts.getName());

        repository.insert(contacts);
    }

    public void delete() {

        repository.delete();
    }

    public void upadteFav(Contacts contacts) {
    repository.updateFav(contacts);}

}
