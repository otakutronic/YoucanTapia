package com.mji.tapia.youcantapia.features.setting.serial;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.setting.menu.SettingMenuAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class SerialSettingFragment extends BaseFragment implements SerialSettingContract.View {

    static public final String TAG = "SerialSettingFragment";

    SerialSettingPresenter presenter;

    static public SerialSettingFragment newInstance() {
        return new SerialSettingFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new SerialSettingPresenter(this, new SerialSettingNavigator((AppCompatActivity) getActivity()), Injection.provideRobotManager(getContext()));
        return presenter;
    }

    @BindView(R.id.serial) TextView serial_tv;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_serial_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setSerial(String serial) {
        SpannableString content = new SpannableString(serial);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        serial_tv.setText(content);
    }
}
