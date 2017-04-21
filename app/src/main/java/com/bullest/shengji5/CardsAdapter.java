package com.bullest.shengji5;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunfezhang on 4/25/17.
 */

class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {
    private List<Card> mCardList;

    public CardsAdapter(ArrayList<Card> cardList) {
        mCardList = cardList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        CardsAdapter.ViewHolder holder = new CardsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.card_suit_view.setText(mCardList.get(position).getDisplay_suit());
        holder.card_value_view.setText(mCardList.get(position).getDisplay_value());
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView card_value_view;
        TextView card_suit_view;

        public ViewHolder(View itemView) {
            super(itemView);
            card_suit_view = (TextView) itemView.findViewById(R.id.card_suit_view);
            card_value_view = (TextView) itemView.findViewById(R.id.card_value_view);
        }
    }
}
