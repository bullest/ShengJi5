package com.bullest.shengji5;

import android.content.SharedPreferences;

import com.wilddog.client.SyncReference;
import com.wilddog.client.WilddogSync;

/**
 * Created by yunfezhang on 4/23/17.
 */

public class MatchManager {
    private static final Object syncLock = new Object();
    private static MatchManager singleton;

    public static MatchManager getInstance() {
        synchronized (syncLock) {
            if (singleton == null) {
                singleton = new MatchManager();
            }
        }
        return singleton;
    }

    private SharedPreferences getSharedPreference(String prefName, int mode) {
        return ShengJi5Application.getInstance().getSharedPreferences(prefName, mode);
    }

    public void initMatch() {
        SyncReference ref = WilddogSync.getInstance().getReference().child("match").child("players");
        for (int i = 1; i <= 5; i++) {
            ref.child("player"+i).child("level").setValue(3);
        }
        WilddogSync.getInstance().getReference().child("game").child("boss").setValue(0);
        GameManager.getInstance().setCurrentBoss(0);
    }
}
