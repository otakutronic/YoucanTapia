package com.mji.tapia.youcantapia.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.mji.tapia.youcantapia.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sami on 3/29/2018.
 */

public class CustomFontTextView extends android.support.v7.widget.AppCompatTextView {
     static private Map<String, Typeface> typeFaceMap = new HashMap<>();

    public CustomFontTextView(Context context) {
        super(context);
    }

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomFontTextViewAttrs,0,0);

        String font = styledAttributes.getString(R.styleable.CustomFontTextViewAttrs_custom_font);
        styledAttributes.recycle();

        setInternalFont(font);
    }


    private void setInternalFont(String font) {

        if(font != null) {
            Typeface typeface;
            if (typeFaceMap.containsKey(font)) {
                typeface = typeFaceMap.get(font);
            } else {
                typeface = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), font);
                typeFaceMap.put(font, typeface);
            }

            setTypeface(typeface);
        }
    }

    public void setFont(String font) {
        setInternalFont(font);
        invalidate();
    }
}
