package com.mji.tapia.youcantapia.features.convenient.phonebook.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.Contact;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.ContactsRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

/**
 * Created by andy on 3/30/2018.
 *
 */

public class PhoneBookFragment extends BaseFragment implements PhoneBookContract.View {

    static public final String TAG = "PhoneBookFragment";

    PhoneBookPresenter presenter;

    static public PhoneBookFragment newInstance() {
        return new PhoneBookFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new PhoneBookPresenter(this, new PhoneBookNavigator((AppCompatActivity) getActivity()), Injection.provideContactRepository(getContext()));
        return presenter;
    }

    List<PhoneBookMenuAdapter.SubMenuItem> subMenuItems;

    @BindView(R.id.contact_list) ListView contactsList;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.convenient_phonebook_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();

        ArrayList<Contact> contactList = presenter.getContacts();

        //unbinder = ButterKnife.bind(this, view);

        subMenuItems = new ArrayList<>();

        final String addContactString = getResources().getString(R.string.phonebook_contact_add_label);
        subMenuItems.add(new PhoneBookMenuAdapter.SubMenuItem(-1, addContactString));

        for(int i = 0; i < contactList.size(); i++) {
            Contact contact = contactList.get(i);
            subMenuItems.add(new PhoneBookMenuAdapter.SubMenuItem(i, contact.getName()));
        }

        PhoneBookMenuAdapter settingMenuAdapter = new PhoneBookMenuAdapter(getContext(), subMenuItems);
        contactsList.setAdapter(settingMenuAdapter);

        contactsList.setOnItemClickListener((AdapterView<?> adapterView, View view1, int i, long l) -> {
            final int id = subMenuItems.get(i).id;

            if(id == -1) {
                final int totalContacts = presenter.getContactsSize();
                if(totalContacts < ContactsRepositoryImpl.MAX_CONTACTS) {
                    presenter.onClick();
                    presenter.onAdd();
                }
            } else {
                Contact contact = presenter.getContact(id);
                presenter.onClick();
                presenter.onEdit(contact);
            }
        });

        return view;
    }

    Disposable timeDisposable;
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (timeDisposable != null && !timeDisposable.isDisposed())
            timeDisposable.dispose();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
