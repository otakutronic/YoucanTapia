package com.mji.tapia.youcantapia.features.convenient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mji.tapia.youcantapia.BaseActivity;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.clock.ClockFragment;
import com.mji.tapia.youcantapia.features.convenient.menu.ConvenientMenuFragment;
import com.mji.tapia.youcantapia.features.convenient.phonebook.ui.PhoneBookFragment;
import com.mji.tapia.youcantapia.features.convenient.photo.camera.CameraFragment;
import com.mji.tapia.youcantapia.features.convenient.photo.gallery.GalleryFragment;
import com.mji.tapia.youcantapia.features.convenient.photo.menu.PhotoMenuFragment;
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

public class ConvenientActivity extends BaseActivity implements ConvenientContract.View {

    public static final String BACKSTACK = "CONVENIENT";

    ConvenientPresenter presenter;

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new ConvenientPresenter(this, new ConvenientNavigator(this));
        return presenter;
    }

    @BindView(R.id.back_left)
    View backleft_v;

    @BindView(R.id.back)
    View back_v;

    @BindView(R.id.fragment_layout) View fragment_view;

    private boolean isHidden = true;

    private boolean isLeftHidden = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convenient_activity);
        ButterKnife.bind(this);
        presenter.init();

        backleft_v.setVisibility(View.INVISIBLE);
        back_v.setVisibility(View.INVISIBLE);

        backleft_v.setOnClickListener(v -> onBackPressed());
        back_v.setOnClickListener(v -> onBackPressed());

        if (savedInstanceState == null) {
            String target = getIntent().getStringExtra("target");
            if (target == null) {
                ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), ConvenientMenuFragment.newInstance(), R.id.fragment_layout, ConvenientMenuFragment.TAG);
            } else {
                switch (target) {
                    case "clock":
                        isHidden = false;
                        isLeftHidden = true;
                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), ClockFragment.newInstance(), R.id.fragment_layout, ClockFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        break;
                    case "phone_book":
                        isHidden = false;
                        isLeftHidden = true;
                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), PhoneBookFragment.newInstance(), R.id.fragment_layout, PhoneBookFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        break;
                    case "photo":
                        isHidden = false;
                        isLeftHidden = true;
                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), PhotoMenuFragment.newInstance(), R.id.fragment_layout, PhotoMenuFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        break;
                    case "photo_gallery":
                        isHidden = false;
                        isLeftHidden = true;
                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), GalleryFragment.newInstance(), R.id.fragment_layout, GalleryFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        break;
                    case "photo_take":
                        isHidden = true;
                        isLeftHidden = true;
                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), CameraFragment.newInstance(), R.id.fragment_layout, CameraFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        break;
                    default:
                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), ConvenientMenuFragment.newInstance(), R.id.fragment_layout, ConvenientMenuFragment.TAG);
                        break;
                }
            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        fadeIn();
        if (!isHidden && back_v.getVisibility() != View.VISIBLE) {
            isHidden = true;
            showBack();
        }
        if (!isLeftHidden && backleft_v.getVisibility() != View.VISIBLE) {
            isLeftHidden = true;
            showBackLeft();
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

    @Override
    public Completable showBackLeft() {
        if (isLeftHidden) {
            isLeftHidden = false;
            return Completable.fromSingle(AnimationUtils.jumpIn(backleft_v));
        }
        else
            return Completable.complete();
    }

    @Override
    public Completable hideBackLeft() {
        if (isLeftHidden)
            return Completable.complete();
        else{
            isLeftHidden = true;
            return Completable.fromSingle(AnimationUtils.jumpOut(backleft_v));
        }
    }

    Completable fadeIn() {
        return Completable.fromSingle(AnimationUtils.fadeIn(fragment_view, 500));
    }

    Completable fadeout() {
        return Completable.fromSingle(AnimationUtils.fadeOut(fragment_view, 500));
    }
}
