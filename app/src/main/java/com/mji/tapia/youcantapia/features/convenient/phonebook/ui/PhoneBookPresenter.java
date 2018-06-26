package com.mji.tapia.youcantapia.features.convenient.phonebook.ui;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.Contact;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.ContactsRepository;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import java.util.ArrayList;

/**
 * Created by andy on 3/30/2018.
 *
 */

public class PhoneBookPresenter extends BasePresenter<PhoneBookContract.View, PhoneBookContract.Navigator> implements PhoneBookContract.Presenter {

    private ContactsRepository contactsRepository;

    public PhoneBookPresenter(PhoneBookContract.View view, PhoneBookContract.Navigator navigator, ContactsRepository contactsRepository) {
        super(view, navigator);
        this.contactsRepository = contactsRepository;
    }

    @Override
    public void onAdd() {
        navigator.openAddContactScreen();
    }

    @Override
    public void onEdit(Contact contact) {
        navigator.openEditContactScreen(contact);
    }

    @Override
    public void onClick() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }

    @Override
    public Contact getContact(int id) {
        return contactsRepository.getContact(id);
    }

    @Override
    public ArrayList<Contact> getContacts() {
        return contactsRepository.getContacts();
    }

    @Override
    public int getContactsSize() {
        return contactsRepository.getContacts().size();
    }

    @Override
    public void deleteContacts() {
        contactsRepository.deleteContacts();
    }
}
