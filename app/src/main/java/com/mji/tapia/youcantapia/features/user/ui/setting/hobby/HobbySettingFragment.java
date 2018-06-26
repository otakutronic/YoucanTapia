package com.mji.tapia.youcantapia.features.user.ui.setting.hobby;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class HobbySettingFragment extends BaseFragment implements HobbySettingContract.View {

    static public final String TAG = "HobbySettingFragment";

    HobbySettingPresenter presenter;

    static public HobbySettingFragment newInstance() {
        HobbySettingFragment timeSettingFragment = new HobbySettingFragment();
        timeSettingFragment.setArguments(new Bundle());
        return timeSettingFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new HobbySettingPresenter(this, new HobbySettingNavigator((AppCompatActivity) getActivity()), Injection.provideUserRepository(getContext()));
        return presenter;
    }

    @BindView(R.id.hobby_list) GridView hobby_grid;
    @BindView(R.id.arrow_down) View arrow_down;
    @BindView(R.id.arrow_up) View arrow_up;

    @BindView(R.id.back) View back_bt;
    @BindView(R.id.next) View next_bt;

    private HobbySettingAdapter hobbySettingAdapter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_user_hobby_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);


        back_bt.setOnClickListener(view1 -> presenter.onBack());
        next_bt.setOnClickListener(v -> presenter.onNext(hobbySettingAdapter.getSelectedItems()));

        hobbySettingAdapter = new HobbySettingAdapter(getContext());

        hobby_grid.setAdapter(hobbySettingAdapter);
        hobby_grid.setOnItemClickListener((adapterView, view12, i, l) -> {
            presenter.onHobbyChange();
            hobbySettingAdapter.toggleItem(i);
        });


        hobby_grid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                View v = hobby_grid.getChildAt(0);
                View vlast = hobby_grid.getChildAt(i1 - 1);
                int top = (v == null) ? 0 : (v.getTop() - hobby_grid.getPaddingTop());
                int bottom = (vlast == null) ? 0 : (vlast.getBottom() - hobby_grid.getPaddingBottom());
                if (i == 0 && top == 0) {
                    arrow_up.setVisibility(View.INVISIBLE);
                } else {
                    arrow_up.setVisibility(View.VISIBLE);
                }

                if (i + i1 == i2 && bottom == hobby_grid.getHeight()) {
                    arrow_down.setVisibility(View.INVISIBLE);
                } else {
                    arrow_down.setVisibility(View.VISIBLE);
                }
            }
        });

        arrow_up.setOnClickListener(v-> {
            hobby_grid.smoothScrollToPosition(0);
        });

        arrow_down.setOnClickListener(v-> {
            hobby_grid.smoothScrollToPosition(hobbySettingAdapter.getCount() -1);
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
    public void setSelectedHobbies(List<User.Hobby> hobbies) {
        hobbySettingAdapter.setSelectedItems(hobbies);
    }
}
