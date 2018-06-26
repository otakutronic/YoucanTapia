package com.mji.tapia.youcantapia.features.setting.time.date;

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
import com.mji.tapia.youcantapia.widget.PickerView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class DateSettingFragment extends BaseFragment implements DateSettingContract.View {

    static public final String TAG = "DateSettingFragment";

    static int baseYear = 2015;

    DateSettingPresenter presenter;

    static public DateSettingFragment newInstance() {
        DateSettingFragment dateSettingFragment = new DateSettingFragment();
        return dateSettingFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new DateSettingPresenter(this, new DateSettingNavigator((AppCompatActivity) getActivity()));
        return presenter;
    }


    private Unbinder unbinder;

    @BindView(R.id.date_year_pv) PickerView dateYear_pv;
    @BindView(R.id.date_month_pv) PickerView dateMonth_pv;
    @BindView(R.id.date_day_pv) PickerView dateDay_pv;


    @BindView(R.id.back) View back_bt;
    @BindView(R.id.next) View next_bt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_time_set_date_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();

        String[] yearArray = new String[100];
        for (int i = baseYear; i < baseYear + 100; i++) {
            yearArray[i- baseYear] = Integer.toString(i);
        }
        dateYear_pv.setItemArray(yearArray);

        String[] monthArray = new String[12];
        for (int i = 1; i <= 12; i++) {
            monthArray[i - 1] = Integer.toString(i);
        }
        dateMonth_pv.setItemArray(monthArray);

        GregorianCalendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        dateMonth_pv.setSelectedItem(month, false);
        dateYear_pv.setSelectedItem(year - baseYear, false);
        setDayList(year, month);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dateDay_pv.setSelectedItem(day - 1, false);

        PickerView.OnSelectedItemChangeListener onSelectedItemChangeListener = new PickerView.OnSelectedItemChangeListener() {
            @Override
            public void onSelectedItemChange(int itemIndex, boolean fromUser) {

            }

            @Override
            public void onSelectedItemFinalChange(int itemIndex, boolean fromUser) {
                setDayList(dateYear_pv.getItemSelected() + baseYear, dateMonth_pv.getItemSelected());
            }
        };
        dateYear_pv.setOnSelectedItemChangeListener(onSelectedItemChangeListener);
        dateMonth_pv.setOnSelectedItemChangeListener(onSelectedItemChangeListener);


        back_bt.setOnClickListener(v -> presenter.onBack());
        next_bt.setOnClickListener(v -> presenter.onNext(dateYear_pv.getItemSelected() + baseYear, dateMonth_pv.getItemSelected() + 1, dateDay_pv.getItemSelected() + 1));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    void setDayList(int year, int month) {
        Calendar calendar = new GregorianCalendar(year, month, 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String[] daysArray = new String[daysInMonth];
        for (int i = 1; i <= daysInMonth; i++) {
            daysArray[i - 1] = Integer.toString(i);
        }
        dateDay_pv.setItemArray(daysArray);
        if (dateDay_pv.getItemSelected() >= daysInMonth) {
            dateDay_pv.setSelectedItem(daysInMonth - 1, true);
        }
        dateDay_pv.invalidate();
    }
}
