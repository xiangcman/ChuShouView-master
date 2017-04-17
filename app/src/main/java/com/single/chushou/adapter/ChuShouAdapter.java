package com.single.chushou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.single.chushou.R;

import java.util.List;

/**
 * Created by xiangcheng on 17/4/11.
 */

public class ChuShouAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ChuShouAdapter.class.getSimpleName();
    private List<String> maps;
    private Context context;

    public ChuShouAdapter(Context context, List maps) {
        this.maps = maps;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        return new MyHolder(View.inflate(context, R.layout.item_layout, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        Glide.with(context).load(maps.get(position)).into(((MyHolder) holder).img);
    }

    @Override
    public int getItemCount() {
        return maps.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public MyHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
