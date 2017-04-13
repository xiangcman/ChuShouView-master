package com.single.chushou;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.library.chushou.ChuShouCallBack;
import com.library.chushou.ChuShouManager;
import com.library.chushou.ScrollRecyclerView;

import java.util.List;
import java.util.Random;

/**
 * Created by xiangcheng on 17/4/12.
 */

public class ChuShouScrollActivity extends Activity {
//    List<String[]> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScrollRecyclerView chuShouView = (ScrollRecyclerView) findViewById(R.id.chushou_view);
        chuShouView.setLayoutManager(new ChuShouManager());
//        chuShouView.setLayoutManager(new li());
//        items = new ArrayList<>();
//        initData();
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
        int color;
        String des;

        public ShowItem(String des) {
            this.des = des;
            color = Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
        }
    }
}
