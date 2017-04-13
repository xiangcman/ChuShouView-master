package com.library.chushou;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by xiangcheng on 17/4/13.
 */

public class SlideRecyclerView extends RecyclerView implements ChuShouCallBack.OnSwipedListener {
    private static final String TAG = SlideRecyclerView.class.getSimpleName();

    private int scrollY;

    ChuShouCallBack chuShouCallBack;

    ScrollRecyclerView sr;

    ViewGroup parent;


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
//                        ViewHolder vh = sr.getChildViewHolder(parent);
//                        if (getIsCurrentItem()) {
                            if (scrollY == 0) {
                                if (dy < 0) {
                                    chuShouCallBack.setDefaultSwipeDirs(0);
                                }
                            }
                            if (isSlideToBottom()) {
                                if (dy > 0) {
                                    chuShouCallBack.setDefaultSwipeDirs(0);
                                }
                            }
//                        }
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
//                if (getIsCurrentItem()) {
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
//                scrollY = 0;
                    break;
            }
        }
        return super.onTouchEvent(e);
    }

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

    private boolean getIsCurrentItem() {
        ViewHolder vh = sr.getChildViewHolder(parent);
        if (vh.getAdapterPosition() == 0) {
            return true;
        }
        return false;
    }

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
//        if (recyclerView == null) return false;
//        if (computeVerticalScrollExtent() + computeVerticalScrollOffset()
//                >= computeVerticalScrollRange())
//            return true;
//        return false;

    }

    @Override
    public void onSwiped() {
        Log.d(TAG, "onSwiped");
        scrollY = 0;
    }
}
