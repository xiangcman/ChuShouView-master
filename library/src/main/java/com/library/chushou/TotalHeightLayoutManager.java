package com.library.chushou;

import android.support.v7.widget.RecyclerView;

/**
 * Created by xiangcheng on 17/4/13.
 */

public abstract class TotalHeightLayoutManager extends RecyclerView.LayoutManager {
    //    protected int totalHeight;
    public abstract int getTotalHeight();
}
