package com.library.chushou.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.library.chushou.callback.ChuShouCallBack;

/**
 * Created by xiangcheng on 17/4/13.
 * 父RecyclerView：主要是接收自己的chuShouCallBack，没什么好说的
 */

public class ScrollRecyclerView extends RecyclerView {
    private ChuShouCallBack chuShouCallBack;

    public ScrollRecyclerView(Context context) {
        super(context);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setChuShouCallBack(ChuShouCallBack chuShouCallBack) {
        this.chuShouCallBack = chuShouCallBack;
    }

    public ChuShouCallBack getChuShouCallBack() {
        return chuShouCallBack;
    }
}
