package com.single.chushou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.single.chushou.ItemDecoration.SpaceItemDecoration;
import com.single.chushou.R;
import com.single.chushou.activity.ChuShouScrollActivity;
import com.single.chushou.holder.MyHolder;
import com.single.chushou.manager.MyFlowLayoutManager;

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
                return new MyFlowLayoutManager();
            }

            @Override
            public RecyclerView.ItemDecoration getItemDecoration() {
                return new SpaceItemDecoration(dp2px(10));
            }
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder:" + position);
        List<ChuShouScrollActivity.ShowItem> showItems = maps.get(position);
        Log.d(TAG, "position:" + position + "--" + showItems.size());
        ((MyHolder) holder).refreshData(showItems);
    }

    @Override
    public int getItemCount() {
        return maps.size();
    }

    private int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

}
