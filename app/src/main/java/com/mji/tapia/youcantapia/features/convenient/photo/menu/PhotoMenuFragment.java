package com.mji.tapia.youcantapia.features.convenient.photo.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.photo.camera.CameraFragment;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;
import com.mji.tapia.youcantapia.widget.TapiaDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

/**
 * Created by Usman on 2018/05/01.
 */

public class PhotoMenuFragment extends BaseFragment implements PhotoMenuContract.View {

    static public final String TAG = "PhotoMenuFragment";

    private PhotoMenuPresenter presenter;
    private TTSManager ttsManager;
    private Disposable autoSpeechDisposable;

    @BindView(R.id.camera)
    View camera_view;

    @BindView(R.id.warningIcon)
    View warningIcon;

    @BindView(R.id.galary)
    View galary_view;

    private Unbinder unbinder;

    static public PhotoMenuFragment newInstance() {
        return new PhotoMenuFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new PhotoMenuPresenter(this, new PhotoMenuNavigator((AppCompatActivity) getActivity()));
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_menu_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();
        ttsManager = Injection.provideTTSManager(getActivity());
        ttsManager.createSession(getResources().getString(R.string.photo_menu_tts)).start();
        camera_view.setEnabled(true);
        galary_view.setEnabled(true);
        File[] fileList = new File(CameraFragment.PHOTO_DIR_SMALL).listFiles();
        if (fileList != null && fileList.length >= 1000) {
            warningIcon.setVisibility(View.VISIBLE);
        }

        camera_view.setOnClickListener(v -> {
            presenter.playButtonSound();
            if (warningIcon.isShown()) {
                ttsManager.createSession(getResources().getString(R.string.photo_number_of_photo_check_tts)).start();
                TapiaDialog tapiaDialog = new TapiaDialog.Builder(getContext())
                        .setType(TapiaDialog.Type.WARNING)
                        .setNegativeButton(getString(R.string.photo_warning_dialog_no), () -> {
                            presenter.playButtonSound();
                            presenter.onPhotoGalarySelect();
                        })
                        .setMessage(getString(R.string.photo_warning_dialog_msg))
                        .show();
                tapiaDialog.setMsgTextSize(30);
                tapiaDialog.enlargeMsgBody(10);
            } else {
                presenter.onCameraSelect();
            }
        });
        galary_view.setOnClickListener(v -> {
            presenter.playButtonSound();
            presenter.onPhotoGalarySelect();
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (autoSpeechDisposable != null && !autoSpeechDisposable.isDisposed()) {
            autoSpeechDisposable.dispose();
        }
    }
}
