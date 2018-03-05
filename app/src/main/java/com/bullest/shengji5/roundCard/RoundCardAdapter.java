package com.bullest.shengji5.roundCard;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bullest.shengji5.R;
import com.bullest.shengji5.data.Card;
import com.bullest.shengji5.view.PokerCardView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yunfezhang on 3/2/18.
 */

public class RoundCardAdapter extends RecyclerView.Adapter<RoundCardAdapter.ViewHolder>{
    private List<Card> cards;

    public RoundCardAdapter(List<Card> cards) {
        Collections.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card card, Card t1) {
                return card.getValue() < t1.getValue() ? -1 : card.getValue() == t1.getValue() ? 0 : 1;
            }
        });
        this.cards = cards;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Card card = cards.get(position);
        if (card != null) {
            holder.mCardView.setCard(card);
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private PokerCardView mCardView;
        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.card_view);
        }
    }
}
