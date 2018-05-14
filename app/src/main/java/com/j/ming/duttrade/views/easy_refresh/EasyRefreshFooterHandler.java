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

public abstract class EasyRefreshFooterHandler {
    @LayoutRes
    protected int footerResourceId;

    public EasyRefreshFooterHandler(int footerResourceId) {
        this.footerResourceId = footerResourceId;
    }


    public void getView(LayoutInflater inflater, ViewGroup viewGroup){
        inflater.inflate(footerResourceId, viewGroup, true);
    }

    public abstract void scrolling(View footer, int scrollDistance, int totalHeaderHeight);

    public abstract void init(View footer);

    public abstract void loading(View footer);

    public abstract void loadFinish(View footer);


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
}
