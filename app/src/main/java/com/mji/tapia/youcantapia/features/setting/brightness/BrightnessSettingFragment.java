package com.mji.tapia.youcantapia.features.setting.brightness;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class BrightnessSettingFragment extends BaseFragment implements BrightnessSettingContract.View {

    static public final String TAG = "BrightnessSettingFragment";

    BrightnessSettingPresenter presenter;

    static public BrightnessSettingFragment newInstance() {
        return new BrightnessSettingFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new BrightnessSettingPresenter(this, new BrightnessSettingNavigator((AppCompatActivity) getActivity()), Injection.provideTapiaBrightnessManager(getActivity()));
        return presenter;
    }

    @BindView(R.id.value) TextView value_tv;
    @BindView(R.id.minus) View minus_bt;
    @BindView(R.id.plus) View plus_bt;

    private Unbinder unbinder;

    int value = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_brightness_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        minus_bt.setOnClickListener(v -> {
            presenter.onDown();
            presenter.onValueChange(value - 1);
        });
        plus_bt.setOnClickListener(v -> {
            presenter.onUp();
            presenter.onValueChange(value + 1);
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
    public void setValue(int value) {
        this.value = value;
        value_tv.setText(Integer.toString(value));
    }
}
