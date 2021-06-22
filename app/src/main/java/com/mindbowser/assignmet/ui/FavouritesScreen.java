package com.mindbowser.assignmet.ui;

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
import com.mindbowser.assignmet.recycleradapterview.FavRecylcerViewAdapter;
import com.mindbowser.assignmet.viewmodel.ContactViewModel;

public class FavouritesScreen extends Fragment {
    FavouriteScreenBinding binding;
    LinearLayoutManager layoutManager;
    FavRecylcerViewAdapter recylcerViewAdapter;
    ContactViewModel contactViewModel;
    DividerItemDecoration itemDecoration;
    static String tag = "favscreen";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        recylcerViewAdapter = new FavRecylcerViewAdapter(getActivity());
        contactViewModel.getFavContacts().observe(this, contacts -> {
            Constants.log(tag, "favVMObserver" + contacts.size());
            if (contacts != null) {
                recylcerViewAdapter.setFavContacts(contacts);
            }
        });

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
        return view;
    }

}



