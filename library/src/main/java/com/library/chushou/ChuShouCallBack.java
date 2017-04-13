package com.library.chushou;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by xiangcheng on 17/3/24.
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

    public void setOnSwipedListener(OnSwipedListener onSwipedListener) {
        this.onSwipedListener = onSwipedListener;
    }

    private OnSwipedListener onSwipedListener;

    /**
     * 下一个要展示的view，如果是向下拉的话，就是上一个view，如果是往上拉的话就是下一个view
     */
    private View nextView;

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

    public interface OnSwipedListener {
        void onSwiped();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d(TAG, "onSwiped");

        refreshData(viewHolder);
        if (onSwipedListener != null) {
            onSwipedListener.onSwiped();
        }
    }

    private void refreshData(RecyclerView.ViewHolder viewHolder) {
        //将当前的item进行还原
        viewHolder.itemView.setAlpha(1);
        ((ViewGroup) viewHolder.itemView).getChildAt(0).setScaleX(1);
        ((ViewGroup) viewHolder.itemView).getChildAt(0).setScaleY(1);
        Object temp;
        if (pullDown) {
            //将上面移动的进行还原
            nextView.setTranslationY(-height);
            //将集合整体往后移一位[i+1]=[i]
            int length = mDatas.size();
            //把最后一个给保存起来
            temp = mDatas.get(length - 1);
            for (int j = length - 2; j >= 0; j--) {
                mDatas.set(j + 1, mDatas.get(j));
            }
            mDatas.set(0, temp);

        } else {
            //将下面移动的进行还原
            nextView.setTranslationY(height);
            //将集合整体往前移一位[i]=[i+1]
            temp = mDatas.get(0);
            for (int i = 0; i < mDatas.size() - 1; i++) {
                mDatas.set(i, mDatas.get(i + 1));
            }
            mDatas.set(mDatas.size() - 1, temp);
        }
        //刷新item
        mAdapter.notifyDataSetChanged();

    }

    //此种方式是原来自己想的一种方式，也可以改变选中的item的样式
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
            pullDown = true;
        } else {
            nextView = recyclerView.getChildAt(1);
            pullDown = false;
        }
        nextView.setTranslationY(dY);
    }

}
