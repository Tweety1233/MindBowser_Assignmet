package com.mindbowser.assignmet.recycleradapterview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindbowser.assignmet.R;
import com.mindbowser.assignmet.model.Contacts;

import java.util.ArrayList;
import java.util.List;

public class FavRecylcerViewAdapter extends RecyclerView.Adapter<FavContactHolder> {
    Context context;
    private List<Contacts> contacts = new ArrayList<>();

    public FavRecylcerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FavContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts, parent, false);
        return new FavContactHolder(itemView, context);

    }

    @Override
    public void onBindViewHolder(@NonNull FavContactHolder holder, int position) {
        Contacts alarm = contacts.get(position);
        holder.bind(alarm);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setFavContacts(List<Contacts> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged(); }
}
