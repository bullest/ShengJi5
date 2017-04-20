package com.bullest.shengji5;

import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;

/**
 * Created by yunfezhang on 4/20/17.
 */

public interface ResourceRetriever {
    String getString(@StringRes int resourceId);
    String getString(@StringRes int resourceId, Object... formatArgs);
    String getQuantityString(@PluralsRes int id, int quantity, Object... formatArgs);
    String getPackageName();
    String[] getStringArray(@ArrayRes int id);
    @ColorInt
    int getColorRes(@ColorRes int error_red);
    Drawable getDrawableRes(@DrawableRes int drawableRes);
}
