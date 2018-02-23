package com.bullest.shengji5.message;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bullest.shengji5.R;
import com.bullest.shengji5.data.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunfezhang on 2/14/18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> mMessages;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageView;

        public ViewHolder(View itemView) {
            super(itemView);
            messageView = itemView.findViewById(R.id.message_view);
        }
    }

    public MessageAdapter(List<Message> messages) {
        if (messages != null) {
            mMessages = messages;
        } else {
            mMessages = new ArrayList<>();
        }
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {
        Message message = mMessages.get(position);
        if (message != null) {
            if (message.getFromSystem()){
                holder.messageView.setTextColor(Color.BLUE);
            }
            holder.messageView.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }
}
