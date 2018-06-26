package com.mji.tapia.youcantapia.features.convenient.phonebook.ui.edit;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.Contact;

/**
 * Created by andy on 3/30/2018.
 *
 */

public interface EditContactContract {

    interface View extends BaseView {
        Contact getContact();
        void onUpdateContact();
    }

    interface Presenter {
        void onClick();
        void onDeleteContact(Contact contact);
        void onFinish();
    }

    interface Navigator extends BaseNavigator {
        void goBack();
    }
}
