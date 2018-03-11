package com.bullest.shengji5;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bullest.shengji5.data.Card;
import com.bullest.shengji5.view.PokerCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yunfezhang on 3/6/18.
 */

public class SelectableCardsAdapter extends RecyclerView.Adapter<SelectableCardsAdapter.ViewHolder> {
    private List<Card> mCardList;
    private SparseBooleanArray selectedItems;

    public SelectableCardsAdapter(List<Card> cardList) {
        Collections.sort(cardList, new Comparator<Card>() {
            @Override
            public int compare(Card card, Card t1) {
                return card.getValue() > t1.getValue() ? -1 : card.getValue() == t1.getValue() ? 0 : 1;
            }
        });
        mCardList = cardList;
        selectedItems = new SparseBooleanArray();
    }

    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    public void clearSelection() {
        List<Integer> selection = getSelectedItems();
        selectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    @Override
    public SelectableCardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectableCardsAdapter.ViewHolder holder, int position) {
        holder.card.setCard(mCardList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        PokerCardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.poker_item);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    card.toggle();
                    card.updateUI();
                }
            });
        }
    }
}
