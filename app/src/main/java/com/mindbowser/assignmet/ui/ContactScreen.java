package com.mindbowser.assignmet.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mindbowser.assignmet.R;
import com.mindbowser.assignmet.databinding.ContactScreenBinding;
import com.mindbowser.assignmet.model.Contacts;
import com.mindbowser.assignmet.recycleradapterview.ContactHolder;
import com.mindbowser.assignmet.recycleradapterview.ContactRecylcerViewAdapter;
import com.mindbowser.assignmet.viewmodel.ContactViewModel;

import java.util.ArrayList;

public class ContactScreen extends Fragment implements ContactHolder.ContactAdapterListener {
    ContactScreenBinding contactScreenBinding;
    LinearLayoutManager layoutManager;
    ContactRecylcerViewAdapter recylcerViewAdapter;
    ContactViewModel contactViewModel;
    DividerItemDecoration itemDecoration;
    static String tag = "contactscreen";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        recylcerViewAdapter = new ContactRecylcerViewAdapter(getActivity(), this);
        contactViewModel.getContacts().observe(this, contacts -> {
            Constants.log(tag, "contactVMObserver" + contacts.size());
            if (contacts != null) {
                recylcerViewAdapter.setContacts(contacts);
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contactScreenBinding = DataBindingUtil.inflate(inflater, R.layout.contact_screen, container, false);
        View view = contactScreenBinding.getRoot();
        layoutManager = new LinearLayoutManager(getActivity());
        contactScreenBinding.contactRecyclerview.setLayoutManager(layoutManager);
        contactScreenBinding.contactRecyclerview.setAdapter(recylcerViewAdapter);
        itemDecoration = new DividerItemDecoration(contactScreenBinding.contactRecyclerview.getContext(), layoutManager.getOrientation());
        contactScreenBinding.contactRecyclerview.addItemDecoration(itemDecoration);
        getContactsFromDevice();
        return view;
    }


    private void getContactsFromDevice() {

        String contactId = null;
        String displayName = null;
        String url = null;
        ArrayList<Contacts> contactsInfoList = new ArrayList<Contacts>();
        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {

                    Contacts contactsInfo = new Contacts();
                    contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    url = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                    //   contactsInfo.setContactId(contactId);
                    contactsInfo.setName(displayName);
                    contactsInfo.setUrl(url);
                    contactsInfo.setFavourite("no");
                    contactsInfo.setDelete("no");

                    Cursor phoneCursor = getActivity().getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactId},
                            null);

                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Constants.log("contactscreen", phoneNumber);

                        contactsInfo.setNumber(phoneNumber);
                    }
                    Constants.log("contactscreen", contactId + displayName + url);
                    phoneCursor.close();

                    contactsInfoList.add(contactsInfo);
                    contactViewModel.insert(contactsInfo);

                }
            }

        }
        cursor.close();

    }

    @Override
    public void deleteOnClick(View view, Contacts contacts) {
        Constants.log(tag, "delete" + contacts.getName());


    }

    @Override
    public void favOnClick(View view, Contacts contacts) {
        Constants.log(tag, "fav" + contacts.getName());
        contacts.setFavourite("yes");
        contactViewModel.upadteFav(contacts);

    }
}
