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

public class FavContactHolder extends RecyclerView.ViewHolder {
    private TextView contactName;
    private ImageView contactIme;
    private TextView contactNumber;
    Context context;
    Contacts contactHolder;
    TextView fav;
    TextView delete;

    public FavContactHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        contactName = itemView.findViewById(R.id.contactName);
        contactNumber = itemView.findViewById(R.id.contactNumber);
        contactIme = itemView.findViewById(R.id.contactImg);
//        contactImgTxt = itemView.findViewById(R.id.contactImgTxt);
        fav = itemView.findViewById(R.id.favourite);
        delete = itemView.findViewById(R.id.delete);
    }

    public void bind(Contacts contacts) {
        fav.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
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
    }

}
