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
import com.mindbowser.assignmet.model.DeleteContact;
import com.mindbowser.assignmet.ui.Constants;

public class FavContactHolder extends RecyclerView.ViewHolder {
    private final TextView contactImgTxt;
    private TextView contactName;
    private ImageView contactIme;
    private TextView contactNumber;
    Context context;
    Contacts contactHolder;
    TextView fav;
    OnItemClickListerner itemClickListerner;
    TextView delete;

    public FavContactHolder(@NonNull View itemView, Context context, OnItemClickListerner clickListerner) {
        super(itemView);
        this.context = context;
        contactName = itemView.findViewById(R.id.contactName);
        contactNumber = itemView.findViewById(R.id.contactNumber);
        contactIme = itemView.findViewById(R.id.contactImg);
        contactImgTxt = itemView.findViewById(R.id.contactImgTxt);
        fav = itemView.findViewById(R.id.favourite);
        delete = itemView.findViewById(R.id.delete);
        itemClickListerner = clickListerner;

    }

    public void bind(Contacts contacts) {
        fav.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
        contactHolder = contacts;
        contactName.setText(contacts.getName());
        contactNumber.setText(contacts.getNumber());
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
        itemView.setOnClickListener(view -> itemClickListerner.onItemClick(view,contacts,getAdapterPosition()));

    }

    public interface OnItemClickListerner {
        void onItemClick(View view, Contacts contacts, int adapterPosition);

    }

}
