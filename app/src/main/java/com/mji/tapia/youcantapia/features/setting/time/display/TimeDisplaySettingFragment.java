package com.mji.tapia.youcantapia.features.setting.time.display;

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

public class TimeDisplaySettingFragment extends BaseFragment implements TimeDisplaySettingContract.View {

    static public final String TAG = "ClockFragment";

    TimeDisplaySettingPresenter presenter;

    static public TimeDisplaySettingFragment newInstance() {
        return new TimeDisplaySettingFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new TimeDisplaySettingPresenter(this, new TimeDisplaySettingNavigator((AppCompatActivity) getActivity()));
        return presenter;
    }


    @BindView(R.id.date) TextView date_tv;
    @BindView(R.id.time) TextView time_tv;
    @BindView(R.id.am) TextView am_tv;
    @BindView(R.id.pm) TextView pm_tv;

    @BindView(R.id.setting) View setting_bt;
    @BindView(R.id.next) View next_bt;

    private Unbinder unbinder;

    SimpleDateFormat dateFormat;
    SimpleDateFormat timeFormat;
    SimpleDateFormat ampmFormat;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_time_display_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();
        dateFormat = new SimpleDateFormat("yyyy' 年 'M' 月 'd' 日'", Locale.JAPANESE);
        timeFormat = new SimpleDateFormat("h:mm", Locale.JAPANESE);
        ampmFormat = new SimpleDateFormat("a", Locale.JAPANESE);
        setDate(new Date());

        setting_bt.setOnClickListener(v -> presenter.onSetting());
        next_bt.setOnClickListener(v -> presenter.onAccept());
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

        if (a.equals(pm_tv.getText())) {
            am_tv.setAlpha(0.3f);
            pm_tv.setAlpha(1);
        } else {
            pm_tv.setAlpha(0.3f);
            am_tv.setAlpha(1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
