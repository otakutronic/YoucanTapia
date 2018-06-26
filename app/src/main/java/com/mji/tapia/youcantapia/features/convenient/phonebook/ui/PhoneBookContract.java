package com.mji.tapia.youcantapia.features.convenient.phonebook.ui;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.Contact;

import java.util.ArrayList;

/**
 * Created by andy on 3/30/2018.
 *
 */

public interface PhoneBookContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onAdd();
        void onEdit(Contact contact);
        void onClick();
        Contact getContact(int id);
        ArrayList<Contact> getContacts();
        int getContactsSize();
        void deleteContacts();
    }

    interface Navigator extends BaseNavigator {
        void openAddContactScreen();
        void openEditContactScreen(Contact contact);
    }
}
