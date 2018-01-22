package com.single.chushou.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.library.chushou.callback.ChuShouCallBack;
import com.library.chushou.manager.ChuShouManager;
import com.library.chushou.view.ScrollRecyclerView;
import com.single.chushou.R;
import com.single.chushou.adapter.ChuShouGridAdapter;
import com.single.chushou.data.DataConfig;

import java.util.List;

/**
 * Created by xiangcheng on 17/4/13.
 */

public class ChuShouGridActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScrollRecyclerView chuShouView = (ScrollRecyclerView) findViewById(R.id.chushou_view);
        List<List<GridItem>> gridItems = DataConfig.getGridItems();

        chuShouView.setLayoutManager(new ChuShouManager());

        ChuShouGridAdapter chuShouGridAdapter = new ChuShouGridAdapter(this, gridItems);

        ChuShouCallBack callback = new ChuShouCallBack(chuShouGridAdapter, gridItems);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(chuShouView);

        chuShouView.setAdapter(chuShouGridAdapter);
        chuShouView.setChuShouCallBack(callback);
    }

    public static class GridItem {
        public String img;
        public String des;

        public String img1;
        public String des1;

        public GridItem(String img, String des) {
            this.img = img;
            this.des = des;
        }

        public GridItem(String img, String des, String img1, String des1) {
            this.img = img;
            this.des = des;
            this.img1 = img1;
            this.des1 = des1;
        }
    }
}
