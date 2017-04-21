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
    private int position;
    private boolean isReady;

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public static UserManager getInstance() {
        synchronized (syncLock) {
            if (singleton == null) {
                singleton = new UserManager();
            }
        }
        return singleton;
    }

    public void loginUser(final String name) {
        final SyncReference ref = WilddogSync.getInstance().getReference().child("match").child("players");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int i=1;i<=5;i++){
                    if(dataSnapshot.getValue()!=null){
                        if ((dataSnapshot.child("player"+i).child("name").getValue()).equals("player")){
                            ref.child("player"+i).child("name").setValue(name);
                            position = i;
                            persistUserValues(name, position);
                            EventBus.getDefault().post(new LoginSuccessEvent());
                            break;
                        }
                    }
                    if (i == 5) {
                        EventBus.getDefault().post(new LoginFailEvent());
                    }
                }
            }
            @Override
            public void onCancelled(SyncError syncError) {
                if(syncError!=null){
                    position = 0;
                    EventBus.getDefault().post(new LoginFailEvent());
                    Log.d("onCancelled",syncError.toString());}
            }
        });
    }

    public void logoutUser() {
        final SharedPreferences settings = getSharedPreference(Constants.LOGIN_DATA, Context.MODE_PRIVATE);
        final SyncReference ref = WilddogSync.getInstance().getReference().child("match").child("players");
        ref.child("player"+this.getPlayerPosition()).child("name").setValue("player");
        ref.child("player"+this.getPlayerPosition()).child("isready").setValue(0);

        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN, false);
        editor.remove(Constants.PLAYER_NAME);
        editor.remove(Constants.PLAYER_POSITION);
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
