package com.mji.tapia.youcantapia.features.first_setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mji.tapia.youcantapia.BaseActivity;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.first_setting.introduction.IntroductionFragment;
import com.mji.tapia.youcantapia.features.game.menu.GameMenuFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;
import com.mji.tapia.youcantapia.util.AnimationUtils;
import com.mji.tapia.youcantapia.widget.KeyboardDialog;
import com.mji.tapia.youcantapia.widget.KeyboardView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class FirstSettingActivity extends BaseActivity implements FirstSettingContract.View {

    public static final String BACKSTACK = "FIRST_SETTING";

    public FirstSettingPresenter presenter;

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new FirstSettingPresenter(this, new FirstSettingNavigator(this), Injection.provideUserRepository(this));
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_setting_activity);
        ButterKnife.bind(this);
        presenter.init();

        if (savedInstanceState == null) {
            ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), IntroductionFragment.newInstance(), R.id.fragment_layout, IntroductionFragment.TAG);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        presenter.onBack();
    }
}
