package com.mji.tapia.youcantapia.features.setting.battery;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class BatterySettingFragment extends BaseFragment implements BatterySettingContract.View {

    static public final String TAG = "BatterySettingFragment";

    BatterySettingPresenter presenter;

    static public BatterySettingFragment newInstance() {
        return new BatterySettingFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new BatterySettingPresenter(this, new BatterySettingNavigator((AppCompatActivity) getActivity()), Injection.provideTapiaBatteryManager(getContext()));
        return presenter;
    }

    @BindView(R.id.percent) TextView percent_tv;
    @BindView(R.id.level) ProgressBar batteryLevel_pb;
    @BindView(R.id.charging_icon) ImageView charging_iv;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_battery_fragment, container, false);
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
    public void setLevel(int level) {
        percent_tv.setText(String.format(Locale.JAPANESE,"%d %%", level));
        batteryLevel_pb.setProgress(level);
    }

    @Override
    public void setIsCharging(boolean isCharging) {
        if(isCharging) {
            charging_iv.setVisibility(View.VISIBLE);
            batteryLevel_pb.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.lt_green)));
        } else {
            charging_iv.setVisibility(View.INVISIBLE);
            batteryLevel_pb.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
        }
    }
}
