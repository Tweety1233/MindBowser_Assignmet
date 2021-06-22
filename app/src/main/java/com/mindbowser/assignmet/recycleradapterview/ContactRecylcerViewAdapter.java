package com.mindbowser.assignmet.recycleradapterview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindbowser.assignmet.R;
import com.mindbowser.assignmet.model.Contacts;
import com.mindbowser.assignmet.ui.Constants;

import java.util.ArrayList;
import java.util.List;

public class ContactRecylcerViewAdapter extends RecyclerView.Adapter<ContactHolder>  {
    private List<Contacts> contacts = new ArrayList<>();
    Context context;
    ContactHolder.ContactAdapterListener adapterListener;

    public ContactRecylcerViewAdapter(Context contactScreen, ContactHolder.ContactAdapterListener adapterListener) {
        this.context = contactScreen;
        this.adapterListener=adapterListener;
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts, parent, false);
        return new ContactHolder(itemView, context,adapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contacts alarm = contacts.get(position);
        holder.bind(alarm);
    }

    @Override
    public int getItemCount() {
        Constants.log("rcyl", "" + contacts.size());
        return contacts.size();
    }

    public void setContacts(List<Contacts> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }


}
