package com.single.chushou.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.library.chushou.view.SlideRecyclerView;
import com.single.chushou.R;

import java.util.List;

/**
 * Created by xiangcheng on 17/4/14.
 * 每一个item(recyclerView用到的holder)
 */

public abstract class MyHolder<T> extends RecyclerView.ViewHolder {
    private static final String TAG = MyHolder.class.getSimpleName();
    RecyclerView.LayoutManager layoutManager = null;
    SlideRecyclerView container;
    List<T> showItems;
    private Context context;

    public MyHolder(View itemView, Context context) {
        super(itemView);
        container = (SlideRecyclerView) itemView.findViewById(R.id.container);
        this.context = context;
    }

    public void refreshData(List<T> showItems) {
        this.showItems = showItems;
        RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = getAdapter(this.showItems, context);
        layoutManager = getLayoutManager(context, adapter);
        container.setLayoutManager(getLayoutManager(context, adapter));
        container.setAdapter(adapter);
        container.pullScroll();
    }

    protected abstract RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter(List<T> list, Context context);

    protected abstract RecyclerView.LayoutManager getLayoutManager(Context context, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter);
}
