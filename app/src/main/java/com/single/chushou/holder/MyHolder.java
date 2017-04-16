package com.single.chushou.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.library.chushou.view.SlideRecyclerView;
import com.single.chushou.R;

import java.util.List;

/**
 * Created by xiangcheng on 17/4/14.
 * 每一个item(recyclerView用到的holder)
 */

public abstract class MyHolder<T> extends RecyclerView.ViewHolder {
    private static final String TAG = MyHolder.class.getSimpleName();
    SlideRecyclerView container;

    List<T> showItems;
    private Context context;

    public MyHolder(View itemView, Context context) {
        super(itemView);
        container = (SlideRecyclerView) itemView.findViewById(R.id.container);
        this.context = context;
    }

    RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = null;

    RecyclerView.LayoutManager layoutManager = null;


    public void refreshData(List<T> showItems) {


//        container.pullScroll(scroll);

//        if (adapter == null) {
        ViewGroup.LayoutParams ls = container.getLayoutParams();
        ls.width = context.getResources().getDisplayMetrics().widthPixels;
        ls.height = context.getResources().getDisplayMetrics().heightPixels - 50;
        container.setLayoutParams(ls);
        Log.d(TAG, "flowAdapter is null");
        this.showItems = showItems;
        adapter = getAdapter(this.showItems, context);
        layoutManager = getLayoutManager(context, adapter);
        container.setLayoutManager(getLayoutManager(context, adapter));
        container.setAdapter(adapter);
        container.pullScroll();
//        } else {
//            Log.d(TAG, "flowAdapter is not null");
//            Log.d(TAG, "showItems.size:" + showItems.size());
//            this.showItems.clear();
//            this.showItems.addAll(showItems);
////            adapter = getAdapter(this.showItems, context);
////            container.setLayoutManager(getLayoutManager(context, adapter));
////            adapter.notifyItemChanged(0, this.showItems.size() - 1);
//            container.setLayoutManager(layoutManager);
//            adapter.notifyDataSetChanged();
////            container.setLayoutManager(getLayoutManager(context, adapter));
//        }
    }

    protected abstract RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter(List<T> list, Context context);

    protected abstract RecyclerView.LayoutManager getLayoutManager(Context context, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter);
}
