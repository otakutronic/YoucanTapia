package com.mji.tapia.youcantapia.features.convenient.phonebook.ui.edit;

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
import com.mji.tapia.youcantapia.widget.TapiaDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by andy on 3/30/2018.
 *
 */

public class EditContactFragment extends BaseFragment implements EditContactContract.View {

    static public final String TAG = "EditContactFragment";

    EditContactPresenter presenter;

    static public EditContactFragment newInstance(Contact contact) {
        EditContactFragment fragment = new EditContactFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", contact.getId());
        bundle.putString("name", contact.getName());
        bundle.putString("number", contact.getTelephoneNumber());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new EditContactPresenter(this, new EditContactNavigator((AppCompatActivity) getActivity()), Injection.provideContactRepository(getContext()));
        return presenter;
    }

    @BindView(R.id.name_input) TextView name_tv;
    @BindView(R.id.number_input) TextView number_tv;
    @BindView(R.id.confirm_button) View confirm_button;

    private Contact contact = new Contact();
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.convenient_phonebook_edit_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        final int id = this.getArguments().getInt("id");
        final String name = this.getArguments().getString("name");
        final String number = this.getArguments().getString("number");
        contact = new Contact(id, name, number);

        confirm_button.setOnClickListener(v -> showDeleteConfirmationDialogue());
        presenter.init();
        return view;
    }

    public void showDeleteConfirmationDialogue() {
        presenter.onClick();
        new TapiaDialog.Builder(getContext())
                .setType(TapiaDialog.Type.WARNING)
                .setMessage(getString(R.string.phonebook_contact_delete_text_confirm))
                .setNegativeButton(getString(R.string.phonebook_contact_delete_button_text_cancel), this::onDialogueCancel)
                .setPositiveButton(getString(R.string.phonebook_contact_delete_button_text_confirm), this::onDialogueConfirm)
                .show();
    }

    public void onDialogueConfirm() {
        presenter.onClick();
        presenter.onDeleteContact(contact);
        presenter.onFinish();
    }

    public void onDialogueCancel() {
        presenter.onClick();
    }

   @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Contact getContact() {
        return null;
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
