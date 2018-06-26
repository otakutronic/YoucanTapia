package com.mji.tapia.youcantapia.features.convenient.photo.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;
import com.mji.tapia.youcantapia.widget.TapiaDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.reactivex.disposables.Disposable;

/**
 * Created by Usman on 2018/05/01.
 */

public class CameraFragment extends BaseFragment implements CameraContract.View {
    public static final String TAG = "CameraFragment";
    public static final String PHOTO_DIR = Environment.getExternalStorageDirectory().toString() + "/YouCan/Photo/";
    public static final String PHOTO_DIR_SMALL = Environment.getExternalStorageDirectory().toString() + "/YouCan/Photo_Small/";
    private CameraPresenter presenter;
    private byte[] mPictureData;
    private Camera mCamera;
    private boolean previewing = false;
    private Bitmap mCameraPicture;
    private Disposable autoSpeechDisposable;

    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;

    @BindView(R.id.countDownTv)
    TextView countDownTv;

    @BindView(R.id.finishBtn)
    Button finishBtn;

    @BindView(R.id.retakeBtn)
    Button retakeBtn;

    static public CameraFragment newInstance() {
        return new CameraFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new CameraPresenter(this, new CameraNavigator((AppCompatActivity) getActivity()), Injection.provideTTSManager(getContext()));
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camera_fragment, container, false);
        ButterKnife.bind(this, view);
        presenter.init();
        prepareCamera();
        presenter.getReady();

        return view;
    }

    private void prepareCamera() {
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    mCamera = Camera.open(0);
                } catch (RuntimeException e) {
                    Log.i(TAG, "Camera failed to open: " + e.getLocalizedMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (previewing) {
                    mCamera.stopPreview();
                    previewing = false;
                }

                try {
                    mCamera.setPreviewDisplay(holder);
                    mCamera.startPreview();
                    previewing = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mCamera != null) {
                    mCamera.stopPreview();
                    mCamera.release();
                    mCamera = null;
                    previewing = false;
                }
            }
        });

        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        createPhotoDir(PHOTO_DIR);
        createPhotoDir(PHOTO_DIR_SMALL);
    }

    private void createPhotoDir(String path) {
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
    }

    private void savePhotoToDir() {
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = "IMG_" + currentTime + ".jpeg";
        File dest = new File(new File(PHOTO_DIR), fileName);
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inScaled = false;
        try {
            OutputStream fOut = new FileOutputStream(dest);
            mCameraPicture.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        dest = new File(new File(PHOTO_DIR_SMALL), fileName);
        try {
            Bitmap resized = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(mPictureData, 0, mPictureData.length), 320, 240, true);
            resized = getFlippedImage(resized);
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.JPEG, 100, blob);
            FileOutputStream fos = new FileOutputStream(dest);
            fos.write(blob.toByteArray());
            fos.close();
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void takePicture() {
        finishBtn.setEnabled(true);
        if (mCamera != null) {
            mCamera.enableShutterSound(false);
            presenter.playSound("shared/se/camera-shutter.mp3");
            mCamera.takePicture(() -> {
            }, (data, camera) -> {
            }, (data, camera) -> {
                mCameraPicture = BitmapFactory.decodeByteArray(data, 0, data.length);
                mCameraPicture = getFlippedImage(mCameraPicture);
                mPictureData = data;
                savePhotoToDir();
                finishBtn.setOnClickListener(v -> {
                    presenter.playSound("shared/se/button_click.mp3");
                    presenter.onGalarySelect();
                });
                finishBtn.setVisibility(View.VISIBLE);
                presenter.playSpeech(getResources().getString(R.string.photo_shooting_end_screen_tts));
                retakeBtn.setOnClickListener(v -> {
                    presenter.playSound("shared/se/button_click.mp3");
                    finishBtn.setEnabled(false);
                    mCamera.startPreview();
                    presenter.getReady();

                });
                retakeBtn.setVisibility(View.VISIBLE);
            });
        }
    }

    private Bitmap getFlippedImage(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @Override
    public void showCountDownPicture(String countText) {
        countDownTv.setVisibility(View.VISIBLE);
        countDownTv.setText(countText);
    }

    @Override
    public void showDialogForCrossingLimitaion() {
        TapiaDialog tapiaDialog = new TapiaDialog.Builder(getContext())
                .setType(TapiaDialog.Type.WARNING)
                .setNegativeButton(getString(R.string.photo_warning_dialog_no), () -> {
                    presenter.onGalarySelect();
                })
                .setMessage(getString(R.string.photo_warning_dialog_msg))
                .create();
        tapiaDialog.setMsgTextSize(30);
        tapiaDialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (autoSpeechDisposable != null && !autoSpeechDisposable.isDisposed()) {
            autoSpeechDisposable.dispose();
        }
    }
}
