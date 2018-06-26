package com.mji.tapia.youcantapia.features.convenient.clock;

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
import com.mji.tapia.youcantapia.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class ClockFragment extends BaseFragment implements ClockContract.View {

    static public final String TAG = "ClockFragment";

    ClockPresenter presenter;

    static public ClockFragment newInstance() {
        return new ClockFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new ClockPresenter(this, new ClockNavigator((AppCompatActivity) getActivity()));
        return presenter;
    }


    @BindView(R.id.date) TextView date_tv;
    @BindView(R.id.time) TextView time_tv;
    @BindView(R.id.ampm) TextView ampm_tv;

    @BindView(R.id.setting) View setting_bt;

    private Unbinder unbinder;

    SimpleDateFormat dateFormat;
    SimpleDateFormat timeFormat;
    SimpleDateFormat ampmFormat;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.convenient_clock_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();
        dateFormat = new SimpleDateFormat("yyyy' 年 'M' 月 'd' 日'", Locale.JAPANESE);
        timeFormat = new SimpleDateFormat("h:mm", Locale.JAPANESE);
        ampmFormat = new SimpleDateFormat("a", Locale.JAPANESE);
        setDate(new Date());

        setting_bt.setOnClickListener(v -> presenter.onSetting());
        return view;
    }


    Disposable timeDisposable;
    @Override
    public void onResume() {
        super.onResume();
        timeDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    setDate(new Date());
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timeDisposable != null && !timeDisposable.isDisposed())
            timeDisposable.dispose();
    }


    void setDate(Date date) {
        date_tv.setText(dateFormat.format(date));
        time_tv.setText(timeFormat.format(date));
        String a = ampmFormat.format(date);

        ampm_tv.setText(a);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
