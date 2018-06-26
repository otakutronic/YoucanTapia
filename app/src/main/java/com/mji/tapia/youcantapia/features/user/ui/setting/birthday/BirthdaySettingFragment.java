package com.mji.tapia.youcantapia.features.user.ui.setting.birthday;

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
import com.mji.tapia.youcantapia.features.first_setting.FirstSettingActivity;
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

public class BirthdaySettingFragment extends BaseFragment implements BirthdaySettingContract.View {

    static public final String TAG = "BirthdaySettingFragment";
    static int minYear = 1900;
    static int maxYear;

    BirthdaySettingPresenter presenter;

    static public BirthdaySettingFragment newInstance() {
        BirthdaySettingFragment timeSettingFragment = new BirthdaySettingFragment();
        timeSettingFragment.setArguments(new Bundle());
        return timeSettingFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        boolean isFirstSetting = false;
        if (getActivity() instanceof FirstSettingActivity) {
            isFirstSetting = true;
        }
        presenter = new BirthdaySettingPresenter(this, new BirthdaySettingNavigator((AppCompatActivity) getActivity()), Injection.provideUserRepository(getContext()), isFirstSetting);
        return presenter;
    }
    @BindView(R.id.date_year_pv) PickerView dateYear_pv;
    @BindView(R.id.date_month_pv) PickerView dateMonth_pv;
    @BindView(R.id.date_day_pv) PickerView dateDay_pv;


    @BindView(R.id.back) View back_bt;
    @BindView(R.id.next) View next_bt;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_user_birthday_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        maxYear = Calendar.getInstance().get(Calendar.YEAR) - 2;
        String[] yearArray = new String[maxYear - minYear];
        for (int i = minYear; i < maxYear; i++) {
            yearArray[i- minYear] = Integer.toString(i);
        }
        dateYear_pv.setItemArray(yearArray);

        String[] monthArray = new String[12];
        for (int i = 1; i <= 12; i++) {
            monthArray[i - 1] = Integer.toString(i);
        }
        dateMonth_pv.setItemArray(monthArray);

        int defaultYear = (maxYear - minYear)/2;
        dateYear_pv.setSelectedItem(defaultYear, false);
        setDayList(defaultYear, 1);

        PickerView.OnSelectedItemChangeListener onSelectedItemChangeListener = new PickerView.OnSelectedItemChangeListener() {
            @Override
            public void onSelectedItemChange(int itemIndex, boolean fromUser) {

            }

            @Override
            public void onSelectedItemFinalChange(int itemIndex, boolean fromUser) {
                setDayList(dateYear_pv.getItemSelected() + minYear, dateMonth_pv.getItemSelected());
            }
        };
        dateYear_pv.setOnSelectedItemChangeListener(onSelectedItemChangeListener);
        dateMonth_pv.setOnSelectedItemChangeListener(onSelectedItemChangeListener);

        back_bt.setOnClickListener(view1 -> presenter.onBack());

        next_bt.setOnClickListener(v -> presenter.onNext(dateYear_pv.getItemSelected() + minYear, dateMonth_pv.getItemSelected(), dateDay_pv.getItemSelected() + 1));

        presenter.init();
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

    @Override
    public void setBirthday(int year, int month, int day) {
        dateYear_pv.setSelectedItem(year - minYear, false);
        dateMonth_pv.setSelectedItem(month, false);
        dateDay_pv.setSelectedItem(day - 1, false);
    }
}
