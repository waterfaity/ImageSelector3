package com.waterfairy.myapplication;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.waterfairy.imageselect.utils.ConstantUtils;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //
//            getWindow().setSharedElementEnterTransition(new MyGlideTransition()); // 进入
//            getWindow().setSharedElementReturnTransition(new MyGlideTransition()); // 返回
//            getWindow().setSharedElementEnterTransition(new AutoTransition()); // 进入
//            getWindow().setSharedElementReturnTransition(new AutoTransition()); // 返回
        }
        setContentView(R.layout.activity_main2);
        String path = getIntent().getStringExtra(ConstantUtils.STR_PATH);
        ImageView imageView = (ImageView) findViewById(R.id.img);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(path);
        }
        Picasso.with(this).load(path). into(imageView);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
