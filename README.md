前几天在看手机直播的时候，自己就用上了触手app。一进到主页就看上了里面页面切换的效果，自己想这种效果用什么控件可以实现呢。触手app主页效果图:

![触手app主页效果.gif](https://github.com/1002326270xc/ChuShouView-master/blob/master/photos/触手app主页效果.gif)

看到这个效果图后，第一想到的就是`RecyclerView`貌似可以实现这种效果，但是用`RecyclerView`自己的api还是有很多问题的，先不说如何实现的吧，看下实现出来的效果图吧:

**图片式:**

![图片式效果图.gif](https://github.com/1002326270xc/ChuShouView-master/blob/master/photos/图片式效果图.gif)

**流式布局效果:**

![流式布局效果图.gif](https://github.com/1002326270xc/ChuShouView-master/blob/master/photos/流式布局效果图.gif)

**多种样式效果:**

![多种样式效果图.gif](https://github.com/1002326270xc/ChuShouView-master/blob/master/photos/多种样式效果图.gif)

**讲解:**
下面就来讲讲如何用`RecyclerView`如何实现上面的效果了:

来张自己画的思路草图吧:

![草图.png](https://github.com/1002326270xc/ChuShouView-master/blob/master/photos/草图.png)

**这里整体就是一个`RecyclerView`了，而且在初始的时候，需要定义我们自己的`Layoutmanager`，代码里面可见(`ChuShouManager`)该类,该`Layoutmanager`的功能就是让最后一个item在屏幕的上面显示，第一个item在屏幕中显示，第二个item到倒数第二个item在屏幕的下面显示。所以手机上面显示的永远是`RecyclerView`中第一个item了，只不过在手指滑动的时候，去改变数据源。**

```java
/**
 * Created by xiangcheng on 17/4/11.
 * 初始化RecyclerView中所有item的位置
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
```

好了，第一步终于完成啦，下面就是`RecyclerView`的`touch`相关代码控制了，关于`touch`的控制，我们需要接触到`android.support.v7.widget.helper.ItemTouchHelper.Callback`该类了:

![屏幕快照 2017-04-17 14.11.28.png](http://upload-images.jianshu.io/upload_images/2528336-4cd6787a1088c9c8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

从源码截图中看到该类是一个**静态的抽象类**，说明我们要使用的时候，需要去实现该类了。这里我这里也定义了一个实现类**ChuShouCallBack**

```java
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
       // super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);这里就不要执行父类的该方法了,执行后就会让当前item随手势移动了，这样就不是我们想要的效果了
        if (height == 0) {
            Log.d(TAG, "height is 0");
            height = recyclerView.getHeight();
        }
        Log.d(TAG, "dy:" + dY);
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
            pullDown = true;
        } else {
            nextView = recyclerView.getChildAt(1);
            pullDown = false;
        }
        nextView.setTranslationY(dY);
    }
```

大家很好奇，为啥上面的父类不是`android.support.v7.widget.helper.ItemTouchHelper.Callback`呢，而是`android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback`该类呢，这是因为该类在`android.support.v7.widget.helper.ItemTouchHelper.Callback`基础上加了`onSwiped`的操作，该方法就是监听当前`item`滑动位置达到了一个值就触发`onSwiped `,源码的默认值：

```java
 /**
 * Returns the fraction that the user should move the View to be considered as swiped.
 * The fraction is calculated with respect to RecyclerView's bounds.
 * <p>
 * Default value is .5f, which means, to swipe a View, user must move the View at least
 * half of RecyclerView's width or height, depending on the swipe direction.
 *
 * @param viewHolder The ViewHolder that is being dragged.
 * @return A float value that denotes the fraction of the View size. Default value
 * is .5f .
 */
 public float getSwipeThreshold(ViewHolder viewHolder) {
      return .5f;
 }
```

**源码说是只要滑动位置超过了RecyclerView的width或height时就会触发onSwiped方法，我们这里不需要去动该值就可以了，默认就可以**

**onChildDraw方法不要去重写父类的了，重写后会让当前item随手势移动了，这里我们要的只是当前itemscale和alpha的改变**

好了手势的说明也说完了。

**再来细说上面几个效果图中展示的效果:**

**这里分两种操作，一种是item自己本身有滑动的操作和item没有滑动的操作。图二和图三就是有滑动操作的情况，此时需要动态去改变ChuShouCallBack的swipDirection，当item滑动到顶部或底部的时候，才去打开ChuShouCallBack的swipDirection，其余情况都不打开了**

第一个效果图中的每一个item就是一个图片(`ImageView`)，也就是item没有滑动的动作，直接使用:

```java
setContentView(R.layout.activity_main);
RecyclerView chuShouView = (RecyclerView) findViewById(R.id.chushou_view);
chuShouView.setLayoutManager(new ChuShouManager());
ChuShouAdapter adapter;
chuShouView.setAdapter(adapter = new ChuShouAdapter(this, maps));

ItemTouchHelper.Callback callback = new ChuShouCallBack(adapter, maps, ItemTouchHelper.UP | ItemTouchHelper.DOWN);
ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
itemTouchHelper.attachToRecyclerView(chuShouView);
```

使用代码可见`ChuShouImageActivity`

第二个效果图中的每一个item又是一个`RecyclerView`，所以这里的思路就是`RecyclerView`的嵌套了。只不过里面的`RecyclerView`显示的是流式布局了，想要学习`RecyclerView`搭建流式布局，去我的另一篇文章([RecyclerView快速搭建流式布局](http://www.jianshu.com/p/95bd3dd02d42))。
**上面说到该种情况item是带滑动的，因此和`ChuShouCallBack`的onSwiped动作是有冲突的，这里需要我们通过代码去判断什么时候去打开onSwiped，什么去关闭onSwiped动作了，因此该处我自定义了一个SlideRecyclerView类，也就是我们item要用到的RecyclerView了**

```java
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
    //父RecyclerView：主要是接收自己的chuShouCallBack，没什么好说的
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
```

**如何使用:**

```java
setContentView(R.layout.activity_main);

ScrollRecyclerView chuShouView = (ScrollRecyclerView) findViewById(R.id.chushou_view);
chuShouView.setLayoutManager(new ChuShouManager());
ChuShouScrollAdapter adapter;
List<List<ShowItem>> items = DataConfig.getItems();

adapter = new ChuShouScrollAdapter(this, items);
ChuShouCallBack callback = new ChuShouCallBack(adapter, items);
ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
itemTouchHelper.attachToRecyclerView(chuShouView);

chuShouView.setAdapter(adapter);
chuShouView.setChuShouCallBack(callback);
```

使用代码可见`ChuShouScrollActivity`

第三个效果图中的每一个item又是一个`RecyclerView`**(SlideRecyclerView)**,此时的item用到的`RecyclerView`的排版就是`LinearLayoutManager`。

**如何使用:**
```java
setContentView(R.layout.activity_main);
ScrollRecyclerView chuShouView = (ScrollRecyclerView) findViewById(R.id.chushou_view);
List<List<GridItem>> gridItems = DataConfig.getGridItems();

chuShouView.setLayoutManager(new ChuShouManager());

ChuShouGridAdapter chuShouGridAdapter = new ChuShouGridAdapter(this, gridItems);

ChuShouCallBack callback = new ChuShouCallBack(chuShouGridAdapter, gridItems);
ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
itemTouchHelper.attachToRecyclerView(chuShouView);

chuShouView.setAdapter(chuShouGridAdapter);
chuShouView.setChuShouCallBack(callback);
```

使用代码可见`ChuShouGridActivity`

好了，代码讲解大致过了一遍了。如果还有什么不懂的，可以直接跟我联系

### 后续添加:
滑动控件还会有`ListView`、`ScrollView`等

### 关于我:
**email:** a1002326270@163.com

**csdn:**[enter](http://blog.csdn.net/u010429219/article/details/70186730)

**简书:**[enter](http://www.jianshu.com/p/cf2169630f5e)
