package com.single.chushou;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.library.chushou.ChuShouCallBack;
import com.library.chushou.ChuShouManager;

import java.util.Arrays;
import java.util.List;

public class ChuShouImageActivity extends Activity {

    private List<Integer> maps = Arrays.asList(R.mipmap.chushou1, R.mipmap.chushou2, R.mipmap.chushou3, R.mipmap.chushou4);

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
