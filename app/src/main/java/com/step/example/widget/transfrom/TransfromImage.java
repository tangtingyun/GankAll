package com.step.example.widget.transfrom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.step.gankall.R;

public class TransfromImage extends View {

    private float BITMAP_SIZE;
    private float BITMAP_PADDING;
    Bitmap bitmap;

    public TransfromImage(Context context) {
        super(context);
    }

    public TransfromImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        BITMAP_SIZE = getDp(200f);
        BITMAP_PADDING = getDp(100f);
        bitmap = getAvatar((int) BITMAP_SIZE);
    }

    public TransfromImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        // bad Practice
        Paint paint = new Paint();
        paint.setTextSize(80);
        paint.setAntiAlias(true);


        Camera camera = new Camera();

        camera.rotateX(40f);

        // 设置照相机的位置
        camera.setLocation(0, 0, -6 * Resources.getSystem().getDisplayMetrics().density);


        // 重要!!!!!!!  即写在下⾯的变换先执⾏

        // 上半部分
        canvas.save();
        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2);
        canvas.rotate(-20f);
        canvas.clipRect(-BITMAP_SIZE, -BITMAP_SIZE, BITMAP_SIZE, 0f);
        canvas.rotate(20f);
        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2), -(BITMAP_PADDING + BITMAP_SIZE / 2));
        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint);
        canvas.restore();

        // 下半部分
        canvas.save();
        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2);
        canvas.rotate(-20f);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-BITMAP_SIZE, 0f, BITMAP_SIZE, BITMAP_SIZE);
        canvas.rotate(20f);
        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2), -(BITMAP_PADDING + BITMAP_SIZE / 2));
        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint);
        canvas.restore();

    }

    private Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
    }

    private float getDp(float px) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                px,
                Resources.getSystem().getDisplayMetrics()
        );
    }
}
