package com.j.ming.duttrade.views.easy_refresh;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Sunny on 2017/9/9 0009.
 */

public abstract class EasyRefreshHeaderHandler {
    @LayoutRes
    protected int headerResourceId;
    /**
     * 箭头转向比例，如果等于1则表示要头布局完全下拉箭头才转向
     */
    protected float changeRate = 0.8f;

    public EasyRefreshHeaderHandler(int headResourceId) {
        this.headerResourceId = headResourceId;
    }

    public abstract void getView(LayoutInflater inflater, ViewGroup viewGroup);

    public abstract void scrolling(View header, int scrollDistance, int totalHeaderHeight);

    public abstract void init(View header);

    public abstract void refreshing(View header);

    public abstract void refreshFinish(View header);

    public void setText(View containView, int id, String text) {
        View view = containView.findViewById(id);
        if(view instanceof TextView){
            ((TextView)view).setText(text);
        }
        if(view instanceof Button){
            ((Button)view).setText(text);
        }
    }

    public void setRotation(View containView, int id, float rotation) {
        containView.findViewById(id).setRotation(rotation);
    }

    public void setVisibility(View containView, int id, int visible) {
        containView.findViewById(id).setVisibility(visible);
    }

    public float getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(float changeRate) {
        this.changeRate = changeRate;
    }
}
