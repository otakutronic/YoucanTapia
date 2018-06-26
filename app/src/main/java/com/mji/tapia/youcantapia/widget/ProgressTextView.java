package com.mji.tapia.youcantapia.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mji.tapia.youcantapia.R;


/**
 * Created by Sami on 8/7/2017.
 */

public class ProgressTextView extends View {

    float mProgress = 0.553f;
    String mText;
    int color1 = Color.parseColor("#FFFFFFFF");
    int color2 = Color.parseColor("#00FFFFFF");

    // horizontal orientation
    int offset = 20;

    // vertical orientation
    int horizontalOffset = 30;
    int verticalOffset = -5;

    enum Orientation {
        VERTICAL,
        HORIZONTAL
    }

    private class ProgressText{
        String text;
        TextPaint textPaint;
        int x, y, width, height;
        Rect txtRect;
        ProgressText[] verticalTexts;
    }

    ProgressText[] progressTexts;

    Orientation mOrientation = Orientation.VERTICAL ;
    int totalTextWidth;
    int totalTextHeight;

    int fade;
    int textSize;
    public ProgressTextView(Context context) {
        super(context);
        myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/hkreikk.ttf");
        mText = "テスト試験です。\nおはよう";

        updateText();
    }

    Typeface myTypeface;
    String font;
    public ProgressTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressTextViewAttrs,0,0);

        try {
            mText = styledAttributes.getString(R.styleable.ProgressTextViewAttrs_text);
            color1 = styledAttributes.getColor(R.styleable.ProgressTextViewAttrs_fillColor, Color.GREEN);
            color2 = styledAttributes.getColor(R.styleable.ProgressTextViewAttrs_baseColor, Color.RED);
            int orientation = styledAttributes.getInt(R.styleable.ProgressTextViewAttrs_orientation,1);
            if(orientation == 1)
                mOrientation = Orientation.HORIZONTAL;
            else
                mOrientation = Orientation.VERTICAL;
            font = styledAttributes.getString(R.styleable.ProgressTextViewAttrs_fontAssetPath);
            textSize = styledAttributes.getDimensionPixelSize(R.styleable.ProgressTextViewAttrs_textSize,32);
            offset = styledAttributes.getDimensionPixelSize(R.styleable.ProgressTextViewAttrs_horizontal_lineSpace,20);
            verticalOffset = styledAttributes.getDimensionPixelSize(R.styleable.ProgressTextViewAttrs_vertical_characterSpace,0);
            horizontalOffset = styledAttributes.getDimensionPixelSize(R.styleable.ProgressTextViewAttrs_vertical_lineSpace,30);
            fade = styledAttributes.getDimensionPixelSize(R.styleable.ProgressTextViewAttrs_fade,20);
            mProgress = styledAttributes.getFloat(R.styleable.ProgressTextViewAttrs_progress,0.5f);
        } finally {
            styledAttributes.recycle();
        }

        if(font != null && !font.equals("")){
            myTypeface = Typeface.createFromAsset(context.getAssets(), font);
        }

        updateText();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        updateProgress();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < progressTexts.length ; i++) {
            if(mOrientation == Orientation.HORIZONTAL)
                canvas.drawText(progressTexts[i].text, progressTexts[i].x, progressTexts[i].y, progressTexts[i].textPaint);
            else if(mOrientation == Orientation.VERTICAL) {
                for (int j = 0; j < progressTexts[i].text.length(); j++){
                    canvas.drawText(progressTexts[i].verticalTexts[j].text,
                            progressTexts[i].verticalTexts[j].x - progressTexts[i].verticalTexts[j].txtRect.left,
                            progressTexts[i].verticalTexts[j].y - progressTexts[i].verticalTexts[j].txtRect.top,
                            progressTexts[i].verticalTexts[j].textPaint);

//                    Paint paint = new Paint();
//                    paint.setColor(Color.argb(255,255,0,0));
//                    paint.setStyle(Paint.Style.STROKE);
//                    canvas.drawRect(progressTexts[i].verticalTexts[j].x,
//                            progressTexts[i].verticalTexts[j].y ,
//                            progressTexts[i].verticalTexts[j].x + progressTexts[i].verticalTexts[j].width,
//                            progressTexts[i].verticalTexts[j].y + progressTexts[i].verticalTexts[j].height, paint);
                }
            }
        }
    }

    public void setProgress(float progress) {
        mProgress = progress;
        updateProgress();
    }

    void updateProgress(){
        int progress = 0;
        if(mOrientation == Orientation.HORIZONTAL) {
            int totalProgressWidth = (int) (mProgress * (float)totalTextWidth);
            for (int i = 0; i < progressTexts.length ; i++) {
                if(progress > totalProgressWidth){
                    progressTexts[i].textPaint.setColor(color2);
                    progressTexts[i].textPaint.setShader(null);
                }
                else if(progress + progressTexts[i].width < totalProgressWidth) {
                    progressTexts[i].textPaint.setColor(color1);
                    progressTexts[i].textPaint.setShader(null);
                }
                else {
                    int offset =totalProgressWidth - progress;
                    progressTexts[i].textPaint.setShader(new LinearGradient(progressTexts[i].x + offset, 0, progressTexts[i].x + offset + 1,0, color1, color2, Shader.TileMode.CLAMP));
                }
                progress += progressTexts[i].width;
            }
        }
        else if (mOrientation == Orientation.VERTICAL){
            int totalProgressHeight = (int) (mProgress * (float)totalTextHeight);
            for (int i = 0; i < progressTexts.length ; i++){
                for (int j = 0; j < progressTexts[i].text.length(); j++){
                    if(progress > totalProgressHeight) {
                        progressTexts[i].verticalTexts[j].textPaint.setColor(color2);
                        progressTexts[i].verticalTexts[j].textPaint.setShader(null);
                    }
                    else if(progress + progressTexts[i].verticalTexts[j].height < totalProgressHeight) {
                        progressTexts[i].verticalTexts[j].textPaint.setColor(color1);
                        progressTexts[i].verticalTexts[j].textPaint.setShader(null);
                    }
                    else {
                        int offset =totalProgressHeight - progress;
                        progressTexts[i].verticalTexts[j].textPaint.setColor(color1);
                        progressTexts[i].verticalTexts[j].textPaint.setShader(
                                new LinearGradient(
                                        0,
                                        progressTexts[i].verticalTexts[j].y + offset,
                                        0,
                                        progressTexts[i].verticalTexts[j].y + offset + fade,
                                        color1,
                                        color2,
                                        Shader.TileMode.CLAMP));
                    }
                    if(j + 1 < progressTexts[i].text.length())
                        progress += progressTexts[i].verticalTexts[j + 1].y - progressTexts[i].verticalTexts[j].y;
                    else
                        progress += progressTexts[i].verticalTexts[j].height;
                }
            }
        }
        invalidate();
    }

    void updateText(){
        String[] mTextArray = mText.split("\n");

        progressTexts = new ProgressText[mTextArray.length];
        totalTextHeight = 0;
        totalTextWidth = 0;
        for (int i = 0; i < progressTexts.length; i++){


            ProgressText progressText = new ProgressText();
            progressText.text = mTextArray[i];
            if(mOrientation == Orientation.HORIZONTAL) {
                TextPaint textPaint = new TextPaint();
                textPaint.setTypeface(myTypeface);
                textPaint.setTextSize(textSize);
                textPaint.setARGB(255,255,0,0);
                textPaint.setTextAlign(Paint.Align.LEFT);
                progressText.textPaint = textPaint;
                progressText.txtRect = new Rect();
                progressText.textPaint.getTextBounds(progressText.text, 0, progressText.text.length(), progressText.txtRect);
                progressText.height = progressText.txtRect.height();
                progressText.width = progressText.txtRect.width();
                progressText.x = (int) getX();
                progressText.y = (int) getY() + (i + 1) * (progressText.height + offset);
            }
            else if(mOrientation == Orientation.VERTICAL){
                progressText.x = (int) getX() +  (progressTexts.length - i) * (horizontalOffset);
                progressText.y = (int) getY() + progressText.height + offset;
                progressText.width = 0;
                progressText.height = 0;
                progressText.verticalTexts = new ProgressText[progressText.text.length()];
                for (int j = 0; j < progressText.text.length(); j++){
                    ProgressText verticalText = new ProgressText();
                    verticalText.text = Character.toString(progressText.text.charAt(j));
                    TextPaint textPaint = new TextPaint();
                    textPaint.setTypeface(myTypeface);
                    textPaint.setTextSize(textSize);
                    textPaint.setARGB(255,255,0,0);
                    textPaint.setTextAlign(Paint.Align.LEFT);
                    verticalText.textPaint = textPaint;
                    verticalText.txtRect = new Rect();
                    verticalText.textPaint.getTextBounds(verticalText.text, 0, verticalText.text.length(), verticalText.txtRect);
                    verticalText.height = verticalText.txtRect.height();
                    verticalText.width = verticalText.txtRect.width();
                    verticalText.x = progressText.x + verticalText.txtRect.left;
                    if(j == 0)
                        verticalText.y = progressText.y;
                    else
                        verticalText.y = progressText.y + progressText.verticalTexts[j-1].height + progressText.verticalTexts[j-1].y + verticalOffset;

                    if(verticalText.width > progressText.width)
                        progressText.width = verticalText.width;

                    progressText.verticalTexts[j] = verticalText;
                }
                progressText.height += progressText.verticalTexts[progressText.text.length() - 1].y + progressText.verticalTexts[progressText.text.length() - 1].height;
            }

            progressTexts[i] = progressText;
            totalTextHeight += progressText.height;
            totalTextWidth += progressText.width;
        }
        Log.e("PROGRESS_TEXT_VIEW",mTextArray.toString());
    }

    public void setText(String text){
        mText = text;
        updateText();
        updateProgress();
    }

}
