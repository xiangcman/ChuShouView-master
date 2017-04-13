package com.single.chushou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.single.chushou.R;

import java.util.List;

/**
 * Created by xiangcheng on 17/4/11.
 */

public class ChuShouAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Integer> maps;
    private Context context;

    public ChuShouAdapter(Context context, List maps) {
        this.maps = maps;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(View.inflate(context, R.layout.item_layout, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyHolder) holder).img.setImageResource(maps.get(position));
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
