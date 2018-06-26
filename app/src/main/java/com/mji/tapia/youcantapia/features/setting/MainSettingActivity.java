package com.mji.tapia.youcantapia.features.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mji.tapia.youcantapia.BaseActivity;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.first_setting.introduction.IntroductionFragment;
import com.mji.tapia.youcantapia.features.setting.menu.SettingMenuFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;
import com.mji.tapia.youcantapia.util.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MainSettingActivity extends BaseActivity implements MainSettingContract.View {

    public static final String BACKSTACK = "MAIN_SETTING";

    MainSettingPresenter presenter;

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new MainSettingPresenter(this, new MainSettingNavigator(this));
        return presenter;
    }

    @BindView(R.id.fragment_layout) View fragment_view;

    @BindView(R.id.back_left)
    View back_v;

    private boolean isHidden = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        ButterKnife.bind(this);
        presenter.init();

        back_v.setVisibility(View.INVISIBLE);
        back_v.setOnClickListener(v -> onBackPressed());

        if (savedInstanceState == null) {
            ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), SettingMenuFragment.newInstance(), R.id.fragment_layout, SettingMenuFragment.TAG);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        fadeIn();
        if(!isHidden && back_v.getVisibility() == View.INVISIBLE) {
            isHidden = true;
            showBack();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        presenter.onBack();
    }

    @Override
    public Completable showBack() {
        if (isHidden) {
            isHidden = false;
            return Completable.fromSingle(AnimationUtils.jumpIn(back_v));
        }
        else
            return Completable.complete();
    }

    @Override
    public Completable hideBack() {
        if (isHidden)
            return Completable.complete();
        else{
            isHidden = true;
            return Completable.fromSingle(AnimationUtils.jumpOut(back_v));
        }
    }

    Completable fadeIn() {
        return Completable.fromSingle(AnimationUtils.fadeIn(fragment_view, 500));
    }

    Completable fadeout() {
        return Completable.fromSingle(AnimationUtils.fadeOut(fragment_view, 500));
    }
}
