package com.bullest.shengji5;

import com.bullest.shengji5.data.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunfezhang on 11/22/17.
 */

public class HandCard {
    private List<Card> mJokerCardList;
    private List<Card> mDiamondCardList;
    private List<Card> mSpadeCardList;
    private List<Card> mHeartCardList;
    private List<Card> mCludCardList;

    public HandCard() {
        mCludCardList = new ArrayList<>();
        mSpadeCardList = new ArrayList<>();
        mCludCardList = new ArrayList<>();
        mJokerCardList = new ArrayList<>();
        mDiamondCardList = new ArrayList<>();
    }


}
