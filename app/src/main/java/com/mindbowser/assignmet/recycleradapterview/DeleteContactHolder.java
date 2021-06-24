package com.mindbowser.assignmet.recycleradapterview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mindbowser.assignmet.R;
import com.mindbowser.assignmet.model.DeleteContact;
import com.mindbowser.assignmet.ui.Constants;

public class DeleteContactHolder extends RecyclerView.ViewHolder {
    private final TextView contactImgTxt;
    private TextView contactName;
    private ImageView contactIme;
    private TextView contactNumber;
    Context context;
    DeleteContact contactHolder;
    TextView fav;
    TextView delete;
    RestoreClickListerner listerner;

    public DeleteContactHolder(@NonNull View itemView, Context context, RestoreClickListerner listerner) {
        super(itemView);
        this.context = context;
        contactName = itemView.findViewById(R.id.contactName);
        contactNumber = itemView.findViewById(R.id.contactNumber);
        contactIme = itemView.findViewById(R.id.contactImg);
        contactImgTxt = itemView.findViewById(R.id.contactImgTxt);
        fav = itemView.findViewById(R.id.favourite);
        delete = itemView.findViewById(R.id.delete);
        this.listerner = listerner;
    }

    public void bind(DeleteContact contacts) {
        delete.setVisibility(View.GONE);
        fav.setText("Restored");
        fav.setVisibility(View.VISIBLE);
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

        fav.setOnClickListener(view -> listerner.restoreClick(view, contacts, getAdapterPosition()));
    }

    public interface RestoreClickListerner {
        void restoreClick(View view, DeleteContact contacts, int adapterPosition);

    }
}

