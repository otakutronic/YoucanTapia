package com.mji.tapia.youcantapia.features.music.song.ui.player;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;
import com.mji.tapia.youcantapia.features.music.song.model.MusicSong;


import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MusicPlayerFragment extends BaseFragment implements MusicPlayerContract.View, MediaPlayer.OnCompletionListener, Animator.AnimatorListener {

    static public final String TAG = "MusicPlayerFragment";

    static private final String MUSIC_START_ID = "MUSIC_START_ID";
    static private final String CATEGORY = "CATEGORY";

    static private final int NORMAL = 0;
    static private final int PAUSING = 1;
    static private final int RESUMING = 2;

    private int mode;

    MusicPlayerPresenter presenter;

    static public MusicPlayerFragment newInstance(MusicRepository.Category category, MusicSong song) {
        MusicPlayerFragment musicCategoryFragment = new MusicPlayerFragment();
        musicCategoryFragment.setArguments(new Bundle());
        if (song != null) {
            musicCategoryFragment.getArguments().putInt(MUSIC_START_ID,song.getUid());
        }
        musicCategoryFragment.getArguments().putString(CATEGORY,category.name());
        return musicCategoryFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new MusicPlayerPresenter(this, new MusicPlayerNavigator((AppCompatActivity) getActivity()), getArguments().getInt(MUSIC_START_ID, - 1), MusicRepository.Category.valueOf(getArguments().getString(CATEGORY)),Injection.provideMusicRepository(getContext()), Injection.provideTapiaAudioManager(getContext()), Injection.provideTTSManager(getContext()));
        return presenter;
    }

    private Unbinder unbinder;

    private MediaPlayer mediaPlayer;

    private List<MusicSong> musicSongList;

    private int playerIndex;

    private int volume = 10;

    boolean isRepeat;

    @BindView(R.id.minus_bt) TextView minus_bt;
    @BindView(R.id.volume) TextView value_tv;
    @BindView(R.id.plus_bt) TextView plus_bt;
    @BindView(R.id.repeat_on) TextView repeatOn_bt;
    @BindView(R.id.repeat_off) TextView repeatOff_bt;
    @BindView(R.id.play_anim) LottieAnimationView play_anim;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_song_player_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);


        mediaPlayer = new MediaPlayer();

        plus_bt.setOnClickListener(v -> {
            setVolume(volume + 1);
            presenter.onValueChange(volume);
            presenter.onVolumeUp();
        });
        minus_bt.setOnClickListener(v -> {
            setVolume(volume - 1);
            presenter.onValueChange(volume);
            presenter.onVolumeDown();
        });

        repeatOn_bt.setOnClickListener(v -> {
            presenter.onRepeatChange();
            setRepeat(true);
        });

        repeatOff_bt.setOnClickListener(v -> {
            presenter.onRepeatChange();
            setRepeat(false);
        });


        play_anim.setOnClickListener(v -> {
            if (isPaused)
                resumePlayer();
            else
                pausePlayer();
        });

        play_anim.setAnimation("lottie/playing_pause1.json", LottieAnimationView.CacheStrategy.Weak);
        play_anim.setRepeatCount(Animation.INFINITE);

        presenter.init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        play_anim.addAnimatorListener(this);
        if (!isPaused) {
            play_anim.playAnimation();
        }
    }

    @Override
    public void onPause() {
        pausePlayer();
        super.onPause();
        play_anim.removeAnimatorListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mediaPlayer.release();
    }

    @Override
    public void setCategoryList(List<MusicSong> musicSongList) {
        playerIndex = 0;
        this.musicSongList = musicSongList;
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath() + musicSongList.get(playerIndex).getMusicPath());
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setVolume(int volume) {
        this.volume = volume;
        value_tv.setText(Integer.toString(volume));
    }

    @Override
    public void setRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
        repeatOn_bt.setEnabled(!isRepeat);
        repeatOff_bt.setEnabled(isRepeat);
        if (isRepeat) {
            repeatOn_bt.setTextColor(getResources().getColor(R.color.colorLightText));
            repeatOff_bt.setTextColor(getResources().getColor(R.color.colorDarkText));
        } else {
            repeatOn_bt.setTextColor(getResources().getColor(R.color.colorDarkText));
            repeatOff_bt.setTextColor(getResources().getColor(R.color.colorLightText));
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (!isRepeat) {
            playerIndex++;
        }
        if(playerIndex >= musicSongList.size()){
            if (getActivity() != null) {
                getActivity().onBackPressed();
                return;
            }
        }
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath() + musicSongList.get(playerIndex).getMusicPath());
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    boolean isPaused = false;
    private int pausedPosition = 0;
    void pausePlayer() {
        if (!isPaused) {
            pausedPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            isPaused = true;
        }
    }

    void resumePlayer() {
        if (isPaused) {
            mediaPlayer.seekTo(pausedPosition);
            mediaPlayer.start();
            isPaused = false;
            mode = RESUMING;
            play_anim.setAnimation("lottie/playing_pause.json", LottieAnimationView.CacheStrategy.Weak);
            play_anim.setSpeed(-1);
            play_anim.setProgress(1);
            play_anim.playAnimation();
        }
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {

    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {
        switch (mode) {
            case NORMAL:
                if (isPaused) {
                    play_anim.setAnimation("lottie/playing_pause.json", LottieAnimationView.CacheStrategy.Weak);
                    play_anim.playAnimation();
                    mode = PAUSING;
                }
                break;
            case PAUSING:
                play_anim.pauseAnimation();
                break;
            case RESUMING:
                play_anim.setAnimation("lottie/playing_pause1.json", LottieAnimationView.CacheStrategy.Weak);
                play_anim.setSpeed(1);
                play_anim.setProgress(0);
                play_anim.playAnimation();
                mode = NORMAL;
                break;
        }

    }
}
