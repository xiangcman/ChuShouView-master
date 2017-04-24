package com.library.chushou.callback;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
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

    private float fingerY;
    private float baseFingerY;

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
//        Log.d(TAG, "onMove");
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
        lastDy = 0;
        isChangeFinger = false;
        baseFingerY = 0;
//        canPull = false;
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

    //add  2017/4/17，为了解决换手指的bug，保存上一次拉到的位置
    private float lastDy;
    //add  2017/4/17，为了解决换手指的bug，当松手指的时候用到的值动画
    ValueAnimator valueAnimator;

    boolean isChangeFinger;

    /**
     * 监听recyclerView切换item的事件
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        // super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);这里就不要执行父类的该方法了,执行后就会让当前item随手势移动了，这样就不是我们想要的效果了
//        Log.d(TAG,"dy")
        if (height == 0) {
            height = recyclerView.getHeight();
        }
        Log.d(TAG, "dy:" + dY + ",actionState:" + actionState + ",isCurrentlyActive:" + isCurrentlyActive);
//        Log.d(TAG, "lastDy:" + lastDy);
        //add  2017/4/17，此时换了手指再去按住nextView，如果是下拉时：lastDy > 0 && dY <= 0，如果是上拉时：lastDy < 0 && dY >= 0
        if (lastDy > 0 && dY <= 0 || lastDy < 0 && dY >= 0) {
            //这个是当松手时isCurrentlyActive=false
            isChangeFinger = true;
            baseFingerY = lastDy;
            if (!isCurrentlyActive) {
                if (valueAnimator == null) {
                    //从松手一瞬间，从lastDy的位置到0
                    valueAnimator = ValueAnimator.ofFloat(lastDy, 0);
                    //这里的下拉或上拉的最大距离是按照swipe的临界值来算的
                    float maxPullHeight = height * getSwipeThreshold(viewHolder);
                    //最长的时间是200毫秒
                    float duration = 200 * (Math.abs(lastDy) / maxPullHeight);
                    valueAnimator.setDuration((long) duration);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float animatedValue = (float) animation.getAnimatedValue();
                            float percent = Math.abs(animatedValue / height);
                            float scaleAlpha = (float) (1.0 - percent * 1.0);
                            viewHolder.itemView.setAlpha(scaleAlpha);
                            ((ViewGroup) viewHolder.itemView).getChildAt(0).setScaleX(scaleAlpha);
                            ((ViewGroup) viewHolder.itemView).getChildAt(0).setScaleY(scaleAlpha);
                            nextView.setTranslationY(animatedValue);
                        }
                    });
                    valueAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            lastDy = 0;
                            isChangeFinger = false;
                            baseFingerY = 0;
                            valueAnimator = null;
                        }
                    });
                    valueAnimator.start();
                }
            }

        } else {
            //该种情况就是没有换手指的情况
            if (!isChangeFinger) {
                pullSome(recyclerView, viewHolder, dY);
            } else {
                Log.d(TAG, "换了手指，继续往下拉或往上拉的情况");
                //如果是在换手指情况下呢
                fingerY = baseFingerY + dY;
                if (fingerY >= height) {
                    fingerY = height;
                }
                if (fingerY <= -height) {
                    fingerY = -height;
                }
                Log.d(TAG, "fingerY:" + fingerY);
                pullSome(recyclerView, viewHolder, fingerY);
            }
        }
    }

    private void pullSome(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dY) {
        float percent = Math.abs(dY / height);
        float scaleAlpha = (float) (1.0 - percent * 1.0);
        viewHolder.itemView.setAlpha(scaleAlpha);
        ((ViewGroup) viewHolder.itemView).getChildAt(0).setScaleX(scaleAlpha);
        ((ViewGroup) viewHolder.itemView).getChildAt(0).setScaleY(scaleAlpha);
        //往下拉
        if (dY > 0) {
            nextView = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
            View childAt = ((ViewGroup) nextView).getChildAt(0);
            if (childAt instanceof SlideRecyclerView) {
                SlideRecyclerView sl = (SlideRecyclerView) childAt;
                if (sl.getScrollY() == 0) {
                    sl.pullNextScroll();
                }
            }
            nextView.setTranslationY(dY);
            pullDown = true;
            lastDy = dY;
        } else if (dY < 0) {
            nextView = recyclerView.getChildAt(1);
            pullDown = false;
            nextView.setTranslationY(dY);
            lastDy = dY;
        }
    }

}
