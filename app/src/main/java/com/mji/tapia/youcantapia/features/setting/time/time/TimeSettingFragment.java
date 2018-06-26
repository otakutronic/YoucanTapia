package com.mji.tapia.youcantapia.features.setting.time.time;

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
import com.mji.tapia.youcantapia.widget.PickerView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TimeSettingFragment extends BaseFragment implements TimeSettingContract.View {

    static public final String TAG = "ClockFragment";

    static private final String YEAR = "YEAR";
    static private final String MONTH = "MONTH";
    static private final String DAY = "DAY";

    TimeSettingPresenter presenter;

    static public TimeSettingFragment newInstance(int year, int month, int day) {
        TimeSettingFragment timeSettingFragment = new TimeSettingFragment();
        timeSettingFragment.setArguments(new Bundle());
        timeSettingFragment.getArguments().putInt(YEAR, year);
        timeSettingFragment.getArguments().putInt(MONTH, month);
        timeSettingFragment.getArguments().putInt(DAY, day);
        return timeSettingFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new TimeSettingPresenter(this, new TimeSettingNavigator((AppCompatActivity) getActivity()), Injection.provideTapiaTimeManager(getContext()));
        return presenter;
    }


    private Unbinder unbinder;

    @BindView(R.id.date_hour_pv) PickerView dateHour_pv;
    @BindView(R.id.date_minute_pv) PickerView dateMinute_pv;
    @BindView(R.id.am) TextView am_tv;
    @BindView(R.id.pm) TextView pm_tv;

    @BindView(R.id.back) View back_bt;
    @BindView(R.id.next) View next_bt;

    private boolean isAM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_time_set_time_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();

        String[] hourStrings = new String[12];
        for (int i = 0; i < 12; i++) {
            hourStrings[i] = String.format("%d",i);
        }
        String[] minuteStrings = new String[60];
        for (int i = 0; i < 60; i++) {
            minuteStrings[i] = String.format("%02d",i);
        }
        dateHour_pv.setItemArray(hourStrings);
        dateMinute_pv.setItemArray(minuteStrings);

        Calendar calendar = new GregorianCalendar();
        dateMinute_pv.setSelectedItem(calendar.get(Calendar.MINUTE), false);
        dateHour_pv.setSelectedItem(calendar.get(Calendar.HOUR), false);

        String ampm = new SimpleDateFormat("a", Locale.JAPANESE).format(new Date());

        if (ampm.equals(am_tv.getText())) {
            isAM = true;
        } else {
            isAM = false;
        }
        setAMPM(isAM);

        back_bt.setOnClickListener(v -> presenter.onBack());
        next_bt.setOnClickListener(v -> presenter.onNext(getArguments().getInt(YEAR), getArguments().getInt(MONTH), getArguments().getInt(DAY),isAM, dateHour_pv.getItemSelected(), dateMinute_pv.getItemSelected()));

        View.OnClickListener clickListener = v -> {
            if (v.getAlpha() == 0.3f) {
                setAMPM(!isAM);
            }
        };
        am_tv.setOnClickListener(clickListener);
        pm_tv.setOnClickListener(clickListener);
        return view;
    }

    private void setAMPM(boolean isAM) {
        this.isAM = isAM;
        if (isAM) {
            am_tv.setAlpha(1f);
            pm_tv.setAlpha(0.3f);
        } else {
            am_tv.setAlpha(0.3f);
            pm_tv.setAlpha(1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
