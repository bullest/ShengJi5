package com.bullest.shengji5;

/**
 * Created by yunfezhang on 5/6/17.
 */

class SingleCardComparator implements java.util.Comparator<Card> {
    @Override
    public int compare(Card card1, Card card2) {
        return card1.getValue() < card2.getValue() ? 1 : card1.getValue() == card2.getValue() ? 0 : -1;
    }
}
