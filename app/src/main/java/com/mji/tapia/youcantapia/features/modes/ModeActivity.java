package com.mji.tapia.youcantapia.features.modes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mji.tapia.youcantapia.BaseActivity;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.marubatsu.MarubatsuMenuFragment;
import com.mji.tapia.youcantapia.features.game.menu.GameMenuFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.TouchMenuFragment;
import com.mji.tapia.youcantapia.features.modes.sleep.SleepFragment;
import com.mji.tapia.youcantapia.features.modes.standby.StandbyFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;
import com.mji.tapia.youcantapia.util.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class ModeActivity extends BaseActivity implements ModeContract.View {

    public static final String BACKSTACK = "MODE";

    ModePresenter presenter;

    @BindView(R.id.fragment_layout) View fragment_view;


    @Override
    protected BasePresenter injectPresenter() {
        presenter = new ModePresenter(this, new ModeNavigator(this));
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_activity);
        ButterKnife.bind(this);
        presenter.init();

        if (savedInstanceState == null) {
            String target = getIntent().getStringExtra("target");
            if (target == null) {
                if (getIntent().getBooleanExtra("first", false)) {
                    ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), StandbyFragment.newInstance(StandbyFragment.FIRST_SETTING_CALLER), R.id.fragment_layout, StandbyFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                } else {
                    ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), StandbyFragment.newInstance(StandbyFragment.SPLASH_CALLER), R.id.fragment_layout, StandbyFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                }

            } else {
                switch (target) {
                    case "sleep":
                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), SleepFragment.newInstance(), R.id.fragment_layout, SleepFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        break;
                    case "talk":
//                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), TouchMenuFragment.newInstance(), R.id.fragment_layout, TouchMenuFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        break;
                    default:
                        if (getIntent().getBooleanExtra("first", false)) {
                            ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), StandbyFragment.newInstance(StandbyFragment.FIRST_SETTING_CALLER), R.id.fragment_layout, StandbyFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        } else {
                            ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), StandbyFragment.newInstance(StandbyFragment.SPLASH_CALLER), R.id.fragment_layout, StandbyFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        }
                        break;
                }
            }

        }
    }
}
