package com.mji.tapia.youcantapia.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sami on 4/6/2018.
 */

public class TapiaDialog extends AlertDialog{

    @BindView(R.id.msg_tv)
    TextView msg_tv;

    @BindView(R.id.positive_bt)
    Button positive_bt;

    @BindView(R.id.negative_bt)
    Button negative_bt;

    @BindView(R.id.logo)
    ImageView icon_iv;

    @BindView(R.id.spacer)
    View spacer;

    public enum Type {
        ERROR,
        WARNING,
        NORMAL
    }

    private Type type;

    private String message;

    private String positiveText;

    private Runnable positiveRunnable;

    private String negativeText;

    private Runnable negativeRunnable;

    TapiaAudioManager tapiaAudioManager;

    protected TapiaDialog(Context context) {
        super(context);
    }

    protected TapiaDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected TapiaDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared_dialog);
        if(getWindow() != null) {
            getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
//            getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            getWindow().setLayout(990, 557);
            getWindow().setGravity(Gravity.CENTER);
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        ButterKnife.bind(this);

        tapiaAudioManager = Injection.provideTapiaAudioManager(getContext());

        setCancelable(false);
        if(message != null) {
            msg_tv.setText(message);
        }

        if(positiveText == null){
            positive_bt.setVisibility(View.GONE);
        } else {
            positive_bt.setText(positiveText);
        }

        if(negativeText == null) {
            negative_bt.setVisibility(View.GONE);
        } else {
            negative_bt.setText(negativeText);
        }

        positive_bt.setOnClickListener(view -> {
            tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
            if(isShowing()) {
                if(positiveRunnable != null) {
                    positiveRunnable.run();
                }
                dismiss();
            }
        });

        negative_bt.setOnClickListener(view -> {
            tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
            if(isShowing()) {
                if(negativeRunnable != null) {
                    negativeRunnable.run();
                }
                dismiss();
            }
        });

        init();
    }

    private void init() {
        switch (type) {
            case ERROR:
                icon_iv.setVisibility(View.VISIBLE);
                spacer.setVisibility(View.VISIBLE);
                icon_iv.setImageResource(R.drawable.dialog_error_icon);
                break;
            case NORMAL:
                icon_iv.setVisibility(View.GONE);
                spacer.setVisibility(View.GONE);
                icon_iv.setImageResource(0);
                break;
            case WARNING:
                icon_iv.setVisibility(View.VISIBLE);
                spacer.setVisibility(View.VISIBLE);
                icon_iv.setImageResource(R.drawable.dialog_warning_icon);
                break;
        }
        if(positive_bt.getVisibility() == View.VISIBLE && negative_bt.getVisibility() == View.VISIBLE) {
            positive_bt.setBackgroundResource(R.drawable.shared_button_small_yellow);
            positive_bt.setTextColor(getContext().getResources().getColor(R.color.colorLightText));
            negative_bt.setBackgroundResource(R.drawable.shared_button_small_clear_yellow);
            negative_bt.setTextColor(getContext().getResources().getColor(R.color.colorDarkText));
        } else {
            switch (type) {
                case ERROR:
                    positive_bt.setBackgroundResource(R.drawable.shared_button_small_red);
                    negative_bt.setBackgroundResource(R.drawable.shared_button_small_red);
                    break;
                case NORMAL:

                    positive_bt.setBackgroundResource(R.drawable.shared_button_small_primary);
                    negative_bt.setBackgroundResource(R.drawable.shared_button_small_primary);
                    break;
                case WARNING:
                    positive_bt.setBackgroundResource(R.drawable.shared_button_small_primary);
                    negative_bt.setBackgroundResource(R.drawable.shared_button_small_primary);
                    break;
            }
        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getWindow() != null) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }

    public void setMsgTextSize(float size) {
        if (msg_tv != null)
            msg_tv.setTextSize(size);
    }

    public void enlargeMsgBody(int value){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) msg_tv.getLayoutParams();
        params.setMarginStart(value);
        params.setMarginEnd(value);
        msg_tv.setLayoutParams(params);
    }

    @Override
    public void show() {
        super.show();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

//Clear the not focusable flag from the window
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    public static class Builder {

        Context context;

        String message;

        String positiveText;

        Runnable positiveRunnable;

        String negativeText;

        Runnable negativeRunnable;

        Type type = Type.NORMAL;



        public Builder(Context context) {
            this.context = context;
        }

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setPositiveButton(String text, Runnable runnable) {
            this.positiveText = text;
            this.positiveRunnable = runnable;
            return this;
        }

        public Builder setNegativeButton(String text, Runnable runnable) {
            this.negativeText = text;
            this.negativeRunnable = runnable;
            return this;
        }

        public TapiaDialog create() {
            TapiaDialog tapiaDialog = new TapiaDialog(context);
            tapiaDialog.message = message;
            tapiaDialog.positiveText = positiveText;
            tapiaDialog.negativeText = negativeText;
            tapiaDialog.positiveRunnable =positiveRunnable;
            tapiaDialog.negativeRunnable = negativeRunnable;
            tapiaDialog.type = type;
            return tapiaDialog;
        }

        public TapiaDialog show() {
            TapiaDialog tapiaDialog = create();
            tapiaDialog.show();
            return tapiaDialog;
        }
    }
}
