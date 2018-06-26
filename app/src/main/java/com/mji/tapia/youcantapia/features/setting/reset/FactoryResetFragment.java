package com.mji.tapia.youcantapia.features.setting.reset;

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

public class FactoryResetFragment extends BaseFragment implements FactoryResetContract.View {

    static public final String TAG = "FactoryResetFragment";

    FactoryResetPresenter presenter;

    static public FactoryResetFragment newInstance() {
        return new FactoryResetFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new FactoryResetPresenter(this, new FactoryResetNavigator((AppCompatActivity) getActivity()));
        return presenter;
    }


    @BindView(R.id.yes) View yes;
    @BindView(R.id.no) View no;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_factory_reset, container, false);
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
