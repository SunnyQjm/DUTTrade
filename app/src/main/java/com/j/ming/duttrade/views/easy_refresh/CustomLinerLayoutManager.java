package com.j.ming.duttrade.views.easy_refresh;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by Sunny on 2017/9/8 0008.
 */

public class CustomLinerLayoutManager extends LinearLayoutManager {

    private boolean isScrollAble = true;

    public CustomLinerLayoutManager(Context context) {
        super(context);
    }

    public CustomLinerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomLinerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setScrollAble(boolean scrollAble) {
        isScrollAble = scrollAble;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollAble && super.canScrollVertically();
    }
}
