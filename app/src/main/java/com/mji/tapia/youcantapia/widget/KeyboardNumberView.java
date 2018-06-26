package com.mji.tapia.youcantapia.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

/**
 * Created by andy on 4/25/2018.
 */

public class KeyboardNumberView extends View {

    static final private int OFFSET_X = 400;
    static final private int OFFSET_Y = 20;

    static final private float key_height_weight = 1;
    static final private float header_height_weight = 1;
    static final private float vertical_margin_weight = 0.5f;
    static final private float vertical_bottom_margin_weight = 1;
    static final private float vertical_top_margin_weight = 0.5f;

    static final private float horizontal_side_margin_weight = 0.5f;
    static final private float horizontal_margin_weight = 0.5f;
    static final private float key_width_weight = 1;

    static final private float text_width_weight = 3;
    static final private float large_key_width_weight = 1;

    int max = 13;
    int pressedOffset = -5;

    int textColor = getResources().getColor(R.color.colorDarkText);
    int textLightColor = getResources().getColor(R.color.colorLightText);
    int keyColor = getResources().getColor(R.color.colorCard);
    int keyPressedColor = getResources().getColor(R.color.colorCardDark);
    int enterColor = getResources().getColor(R.color.colorCard);
    int enterPressedColor = getResources().getColor(R.color.colorCardDark);
    int backColor = getResources().getColor(R.color.colorCard);
    int backPressedColor = getResources().getColor(R.color.colorCardDark);
    int inputTextColor = getResources().getColor(R.color.colorDarkText);
    int hintTextColor = Color.LTGRAY;

    int inputBarColor = Color.GRAY;

    String hint = "電話番号を入力";

    String[][] layout_keyboard = {
            {"1","4","7"},
            {"2","5","8"},
            {"3","6","9"}
    };

    String[][] currentLayout = layout_keyboard;

    Drawable delete;

    Paint keyTextPaint;
    Paint keyPressedTextPaint;
    Paint keyPaint;
    Paint inputTextPaint;
    Paint hintTextPaint;
    Paint inputBarPaint;

    Paint lightTextPaint;
    Paint keyPressedPaint;
    Paint enterKeyPaint;
    Paint enterKeyPressedPaint;
    Paint backKeyPaint;
    Paint backKeyPressedPaint;

    String enter_txt = "OK";
    String zero_txt = "0";
    String hyphen_txt = "-";

    String input = "";

    TapiaAudioManager tapiaAudioManager;

    static class Key {
        Rect rect;
        Consumer<Key> onClick;
        boolean pressed = false;
        BiConsumer<Key, Canvas> draw;

        //for inputs keys
        int row;
        int column;

        Key(Rect rect,BiConsumer<Key, Canvas> draw,  Consumer<Key> onClick) {
            this.rect = rect;
            this.draw = draw;
            this.onClick = onClick;
        }
    }
    List<Key> keyList;

    public KeyboardNumberView(Context context) {
        super(context);
        init();
    }

