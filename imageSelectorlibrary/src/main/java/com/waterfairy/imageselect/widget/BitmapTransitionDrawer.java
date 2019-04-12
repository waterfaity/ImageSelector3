package com.waterfairy.imageselect.widget;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * @author water_fairy
 * @email 995637517@qq.com
 * @date 2019/4/12 17:31
 * @info: 处理imageView 使用 transition 时的变化  centerCrop  ->  centerInside
 */
public class BitmapTransitionDrawer {

    ImageView imageView;
    private int bitmapHeight, bitmapWidth;
    //    private int fromWith, fromHeight;
    private float[] values = new float[9];
    private float bitmapRadio;
    //    private float oldViewRadio;
    private boolean toCenterInside = true;

    /**
     * 设置居中
     *
     * @param toCenterInside
     */
    public void setToCenterInside(boolean toCenterInside) {
        this.toCenterInside = toCenterInside;
    }

    public BitmapTransitionDrawer(ImageView imageView) {
        this.imageView = imageView;
    }

    //w  >  oldW 变大 反之变小
    public void onSizeChanged(int w, int h, int oldW, int oldH) {

        if (imageView != null) {
            if (bitmapHeight <= 0 || bitmapWidth <= 0) {
                Drawable drawable = getDrawable();
                if (drawable != null) {
                    bitmapHeight = drawable.getIntrinsicHeight();
                    bitmapWidth = drawable.getIntrinsicWidth();
                    bitmapRadio = bitmapWidth / (float) bitmapHeight;
                }
            }
//            if (fromWith <= 0 || fromHeight < 0) {
//                fromWith = oldW;
//                fromHeight = oldH;
//                oldViewRadio = fromWith / (float) fromHeight;
//            }
            if (bitmapHeight > 0 && bitmapWidth > 0) {


                // 变大
//                if (toCenterInside) {
                RectF bitmapRect = getMatrixRectF();
                float height = bitmapRect.height();
                float width = bitmapRect.width();
                float min = Math.min(w / width, h / height);
                getMatrix().postScale(min, min, w / 2F, h / 2F);

//                }
            }
        }
    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     *
     * @return
     */
    private RectF getMatrixRectF() {
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            getMatrix().mapRect(rect);
        }
        return rect;
    }

    private Matrix getMatrix() {
        return imageView.getMatrix();
    }

    private Drawable getDrawable() {
        return imageView.getDrawable();
    }
}
