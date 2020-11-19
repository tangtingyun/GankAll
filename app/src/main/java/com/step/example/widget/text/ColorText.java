package com.step.example.widget.text;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class ColorText extends AppCompatTextView {
    public static final String TEXT = "鹿鼎记";

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
        invalidate();
    }

    float percent = 0;

    public ColorText(@NonNull Context context) {
        super(context);
    }

    public ColorText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "percent", 0f, 1f);
        objectAnimator.setDuration(4000);
        objectAnimator.start();
    }


    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCenterLine(canvas);

        // bad Practice
        Paint paint = new Paint();
        paint.setTextSize(50);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        float textWidth = paint.measureText(TEXT);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float ascent = fontMetrics.ascent;
        float descent = fontMetrics.descent;


        Log.e("ColorText", "onDraw: ascent " + ascent);      // ascent -46.38672
        Log.e("ColorText", "onDraw: descent " + descent);  // descent 12.207031
        Log.e("ColorText", "onDraw: diff " + ((descent - ascent) / 2 - descent));  //diff 17.089844
        int centerBaseLine = (int) (getHeight() / 2 + ((descent - ascent) / 2 - descent));
        Log.e("ColorText", "onDraw: getHeight() / 2    " + getHeight() / 2);   // getHeight() / 2    497
        Log.e("ColorText", "onDraw: centerBaseLine " + centerBaseLine);  // centerBaseLine 514
        canvas.save();
        paint.setColor(Color.BLACK);
        int leftBound = (int) (getWidth() / 2 - textWidth / 2 + textWidth * percent);
        Rect rect = new Rect(leftBound, 0, getWidth(), getHeight());
        canvas.clipRect(rect);
        canvas.drawText(TEXT, getWidth() / 2, centerBaseLine, paint);
        canvas.restore();

        canvas.save();
        paint.setColor(Color.RED);
        int rightBound = (int) (getWidth() / 2 - textWidth / 2 + textWidth * percent);
        Rect rect2 = new Rect(0, 0, rightBound, getHeight());
        canvas.clipRect(rect2);
        canvas.drawText("鹿鼎记", getWidth() / 2, centerBaseLine, paint);
        canvas.restore();
    }
    private void drawCenterLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3);


        paint.setColor(Color.RED);
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint);

        paint.setColor(Color.BLUE);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), paint);
    }
}
