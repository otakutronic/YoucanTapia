package com.mji.tapia.youcantapia.features.user.ui.setting.name;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.User;
import com.mji.tapia.youcantapia.widget.KeyboardDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class NameSettingFragment extends BaseFragment implements NameSettingContract.View {

    static public final String TAG = "NameSettingFragment";

    NameSettingPresenter presenter;

    static public NameSettingFragment newInstance() {
        NameSettingFragment timeSettingFragment = new NameSettingFragment();
        timeSettingFragment.setArguments(new Bundle());
        return timeSettingFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new NameSettingPresenter(this, new NameSettingNavigator((AppCompatActivity) getActivity()),
                Injection.provideUserRepository(getContext()), Injection.provideResourcesManager(getContext()), Injection.provideTTSManager(getContext()));
        return presenter;
    }

    @BindView(R.id.add_entry) TextView addEntryView;

    @BindView(R.id.name_list) ListView name_lv;

    @BindView(R.id.arrow_down) View arrow_down;
    @BindView(R.id.arrow_up) View arrow_up;

    @BindView(R.id.back) View back_bt;
    @BindView(R.id.next) View next_bt;

    private Unbinder unbinder;

    private List<String> nameList;

    private NameSettingAdapter nameSettingAdapter;

    private String custom_name = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_user_name_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        back_bt.setOnClickListener(v -> presenter.onBack());
        next_bt.setOnClickListener(v -> presenter.onNext(nameSettingAdapter.getSelectedItem()));


        String [] names = getResources().getStringArray(R.array.user_default_names);


        nameList = new ArrayList<>(Arrays.asList(names));

        nameSettingAdapter = new NameSettingAdapter(getContext(),nameList);
        name_lv.setAdapter(nameSettingAdapter);

        name_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                View v = name_lv.getChildAt(0);
                View vlast = name_lv.getChildAt(i1 - 1);
                int top = (v == null) ? 0 : (v.getTop() - name_lv.getPaddingTop());
                int bottom = (vlast == null) ? 0 : (vlast.getBottom() - name_lv.getPaddingBottom());
                if (i == 0 && top == 0) {
                    arrow_up.setVisibility(View.INVISIBLE);
                } else {
                    arrow_up.setVisibility(View.VISIBLE);
                }

                if (i + i1 == i2 && bottom == name_lv.getHeight()) {
                    arrow_down.setVisibility(View.INVISIBLE);
                } else {
                    arrow_down.setVisibility(View.VISIBLE);
                }
            }
        });

        arrow_up.setOnClickListener(v-> {
            name_lv.smoothScrollToPosition(0);
        });

        arrow_down.setOnClickListener(v-> {
            name_lv.smoothScrollToPosition(nameList.size() -1);
        });

        name_lv.setOnItemClickListener((adapterView, view1, i, l) -> {
            nameSettingAdapter.setSelectedItem(i);
            presenter.onNameSelected(nameList.get(i));
        });

        addEntryView.setOnClickListener(v -> {
            presenter.onRightButton();
            KeyboardDialog keyboardDialog = new KeyboardDialog(getContext());
            keyboardDialog.setHint(getString(R.string.user_name_keyboard_hint));
            keyboardDialog.setText(custom_name);
            keyboardDialog.setOnEnterListener(text -> {
                if (text != null && !
                        text.equals("")) {
                    presenter.onCustomNameChange(text);
                    if (nameList.size() == getResources().getStringArray(R.array.user_default_names).length) {
                        nameList.add(0,text);
                        custom_name = text;
                        nameSettingAdapter.refresh(nameList);
                        addEntryView.setText(R.string.user_name_edit);
                    } else {
                        nameList.set(0, text);
                        custom_name = text;
                        nameSettingAdapter.refresh(nameList);
                    }
                }
            });
            keyboardDialog.show();
        });
        presenter.init();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onUser(User user) {
        if (user != null) {
            for (int i = 0; i < nameList.size(); i++) {
                if (nameList.get(i).equals(user.name)) {
                    nameSettingAdapter.setSelectedItem(i);
                }
            }
        }
    }

    @Override
    public void onCustomName(String name) {
        custom_name = name;
        if(name != null) {
            nameList.add(0,name);
            addEntryView.setText(R.string.user_name_edit);
            nameSettingAdapter.refresh(nameList);
        }
    }
}
