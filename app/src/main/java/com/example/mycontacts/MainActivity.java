package com.example.mycontacts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView contactRV;
    ArrayList<Contact> contacts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Context context = this;

        contacts = new ArrayList<>();
        contacts.clear();
        contacts.add(new Contact("Olga"));
        contacts.add(new Contact("Oleg"));
        contacts.add(new Contact("Vika"));
        contacts.add(new Contact("Larisa"));
        contacts.add(new Contact("Nikita"));
        contacts.add(new Contact("Tasya"));


        contactRV = (RecyclerView)findViewById(R.id.contact_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contactRV.setLayoutManager(linearLayoutManager);
        ContactAdapter contactAdapter = new ContactAdapter(this, contacts);
        contactRV.setAdapter(contactAdapter);
    }
}
