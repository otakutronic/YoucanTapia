package com.mji.tapia.youcantapia.features.convenient.phonebook.model;

import android.util.Log;

import com.mji.tapia.youcantapia.features.convenient.phonebook.model.source.LocalDataSource;
import com.mji.tapia.youcantapia.managers.shared_preference.SharedPreferenceManager;

import java.util.ArrayList;

/**
 * Created by andy on 4/10/2018.
 */

public class ContactsRepositoryImpl implements ContactsRepository {

    static public final int MAX_CONTACTS = 10;

    private LocalDataSource localDataSource;

    static private ContactsRepository instance;

    static public ContactsRepository getInstance(SharedPreferenceManager sharedPreferenceManager) {
        if(instance == null) {
            instance = new ContactsRepositoryImpl(sharedPreferenceManager);
        }
        return instance;
    }

    private ContactsRepositoryImpl(SharedPreferenceManager sharedPreferenceManager) {
        localDataSource = new LocalDataSource(sharedPreferenceManager);
    }

    @Override
    public Contact getContact(int id) {
        return localDataSource.getContact(id);
    }

    @Override
    public void addContact(Contact contact) {
        localDataSource.addContact(contact);
    }

    @Override
    public void deleteContact(Contact contact) {
        localDataSource.deleteContact(contact);
    }

    @Override
    public ArrayList<Contact> getContacts() {
        return localDataSource.getContacts();
    }

    @Override
    public void deleteContacts() {
        localDataSource.deleteContacts();
    }
}
