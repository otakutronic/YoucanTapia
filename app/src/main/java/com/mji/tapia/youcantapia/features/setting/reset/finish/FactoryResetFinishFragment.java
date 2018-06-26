package com.mji.tapia.youcantapia.features.setting.reset.finish;

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

public class FactoryResetFinishFragment extends BaseFragment implements FactoryResetFinishContract.View {

    static public final String TAG = "FactoryResetFinishFragment";

    FactoryResetFinishPresenter presenter;

    static public FactoryResetFinishFragment newInstance() {
        return new FactoryResetFinishFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new FactoryResetFinishPresenter(this, new FactoryResetFinishNavigator((AppCompatActivity) getActivity()));
        return presenter;
    }


    @BindView(R.id.back) View back;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_factory_reset_finish, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();

        back.setOnClickListener(v-> presenter.onOk());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
