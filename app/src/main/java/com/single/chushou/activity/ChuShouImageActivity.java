package com.single.chushou.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.library.chushou.callback.ChuShouCallBack;
import com.library.chushou.manager.ChuShouManager;
import com.single.chushou.R;
import com.single.chushou.adapter.ChuShouAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * 没有滑动的activity
 */
public class ChuShouImageActivity extends Activity {

    private List<String> maps = Arrays.asList("http://imgb.mumayi.com/android/img_mumayi/2016/05/30/109/1093874/nologo/nologo_pic_994333_417f38.jpg",
            "http://imgb.mumayi.com/android/img_mumayi/2016/05/30/109/1093874/nologo/nologo_pic_994333_3a8e9d.jpg",
            "http://imga.mumayi.com/android/img_mumayi/2016/05/30/109/1093874/nologo/nologo_pic_994333_2aafe9.jpg",
            "http://imgb.mumayi.com/android/img_mumayi/2016/05/30/109/1093874/nologo/nologo_pic_994333_17d15a.jpg",
            "http://imgb.mumayi.com/android/img_mumayi/2016/05/30/109/1093874/nologo/nologo_pic_994333_01cb78.jpg");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView chuShouView = (RecyclerView) findViewById(R.id.chushou_view);
        chuShouView.setLayoutManager(new ChuShouManager());
        ChuShouAdapter adapter;
        chuShouView.setAdapter(adapter = new ChuShouAdapter(this, maps));

        ItemTouchHelper.Callback callback = new ChuShouCallBack(adapter, maps, ItemTouchHelper.UP | ItemTouchHelper.DOWN);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(chuShouView);
    }
}
