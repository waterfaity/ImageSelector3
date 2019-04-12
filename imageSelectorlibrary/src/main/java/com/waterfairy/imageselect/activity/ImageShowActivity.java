package com.waterfairy.imageselect.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.waterfairy.imageselect.R;
import com.waterfairy.imageselect.utils.AnimUtils;
import com.waterfairy.imageselect.utils.ConstantUtils;
import com.waterfairy.imageselect.utils.PathUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

//import com.github.chrisbanes.photoview.OnPhotoTapListener;
//import com.github.chrisbanes.photoview.PhotoView;


public class ImageShowActivity extends AppCompatActivity {
    private static final String TAG = "imageShow";
    private boolean isVisibility = true;
    private ImageView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_selector_activity_image_show);
        initEnter();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            postponeEnterTransition();
//        }
        photoView = findViewById(R.id.image);

        Intent intent = getIntent();
        //url
        String url = intent.getStringExtra(ConstantUtils.STR_URL);
        //本地地址
        String path = intent.getStringExtra(ConstantUtils.STR_PATH);
        //标题
        String title = intent.getStringExtra(ConstantUtils.STR_IMG_TITLE);  //设置标题

        TextView tVTitle = findViewById(R.id.title);
        if (!TextUtils.isEmpty(url)) {
            tVTitle.setText(TextUtils.isEmpty(title) ? PathUtils.getNameFromUrl(url) : title);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                photoView.setTransitionName(url);
            }
            Picasso.with(this).load(url).into(photoView);
        } else if (!TextUtils.isEmpty(path)) {
            tVTitle.setText(TextUtils.isEmpty(title) ? new File(path).getName() : title);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                photoView.setTransitionName(path);
            }
            Picasso.with(this).load(new File(path)).into(photoView);
        }
        final View topView = findViewById(R.id.rel_top);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisibility) {
                    topView.startAnimation(AnimUtils.getInAnim(true, false));
                } else {
                    topView.startAnimation(AnimUtils.getInAnim(true, true));
                }
                isVisibility = !isVisibility;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scheduleStartPostponedTransition(photoView);
        }

    }

    private void initEnter() {
        ActivityCompat.setEnterSharedElementCallback(this, new SharedElementCallback() {
            private int width;
            private int height;

            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
                View toView = sharedElements.get(0);
                View fromView = sharedElementSnapshots.get(0);
                int fromHeight = fromView.getHeight();
                int fromWith = fromView.getWidth();


                //启动时view  sharedElementNames 当前的view(大小和传递过来的一致)    sharedElementSnapshots 传送过来的数据生成的view
                Log.i(TAG, "---onSharedElementStart: ");
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                ImageView imageView = (ImageView) sharedElements.get(0);

//                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                //结束时view
                Log.i(TAG, "---onSharedElementEnd: ");
            }

            @Override
            public void onRejectSharedElements(List<View> rejectedSharedElements) {
                super.onRejectSharedElements(rejectedSharedElements);
                Log.i(TAG, "---onRejectSharedElements: ");
            }

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.onMapSharedElements(names, sharedElements);
                ImageView imageView = (ImageView) sharedElements.get(names.get(0));
                height = imageView.getHeight();
                width = imageView.getWidth();

                Log.i(TAG, "---onMapSharedElements: ");
            }

            @Override
            public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                Log.i(TAG, "---onCaptureSharedElementSnapshot: ");
                return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
            }

            @Override
            public View onCreateSnapshotView(Context context, Parcelable snapshot) {
                Log.i(TAG, "---onCreateSnapshotView: ");
                return super.onCreateSnapshotView(context, snapshot);
            }

            @Override
            public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
                Log.i(TAG, "---onSharedElementsArrived: ");
                super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
            }
        });
    }

    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        //启动动画
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startPostponedEnterTransition();
                        }
                        return true;
                    }
                });
    }


    public void back(View view) {
        if (isVisibility)
            ActivityCompat.finishAfterTransition(this);
    }
}
