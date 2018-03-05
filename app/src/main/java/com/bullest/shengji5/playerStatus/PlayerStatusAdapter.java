package com.bullest.shengji5.playerStatus;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bullest.shengji5.R;
import com.bullest.shengji5.data.PlayerStatus;
import com.bullest.shengji5.view.PokerCardView;

import java.util.List;

/**
 * Created by yunfezhang on 2/23/18.
 */

public class PlayerStatusAdapter extends RecyclerView.Adapter<PlayerStatusAdapter.ViewHolder> {
    private List<PlayerStatus> mPlayerStatuses;

    public PlayerStatusAdapter(List<PlayerStatus> playerStatus) {
        mPlayerStatuses = playerStatus;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_status_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PlayerStatus playerStatus = mPlayerStatuses.get(position);
        if (playerStatus != null) {
            holder.playerPositionView.setText(""+playerStatus.getPosition());
            holder.playerNameView.setText(playerStatus.getName());
            holder.playerGradeView.setText(""+playerStatus.getGrade());
            holder.friendCardView.setCard(playerStatus.getFriendCard());

            if (playerStatus.getShowCards()==null || playerStatus.getShowCards().isEmpty()) {
                holder.showCardView1.setVisibility(View.GONE);
                holder.showCardView2.setVisibility(View.GONE);
                holder.showCardView3.setVisibility(View.GONE);
            } else {
                switch (playerStatus.getShowCards().size()) {
                    case 1:
                        holder.showCardView1.setVisibility(View.GONE);
                        holder.showCardView2.setVisibility(View.GONE);
                        holder.showCardView3.setVisibility(View.VISIBLE);
                        holder.showCardView3.setCard(playerStatus.getShowCards().get(0));
                        break;
                    case 2:
                        holder.showCardView1.setVisibility(View.GONE);
                        holder.showCardView2.setVisibility(View.VISIBLE);
                        holder.showCardView3.setCard(playerStatus.getShowCards().get(1));
                        holder.showCardView3.setVisibility(View.VISIBLE);
                        holder.showCardView3.setCard(playerStatus.getShowCards().get(0));
                        break;
                    case 3:
                        holder.showCardView1.setVisibility(View.VISIBLE);
                        holder.showCardView3.setCard(playerStatus.getShowCards().get(2));
                        holder.showCardView2.setVisibility(View.VISIBLE);
                        holder.showCardView3.setCard(playerStatus.getShowCards().get(1));
                        holder.showCardView3.setVisibility(View.VISIBLE);
                        holder.showCardView3.setCard(playerStatus.getShowCards().get(0));
                        break;
                }
            }

            holder.playerPointView.setText(playerStatus.getPoint()+"Pt");
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerPositionView;
        TextView playerNameView;
        TextView playerGradeView;
        PokerCardView friendCardView;
        PokerCardView showCardView1, showCardView2, showCardView3;
        TextView playerPointView;

        public ViewHolder(View itemView) {
            super(itemView);
            playerPositionView = itemView.findViewById(R.id.player_position_view);
            playerNameView = itemView.findViewById(R.id.player_name_view);
            playerGradeView = itemView.findViewById(R.id.player_grade_view);
            friendCardView = itemView.findViewById(R.id.game_friend_card);
            showCardView1 = itemView.findViewById(R.id.game_suit_display_card_1);
            showCardView2 = itemView.findViewById(R.id.game_suit_display_card_2);
            showCardView3 = itemView.findViewById(R.id.game_suit_display_card_3);
            playerPointView = itemView.findViewById(R.id.player_point);
        }
    }
}
