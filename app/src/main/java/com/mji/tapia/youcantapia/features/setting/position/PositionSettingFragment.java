package com.mji.tapia.youcantapia.features.setting.position;

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

public class PositionSettingFragment extends BaseFragment implements PositionSettingContract.View {

    static public final String TAG = "PositionSettingFragment";

    PositionSettingPresenter presenter;

    static public PositionSettingFragment newInstance() {
        return new PositionSettingFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new PositionSettingPresenter(this, new PositionSettingNavigator((AppCompatActivity) getActivity()), Injection.provideRobotManager(getContext()), Injection.provideTTSManager(getContext()));
        return presenter;
    }

    @BindView(R.id.up_bt) View up_bt;
    @BindView(R.id.down_bt) View down_bt;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_position_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        up_bt.setOnClickListener(v -> presenter.onUp());
        down_bt.setOnClickListener(v -> presenter.onDown());

        presenter.init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void lock() {
        if (up_bt != null)
            up_bt.setEnabled(false);
        if (down_bt != null)
            down_bt.setEnabled(false);
    }

    @Override
    public void unlock() {
        if (up_bt != null)
            up_bt.setEnabled(true);
        if (down_bt != null)
            down_bt.setEnabled(true);
    }
}
