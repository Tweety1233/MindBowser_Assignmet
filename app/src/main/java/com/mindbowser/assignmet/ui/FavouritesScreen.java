package com.mindbowser.assignmet.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.mindbowser.assignmet.databinding.FavouriteScreenBinding;
import com.mindbowser.assignmet.model.Contacts;
import com.mindbowser.assignmet.recycleradapterview.FavContactHolder;
import com.mindbowser.assignmet.recycleradapterview.FavRecylcerViewAdapter;
import com.mindbowser.assignmet.viewmodel.ContactViewModel;

public class FavouritesScreen extends Fragment implements FavContactHolder.OnItemClickListerner {
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
        recylcerViewAdapter = new FavRecylcerViewAdapter(getActivity(), this);
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

    @Override
    public void onItemClick(View view, Contacts contacts, int adapterPosition) {
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



