package com.bullest.shengji5.handCard;

import android.arch.lifecycle.LiveData;

import com.bullest.shengji5.data.CARD_SUIT;
import com.bullest.shengji5.data.Card;

import java.util.List;

/**
 * Created by yunfezhang on 3/2/18.
 */

public class HandCardViewModel {
    LiveData<List<Card>> jokerCards;
    LiveData<List<Card>> heartCards;
    LiveData<List<Card>> spadeCards;
    LiveData<List<Card>> diamondCards;
    LiveData<List<Card>> clubCards;

    public LiveData<List<Card>> getHandCard(CARD_SUIT suit) {
        return HandCardRepository.getInstance().getCardsWithSuite(suit);
    }

}
