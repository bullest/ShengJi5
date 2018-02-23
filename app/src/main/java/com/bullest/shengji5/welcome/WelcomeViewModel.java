package com.bullest.shengji5.welcome;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.bullest.shengji5.data.Player;
import com.bullest.shengji5.PlayerRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yunfezhang on 11/22/17.
 */

public class WelcomeViewModel extends ViewModel {
    private PlayerRepository mPlayerRepository = PlayerRepository.getInstance();
    public LiveData<List<Player>> getPlayer() {
        return PlayerRepository.getInstance().getPlayer();
    }

    public void getCurrentPlayerNumber() {

    }

    public void addCurrentPlayer(Player player) {
        PlayerRepository.getInstance().addPlayer(player);
    }

    public boolean isAllReady() {
        return PlayerRepository.getInstance().isAllReady();
    }

    public int getAvailablePosition() {
        List<Integer> postions = new ArrayList<>(Arrays.asList(1,2,3,4,5));
        if (mPlayerRepository.getPlayer().getValue() != null) {
            for (Player player:mPlayerRepository.getPlayer().getValue()) {
                postions.remove((Integer)player.getPosition());
            }
            if (postions.isEmpty()) {
                return -1;
            } else {
                return postions.get(0);
            }
        } else {
            return 1;
        }

    }
}
