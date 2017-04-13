package com.library.chushou;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xiangcheng on 17/4/11.
 */

public class ChuShouManager extends RecyclerView.LayoutManager {
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        Log.d("ChuShouManager", "getItemCount():" + getItemCount());
        for (int i = 0; i < getItemCount(); i++) {
            View childAt = recycler.getViewForPosition(i);
            measureChildWithMargins(childAt, 0, 0);
            addView(childAt);
            if (getChildCount() >= 1) {
                //放在中间
                if (i == 0) {
                    layoutDecoratedWithMargins(childAt, 0, 0,
                            getDecoratedMeasuredWidth(childAt),
                            getDecoratedMeasuredHeight(childAt));
                }
            }
            if (getChildCount() >= 2) {
                //放在下面
                if (i >= 1 && i < getItemCount() - 1) {
                    layoutDecoratedWithMargins(childAt, 0, getHeight(),
                            getDecoratedMeasuredWidth(childAt),
                            getHeight() + getDecoratedMeasuredHeight(childAt));
                }
            }

            if (getChildCount() >= 3) {
                //最后一个view放在最上面
                if (i == getItemCount() - 1) {
                    layoutDecoratedWithMargins(childAt, 0, -getDecoratedMeasuredHeight(childAt),
                            getDecoratedMeasuredWidth(childAt),
                            0);
                }
            }
        }
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
