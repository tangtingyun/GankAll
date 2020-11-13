package com.step.example.widget.screenadapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.lang.reflect.Field;


/*

    屏幕适配解决方案
        1.  dimens
           最小宽度限定符   ScreenMatch 事先写好各种分辨率尺寸
        2. 自定义控件
            运行时 获取设备尺寸 动态转换


    异形屏    刘海屏 参考博客
	https://blog.csdn.net/xiangzhihong8/article/details/80317682
* */
public class UIUtils {

    private Context context;

    private static UIUtils utils;

    public static UIUtils getInstance(Context context) {
        if (utils == null) {
            utils = new UIUtils(context);
        }
        return utils;
    }


    //参照宽高
    public final float STANDARD_WIDTH = 720;
    public final float STANDARD_HEIGHT = 1232;

    //当前设备实际宽高
    public float displayMetricsWidth;
    public float displayMetricsHeight;

    private final String DIMEN_CLASS = "com.android.internal.R$dimen";


    public UIUtils(Context context) {
        this.context = context;
        //
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        //加载当前界面信息
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        if (displayMetricsWidth == 0.0f || displayMetricsHeight == 0.0f) {
            //获取状态框信息
            int systemBarHeight = getValue(context, "system_bar_height", 48);

            if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
                this.displayMetricsWidth = displayMetrics.heightPixels;
                this.displayMetricsHeight = displayMetrics.widthPixels - systemBarHeight;
            } else {
                this.displayMetricsWidth = displayMetrics.widthPixels;
                this.displayMetricsHeight = displayMetrics.heightPixels - systemBarHeight;
            }

        }
    }

    //对外提供系数
    public float getHorizontalScaleValue() {
        return displayMetricsWidth / STANDARD_WIDTH;
    }

    public float getVerticalScaleValue() {

        Log.i("testUIUtils", "displayMetricsHeight:" + displayMetricsHeight);
        return displayMetricsHeight / STANDARD_HEIGHT;
    }


    public int getValue(Context context, String systemid, int defValue) {

        try {
            Class<?> clazz = Class.forName(DIMEN_CLASS);
            Object r = clazz.newInstance();
            Field field = clazz.getField(systemid);
            int x = (int) field.get(r);
            return context.getResources().getDimensionPixelOffset(x);

        } catch (Exception e) {
            return defValue;
        }
    }

    public void test(Activity context) {
        //获取swdp方案
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;
        float density = dm.density;
        float widthDP = widthPixels / density;
        int heightPixels = dm.heightPixels;


        DisplayMetrics dm2 = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getRealMetrics(dm2);

        int height = dm2.heightPixels;
        float heightdp = (height) / density;


        Log.i("UIUtils", "widthPixels:" + widthPixels);
        Log.i("UIUtils", "height:" + height);
        Log.i("UIUtils", "heightdp:" + heightdp);
        Log.i("UIUtils", "heightPixels:" + heightPixels);
        Log.i("UIUtils", "density:" + density);
        Log.i("UIUtils", "sw widthDP:" + widthDP);
    }
}
