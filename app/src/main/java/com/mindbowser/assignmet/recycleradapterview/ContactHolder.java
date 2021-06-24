package com.mindbowser.assignmet.recycleradapterview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mindbowser.assignmet.R;
import com.mindbowser.assignmet.model.Contacts;
import com.mindbowser.assignmet.ui.Constants;

public class ContactHolder extends RecyclerView.ViewHolder {
    private TextView contactName;
    private ImageView contactIme;
    private TextView contactNumber;
    private TextView contactImgTxt;
    TextView fav;
    TextView delete;
    Context context;
    Contacts contactHolder;
    ContactAdapterListener adapterListener;

    public ContactHolder(@NonNull View itemView, Context context, ContactAdapterListener adapterListener) {
        super(itemView);
        this.context = context;
        contactName = itemView.findViewById(R.id.contactName);
        contactNumber = itemView.findViewById(R.id.contactNumber);
        contactIme = itemView.findViewById(R.id.contactImg);
        contactImgTxt = itemView.findViewById(R.id.contactImgTxt);
        fav = itemView.findViewById(R.id.favourite);
        delete = itemView.findViewById(R.id.delete);

        this.adapterListener = adapterListener;
    }

    public void bind(Contacts contacts) {

        contactHolder = contacts;
        contactName.setText(contacts.getName());
        contactNumber.setText(contacts.getNumber());
        Constants.log("contactholder", contacts.getDeleted());

        if (contacts.getDeleted().equalsIgnoreCase("no")) {
            if (contacts.getFavourite().equalsIgnoreCase("yes")) {
                fav.setText(context.getResources().getString(R.string.remove));
            } else if (contacts.getFavourite().equalsIgnoreCase("no")) {
                fav.setText(context.getResources().getString(R.string.fav));
            }
            if (contacts.getUrl() == null) {
                Constants.log("contactholder", String.valueOf(contactName.getText().charAt(0)));
                contactImgTxt.setVisibility(View.VISIBLE);
                contactImgTxt.setText(String.valueOf(contactName.getText().charAt(0)));
                contactIme.setVisibility(View.GONE);
            } else {
                contactImgTxt.setVisibility(View.GONE);
                contactIme.setVisibility(View.VISIBLE);
                Glide.with(context).load(contacts.getUrl()).error(R.color.purple_200).into(contactIme);
            }

            delete.setOnClickListener(view -> adapterListener.deleteOnClick(view, contacts, getAdapterPosition()));
            fav.setOnClickListener(view -> adapterListener.favOnClick(view, contacts));
            itemView.setOnClickListener(view -> adapterListener.onItemListner(view, contacts, getAdapterPosition()));
        }
    }

    public interface ContactAdapterListener {

        void deleteOnClick(View view, Contacts contacts, int position);

        void favOnClick(View view, Contacts contacts);

        void onItemListner(View view, Contacts contacts, int position);
    }

}
