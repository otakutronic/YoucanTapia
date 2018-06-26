package com.mji.tapia.youcantapia.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.mji.tapia.youcantapia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 4/25/2018.
 */

public class KeyboardDialog extends Dialog {

    @BindView(R.id.keyboard)
    KeyboardView keyboardView;

    private Unbinder unbinder;


    private KeyboardView.OnEnterListener onEnterListener;
    private KeyboardView.OnBackListener onBackListener;
    private KeyboardView.OnInputChangeListener onInputChangeListener;

    private String hint;
    private String text;
    public KeyboardDialog(Context context) {
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
        setContentView(R.layout.keyboard_dialog);
        unbinder = ButterKnife.bind(this);

        keyboardView.setOnBackListener(new KeyboardView.OnBackListener() {
            @Override
            public void onBack() {
                dismiss();
                if (onBackListener != null) {
                    onBackListener.onBack();
                }
            }
        });

        keyboardView.setOnEnterListener(new KeyboardView.OnEnterListener() {
            @Override
            public void onEnter(String text) {
                dismiss();
                if (onEnterListener != null) {
                    onEnterListener.onEnter(text);
                }
            }
        });

        keyboardView.setOnInputChangeListener(new KeyboardView.OnInputChangeListener() {
            @Override
            public void onInputChange(String text) {
                if (onInputChangeListener!= null) {
                    onInputChangeListener.onInputChange(text);
                }
            }
        });

        if (hint != null) {
            keyboardView.setHint(hint);
        }

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
        Log.e("TAG", "SETTING HINT " + text);
        if (keyboardView != null) {
            keyboardView.setHint(text);
        } else {
            this.hint = text;
        }
    }

    public void setOnEnterListener(KeyboardView.OnEnterListener onEnterListener) {
        this.onEnterListener = onEnterListener;
    }

    public void setOnBackListener(KeyboardView.OnBackListener onBackListener) {
        this.onBackListener = onBackListener;
    }

    public void setOnInputChangeListener(KeyboardView.OnInputChangeListener onInputChangeListener) {
        this.onInputChangeListener = onInputChangeListener;
    }

}
