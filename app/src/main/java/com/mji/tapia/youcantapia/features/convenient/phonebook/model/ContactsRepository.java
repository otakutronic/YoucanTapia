package com.mji.tapia.youcantapia.features.convenient.phonebook.model;

import java.util.ArrayList;

/**
 * Created by andy on 4/9/2018.
 *
 */

public interface ContactsRepository {

    Contact getContact(int id);

    void addContact(Contact contact);

    void deleteContact(Contact contact);

    ArrayList<Contact> getContacts();

    void deleteContacts();
}
