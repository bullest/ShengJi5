package com.bullest.shengji5.playerStatus;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.bullest.shengji5.SelfSingleton;
import com.bullest.shengji5.data.Card;
import com.bullest.shengji5.data.PlayerStatus;

import java.util.List;

/**
 * Created by yunfezhang on 2/23/18.
 */

public class PlayerStatusViewModel extends ViewModel {

    public LiveData<List<PlayerStatus>> getOtherPlayerStatus() {
        return PlayerStatusRepository.getInstance().getOtherPlayerStatus();
    }

    public LiveData<PlayerStatus> getSelfPlayerStatus() {
        return PlayerStatusRepository.getInstance().getSelfStatus();
    }

    public void resetAllPlayerStatus() {
        PlayerStatusRepository.getInstance().resetStatus();
    }

    public void setSelfReady() {
        PlayerStatusRepository.getInstance().setSelfReady();
    }
}
