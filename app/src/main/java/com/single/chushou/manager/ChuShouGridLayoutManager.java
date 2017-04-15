package com.single.chushou.manager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.library.chushou.manager.TotalHeightLayoutManager;

/**
 * Created by xiangcheng on 17/4/13.
 */

public class ChuShouGridLayoutManager extends LinearLayoutManager implements TotalHeightLayoutManager {


    private static final String TAG = ChuShouGridLayoutManager.class.getSimpleName();
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    int totalHeight = 0;

    public ChuShouGridLayoutManager(Context context, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        super(context);
        this.adapter = adapter;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int getTotalHeight() {
        if (totalHeight == 0) {
            int itemCount = adapter.getItemCount();
            Log.d(TAG, "itemCount:" + itemCount);
            totalHeight = getChildAt(0).getHeight() * itemCount;
        }
        Log.d(TAG, "totalHeight:" + totalHeight);
        return totalHeight;
    }
}
