package com.mji.tapia.youcantapia.features.convenient.photo.slide_show;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.photo.camera.CameraFragment;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Usman on 2018/05/10.
 */

public class SlideShowFragment extends BaseFragment implements SlideShowContract.View {
    static public final String TAG = "SlideShowFragment";

    private SlideShowPresenter presenter;
    private Handler handler;
    private Runnable runnable;
    private ArrayList<String> fileList = new ArrayList<>();
    private int counter = 0;
    private boolean isPaused = false;

    @BindView(R.id.itemId)
    ImageView imageView;

    @BindView(R.id.stopBtn)
    Button stopBtn;

    @BindView(R.id.returnBtn)
    Button returnBtn;

    static public SlideShowFragment newInstance() {
        return new SlideShowFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new SlideShowPresenter(this, new SlideShowNavigator((AppCompatActivity) getActivity()), Injection.provideTapiaAudioManager(getActivity()));
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slide_show_fragment, null);
        ButterKnife.bind(this, view);

        File fileDir = new File(CameraFragment.PHOTO_DIR);
        File[] files = fileDir.listFiles();
        for (File file : files) {
            fileList.add(CameraFragment.PHOTO_DIR + file.getName());
        }

        presenter.playBgm();
        handler = new Handler();
        runnable = () -> {
            showNextImage();
            handler.postDelayed(runnable, 3000);
        };
        handler.postDelayed(runnable, 500);

        stopBtn.setOnClickListener(v -> {
            presenter.playButtonSound();
            if (!isPaused) {
                presenter.pauseBgm();
                stopBtn.setText(getResources().getString(R.string.photo_slide_show_playback));
                handler.removeCallbacks(runnable);
            } else {
                presenter.resumeBgm();
                handler.postDelayed(runnable, 3000);
                stopBtn.setText(getResources().getString(R.string.photo_slide_show_pause));
            }
            isPaused = !isPaused;
        });

        returnBtn.setOnClickListener(v -> {
            presenter.playButtonSound();
            presenter.stopBgm();
            presenter.onFinish();
        });

        return view;
    }

    private void showNextImage() {
        if (imageView != null) {
            imageView.setImageURI(Uri.parse(fileList.get(counter)));
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.myanim);
            imageView.startAnimation(fadeIn);
            counter++;
            if (counter >= fileList.size()) {
                counter = 0;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (imageView != null) {
            imageView.setImageURI(null);
            imageView = null;
        }
        handler.removeCallbacks(runnable);
    }
}
