[![](https://jitpack.io/v/1002326270xc/ChuShouView-master.svg)](https://jitpack.io/#1002326270xc/ChuShouView-master/v1.3)

触手app新版已经改版了,大家请下载[2.2.3.7424](http://download.csdn.net/download/u010429219/9941988):

**触手app主页效果图:**

![触手app主页效果.gif](https://github.com/1002326270xc/ChuShouView-master/blob/master/photos/触手app主页效果.gif)

看下实现出来的效果图吧:

**图片式:**

![图片式效果图.gif](https://github.com/1002326270xc/ChuShouView-master/blob/master/photos/图片式效果图.gif)

**流式布局效果:**

![流式布局效果图.gif](https://github.com/1002326270xc/ChuShouView-master/blob/master/photos/流式布局效果图.gif)

**多种样式效果:**

![多种样式效果图.gif](https://github.com/1002326270xc/ChuShouView-master/blob/master/photos/多种样式效果图.gif)

### 教你一分钟搞定如何使用:

**设置Manager:**

```
RecyclerView chuShouView = (RecyclerView) findViewById(R.id.chushou_view);
chuShouView.setLayoutManager(new ChuShouManager());
```
 
**设置触摸辅助类ChuShouCallBack:**

```        
ItemTouchHelper.Callback callback = new ChuShouCallBack(adapter, maps, ItemTouchHelper.UP | ItemTouchHelper.DOWN);
ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
itemTouchHelper.attachToRecyclerView(chuShouView);
```

**图片式设置Adapter:**

```
chuShouView.setAdapter(adapter = new ChuShouAdapter(this, maps));
```

**流式布局式设置Adapter:**

```
chuShouView.setAdapter(adapter = new ChuShouScrollAdapter(this, items));
```

**多种样式设置Adapter:**

```
chuShouView.setAdapter(chuShouGridAdapter = new ChuShouGridAdapter(this, gridItems));
```

这里面的流式布局的Adapter和多种样式的Adapter有一个共同点，它们的item都是带有滑动结构的，因此这里我把它们的结构当成**RecyclerView+RecyclerView**来处理了，而上面的图片式结构就是**RecyclerView+ImageView**来处理了，大家可以着重看看**ChuShouScrollAdapter**和**ChuShouGridAdapter**代码：

**ChuShouGridAdapter**的**onCreateViewHolder**方法:

```
@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new MyHolder<ChuShouGridActivity.GridItem>(View.inflate(context, R.layout.scroll_item_layout, null), context) {
        @Override
        protected RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter(List<ChuShouGridActivity.GridItem> list, Context context) {
            return new ChuShouGridItemAdapter(list, context);
        }
        @Override
        protected RecyclerView.LayoutManager getLayoutManager(Context context, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
            return new ChuShouGridLayoutManager(context, adapter);
        }
    };
}
```

**ChuShouScrollAdapter**的**onCreateViewHolder**方法:

```
@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new MyHolder<ChuShouScrollActivity.ShowItem>(View.inflate(context, R.layout.scroll_item_layout, null), context) {
        @Override
        protected RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter(List<ChuShouScrollActivity.ShowItem> list, Context context) {
            return new FlowAdapter(list, context);
        }
        @Override
        protected RecyclerView.LayoutManager getLayoutManager(Context context, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
            return new FlowLayoutManager();
        }
    };
}
```

**R.layout.scroll_item_layout代码:**

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#cccccc"
    android:orientation="vertical">

    <com.library.chushou.view.SlideRecyclerView
        android:id="@+id/container"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>
```

### 后续添加:
滑动控件还会有`ListView`、`ScrollView`等

**欢迎客官到本店光临:**`184793647`(qq群)

**v1.3版本:**
修复触摸问题

### gradle依赖:
```java
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
        ...
        compile 'com.github.1002326270xc:ChuShouView-master:v1.3'
        ...
}
```

### 关于我:
**email:** a1002326270@163.com

**csdn:**[enter](http://blog.csdn.net/u010429219/article/details/70186730)

**简书:**[enter](http://www.jianshu.com/p/cf2169630f5e)
