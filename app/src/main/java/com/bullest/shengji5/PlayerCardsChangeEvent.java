package com.bullest.shengji5;

import com.bullest.shengji5.data.CARD_SUIT;

/**
 * Created by yunfezhang on 4/28/17.
 */

class PlayerCardsChangeEvent {
    private CARD_SUIT suit;

    public PlayerCardsChangeEvent(CARD_SUIT suit) {
        this.suit = suit;
    }

    public CARD_SUIT getSuit() {
        return this.suit;
    }
}
