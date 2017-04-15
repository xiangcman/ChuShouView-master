package com.single.chushou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.single.chushou.R;
import com.single.chushou.activity.ChuShouGridActivity;
import com.single.chushou.holder.MyHolder;
import com.single.chushou.manager.ChuShouGridLayoutManager;

import java.util.List;

/**
 * Created by xiangcheng on 17/4/13.
 */

public class ChuShouGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ChuShouGridAdapter.class.getSimpleName();
    private List<List<ChuShouGridActivity.GridItem>> gridItems;
    private Context context;

    public ChuShouGridAdapter(Context context, List<List<ChuShouGridActivity.GridItem>> gridItems) {
        this.gridItems = gridItems;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder<ChuShouGridActivity.GridItem>(View.inflate(context, R.layout.scroll_item_layout, null), context) {
            @Override
            protected RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter(List<ChuShouGridActivity.GridItem> list, Context context) {
                return new ChuShouGridItemAdapter(list, context);
            }

            @Override
            protected RecyclerView.LayoutManager getLayoutManager(Context context, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
                return new ChuShouGridLayoutManager(context, adapter);
            }
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder:" + position);
        List<ChuShouGridActivity.GridItem> showItems = gridItems.get(position);
        ((MyHolder) holder).refreshData(showItems);
    }

    @Override
    public int getItemCount() {
        return gridItems.size();
    }

}
