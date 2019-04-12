package com.waterfairy.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.waterfairy.imageselect.activity.ImageSelectActivity;
import com.waterfairy.imageselect.activity.ImageShowActivity;
import com.waterfairy.imageselect.test.TestActivity;
import com.waterfairy.imageselect.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    private GridView gridView;
    private String img = "/storage/emulated/0/Android/data/com.tencent.qqlive/files/image/-205197802300918.png";
    private ArrayList<String> strings;
    private View currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("pathName", "com.waterfairy.myapplication.fileProvider");
        intent.putExtra("hasTransAnim", true);
        startActivity(intent);

    }

    private void initView() {
        gridView = findViewById(R.id.grid_view);
        gridView.setNumColumns(3);
        gridView.setOnItemClickListener(this);

        Picasso.with(this).load(img).into((ImageView) findViewById(R.id.img));


        ActivityCompat.setExitSharedElementCallback(this, new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.onMapSharedElements(names, sharedElements);
                sharedElements.put(names.get(0), currentView);
            }
        });


    }

    public void onClick(View view) {
        Intent intent = new Intent(this, ImageSelectActivity.class);
        intent.putExtra(ConstantUtils.GRID_NUM, 3);
        intent.putExtra(ConstantUtils.MAX_NUM, 9);
        intent.putExtra(ConstantUtils.SEARCH_DEEP, 3);
        intent.putExtra(ConstantUtils.LOAD_CACHE, true);

        ArrayList<String> searchPaths = new ArrayList<>();
        searchPaths.add(ConstantUtils.PATH_WX);
        searchPaths.add(ConstantUtils.PATH_QQ_RECV);
        searchPaths.add(ConstantUtils.PATH_QQ_IMAGES);
        intent.putExtra(ConstantUtils.IGNORE_PATHS, searchPaths);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            strings = data.getStringArrayListExtra("data");
            gridView.setAdapter(new MyAdapter(this, strings));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyAdapter adapter = (MyAdapter) gridView.getAdapter();
        String path = (String) adapter.getItem(position);
        showBig(view, path, position);
    }

    private void showBig(View view, String path, int position) {
        currentView = view;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setTransitionName(path);
        }
//        Intent intent = new Intent(this, ImageViewPagerShowActivity.class);
//        intent.putExtra(ConstantUtils.DATA_LIST, strings);
//        intent.putExtra(ConstantUtils.CURRENT_POS, position);
//        intent.putExtra(ConstantUtils.CAN_SAVE_IMG, true);
//        intent.putExtra(ConstantUtils.SAVE_PARENT_PATH, "/sdcard/DCIM/picture");
//        ActivityCompat.startActivity(this, intent,
//                ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, path).toBundle());
    }

    public void fade(View view) {
        Intent intent = new Intent(this, ImageShowActivity.class);
        intent.putExtra(ConstantUtils.STR_PATH, img);
        ActivityCompat.startActivity(this, intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, img).toBundle());
    }


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                int pos = data.getIntExtra(ConstantUtils.CURRENT_POS, 0);
                currentView = gridView.getChildAt(pos);
            }
        }
    }
}
