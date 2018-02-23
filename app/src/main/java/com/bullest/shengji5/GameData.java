package com.bullest.shengji5;

import com.bullest.shengji5.data.CARD_SUIT;
import com.bullest.shengji5.data.Card;

import java.util.List;

/**
 * Created by yunfezhang on 11/22/17.
 */

public class GameData {
    int currentBoss;
    int currentBossFriend;
    List<Card> backCards;
    CARD_SUIT gameSuit;

    public int getCurrentBoss() {
        return currentBoss;
    }

    public void setCurrentBoss(int currentBoss) {
        this.currentBoss = currentBoss;
    }

    public int getCurrentBossFriend() {
        return currentBossFriend;
    }

    public void setCurrentBossFriend(int currentBossFriend) {
        this.currentBossFriend = currentBossFriend;
    }

    public List<Card> getBackCards() {
        return backCards;
    }

    public void setBackCards(List<Card> backCards) {
        this.backCards = backCards;
    }

    public CARD_SUIT getGameSuit() {
        return gameSuit;
    }

    public void setGameSuit(CARD_SUIT gameSuit) {
        this.gameSuit = gameSuit;
    }


}
