package com.single.chushou.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.single.chushou.R;

/**
 * Created by xiangcheng on 17/4/14.
 */

public class SingleHolder extends RecyclerView.ViewHolder {
    public ImageView img;
    public TextView text;

    public SingleHolder(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.img1);
        text = (TextView) itemView.findViewById(R.id.text1);
    }
}
