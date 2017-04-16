package com.library.chushou.callback;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.library.chushou.view.SlideRecyclerView;

import java.util.Collections;
import java.util.List;

/**
 * Created by xiangcheng on 17/3/24.
 * 用来处理recyclerView里面item的swipe动作
 */

public class ChuShouCallBack extends ItemTouchHelper.SimpleCallback {

    private static final String TAG = ChuShouCallBack.class.getSimpleName();
    protected List mDatas;
    protected RecyclerView.Adapter mAdapter;

    /**
     * recyclerView的高度
     */
    private int height;

    /**
     * 向下拉的标志
     */
    private boolean pullDown;

    private OnSwipedListener onSwipedListener;

    /**
     * 下一个要展示的view，如果是向下拉的话，就是上一个view，如果是往上拉的话就是下一个view
     */
    private View nextView;

    public void setOnSwipedListener(OnSwipedListener onSwipedListener) {
        this.onSwipedListener = onSwipedListener;
    }


    public ChuShouCallBack(RecyclerView.Adapter adapter, List mDatas) {
        this(adapter, mDatas, 0);
    }

    public ChuShouCallBack(RecyclerView.Adapter adapter, List mDatas, int swipDirection) {
        this(0, swipDirection);
        this.mAdapter = adapter;
        this.mDatas = mDatas;
    }

    public ChuShouCallBack(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.d(TAG, "onMove");
        return true;
    }

    /**
     * 用来处理onswipe时候的回调接口
     */
    public interface OnSwipedListener {
        void onSwiped(boolean pullDown);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d(TAG, "onSwiped");
        refreshData(viewHolder);
        if (onSwipedListener != null) {
            onSwipedListener.onSwiped(pullDown);
        }
    }

    /**
     * 处理swipe时候view的还原以及数据源的刷新
     *
     * @param viewHolder
     */
    private void refreshData(RecyclerView.ViewHolder viewHolder) {
        //将当前的item进行还原
        viewHolder.itemView.setAlpha(1);
        ((ViewGroup) viewHolder.itemView).getChildAt(0).setScaleX(1);
        ((ViewGroup) viewHolder.itemView).getChildAt(0).setScaleY(1);
        Object temp;
        if (pullDown) {
            //将上面移动的进行还原
            nextView.setTranslationY(-height);
            Collections.rotate(mDatas, 1);
        } else {
            //将下面移动的进行还原
            nextView.setTranslationY(height);
            Collections.rotate(mDatas, -1);
        }
        //刷新item
        mAdapter.notifyDataSetChanged();

    }

    /**
     * 监听recyclerView切换item的事件
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (height == 0) {
            height = recyclerView.getHeight();
        }
        float percent = Math.abs(dY / height);
        float alpha = (float) (1.0 - percent * 1.0);
        float scale = (float) (1.0 - percent * 1.0);
        viewHolder.itemView.setAlpha(alpha);
        ((ViewGroup) viewHolder.itemView).getChildAt(0).setScaleX(scale);
        ((ViewGroup) viewHolder.itemView).getChildAt(0).setScaleY(scale);
        //往下拉
        if (dY > 0) {
            nextView = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
            View childAt = ((ViewGroup) nextView).getChildAt(0);
            if (childAt instanceof SlideRecyclerView) {
                SlideRecyclerView sl = (SlideRecyclerView) childAt;
                sl.pullNextScroll();
            }
            pullDown = true;
        } else {
            nextView = recyclerView.getChildAt(1);
            pullDown = false;
        }
        nextView.setTranslationY(dY);
    }

}
