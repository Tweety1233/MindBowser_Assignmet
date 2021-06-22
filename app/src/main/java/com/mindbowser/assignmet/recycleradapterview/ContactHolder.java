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
//        contactImgTxt = itemView.findViewById(R.id.contactImgTxt);
        fav = itemView.findViewById(R.id.favourite);
        delete = itemView.findViewById(R.id.delete);

        this.adapterListener = adapterListener;
//        this.listClickListener = listClickListener;
    }

    public void bind(Contacts contacts) {

        contactHolder = contacts;
        contactName.setText(contacts.getName());
        contactNumber.setText(contacts.getNumber());
        if (contacts.getUrl() == null) {
//            contactImgTxt.setVisibility(View.VISIBLE);
//            contactImgTxt.setText(contacts.getName().charAt(0));
//            contactIme.setVisibility(View.GONE);
        } else {
//            contactImgTxt.setVisibility(View.GONE);
            Glide.with(context).load(contacts.getUrl()).error(R.color.purple_200).into(contactIme);
        }
        delete.setOnClickListener(view -> adapterListener.deleteOnClick(view, contacts));
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListener.favOnClick(view, contacts);

            }
        });
    }

    public interface ContactAdapterListener {

        void deleteOnClick(View view, Contacts contacts);

        void favOnClick(View view, Contacts contacts);
    }

}
