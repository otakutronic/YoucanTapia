package com.mji.tapia.youcantapia.features.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mji.tapia.youcantapia.BaseActivity;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.marubatsu.MarubatsuMenuFragment;
import com.mji.tapia.youcantapia.features.game.menu.GameMenuFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.TouchMenuFragment;
import com.mji.tapia.youcantapia.features.music.karaoke.ui.KaraokeMenuFragment;
import com.mji.tapia.youcantapia.features.music.karaoke.ui.favorite.KaraokeFavoriteFragment;
import com.mji.tapia.youcantapia.features.music.menu.MusicMenuFragment;
import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;
import com.mji.tapia.youcantapia.features.music.song.ui.player.MusicPlayerFragment;
import com.mji.tapia.youcantapia.features.music.song.ui.radio_player.RadioPlayerFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;
import com.mji.tapia.youcantapia.util.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MusicActivity extends BaseActivity implements MusicContract.View {

    public static final String BACKSTACK = "MUSIC";

    MusicPresenter presenter;

    @BindView(R.id.back)
    View back;

    @BindView(R.id.fragment_layout) View fragment_view;


    @Override
    protected BasePresenter injectPresenter() {
        presenter = new MusicPresenter(this, new MusicNavigator(this));
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_activity);
        ButterKnife.bind(this);
        presenter.init();

        if (savedInstanceState == null) {
            String target = getIntent().getStringExtra("target");
            if (target == null) {
                ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), MusicMenuFragment.newInstance(), R.id.fragment_layout, MusicMenuFragment.TAG, R.animator.fade_in, R.animator.fade_out);
            } else {
                switch (target) {
                    case "song_player":
                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), MusicPlayerFragment.newInstance(MusicRepository.Category.valueOf(getIntent().getStringExtra("category")), null), R.id.fragment_layout, MarubatsuMenuFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        break;
                    case "song_menu":
                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), com.mji.tapia.youcantapia.features.music.song.ui.MusicMenuFragment.newInstance(), R.id.fragment_layout, MarubatsuMenuFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        break;
                    case "radio_player":
                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), RadioPlayerFragment.newInstance("/Music/Radio_Gym/radio_test.mp4"), R.id.fragment_layout, MarubatsuMenuFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        break;
                    case "karaoke_menu":
                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), KaraokeMenuFragment.newInstance(), R.id.fragment_layout, TouchMenuFragment.TAG, R.animator.fade_in, R.animator.fade_out);
                        break;
                    case "karaoke_favorite":
                        ActivityUtils.addFragmentWithTagToActivity(getSupportFragmentManager(), KaraokeFavoriteFragment.newInstance(), R.id.fragment_layout, KaraokeFavoriteFragment.TAG, R.animator.fade_in, R.animator.fade_out);
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
