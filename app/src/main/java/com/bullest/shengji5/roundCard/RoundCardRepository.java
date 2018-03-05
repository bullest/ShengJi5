package com.bullest.shengji5.roundCard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.bullest.shengji5.data.Card;

import java.util.List;

/**
 * Created by yunfezhang on 3/2/18.
 */

public class RoundCardRepository {
    private static RoundCardRepository instance;
    private MutableLiveData<List<Card>> roundCards = new MutableLiveData<>();

    public static RoundCardRepository getInstance() {
        if (instance == null) {
            instance = new RoundCardRepository();
        }
        return instance;
    }

    public LiveData<List<Card>> getRoundCardAt(int position) {
        return roundCards;
    }
}
