package com.single.chushou.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.library.chushou.callback.ChuShouCallBack;
import com.library.chushou.manager.ChuShouManager;
import com.library.chushou.view.ScrollRecyclerView;
import com.single.chushou.R;
import com.single.chushou.adapter.ChuShouScrollAdapter;
import com.single.chushou.data.DataConfig;

import java.util.List;
import java.util.Random;

/**
 * Created by xiangcheng on 17/4/12.
 */

public class ChuShouScrollActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScrollRecyclerView chuShouView = (ScrollRecyclerView) findViewById(R.id.chushou_view);
        chuShouView.setLayoutManager(new ChuShouManager());
        ChuShouScrollAdapter adapter;
        List<List<ShowItem>> items = DataConfig.getItems();

        adapter = new ChuShouScrollAdapter(this, items);
        ChuShouCallBack callback = new ChuShouCallBack(adapter, items);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(chuShouView);

        chuShouView.setAdapter(adapter);
        chuShouView.setChuShouCallBack(callback);

    }


    public static class ShowItem {
        public int color;
        public String des;

        public ShowItem(String des) {
            this.des = des;
            color = Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
        }
    }
}
