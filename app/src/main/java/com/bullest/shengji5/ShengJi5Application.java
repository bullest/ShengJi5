package com.bullest.shengji5;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.PluralsRes;
import android.support.v4.content.ContextCompat;

import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;

/**
 * Created by yunfezhang on 4/19/17.
 */

public class ShengJi5Application extends Application implements ResourceRetriever{

    protected static ShengJi5Application instance = null;

    public static ShengJi5Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        WilddogOptions wilddogOptions = new WilddogOptions.Builder().setSyncUrl("https://shengji5.wilddogio.com ").build();
        WilddogApp wilddogApp = WilddogApp.initializeApp(this,wilddogOptions);

    }

    @Override
    public String getQuantityString(@PluralsRes int id, int quantity, Object... formatArgs) {
        return getResources().getQuantityString(id, quantity, formatArgs);
    }

    @Override
    public String[] getStringArray(@ArrayRes int id) {
        return getResources().getStringArray(id);
    }

    @Override
    public int getColorRes(@ColorRes int id) {
        return ContextCompat.getColor(this, id);
    }

    @Override
    public Drawable getDrawableRes(@DrawableRes int drawableRes) {
        return ContextCompat.getDrawable(this, drawableRes);
    }
}
