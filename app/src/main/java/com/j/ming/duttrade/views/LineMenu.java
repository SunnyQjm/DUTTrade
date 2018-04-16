package com.j.ming.duttrade.views;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sunny on 2017/10/6 0006.
 */

public interface LineMenu {
    ImageView getLeft_icon();

    ImageView getRight_icon();

    TextView getTv_title();

    TextView getTv_value();

    void setLeftIcon(@DrawableRes int res);

    void setRightIcon(@DrawableRes int res);


    void setRightIconVisible(int visible);

    void setLeftIconVisible(int visible);

    void setTitleIconVisible(int visible);

    void setValueIconVisible(int visible);

    void setTitle(String title);

    void setValue(String value);
}
