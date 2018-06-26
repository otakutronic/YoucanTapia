package com.mji.tapia.youcantapia.features.convenient.phonebook.ui.add;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.Contact;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.ContactsRepository;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by andy on 3/30/2018.
 *
 */

public class AddContactPresenter extends BasePresenter<AddContactContract.View, AddContactContract.Navigator> implements AddContactContract.Presenter {

    private ContactsRepository contactsRepository;

    public AddContactPresenter(AddContactContract.View view, AddContactContract.Navigator navigator, ContactsRepository contactsRepository) {
        super(view, navigator);
        this.contactsRepository = contactsRepository;
    }

    @Override
    public void init() {
        super.init();
        view.onUpdateContact();
    }

    @Override
    public void onNameSelect() {
        navigator.openNameSetting();
    }

    @Override
    public void onNumberSelect() {
        navigator.openNumberSetting();
    }

    @Override
    public void onClick() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }

    @Override
    public void onNameChange(String name) {
        view.onUpdateContact();
    }

    @Override
    public void onTelephoneNumberChange(String number) {
        view.onUpdateContact();
    }

    @Override
    public void onAddContact(Contact contact) {
        contactsRepository.addContact(contact);
    }

    @Override
    public void onFinish() {
        navigator.goBack();
    }

}
