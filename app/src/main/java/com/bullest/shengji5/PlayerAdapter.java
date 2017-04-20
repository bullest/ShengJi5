package com.bullest.shengji5;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yunfezhang on 4/19/17.
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private List<Player> mPlayerList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerName;
        TextView position;
        CardView mCardView;
        public ViewHolder(View itemView) {
            super(itemView);
            playerName = (TextView) itemView.findViewById(R.id.player_name_view);
            position = (TextView) itemView.findViewById(R.id.player_position_view);
            mCardView = (CardView) itemView.findViewById(R.id.player_list_card);
        }
    }

    public PlayerAdapter(List<Player> playerList){
        mPlayerList = playerList;
    }

    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PlayerAdapter.ViewHolder holder, int position) {
        Player player = mPlayerList.get(position);
        holder.position.setText(String.valueOf(player.getPosition()));
        holder.playerName.setText(player.getName());
        if (player.isReady()){
            holder.mCardView.setCardBackgroundColor(Color.GREEN);
        } else {
            holder.mCardView.setCardBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {
        return mPlayerList.size();
    }


}
