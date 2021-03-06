package com.example.mycontacts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends  RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    private final LayoutInflater layoutInflater;
    private final List<Contact> contactList;


    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        contactList = contacts;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomContact customContact = new CustomContact(parent.getContext());
        return new ViewHolder(customContact);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        ((CustomContact)holder.itemView).contact = contact.getName();
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull CustomContact itemView) {
            super(itemView);
        }
    }
}

