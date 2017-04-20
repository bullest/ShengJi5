package com.bullest.shengji5;

import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;

/**
 * Created by yunfezhang on 4/20/17.
 */

class AppInstance implements ResourceRetriever{
    protected static AppInstance instance = null;

    public static AppInstance getInstance() {
        return instance;
    }


    @Override
    public String getString(@StringRes int resourceId) {
        return null;
    }

    @Override
    public String getString(@StringRes int resourceId, Object... formatArgs) {
        return null;
    }

    @Override
    public String getQuantityString(@PluralsRes int id, int quantity, Object... formatArgs) {
        return null;
    }

    @Override
    public String getPackageName() {
        return null;
    }

    @Override
    public String[] getStringArray(@ArrayRes int id) {
        return new String[0];
    }

    @Override
    public int getColorRes(@ColorRes int error_red) {
        return 0;
    }

    @Override
    public Drawable getDrawableRes(@DrawableRes int drawableRes) {
        return null;
    }
}