    public KeyboardNumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        loadKeyboardLayout();
    }

    void init() {

        delete = getResources().getDrawable(R.drawable.keyboard_delete,  null);


        keyTextPaint = new Paint();
        keyTextPaint.setAntiAlias(true);
        keyTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        keyTextPaint.setColor(textColor);
        keyTextPaint.setTextSize(40);

        lightTextPaint = new Paint(keyTextPaint);
        lightTextPaint.setColor(textLightColor);

        inputTextPaint = new Paint(keyTextPaint);
        inputTextPaint.setColor(inputTextColor);

        hintTextPaint = new Paint(inputTextPaint);
        hintTextPaint .setColor(hintTextColor);

        keyPaint = new Paint();
        keyPaint.setAntiAlias(true);
        keyPaint.setShadowLayer(8,0,4,Color.GRAY);
        setLayerType(LAYER_TYPE_SOFTWARE, keyPaint);


        keyPaint.setColor(keyColor);

        keyPressedTextPaint = new Paint(keyTextPaint);
        keyPressedTextPaint.setTextSize(50);

        keyPressedPaint = new Paint(keyPaint);
        keyPressedPaint.setColor(keyPressedColor);

        enterKeyPaint = new Paint(keyPaint);
        enterKeyPaint.setColor(enterColor);

        enterKeyPressedPaint= new Paint(enterKeyPaint);
        enterKeyPressedPaint.setColor(enterPressedColor);

        backKeyPaint = new Paint(keyPaint);
        backKeyPaint.setColor(backColor);

        backKeyPressedPaint = new Paint(backKeyPaint);
        backKeyPressedPaint.setColor(backPressedColor);

        inputBarPaint = new Paint();
        inputBarPaint.setAntiAlias(true);
        inputBarPaint.setColor(inputBarColor);

        this.setTranslationZ(50);

        tapiaAudioManager = Injection.provideTapiaAudioManager(getContext());
    }


    void loadKeyboardLayout() {
        keyList = new ArrayList<>();

        //Delete
        keyList.add(new Key(getDeleteRect(),
                this::drawDelete,
                this::onDelete));

        //- key
        keyList.add(new Key(getHyphenRect(),
                this::drawHyphenKey,
                this::onHyphenEnter));

        //0 key
        keyList.add(new Key(getZeroRect(),
                this::drawZeroKey,
                this::onZeroEnter));

        //Enter
        keyList.add(new Key(getEnterRect(),
                this::drawEnter,
                this::onEnter));

        for (int i = 0; i < currentLayout.length; i++) {
            for (int j = 0; j < currentLayout[i].length; j++) {
                Key key = new Key(getKeyRect(i,j),
                        this::drawKey,
                        k -> {
                            tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
                            if (currentLayout[k.row][k.column].equals("゚")) {
                                //onSemiVoicedSound();
                            } else if(currentLayout[k.row][k.column].equals("゙")) {
                                //onVoicedSound();
                            } else {
                                if (input.length() < max) {
                                    input = input.concat(currentLayout[k.row][k.column]);
                                }
                            }

                            invalidate();
                        });
                key.row = i;
                key.column = j;
                keyList.add(key);
            }
        }
    }

    //get rectangles
    Rect getKeyRect(int column, int row) {
        Rect rect = new Rect();
        int start = (int)((float)getWidth() / 2f - (float) getRowWidth() / 2f) + OFFSET_X;
        int left = start + column*getKeySize() + column*getHorizontalKeyMargin();
        int top = OFFSET_Y + getVerticalSideMargin() + getHeaderHeight() + getVerticalKeyMargin()* (row + 1) + getKeySize()*row;
        rect.set(left,top,left + getKeySize() + 50, top + getKeySize());
        return rect;
    }

    Rect getDeleteRect() {
        int start = (int)((float)getWidth() / 2f - (float) getHeaderRowWidth() / 2f) - 390;
        int left = start + 3 * getLargeKeyWidth() + 4 * getHorizontalKeyMargin() + getTextWidth() - 20;
        return new Rect(left, getVerticalSideMargin() + 128, left + getLargeKeyWidth(), getVerticalSideMargin() + 128 + getKeySize());
    }

    Rect getHyphenRect() {
        int start = (int)((float)getWidth() / 2f - (float) getHeaderRowWidth() / 2f) - 390;
        int left = start + 3 * getLargeKeyWidth() + 4 * getHorizontalKeyMargin() + getTextWidth() - 20;
        return new Rect(left,getVerticalSideMargin() + 237, left + getLargeKeyWidth(), getVerticalSideMargin() + 237 + getKeySize());
    }

    Rect getZeroRect() {
        int start = (int)((float)getWidth() / 2f - (float) getHeaderRowWidth() / 2f) - 390;
        int left = start + 3 * getLargeKeyWidth() + 4 * getHorizontalKeyMargin() + getTextWidth() - 20;
        return new Rect(left,getVerticalSideMargin() + 342, left + getLargeKeyWidth(), getVerticalSideMargin() + 342 + getKeySize());
    }

    Rect getEnterRect() {
        int start = (int)((float)getWidth() / 2f - (float) getHeaderRowWidth() / 2f) - 175;
        int left = start + getHorizontalKeyMargin() + getTextWidth() - 156;
        return new Rect(left, getVerticalSideMargin() + 452, left + getLargerKeyWidth(), getVerticalSideMargin() + 452 + getKeySize());
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        drawText(canvas);
        for (Key key: keyList) {
            try {
                key.draw.accept(key, canvas);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void drawKey(Key key, Canvas canvas) {
        Rect rect = new Rect(key.rect);
        if (currentLayout[key.row][key.column] == null)
            return;

        if(key.pressed) {
            rect.top = rect.top - 100 - 20;
            rect.bottom = rect.bottom - 100 + 20;
            rect.left = rect.left - 20;
            rect.right = rect.right + 20;
            Path path = new Path();
            path.addArc(new RectF(rect), 120, 300);
            path.addRect(key.rect.left, key.rect.top - 100, key.rect.right, key.rect.centerY() + 1, Path.Direction.CW);
            path.addArc(new RectF(key.rect), 0, 180);
            path.close();
            canvas.drawPath(path, keyPaint);
        } else {
            canvas.drawCircle(rect.centerX(), rect.centerY(),getKeySize()/2, keyPaint);
        }

        Rect txtBound = new Rect();

        if(key.pressed) {
            keyTextPaint.getTextBounds(currentLayout[key.row][key.column], 0, currentLayout[key.row][key.column].length(), txtBound);
            canvas.drawText(currentLayout[key.row][key.column], rect.centerX() - txtBound.left -(txtBound.right - txtBound.left)/2f, rect.centerY() - txtBound.top -(txtBound.bottom - txtBound.top)/2f, keyTextPaint);
        } else {
            keyTextPaint.getTextBounds(currentLayout[key.row][key.column], 0, currentLayout[key.row][key.column].length(), txtBound);
            canvas.drawText(currentLayout[key.row][key.column], rect.centerX() - txtBound.left -(txtBound.right - txtBound.left)/2f, rect.centerY() - txtBound.top -(txtBound.bottom - txtBound.top)/2f, keyTextPaint);
        }

    }

    //draw Keys
    void drawText(Canvas canvas) {
        int xPos = (int)((float)getWidth() / 2f - (float) getHeaderRowWidth() / 2f) + OFFSET_X + 8;
        int yPos = OFFSET_Y + getVerticalSideMargin() + getKeySize() -10;
        canvas.drawLine(xPos, yPos, xPos + getTextWidth() - 250, yPos, inputBarPaint);
        if(input.length() == 0) {
            canvas.drawText(hint, xPos, yPos - 10, hintTextPaint);
        } else {
            canvas.drawText(input, xPos, yPos - 10, inputTextPaint);
        }
    }

    void drawEnter(Key key, Canvas canvas) {
        Rect rect = new Rect(key.rect);
        if(key.pressed) {
            rect.top = rect.top - pressedOffset;
            rect.bottom = rect.bottom - pressedOffset;
            canvas.drawRoundRect(new RectF(rect), 50,50,enterKeyPressedPaint);
        } else {
            canvas.drawRoundRect(new RectF(rect), 50,50,enterKeyPaint);
        }

        Rect txtBound = new Rect();
        keyTextPaint.getTextBounds(enter_txt, 0, enter_txt.length(), txtBound);
        canvas.drawText(enter_txt, rect.centerX()  - txtBound.left -(txtBound.right - txtBound.left)/2f,
                rect.centerY() - txtBound.top -(txtBound.bottom - txtBound.top)/2f,
                keyTextPaint);
    }

    void drawDelete(Key key, Canvas canvas) {
        Rect rect = new Rect(key.rect);
        if(key.pressed) {
            rect.top = rect.top - pressedOffset;
            rect.bottom = rect.bottom - pressedOffset;
            canvas.drawRoundRect(new RectF(rect), 50,50,keyPressedPaint);
        } else {
            canvas.drawRoundRect(new RectF(rect), 50,50,keyPaint);
        }
        delete.setBounds(
                rect.centerX() - 48/2,
                rect.centerY() - 35/2,
                rect.centerX() + 48/2 ,
                rect.centerY() + 35/2);
        delete.draw(canvas);
    }

    void drawHyphenKey(Key key, Canvas canvas) {
        Rect rect = new Rect(key.rect);
        if(key.pressed) {
            rect.top = rect.top - pressedOffset;
            rect.bottom = rect.bottom - pressedOffset;
            canvas.drawRoundRect(new RectF(rect), 50,50,backKeyPressedPaint);
        } else {
            canvas.drawRoundRect(new RectF(rect), 50,50,backKeyPaint);
        }

        Rect txtBound = new Rect();
        keyTextPaint.getTextBounds(hyphen_txt, 0, hyphen_txt.length(), txtBound);
        canvas.drawText(hyphen_txt, rect.centerX()  - txtBound.left -(txtBound.right - txtBound.left)/2f,
                rect.centerY() - txtBound.top -(txtBound.bottom - txtBound.top)/2f,
                keyTextPaint);
    }

    void drawZeroKey(Key key, Canvas canvas) {
        Rect rect = new Rect(key.rect);
        if(key.pressed) {
            rect.top = rect.top - pressedOffset;
            rect.bottom = rect.bottom - pressedOffset;
            canvas.drawRoundRect(new RectF(rect), 50,50,backKeyPressedPaint);
        } else {
            canvas.drawRoundRect(new RectF(rect), 50,50,backKeyPaint);
        }

        Rect txtBound = new Rect();
        keyTextPaint.getTextBounds(zero_txt, 0, zero_txt.length(), txtBound);
        canvas.drawText(zero_txt, rect.centerX()  - txtBound.left -(txtBound.right - txtBound.left)/2f,
                rect.centerY() - txtBound.top -(txtBound.bottom - txtBound.top)/2f,
                keyTextPaint);
    }

    //calculate Sizes
    int getKeySize() {
        return (int)(((float) getWidth() )*(key_width_weight / (horizontal_side_margin_weight*2 + horizontal_margin_weight*11 + key_width_weight*12))) + 20;
    }

    int getLargerKeyWidth() {
        return (int)(((float) getWidth() )*(large_key_width_weight/ (horizontal_side_margin_weight*2.5 + text_width_weight + horizontal_margin_weight*4 + large_key_width_weight*4))) + 340;
    }

    int getLargeKeyWidth() {
        return (int)(((float) getWidth() )*(large_key_width_weight/ (horizontal_side_margin_weight*2.5 + text_width_weight + horizontal_margin_weight*4 + large_key_width_weight*4))) ;
    }

    int getRowWidth() {
        return getKeySize() * 12 + getHorizontalKeyMargin() * 11;
    }

    int getHeaderRowWidth() {
        return getKeySize() * 12 + getHorizontalKeyMargin() * 10;
    }

    int getTextWidth() {
        return (int) (getHeaderRowWidth() - getLargeKeyWidth() * 4 - getHorizontalKeyMargin() * 4);
    }


    int getHeaderHeight() {
        int weightHeight = (int)(getKeySize()/key_height_weight);
        return (int)(weightHeight*header_height_weight);
    }
    int getVerticalSideMargin() {
        int weightHeight = (int)(getKeySize()/key_height_weight);
        return (int)(weightHeight*vertical_margin_weight);
    }


    int getHorizontalSideMargin() {
        return (int)(((float) getWidth())*(horizontal_side_margin_weight / (horizontal_side_margin_weight*2 + horizontal_margin_weight*11 + key_width_weight*12)));
    }

    int getHorizontalKeyMargin() {
        return (int)(((float) getWidth())*(horizontal_margin_weight / (horizontal_side_margin_weight*2 + horizontal_margin_weight*11 + key_width_weight*12)));
    }

    int getVerticalKeyMargin() {
        int weightHeight = (int)(getKeySize()/key_height_weight);
        return (int)(weightHeight*vertical_margin_weight)-14;
    }

    Key pressedKey = null;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if(pressedKey != null) {
                    try {
                        pressedKey.onClick.accept(pressedKey);
                        pressedKey.pressed = false;
                        pressedKey = null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                performClick();
                break;
            case MotionEvent.ACTION_DOWN:
                pressedKey = findKey((int)event.getX(), (int)event.getY());
                if(pressedKey != null) {
                    pressedKey.pressed = true;
                }
                for (Key k: keyList) {
                    if(k != pressedKey)
                        k.pressed = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                pressedKey = findKey((int)event.getX(), (int)event.getY());
                if(pressedKey != null) {
                    pressedKey.pressed = true;
                }
                for (Key k: keyList) {
                    if(k != pressedKey)
                        k.pressed = false;
                }
                break;
        }
        invalidate();
        return true;
    }

    Key findKey(int x, int y) {
        for (Key k: keyList) {
            Rect rect = new Rect(k.rect);
            rect.left = rect.left - getHorizontalSideMargin()/2;
            rect.right = rect.right + getHorizontalSideMargin()/2 - 1;
            rect.top = rect.top - getVerticalSideMargin()/2;
            rect.bottom = rect.bottom + getVerticalSideMargin()/2 - 1;
            if(rect.contains(x, y)) {;
                return k;
            }
        }
        return null;
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }



    public interface OnInputChangeListener {
        void onInputChange(String text);
    }

    public interface OnEnterListener {
        void onEnter(String text);
    }

    public interface OnHyphenKeyListener {
        void onHyphenEnter(String text);
    }

    public interface OnZeroKeyListener {
        void onZeroEnter(String text);
    }

    OnEnterListener onEnterListener;

    OnInputChangeListener onInputChangeListener;

    OnZeroKeyListener onZeroKeyListener;

    OnHyphenKeyListener onHyphenKeyListener;

    void onEnter(Key key) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        if(onEnterListener != null)
            onEnterListener.onEnter(input);
    }

    void onDelete(Key key) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        if(input != null && input.length() > 0) {
            input = input.substring(0, input.length() - 1);
            if(onInputChangeListener != null) {
                onInputChangeListener.onInputChange(input);
            }
        }
        invalidate();
    }

    void onHyphenEnter(Key key) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        if(onHyphenKeyListener != null)
            if (input.length() < max) {
                input = input.concat(hyphen_txt);
            }
        onHyphenKeyListener.onHyphenEnter(hyphen_txt);
    }

    void onZeroEnter(Key key) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        if(onZeroKeyListener != null)
            if (input.length() < max) {
                input = input.concat(zero_txt);
            }
        onZeroKeyListener.onZeroEnter(zero_txt);
    }

    public void setOnEnterListener(OnEnterListener onEnterListener) {
        this.onEnterListener = onEnterListener;
    }

    public void setOnInputChangeListener(OnInputChangeListener onInputChangeListener) {
        this.onInputChangeListener = onInputChangeListener;
    }

    public void setOnZeroKeyListener(OnZeroKeyListener onZeroKeyListener) {
        this.onZeroKeyListener = onZeroKeyListener;
    }

    public void setOnHyphenKeyListener(OnHyphenKeyListener onHyphenKeyListener) {
        this.onHyphenKeyListener = onHyphenKeyListener;
    }

    public void setHint(@NonNull String hint) {
        this.hint = hint;
        invalidate();
    }

    public void setInput(@NonNull String input) {
        this.input = input;
        invalidate();
    }
}
