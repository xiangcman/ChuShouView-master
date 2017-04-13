package com.library.chushou;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by xiangcheng on 17/4/13.
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

    public void setChuShouCallBack(ChuShouCallBack chuShouCallBack){
        this.chuShouCallBack=chuShouCallBack;
    }

    public ChuShouCallBack getChuShouCallBack() {
        return chuShouCallBack;
    }
}
