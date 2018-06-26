package com.mji.tapia.youcantapia.features.setting.reset.cleaning;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class FactoryResetCleaningFragment extends BaseFragment implements FactoryResetCleaningContract.View {

    static public final String TAG = "FactoryResetCleaningFragment";

    FactoryResetCleaningPresenter presenter;

    static public FactoryResetCleaningFragment newInstance() {
        return new FactoryResetCleaningFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new FactoryResetCleaningPresenter(this, new FactoryResetCleaningNavigator((AppCompatActivity) getActivity()), Injection.provideRobotManager(getContext()));
        return presenter;
    }


    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_factory_reset_cleaning, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
