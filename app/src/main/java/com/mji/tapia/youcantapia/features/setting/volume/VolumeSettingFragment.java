package com.mji.tapia.youcantapia.features.setting.volume;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class VolumeSettingFragment extends BaseFragment implements VolumeSettingContract.View {

    static public final String TAG = "VolumeSettingFragment";

    VolumeSettingPresenter presenter;

    static public VolumeSettingFragment newInstance() {
        return new VolumeSettingFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new VolumeSettingPresenter(this, new VolumeSettingNavigator((AppCompatActivity) getActivity()), Injection.provideTapiaAudioManager(getContext()), Injection.provideResourcesManager(getContext()), Injection.provideTTSManager(getContext()));
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
        View view = inflater.inflate(R.layout.setting_volume_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        minus_bt.setOnClickListener(v -> {
            presenter.onValueChange(value - 1);
            presenter.onVolumeDown();
        });
        plus_bt.setOnClickListener(v -> {
            presenter.onValueChange(value + 1);
            presenter.onVolumeUp();
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
