package com.aqs.pm.myapplication;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Title extends View {

    private final Paint paint;
    private String titleText;
    public Title(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        //Circle color
        paint.setColor(Color.BLUE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(100f);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.Title, 0, 0);
        titleText = a.getString(R.styleable.Title_titleText);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth()/2;
        canvas.drawText(titleText, w, 100, paint);
    }
}
