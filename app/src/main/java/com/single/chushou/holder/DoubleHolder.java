package com.single.chushou.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.single.chushou.R;

/**
 * Created by xiangcheng on 17/4/14.
 */

public class DoubleHolder extends RecyclerView.ViewHolder {
    public ImageView leftImg;
    public ImageView rightImg;
    public TextView leftText;
    public TextView rightText;

    public DoubleHolder(View itemView) {
        super(itemView);
        leftImg = (ImageView) itemView.findViewById(R.id.img1);
        leftText = (TextView) itemView.findViewById(R.id.text1);

        rightImg = (ImageView) itemView.findViewById(R.id.img2);
        rightText = (TextView) itemView.findViewById(R.id.text2);
    }
}
