package com.mji.tapia.youcantapia.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mji.tapia.youcantapia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by andy on 4/25/2018.
 */

public class KeyboardNumberDialog extends Dialog {

    @BindView(R.id.number_keyboard)
    KeyboardNumberView keyboardView;

    private Unbinder unbinder;

    private KeyboardNumberView.OnEnterListener onEnterListener;
    private KeyboardNumberView.OnZeroKeyListener onZeroKeyListener;
    private KeyboardNumberView.OnHyphenKeyListener onHyphenKeyListener;
    private KeyboardNumberView.OnInputChangeListener onInputChangeListener;

    private String text;
    private String hint;
    public KeyboardNumberDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.keyboard_number_dialog);
        unbinder = ButterKnife.bind(this);

        keyboardView.setOnEnterListener(new KeyboardNumberView.OnEnterListener() {
            @Override
            public void onEnter(String text) {
                dismiss();
                if (onEnterListener != null) {
                    onEnterListener.onEnter(text);
                }
            }
        });


       keyboardView.setOnZeroKeyListener(new KeyboardNumberView.OnZeroKeyListener() {
            @Override
            public void onZeroEnter(String text) {
                if (onZeroKeyListener != null) {
                    onZeroKeyListener.onZeroEnter(text);
                }
            }
        });

        keyboardView.setOnHyphenKeyListener(new KeyboardNumberView.OnHyphenKeyListener() {
            @Override
            public void onHyphenEnter(String text) {
                if (onHyphenKeyListener != null) {
                    onHyphenKeyListener.onHyphenEnter(text);
                }
            }
        });

        keyboardView.setOnInputChangeListener(new KeyboardNumberView.OnInputChangeListener() {
            @Override
            public void onInputChange(String text) {
                if (onInputChangeListener!= null) {
                    onInputChangeListener.onInputChange(text);
                }
            }
        });

        if (text != null) {
            keyboardView.setInput(text);
        }
    }

    public void setText(String text) {
        if (keyboardView != null) {
            keyboardView.setInput(text);
        } else {
            this.text = text;
        }
    }

    public void setHint(String text) {
        if (keyboardView != null) {
            keyboardView.setHint(text);
        } else {
            this.hint = text;
        }
    }

    public void setOnEnterListener(KeyboardNumberView.OnEnterListener onEnterListener) {
        this.onEnterListener = onEnterListener;
    }

    public void setOnZeroKeyListener(KeyboardNumberView.OnZeroKeyListener onZeroKeyListener) {
        this.onZeroKeyListener = onZeroKeyListener;
    }

    public void setOnInputChangeListener(KeyboardNumberView.OnInputChangeListener onInputChangeListener) {
        this.onInputChangeListener = onInputChangeListener;
    }

}
