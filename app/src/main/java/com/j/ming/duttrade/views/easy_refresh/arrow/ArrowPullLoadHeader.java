package com.j.ming.duttrade.views.easy_refresh.arrow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j.ming.duttrade.R;
import com.j.ming.duttrade.views.easy_refresh.EasyRefreshHeaderHandler;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by Sunny on 2017/10/7 0007.
 */

public class ArrowPullLoadHeader extends EasyRefreshHeaderHandler {
    private Context context = null;
    public ArrowPullLoadHeader(int headResourceId) {
        super(headResourceId);
    }

    @Override
    public void getView(@NonNull LayoutInflater inflater, ViewGroup viewGroup) {
        context = inflater.getContext();
        inflater.inflate(headerResourceId, viewGroup, true);
    }

    @Override
    public void scrolling(View header, int scrollDistance, int totalHeaderHeight) {
        if (scrollDistance > (totalHeaderHeight * changeRate)) {
            this.setText(header, R.id.refresh_text, context.getString(R.string.release_refresh));
            this.setRotation(header, R.id.refresh_arrow, 180f);
            this.setVisibility(header, R.id.refresh_text, VISIBLE);
            this.setVisibility(header, R.id.refresh_progressBar, INVISIBLE);
            this.setVisibility(header, R.id.refresh_arrow, VISIBLE);
        } else {
            this.setText(header, R.id.refresh_text, context.getString(R.string.pull_down_load));
            this.setRotation(header, R.id.refresh_arrow, 0f);
            this.setVisibility(header, R.id.refresh_text, VISIBLE);
            this.setVisibility(header, R.id.refresh_progressBar, INVISIBLE);
            this.setVisibility(header, R.id.refresh_arrow, VISIBLE);
        }
    }

    @Override
    public void init(View header) {
        this.setText(header, R.id.refresh_text, context.getString(R.string.pull_down_load));
        this.setRotation(header, R.id.refresh_arrow, 0f);
        this.setVisibility(header, R.id.refresh_text, VISIBLE);
        this.setVisibility(header, R.id.refresh_progressBar, INVISIBLE);
        this.setVisibility(header, R.id.refresh_arrow, VISIBLE);
    }

    @Override
    public void refreshing(View header) {
        System.out.println("refreshing");
        this.setText(header, R.id.refresh_text, context.getString(R.string.loading));
        this.setVisibility(header, R.id.refresh_text, VISIBLE);
        this.setVisibility(header, R.id.refresh_progressBar, VISIBLE);
        this.setVisibility(header, R.id.refresh_arrow, INVISIBLE);
    }

    @Override
    public void refreshFinish(View header) {

    }
}
