package com.bullest.shengji5.message;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.bullest.shengji5.data.Message;

import java.util.List;
import java.util.Date;


/**
 * Created by yunfezhang on 2/12/18.
 */

public class MessageViewModel extends ViewModel {
    private MessageRepository mMessageRepository = MessageRepository.getInstance();

    public void addMessage(String message, boolean isFromSystem) {
        mMessageRepository.addMessage(new Message(message, isFromSystem, new Date()));
    }

    public LiveData<List<Message>> getMessage() {
        return MessageRepository.getInstance().getMessage();
    }

    public void clearMessage() {
        // TODO
    }

}
