package com.mji.tapia.youcantapia.features.convenient.phonebook.model.source;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.Contact;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.ContactsRepository;
import com.mji.tapia.youcantapia.managers.shared_preference.SharedPreferenceManager;

import java.util.ArrayList;

/**
 * Created by andy on 4/9/2018.
 *
 */

public class LocalDataSource implements ContactsRepository {

    static final private String CONTACTS_SHARED_PREFERENCE = "contacts_shared_preference";

    private ArrayList<Contact> contactList = new ArrayList<Contact>();

    private SharedPreferences sharedPreferences;

    public LocalDataSource(SharedPreferenceManager sharedPreferenceManager) {
        sharedPreferences = sharedPreferenceManager.getSharedPreference(CONTACTS_SHARED_PREFERENCE);
    }

    @Override
    public Contact getContact(int id) {
        final Contact contact = contactList.get(id);
        return contact;
    }

    @Override
    public void addContact(Contact contact) {
        if (null == contactList) {
            contactList = new ArrayList<Contact>();
        }

        final int id = contactList.size();
        contact.setId(id);
        contactList.add(contact);

        Gson gson = new Gson();
        String contactJsonString = gson.toJson(contact);

        sharedPreferences.edit().putString(String.valueOf(id), contactJsonString).apply();
    }

    @Override
    public void deleteContact(Contact contact) {
        final int index = contact.getId();
        contactList.remove(index);
        final ArrayList<Contact> tempContacts = contactList;

        deleteContacts();

        for(int i = 0; i < tempContacts.size(); i++) {
            final Contact tempContact = tempContacts.get(i);
            addContact(tempContact);
        }
    }

    @Override
    public ArrayList<Contact> getContacts() {
        contactList = new ArrayList<Contact>();

        while(true) {
            String contactID = String.valueOf(contactList.size());
            String value = sharedPreferences.getString          (contactID, null);
            if(value == null) {
                break;
            }
            Gson gson = new Gson();
            Contact contact = gson.fromJson(value, Contact.class);
            contactList.add(contact);
        }

        return contactList;
    }

    @Override
    public void deleteContacts() {
        int index = 0;

        while(true) {
            String value = sharedPreferences.getString(String.valueOf(index), null);
            if(value == null) {
                break;
            }
            sharedPreferences.edit().remove(String.valueOf(index)).apply();
            sharedPreferences.edit().remove(String.valueOf(index)).commit();
            index++;
        }

        contactList = new ArrayList<Contact>();
    }
}
