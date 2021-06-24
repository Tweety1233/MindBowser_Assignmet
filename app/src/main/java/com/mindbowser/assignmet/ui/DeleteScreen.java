package com.mindbowser.assignmet.ui;

import android.app.AlertDialog;
import android.os.Bundle;
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
import com.mindbowser.assignmet.databinding.FavouriteScreenBinding;
import com.mindbowser.assignmet.model.Contacts;
import com.mindbowser.assignmet.model.DeleteContact;
import com.mindbowser.assignmet.recycleradapterview.DeleteContactHolder;
import com.mindbowser.assignmet.recycleradapterview.DeleteRecylcerViewAdapter;
import com.mindbowser.assignmet.viewmodel.ContactViewModel;

public class DeleteScreen extends Fragment implements DeleteContactHolder.RestoreClickListerner {
    FavouriteScreenBinding binding;
    LinearLayoutManager layoutManager;
    DeleteRecylcerViewAdapter recylcerViewAdapter;
    ContactViewModel contactViewModel;
    DividerItemDecoration itemDecoration;
    static String tag = "delscreen";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        recylcerViewAdapter = new DeleteRecylcerViewAdapter(getActivity(), this);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.favourite_screen, container, false);
        View view = binding.getRoot();
        layoutManager = new LinearLayoutManager(getActivity());
        binding.favRecyclerview.setLayoutManager(layoutManager);
        binding.favRecyclerview.setAdapter(recylcerViewAdapter);
        itemDecoration = new DividerItemDecoration(binding.favRecyclerview.getContext(), layoutManager.getOrientation());
        binding.favRecyclerview.addItemDecoration(itemDecoration);
        binding.text.setText("Delete Screen");
        getObserverModel();
        return view;
    }

    private void getObserverModel() {
        contactViewModel.getDeleteContacts().observe(getActivity(), deleteContacts ->
        {
            Constants.log(tag, "deletcontct-" + deleteContacts.size());
            recylcerViewAdapter.setDeleteContacts(deleteContacts);
        });
    }

    @Override
    public void restoreClick(View view, DeleteContact deleteContact, int adapterPosition) {
        Constants.log(tag, "delete" + deleteContact.getName());
        Constants.log(tag, "delete pos" + adapterPosition);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Contact");
        builder.setMessage("Are you sure you want to restore contact?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            Constants.log(tag, "inside delete");
            Contacts contact = new Contacts();
            contact.setName(deleteContact.getName());
            contact.setNumber(deleteContact.getNumber());
            contact.setUrl(deleteContact.getUrl());
            contact.setContact_id(deleteContact.getContact_id());
            contact.setFavourite(deleteContact.getFavourite());
            contact.setDeleted(deleteContact.getDelete());
            contactViewModel.insertDeleteAgain(deleteContact, contact);
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {

        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
