package com.bullest.shengji5;

import android.arch.lifecycle.ViewModel;

/**
 * Created by yunfezhang on 11/22/17.
 */

public class GameViewModel extends ViewModel {
    GameRepository mGameRepository = GameRepository.getInstance();

    public void initMatch() {
        mGameRepository.resetAllLevel();
    }

    public void initGame() {
        mGameRepository.clearAllCards();
        mGameRepository.dispatchCards();
        mGameRepository.setBoss();
        mGameRepository.resetBossFriend();
        mGameRepository.resetGameSuit();
        mGameRepository.setGameLevel();
    }

    public void dispatchCard() {
        mGameRepository.dispatchCards();
    }
}
