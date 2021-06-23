package com.mindbowser.assignmet.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.mindbowser.assignmet.model.DeleteContact;
import com.mindbowser.assignmet.recycleradapterview.ContactHolder;
import com.mindbowser.assignmet.recycleradapterview.ContactRecylcerViewAdapter;
import com.mindbowser.assignmet.repository.SharedRepository;
import com.mindbowser.assignmet.viewmodel.ContactViewModel;

import java.util.ArrayList;

public class ContactScreen extends Fragment implements ContactHolder.ContactAdapterListener {
    ContactScreenBinding contactScreenBinding;
    LinearLayoutManager layoutManager;
    ContactRecylcerViewAdapter recylcerViewAdapter;
    ContactViewModel contactViewModel;
    DividerItemDecoration itemDecoration;
    static String tag = "contactscreen";
    SharedRepository repository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        recylcerViewAdapter = new ContactRecylcerViewAdapter(getActivity(), this);
        repository = new SharedRepository(getActivity());

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
        if (!repository.getUpdate()) {
            getContactsFromDevice();
        }
//        getContactsFromDevice();
        getObserverModel();

        return view;
    }

    private void getObserverModel() {

        contactViewModel.getContacts().observe(getActivity(), contacts -> {
            Constants.log(tag, "contactVMObserver" + contacts.size());
            if (contacts != null) {
                recylcerViewAdapter.setContacts(contacts);
            }
        });
    }


    private void getContactsFromDevice() {
        String contactId = "";
        String displayName = "";
        String url = "";
        ArrayList<Contacts> contactsInfoList = new ArrayList<>();
        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {

                    Contacts contactsInfo = new Contacts();
                    contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    url = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

//                    contactsInfo.setCnt_id(Integer.parseInt(contactId));
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
        repository.setUpdate(true);

    }

    @Override
    public void deleteOnClick(View view, Contacts contacts, int position) {
        Constants.log(tag, "delete" + contacts.getName());
        Constants.log(tag, "delete pos" + position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Contact");
        builder.setMessage("Are you sure you want to delete contact?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            Constants.log(tag, "inside delete");
            DeleteContact deleteContact = new DeleteContact();
            deleteContact.setName(contacts.getName());
            deleteContact.setNumber(contacts.getNumber());
            deleteContact.setUrl(contacts.getUrl());
            deleteContact.setContact_id(contacts.getContact_id());
            deleteContact.setFavourite(contacts.getFavourite());
            deleteContact.setDelete(contacts.getDelete());
            contactViewModel.deleteContact(contacts, deleteContact);
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void favOnClick(View view, Contacts contacts) {
        Constants.log(tag, "fav" + contacts.getName());
        if (contacts.getFavourite().equalsIgnoreCase("no")) {
            contacts.setFavourite("yes");
            contactViewModel.upadteFav(contacts);
        } else if (contacts.getFavourite().equalsIgnoreCase("yes")) {
            contacts.setFavourite("no");
            contactViewModel.upadteFav(contacts);

        }

    }

    @Override
    public void onItemListner(View view, Contacts contacts, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("SMS Or Call");
        builder.setMessage("You can call or send msg");
        builder.setPositiveButton("SMS", (dialogInterface, i) -> {
            sendSMS(contacts.getName(), contacts.getNumber());
        });
        builder.setNegativeButton("Call", (dialogInterface, i) -> {
            sendCall(contacts.getName(), contacts.getNumber());

        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void sendCall(String name, String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    protected void sendSMS(String name, String number) {
        Constants.log("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", number);
        smsIntent.putExtra("sms_body", "Test ");

        try {
            startActivity(smsIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(),
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
}
