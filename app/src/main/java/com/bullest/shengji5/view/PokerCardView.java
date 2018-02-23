package com.bullest.shengji5.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bullest.shengji5.R;
import com.bullest.shengji5.data.CARD_SUIT;
import com.bullest.shengji5.data.Card;

/**
 * Created by yunfezhang on 2/11/18.
 */

public class PokerCardView extends CardView implements Checkable{

    View rootView;
    TextView cardValueView, cardSuitView;

    public PokerCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.card_view, this);
        cardSuitView = rootView.findViewById(R.id.card_suit_view);
        cardValueView = rootView.findViewById(R.id.card_value_view);
    }

    public void setCard(Card card) {
        CARD_SUIT cardSuit = card.getSuit();
        cardSuitView.setText(card.getDisplay_suit());
        cardValueView.setText(card.getDisplay_value());
        if (cardSuit == CARD_SUIT.HEART ||
                cardSuit == CARD_SUIT.DIAMOND ||
                card.aBigJoker()) {
            cardSuitView.setTextColor(Color.RED);
        }
        invalidate();
        requestLayout();
    }

    @Override
    public void setChecked(boolean b) {

    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void toggle() {

    }
}
