package com.bullest.shengji5.roundCard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.bullest.shengji5.data.Card;

import java.util.List;

/**
 * Created by yunfezhang on 3/2/18.
 */

public class RoundCardViewModel extends ViewModel {
    public LiveData<List<Card>> getRoundCardAt(int position) {
        return RoundCardRepository.getInstance().getRoundCardAt(position);
    }
}
