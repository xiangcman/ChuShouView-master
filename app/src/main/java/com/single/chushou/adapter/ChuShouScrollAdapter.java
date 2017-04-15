package com.single.chushou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.single.chushou.R;
import com.single.chushou.activity.ChuShouScrollActivity;
import com.single.chushou.holder.MyHolder;
import com.single.chushou.manager.FlowLayoutManager;

import java.util.List;

/**
 * Created by xiangcheng on 17/4/11.
 */

public class ChuShouScrollAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ChuShouScrollAdapter.class.getSimpleName();
    private List<List<ChuShouScrollActivity.ShowItem>> maps;
    private Context context;

    public ChuShouScrollAdapter(Context context, List<List<ChuShouScrollActivity.ShowItem>> maps) {
        this.maps = maps;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder<ChuShouScrollActivity.ShowItem>(View.inflate(context, R.layout.scroll_item_layout, null), context) {
            @Override
            protected RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter(List<ChuShouScrollActivity.ShowItem> list, Context context) {
                return new FlowAdapter(list, context);
            }

            @Override
            protected RecyclerView.LayoutManager getLayoutManager(Context context, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
                return new FlowLayoutManager();
            }
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("ChuShouScrollAdapter", "onBindViewHolder:" + position);
        List<ChuShouScrollActivity.ShowItem> showItems = maps.get(position);
        ((MyHolder) holder).refreshData(showItems);
    }

    @Override
    public int getItemCount() {
        return maps.size();
    }

}
