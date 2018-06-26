package com.mji.tapia.youcantapia.features.convenient.phonebook.ui.add;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.Contact;
import com.mji.tapia.youcantapia.widget.KeyboardDialog;
import com.mji.tapia.youcantapia.widget.KeyboardNumberDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by andy on 3/30/2018.
 *
 */

public class AddContactFragment extends BaseFragment implements AddContactContract.View {

    static public final String TAG = "AddContactFragment";

    static public AddContactFragment newInstance() {
        AddContactFragment fragment = new AddContactFragment();
        return fragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new AddContactPresenter(this, new AddContactNavigator((AppCompatActivity) getActivity()), Injection.provideContactRepository(getContext()));
        return presenter;
    }

    @BindView(R.id.name_input) TextView name_tv;
    @BindView(R.id.number_input) TextView number_tv;
    @BindView(R.id.add_button) TextView add_button;

    protected Unbinder unbinder;

    private AddContactPresenter presenter;
    private Contact contact = new Contact();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.convenient_phonebook_add_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        name_tv.setOnClickListener(v -> {
            presenter.onClick();
            KeyboardDialog keyboardDialog = new KeyboardDialog(getContext());
            keyboardDialog.setHint(getString(R.string.phonebook_contact_keypad_hint));
            keyboardDialog.setOnEnterListener(text -> {
                if (text != null && !text.equals("")) {
                    contact.setName(text);
                    presenter.onNameChange(text);

                    if(checkForReady()) {
                        add_button.setBackgroundResource(R.drawable.shared_button_accent);
                    }
                }
            });
            keyboardDialog.show();
        });
        presenter.init();

        number_tv.setOnClickListener(v -> {
            presenter.onClick();
            KeyboardNumberDialog keyboardNumberDialog = new KeyboardNumberDialog(getContext());
            keyboardNumberDialog.setHint(getString(R.string.phonebook_contact_numpad_hint));
            keyboardNumberDialog.setOnEnterListener(text -> {
                if (text != null && !text.equals("")) {
                    contact.setTelephoneNumber(text);
                    presenter.onTelephoneNumberChange(text);

                    if(checkForReady()) {
                        add_button.setBackgroundResource(R.drawable.shared_button_accent);
                    }
                }
            });
            keyboardNumberDialog.show();
        });
        presenter.init();

        add_button.setOnClickListener(v -> onAddButtonSelect());

        return view;
    }

    public void onAddButtonSelect() {
        if(checkForReady()) {
            presenter.onClick();
            presenter.onAddContact(contact);
            presenter.onFinish();
        }
    }

    public boolean checkForReady() {
        boolean isReady = false;
        final String name = name_tv.getText().toString();
        final String number = number_tv.getText().toString();

        if(!name.equals("") && !number.equals("")) {
            isReady = true;
        }
        return isReady;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Contact getContact() {
        return contact;
    }

    @Override
    public void onUpdateContact() {
        final String name = contact.getName();
        final String telephoneNumber = contact.getTelephoneNumber();
        name_tv.setText(name);
        number_tv.setText(telephoneNumber);
    }

    private ChipsLayoutManager getLayoutManager() {
        return ChipsLayoutManager.newBuilder(getContext())
                .setScrollingEnabled(false)
                .build();
    }
}
