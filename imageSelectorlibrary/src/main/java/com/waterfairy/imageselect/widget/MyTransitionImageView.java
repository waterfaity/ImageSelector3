package com.waterfairy.imageselect.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @author water_fairy
 * @email 995637517@qq.com
 * @date 2019/4/12 17:26
 * @info:
 */
public class MyTransitionImageView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG = "imageShow";

    private BitmapTransitionDrawer bitmapTransitionDrawer;

    public MyTransitionImageView(Context context) {
        this(context, null);
    }

    public MyTransitionImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmapTransitionDrawer = new BitmapTransitionDrawer(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmapTransitionDrawer.onSizeChanged(w, h, oldw, oldh);
    }
}
