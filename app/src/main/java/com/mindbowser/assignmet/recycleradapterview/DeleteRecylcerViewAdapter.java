package com.mindbowser.assignmet.recycleradapterview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindbowser.assignmet.R;
import com.mindbowser.assignmet.model.DeleteContact;

import java.util.ArrayList;
import java.util.List;

public class DeleteRecylcerViewAdapter extends RecyclerView.Adapter<DeleteContactHolder> {
    Context context;
    private List<DeleteContact> contacts = new ArrayList<>();
    DeleteContactHolder.RestoreClickListerner adapterListener;

    public DeleteRecylcerViewAdapter(Context context, DeleteContactHolder.RestoreClickListerner adapterListener) {
        this.context = context;
        this.adapterListener = adapterListener;
    }

    @NonNull
    @Override
    public DeleteContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts, parent, false);
        return new DeleteContactHolder(itemView, context, adapterListener);

    }

    @Override
    public void onBindViewHolder(@NonNull DeleteContactHolder holder, int position) {
        DeleteContact alarm = contacts.get(position);
        holder.bind(alarm);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setDeleteContacts(List<DeleteContact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }
}
