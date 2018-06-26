package com.mji.tapia.youcantapia.features.music.karaoke.ui.player;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepository;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeSong;
import com.mji.tapia.youcantapia.features.music.karaoke.ui.category.KaraokeCategoryAdapter;
import com.mji.tapia.youcantapia.util.AnimationUtils;
import com.mji.tapia.youcantapia.widget.TapiaDialog;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class KaraokePlayerFragment extends BaseFragment implements KaraokePlayerContract.View, TextureView.SurfaceTextureListener {

    static public final String TAG = "KaraokePlayerFragment";

    static private final String VIDEO_PATH = "VIDEO_PATH";

    KaraokePlayerPresenter presenter;

    static public KaraokePlayerFragment newInstance(KaraokeSong song) {
        KaraokePlayerFragment karaokeCategoryFragment = new KaraokePlayerFragment();
        karaokeCategoryFragment.setArguments(new Bundle());
        karaokeCategoryFragment.getArguments().putString(VIDEO_PATH,song.getVideoPath());
        return karaokeCategoryFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new KaraokePlayerPresenter(this, new KaraokePlayerNavigator((AppCompatActivity) getActivity()), Injection.provideTapiaAudioManager(getContext()), Injection.provideTTSManager(getContext()), Injection.provideUserRepository(getContext()));
        return presenter;
    }

    private Unbinder unbinder;

    private String videoPath;


    private int pausedPosition = 0;

    private MediaPlayer mediaPlayer;

    private int value = 10;

    @BindView(R.id.video_view) TextureView videoView;
    @BindView(R.id.minus_bt) TextView minus_bt;
    @BindView(R.id.volume) TextView value_tv;
    @BindView(R.id.plus_bt) TextView plus_bt;
    @BindView(R.id.play_icon) View play_icon;
    @BindView(R.id.white_layer) View white_layer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_karaoke_player_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null)
            videoPath = getArguments().getString(VIDEO_PATH);

        plus_bt.setOnClickListener(v -> {
            setVolume(value + 1);
            presenter.onValueChange(value);
            presenter.onVolumeUp();
        });
        minus_bt.setOnClickListener(v -> {
            setVolume(value - 1);
            presenter.onValueChange(value);
            presenter.onVolumeDown();
        });


        videoView.setSurfaceTextureListener(this);


        videoView.setOnClickListener(v -> {
            AnimationUtils.fadeIn(white_layer,500);
            AnimationUtils.jumpIn(play_icon);
            pausedPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            mediaPlayer.seekTo(pausedPosition);
        });

        white_layer.setOnClickListener(v -> {
            AnimationUtils.fadeOut(white_layer,500);
            AnimationUtils.jumpOut(play_icon);
//            mediaPlayer.seekTo(pausedPosition);
            mediaPlayer.start();
        });
        presenter.init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        pausedPosition = mediaPlayer.getCurrentPosition();
        mediaPlayer.pause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

        mediaPlayer.release();
    }

    @Override
    public void setVolume(int volume) {
        this.value = volume;
        value_tv.setText(Integer.toString(volume));
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        Surface s = new Surface(surfaceTexture);
        try {
            mediaPlayer= new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath() + videoPath);
            mediaPlayer.setSurface(s);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mediaPlayer -> {
                presenter.onKaraokeFinish();
                new TapiaDialog.Builder(getContext())
                        .setPositiveButton(getString(R.string.music_karaoke_repeat), mediaPlayer::start)
                        .setNegativeButton(getString(R.string.music_karaoke_stop), () -> {
                            if (getActivity() != null)
                                getActivity().onBackPressed();
                        })
                        .setMessage(getString(R.string.music_karaoke_finish))
                        .show();
            });
        } catch (IllegalArgumentException | IOException | IllegalStateException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }
}
