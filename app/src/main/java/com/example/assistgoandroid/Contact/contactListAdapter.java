package com.example.assistgoandroid.Contact;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assistgoandroid.R;

import java.util.ArrayList;

/**
 * Adapter for contact list
 */

public class contactListAdapter extends RecyclerView.Adapter<contactListAdapter.ViewHolder> {
    Activity activity;
    private ArrayList<Contact> contactsList = new ArrayList<Contact>();

    public contactListAdapter(Activity activity, ArrayList<Contact> contactsList) {
        this.activity = activity;
        this.contactsList = contactsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public contactListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull contactListAdapter.ViewHolder holder, int position) {
        Contact contact = contactsList.get(position);

        holder.contactName.setText(contact.getName());
        //todo set contact picture from contact list
        //todo set favorite on click
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView contactName;
        ImageView contactProfilePicture;
        ImageView favoriteHeart;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            contactName = itemView.findViewById(R.id.tvContactName);
            contactProfilePicture = itemView.findViewById(R.id.ivContactProfilePicture);
            favoriteHeart = itemView.findViewById(R.id.ivFavoriteHeart);
        }
    }

}
