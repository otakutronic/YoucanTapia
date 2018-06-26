package com.mji.tapia.youcantapia.features.user.ui.setting.food;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.User;
import com.mji.tapia.youcantapia.widget.PickerView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class FoodSettingFragment extends BaseFragment implements FoodSettingContract.View {

    static public final String TAG = "FoodSettingFragment";

    FoodSettingPresenter presenter;

    static public FoodSettingFragment newInstance() {
        FoodSettingFragment timeSettingFragment = new FoodSettingFragment();
        timeSettingFragment.setArguments(new Bundle());
        return timeSettingFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new FoodSettingPresenter(this, new FoodSettingNavigator((AppCompatActivity) getActivity()), Injection.provideUserRepository(getContext()));
        return presenter;
    }

    @BindView(R.id.food_list) GridView food_grid;
    @BindView(R.id.arrow_down) View arrow_down;
    @BindView(R.id.arrow_up) View arrow_up;

    @BindView(R.id.back) View back_bt;
    @BindView(R.id.next) View next_bt;

    private FoodSettingAdapter foodSettingAdapter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_user_food_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);


        back_bt.setOnClickListener(view1 -> presenter.onBack());
        next_bt.setOnClickListener(v -> presenter.onNext(foodSettingAdapter.getSelectedItems()));

        foodSettingAdapter = new FoodSettingAdapter(getContext());

        food_grid.setAdapter(foodSettingAdapter);
        food_grid.setOnItemClickListener((adapterView, view12, i, l) -> {
            presenter.onFoodChange();
            foodSettingAdapter.toggleItem(i);
        });


        food_grid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                View v = food_grid.getChildAt(0);
                View vlast = food_grid.getChildAt(i1 - 1);
                int top = (v == null) ? 0 : (v.getTop() - food_grid.getPaddingTop());
                int bottom = (vlast == null) ? 0 : (vlast.getBottom() - food_grid.getPaddingBottom());
                if (i == 0 && top == 0) {
                    arrow_up.setVisibility(View.INVISIBLE);
                } else {
                    arrow_up.setVisibility(View.VISIBLE);
                }

                if (i + i1 == i2 && bottom == food_grid.getHeight()) {
                    arrow_down.setVisibility(View.INVISIBLE);
                } else {
                    arrow_down.setVisibility(View.VISIBLE);
                }
            }
        });

        arrow_up.setOnClickListener(v-> {
            food_grid.smoothScrollToPosition(0);
        });

        arrow_down.setOnClickListener(v-> {
            food_grid.smoothScrollToPosition(foodSettingAdapter.getCount() -1);
        });
        presenter.init();
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setSelectedFood(List<User.Food> foodList) {
        foodSettingAdapter.setSelectedItems(foodList);
    }
}
