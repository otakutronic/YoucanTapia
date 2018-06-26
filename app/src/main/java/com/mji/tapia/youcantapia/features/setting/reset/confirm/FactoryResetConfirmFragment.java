package com.mji.tapia.youcantapia.features.setting.reset.confirm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class FactoryResetConfirmFragment extends BaseFragment implements FactoryResetConfirmContract.View {

    static public final String TAG = "FactoryResetConfirmFragment";

    FactoryResetConfirmPresenter presenter;

    static public FactoryResetConfirmFragment newInstance() {
        return new FactoryResetConfirmFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new FactoryResetConfirmPresenter(this, new FactoryResetConfirmNavigator((AppCompatActivity) getActivity()));
        return presenter;
    }


    @BindView(R.id.yes) View yes;
    @BindView(R.id.no) View no;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_factory_reset_confirm, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();
        yes.setOnClickListener(v -> presenter.onYes());
        no.setOnClickListener(v -> presenter.onNo());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
