package com.mji.tapia.youcantapia.features.setting.profile;

import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.beloo.widget.chipslayoutmanager.layouter.breaker.IRowBreaker;
import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class ProfileSettingFragment extends BaseFragment implements ProfileSettingContract.View {

    static public final String TAG = "ProfileSettingFragment";

    ProfileSettingPresenter presenter;

    static public ProfileSettingFragment newInstance() {
        return new ProfileSettingFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new ProfileSettingPresenter(this, new ProfileSettingNavigator((AppCompatActivity) getActivity()), Injection.provideUserRepository(getContext()));
        return presenter;
    }

    @BindView(R.id.name_input) TextView name_tv;
    @BindView(R.id.birthday_input) TextView birthday_tv;
    @BindView(R.id.food_list) RecyclerView food_rv;
    @BindView(R.id.hobby_list) RecyclerView hobby_rv;
    @BindView(R.id.food) View food_v;
    @BindView(R.id.hobby) View hobby_v;

    SimpleDateFormat dateFormat;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_profile_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        dateFormat = new SimpleDateFormat("yyyy' 年 'MM' 月 'dd' 日'", Locale.JAPANESE);



        food_rv.setLayoutManager(getLayoutManager());
        hobby_rv.setLayoutManager(getLayoutManager());


        food_v.setOnClickListener(v -> presenter.onFoodSelect());
        hobby_v.setOnClickListener(v -> presenter.onHobbySelect());
        food_rv.setOnClickListener(v -> presenter.onFoodSelect());
        hobby_rv.setOnClickListener(v -> presenter.onHobbySelect());
        name_tv.setOnClickListener(v -> presenter.onNameSelect());
        birthday_tv.setOnClickListener(v -> presenter.onBirthdaySelect());
        presenter.init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onUser(User user) {
        name_tv.setText(user.name);
        birthday_tv.setText(dateFormat.format(user.birthday));

        List<String> foodList = new ArrayList<>();
        for (User.Food f : user.favoriteFood) {
            foodList.add(getString(f.getResId()));
        }
        ProfileSettingTagAdapter foodAdapter = new ProfileSettingTagAdapter(foodList);
        food_rv.setAdapter(foodAdapter);

        List<String> hobbyList = new ArrayList<>();
        for (User.Hobby h : user.hobbies) {
            hobbyList.add(getString(h.getResId()));
        }
        ProfileSettingTagAdapter hobbyAdapter = new ProfileSettingTagAdapter(hobbyList);
        hobby_rv.setAdapter(hobbyAdapter);
    }

    private ChipsLayoutManager getLayoutManager() {
        return ChipsLayoutManager.newBuilder(getContext())
                .setScrollingEnabled(false)
                .build();
    }
}
