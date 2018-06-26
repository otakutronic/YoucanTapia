package com.mji.tapia.youcantapia.features.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mji.tapia.youcantapia.BaseActivity;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.marubatsu.MarubatsuMenuFragment;
import com.mji.tapia.youcantapia.features.game.menu.GameMenuFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.TouchMenuFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;
import com.mji.tapia.youcantapia.util.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class GameActivity extends BaseActivity implements GameContract.View {

    public static final String BACKSTACK = "GAME";

    GamePresenter presenter;

    @BindView(R.id.back)
    View back;

    @BindView(R.id.fragment_layout) View fragment_view;


    @Override
    protected BasePresenter injectPresenter() {
        presenter = new GamePresenter(this, new GameNavigator(this));
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        ButterKnife.bind(this);
        presenter.init();

        if (savedInstanceState == null) {
            String target = getIntent().getStringExtra("target");
            if (target == null) {
                ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), GameMenuFragment.newInstance(), R.id.fragment_layout, GameMenuFragment.TAG, R.animator.fade_in, R.animator.fade_out);
            } else {
                switch (target) {
                    case "marubatsu":
                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), MarubatsuMenuFragment.newInstance(), R.id.fragment_layout, MarubatsuMenuFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        break;
                    case "touch":
                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), TouchMenuFragment.newInstance(), R.id.fragment_layout, TouchMenuFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        break;
                    default:
                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), GameMenuFragment.newInstance(), R.id.fragment_layout, GameMenuFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        break;
                }
            }

        }

        back.setOnClickListener(v -> presenter.onBack());
        back.setVisibility(View.INVISIBLE);
    }

    private boolean isHidden = false;

    @Override
    protected void onResume() {
        super.onResume();
        fadeIn();
        if(!isHidden && back.getVisibility() == View.INVISIBLE) {
            showBackButton();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        back.setVisibility(View.INVISIBLE);
    }

    @Override
    public Completable hideBackButton() {
        isHidden = true;
        return Completable.fromSingle(AnimationUtils.jumpOut(back));
    }

    @Override
    public Completable showBackButton() {
        isHidden = false;
        return Completable.fromSingle(AnimationUtils.jumpIn(back));
    }

    @Override
    public void onBackPressed() {
        presenter.onBack();
    }

    Completable fadeIn() {
        return Completable.fromSingle(AnimationUtils.fadeIn(fragment_view, 500));
    }

    Completable fadeout() {
        return Completable.fromSingle(AnimationUtils.fadeOut(fragment_view, 500));
    }
}
