package com.mji.tapia.youcantapia.features.today.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.widget.CustomFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by andy on 3/30/2018.
 *
 */

public class TodayMenuFragment extends BaseFragment implements TodayMenuContract.View {

    static public final String TAG = "TodayMenuFragment";

    TodayMenuPresenter presenter;

    static public TodayMenuFragment newInstance() {
        TodayMenuFragment todayMenuFragment = new TodayMenuFragment();
        return todayMenuFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new TodayMenuPresenter(this, new TodayMenuNavigator((AppCompatActivity) getActivity()));
        return presenter;
    }

    @BindView(R.id.first_button)
    CustomFontTextView first_view;

    @BindView(R.id.second_button)
    CustomFontTextView second_view;

    @BindView(R.id.third_button)
    CustomFontTextView third_view;

    @BindView(R.id.fourth_button)
    CustomFontTextView fourth_view;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.today_menu_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();

        setButtons();

        return view;
    }

    private void setButtons() {
        first_view.setOnClickListener(v -> presenter.onFirstSelect());
        second_view.setOnClickListener(v -> presenter.onSecondSelect());
        third_view.setOnClickListener(v -> presenter.onThirdSelect());
        fourth_view.setOnClickListener(v -> presenter.onFourthSelect());

        String firstPart = (String) first_view.getText().subSequence(0, 3);
        String btnOneSecondPart = (String) first_view.getText().subSequence(3, first_view.getText().length());
        String btnTwoSecondPart = (String) second_view.getText().subSequence(3, second_view.getText().length());
        String btnThreeSecondPart = (String) third_view.getText().subSequence(3, third_view.getText().length());
        String btnFourSecondPart = (String) fourth_view.getText().subSequence(3, fourth_view.getText().length());

        int btnOneTextColor = getResources().getColor(R.color.red);
        int btnTwoTextColor = getResources().getColor(R.color.green);
        int btnThreeTextColor = getResources().getColor(R.color.blue);
        int btnFourTextColor = getResources().getColor(R.color.lt_green);

        first_view.setText(Html.fromHtml("<font color='#000000'>" + firstPart + "</font><font color=" + btnOneTextColor + ">" + btnOneSecondPart + "</font>"));
        second_view.setText(Html.fromHtml("<font color='#000000'>" + firstPart + "</font><font color=" + btnTwoTextColor + ">" + btnTwoSecondPart + "</font>"));
        third_view.setText(Html.fromHtml("<font color='#000000'>" + firstPart + "</font><font color=" + btnThreeTextColor + ">" + btnThreeSecondPart + "</font>"));
        fourth_view.setText(Html.fromHtml("<font color='#000000'>" + firstPart + "</font><font color=" + btnFourTextColor + ">" + btnFourSecondPart + "</font>"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
