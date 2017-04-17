package com.library.chushou.manager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xiangcheng on 17/4/11.
 * 初始化所有item的位置
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
            //防止数量没达到1个以上的要求
            if (getChildCount() >= 1) {
                //第一个item放在屏幕中
                if (i == 0) {
                    layoutDecoratedWithMargins(childAt, 0, 0,
                            getDecoratedMeasuredWidth(childAt),
                            getDecoratedMeasuredHeight(childAt));
                }
            }
            //需要判断数量
            if (getChildCount() >= 2) {
                //从第二个item到倒数第二个放在屏幕下面
                if (i >= 1 && i < getItemCount() - 1) {
                    layoutDecoratedWithMargins(childAt, 0, getHeight(),
                            getDecoratedMeasuredWidth(childAt),
                            getHeight() + getDecoratedMeasuredHeight(childAt));
                }
            }
            //数量要求
            if (getChildCount() >= 3) {
                //最后一个item放在屏幕上面
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
        //这里就直接定义recyclerView里面item直接占满整个屏幕了,没有办法啦，触手app都是这样的啊
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
