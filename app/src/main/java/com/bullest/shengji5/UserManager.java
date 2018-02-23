package com.bullest.shengji5;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.wilddog.client.DataSnapshot;
import com.wilddog.client.SyncError;
import com.wilddog.client.SyncReference;
import com.wilddog.client.ValueEventListener;
import com.wilddog.client.WilddogSync;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by yunfezhang on 4/19/17.
 */

public class UserManager {
    private static final Object syncLock = new Object();
    private static UserManager singleton;

    public static UserManager getInstance() {
        synchronized (syncLock) {
            if (singleton == null) {
                singleton = new UserManager();
            }
        }
        return singleton;
    }

    private void persistUserValues(String name, int position) {
        final SharedPreferences settings = getSharedPreference(Constants.LOGIN_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(Constants.PLAYER_POSITION, position);
        editor.putString(Constants.PLAYER_NAME, name);
        editor.putBoolean(Constants.IS_LOGGED_IN, true);
        editor.apply();
    }

    private void setPlayerPosition(int i) {
        SharedPreferences settings = getSharedPreference(Constants.LOGIN_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(Constants.PLAYER_POSITION, i);
        editor.apply();
    }

    private SharedPreferences getSharedPreference(String prefName, int mode) {
        return ShengJi5Application.getInstance().getSharedPreferences(prefName, mode);
    }

    public int getPlayerPosition(){
        SharedPreferences settings = getSharedPreference(Constants.LOGIN_DATA, Context.MODE_PRIVATE);
        return settings.getInt(Constants.PLAYER_POSITION, 0);
    }

    public boolean isLoggedIn() {
        SharedPreferences settings = getSharedPreference(Constants.LOGIN_DATA, Context.MODE_PRIVATE);
        return settings.getBoolean(Constants.IS_LOGGED_IN, false);
    }
}
