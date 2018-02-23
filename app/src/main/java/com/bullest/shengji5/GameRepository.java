package com.bullest.shengji5;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.bullest.shengji5.data.Card;

import java.util.List;

/**
 * Created by yunfezhang on 11/22/17.
 */

public class GameRepository {
    private static final Object syncLock = new Object();
    private static GameRepository instance;
    private static GameData sGameData;
    private LiveData<List<Card>> mJokerCardList = new MutableLiveData<>();
    private LiveData<List<Card>> mDiamondCardList = new MutableLiveData<>();
    private LiveData<List<Card>> mSpadeCardList = new MutableLiveData<>();
    private LiveData<List<Card>> mClubCardList = new MutableLiveData<>();
    private LiveData<List<Card>> mHeartCardList = new MutableLiveData<>();

    public GameRepository() {
        sGameData = new GameData();
    }

    public static GameRepository getInstance() {
        synchronized (syncLock) {
            if (instance == null) {
                instance = new GameRepository();
            }
        }
        return instance;
    }

    public void resetGameSuit() {
        sGameData.setGameSuit(null);
    }

    public void setBoss() {

    }

    public void resetAllLevel() {
    }

    public void clearAllCards() {
    }

    public void dispatchCards() {

    }

    public void setGameLevel() {
    }

    public void resetBossFriend() {
    }
}
