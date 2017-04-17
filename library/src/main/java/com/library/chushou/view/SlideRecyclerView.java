package com.library.chushou.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.library.chushou.callback.ChuShouCallBack;
import com.library.chushou.manager.TotalHeightLayoutManager;

/**
 * Created by xiangcheng on 17/4/13.
 * 子recyclerView，用来处理ChuShouCallBack是否有swipe动作
 */

public class SlideRecyclerView extends RecyclerView implements ChuShouCallBack.OnSwipedListener {
    private static final String TAG = SlideRecyclerView.class.getSimpleName();
    /**
     * 当前y轴滑动的位置
     */
    private int scrollY;

    ChuShouCallBack chuShouCallBack;

    ScrollRecyclerView sr;

    ViewGroup parent;

    //是否下拉的标志
    boolean pullDown;

    public SlideRecyclerView(Context context) {
        this(context, null);
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                initView();
                if (getIsCurrentItem()) {
                    addOnScrollListener(new OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                        }

                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            Log.d(TAG, "position:" + sr.getChildViewHolder(parent).getAdapterPosition());
                            Log.d(TAG, "onScrolled:" + dy);
                            scrollY += dy;
                            Log.d(TAG, "scrollY:" + scrollY);
                            if (scrollY == 0) {
                                //如果父recyclerView已经在顶部并且还往上滑的时候，让chuShouCallBack没有swipe动作
                                if (dy < 0) {
                                    chuShouCallBack.setDefaultSwipeDirs(0);
                                }
                            }
                            if (isSlideToBottom()) {
                                //如果父recyclerView已经在底部并且还往下拉的时候，让chuShouCallBack没有swipe动作
                                if (dy > 0) {
                                    chuShouCallBack.setDefaultSwipeDirs(0);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private float startY;
    private float dataY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (getIsCurrentItem()) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startY = e.getY();
                    chuShouCallBack.setDefaultSwipeDirs(0);
                    break;
                case MotionEvent.ACTION_MOVE:
                    dataY = e.getY() - startY;
                    //只有滑动到顶部的时候才会通过判断两点之间的距离来切换item
                    if (scrollY == 0) {
                        if (dataY > 0) {
                            chuShouCallBack.setDefaultSwipeDirs(ItemTouchHelper.UP | ItemTouchHelper.DOWN);
                            Log.d(TAG, "到了顶部往下拉的时候");
                        }
                    }
                    if (isSlideToBottom()) {
                        if (dataY < 0) {
                            chuShouCallBack.setDefaultSwipeDirs(ItemTouchHelper.UP | ItemTouchHelper.DOWN);
                            Log.d(TAG, "到了底部往上拉的时候");
                        }
                    }
                    if (scrollY != 0 && !isSlideToBottom()) {
                        chuShouCallBack.setDefaultSwipeDirs(0);
                        Log.d(TAG, "在中间的位置");
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    break;
            }
        }
        return super.onTouchEvent(e);
    }

    /**
     * 初始化chuShouCallBack
     */
    private void initView() {
        parent = (ViewGroup) getParent();
        ViewGroup vp = (ViewGroup) parent.getParent();
        if (vp instanceof ScrollRecyclerView) {
            sr = (ScrollRecyclerView) vp;
            if (getIsCurrentItem()) {
                chuShouCallBack = sr.getChuShouCallBack();
                chuShouCallBack.setOnSwipedListener(this);
            }
        }
    }

    /**
     * 判断当前正在显示的item
     *
     * @return
     */
    private boolean getIsCurrentItem() {
        ViewHolder vh = sr.getChildViewHolder(parent);
        if (vh.getAdapterPosition() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断父RecyclerView是否到了底部
     *
     * @return
     */
    public boolean isSlideToBottom() {
        LayoutManager lm = getLayoutManager();
        if (lm instanceof TotalHeightLayoutManager) {
            TotalHeightLayoutManager tl = (TotalHeightLayoutManager) lm;
            int totalHeight = tl.getTotalHeight();
            Log.d(TAG, "totalHeight:" + totalHeight);
            Log.d(TAG, "getHeight():" + getHeight());
            Log.d(TAG, "scrollY:" + scrollY);
            if (totalHeight - getHeight() == scrollY) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onSwiped(boolean pullDown) {
        Log.d(TAG, "onSwiped");
        scrollY = 0;
        this.pullDown = pullDown;
    }

    //处理下拉的时候onswipe完成后滑动到底部的操作
    public void pullScroll() {
        Log.d(TAG, "pullScroll");
        if (pullDown) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    ViewHolder vh = sr.getChildViewHolder(parent);
                    if (vh.getAdapterPosition() == 0) {
                        LayoutManager lm = getLayoutManager();
                        TotalHeightLayoutManager tl = (TotalHeightLayoutManager) lm;
                        Log.d(TAG, "tl.getTotalHeight() :" + tl.getTotalHeight());
                        Log.d(TAG, "getHeight() :" + getHeight());
                        if (tl.getTotalHeight() > getHeight()) {
                            Log.d(TAG, "scrollTo:" + (tl.getTotalHeight() - getHeight()));
                            scrollBy(0, tl.getTotalHeight() - getHeight());
                        }
                    }
                }
            });
        }
    }

    //处理ChuShouCallBack下拉onChildDraw动作，让当前item直接滑动到底部的操作
    public void pullNextScroll() {
        Log.d(TAG, "pullNextScroll");
        LayoutManager lm = getLayoutManager();
        TotalHeightLayoutManager tl = (TotalHeightLayoutManager) lm;
        Log.d(TAG, "tl.getTotalHeight() :" + tl.getTotalHeight());
        Log.d(TAG, "getHeight() :" + getHeight());
        if (tl.getTotalHeight() > getHeight()) {
            Log.d(TAG, "scrollTo:" + (tl.getTotalHeight() - getHeight()));
            scrollBy(0, tl.getTotalHeight() - getHeight());
        }
    }
}
