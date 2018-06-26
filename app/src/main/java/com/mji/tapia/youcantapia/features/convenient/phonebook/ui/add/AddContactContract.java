package com.mji.tapia.youcantapia.features.convenient.phonebook.ui.add;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.Contact;

/**
 * Created by andy on 3/30/2018.
 *
 */

public interface AddContactContract {

    interface View extends BaseView {
        Contact getContact();
        void onUpdateContact();
    }

    interface Presenter {
        void onNameSelect();
        void onNumberSelect();
        void onNameChange(String name);
        void onTelephoneNumberChange(String number);
        void onClick();
        void onAddContact(Contact contact);
        void onFinish();
    }

    interface Navigator extends BaseNavigator {
        void openNameSetting();
        void openNumberSetting();
        void goBack();

    }
}
