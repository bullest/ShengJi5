package com.bullest.shengji5.welcome;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bullest.shengji5.data.Player;
import com.bullest.shengji5.R;

import java.util.ArrayList;
import java.util.List;
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private List<Player> mPlayerList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerName;
        TextView position;
        CardView mCardView;
        public ViewHolder(View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.player_name_view);
            position = itemView.findViewById(R.id.player_position_view);
            mCardView = itemView.findViewById(R.id.player_list_card);
        }
    }

    public PlayerAdapter(List<Player> playerList){
        if (playerList != null) {
            mPlayerList = playerList;
        } else {
            mPlayerList = new ArrayList<>();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Player player = mPlayerList.get(position);
        if (player != null) {
            holder.position.setText(String.valueOf(player.getPosition()));
            holder.playerName.setText(player.getName());
        }
    }

    @Override
    public int getItemCount() {
        return mPlayerList.size();
    }


}