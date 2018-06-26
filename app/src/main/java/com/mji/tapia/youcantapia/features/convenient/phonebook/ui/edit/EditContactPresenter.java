package com.mji.tapia.youcantapia.features.convenient.phonebook.ui.edit;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.Contact;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.ContactsRepository;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by andy on 3/30/2018.
 *
 */

public class EditContactPresenter extends BasePresenter<EditContactContract.View, EditContactContract.Navigator> implements EditContactContract.Presenter {

    private ContactsRepository contactsRepository;

    public EditContactPresenter(EditContactContract.View view, EditContactContract.Navigator navigator, ContactsRepository contactsRepository) {
        super(view, navigator);
        this.contactsRepository = contactsRepository;
    }

    @Override
    public void init() {
        super.init();
        view.onUpdateContact();
    }

    @Override
    public void onClick() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }

    @Override
    public void onDeleteContact(Contact contact) {
        contactsRepository.deleteContact(contact);
    }

    @Override
    public void onFinish() {
        navigator.goBack();
    }
}
