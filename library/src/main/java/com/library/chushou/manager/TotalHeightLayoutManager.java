package com.library.chushou.manager;

import android.support.v7.widget.RecyclerView;

/**
 * Created by xiangcheng on 17/4/13.
 * 用来获取总共高度的layoutmanager
 */

public abstract class TotalHeightLayoutManager extends RecyclerView.LayoutManager {
    public abstract int getTotalHeight();
}
