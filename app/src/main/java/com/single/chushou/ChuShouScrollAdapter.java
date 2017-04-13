package com.single.chushou;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.library.chushou.SlideRecyclerView;

import java.util.List;

import static android.view.View.inflate;

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
        MyHolder myHolder = new MyHolder(inflate(context, R.layout.scroll_item_layout, null));
        return myHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        ((MyHolder) holder).img.setImageResource(maps.get(position));
        Log.d("ChuShouScrollAdapter", "onBindViewHolder:" + position);
        List<ChuShouScrollActivity.ShowItem> showItems = maps.get(position);
        ((MyHolder) holder).refreshData(showItems);
    }

    @Override
    public int getItemCount() {
        return maps.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        SlideRecyclerView container;
        FlowAdapter flowAdapter;
        List<ChuShouScrollActivity.ShowItem> showItems;

        public MyHolder(View itemView) {
            super(itemView);
            container = (SlideRecyclerView) itemView.findViewById(R.id.container);
        }


        public void refreshData(List<ChuShouScrollActivity.ShowItem> showItems) {
//            if (flowAdapter == null) {
            Log.d(TAG, "flowAdapter is null");
            this.showItems = showItems;
            ViewGroup.LayoutParams ls = container.getLayoutParams();
            ls.width = context.getResources().getDisplayMetrics().widthPixels;
            ls.height = context.getResources().getDisplayMetrics().heightPixels - 50;
            container.setLayoutParams(ls);
            container.setLayoutManager(new FlowLayoutManager());
            container.setAdapter(flowAdapter = new FlowAdapter(this.showItems));

        }
    }

    class FlowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

//        SparseArray<Drawable> allDrawAble = new SparseArray<>();

        List<ChuShouScrollActivity.ShowItem> arrays;

        public FlowAdapter(List<ChuShouScrollActivity.ShowItem> arrays) {
            this.arrays = arrays;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(View.inflate(context, R.layout.flow_item, null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView textView = ((MyHolder) holder).text;
            textView.setBackgroundDrawable(getBack(arrays.get(position).color));
            textView.setText(arrays.get(position).des);
        }

        private Drawable getBack(int color) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(8);
            drawable.setColor(color);
            return drawable;
        }

        @Override
        public int getItemCount() {
            return arrays.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {

            private TextView text;

            public MyHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.flow_text);
            }
        }
    }
}
