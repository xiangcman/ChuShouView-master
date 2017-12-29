package com.single.chushou.manager;

import com.library.chushou.manager.TotalHeightLayoutManager;
import com.library.flowlayout.FlowLayoutManager;

/**
 * Created by xiangcheng on 17/3/18.
 * 一种流式布局的LayoutManagery
 */

public class MyFlowLayoutManager extends FlowLayoutManager implements TotalHeightLayoutManager {

    @Override
    public int getTotalHeight() {

        return super.getTotalHeight() + getPaddingTop() + getPaddingBottom();
    }

}
