package com.bullest.shengji5.playerStatus;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.bullest.shengji5.PlayerRepository;
import com.bullest.shengji5.SelfSingleton;
import com.bullest.shengji5.data.PlayerStatus;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunfezhang on 3/1/18.
 */

public class PlayerStatusRepository {
    private static final Object syncLock = new Object();
    private static PlayerStatusRepository instant;
    private int selfPosition;

    CollectionReference playerStatusReference= FirebaseFirestore.getInstance().collection("PlayerStatus");

    final MutableLiveData<List<PlayerStatus>> otherPlayerStatus = new MutableLiveData<>();
    final MutableLiveData<PlayerStatus> selfStatus = new MutableLiveData<>();
    private List<PlayerStatus> allPlayerStatus;

    public PlayerStatusRepository() {
        this.selfPosition = SelfSingleton.getInstance().getSelfPostion();

        playerStatusReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (documentSnapshots != null) {
                    allPlayerStatus = documentSnapshots.toObjects(PlayerStatus.class);
                    allocateStatus();
                }
            }
        });
    }

    private void allocateStatus() {
        List<PlayerStatus> statuses = new ArrayList<>();

        if (allPlayerStatus != null) {
            for (PlayerStatus status:allPlayerStatus
                 ) {
                if (status.getPosition() != selfPosition) {
                    statuses.add(status);
                } else {
                    selfStatus.setValue(status);
                }
            }
            otherPlayerStatus.setValue(statuses);
        }
    }

    public static PlayerStatusRepository getInstance() {
        synchronized (syncLock) {
            if (instant == null) {
                instant = new PlayerStatusRepository();
            }
        }
        return instant;
    }

    public LiveData<List<PlayerStatus>> getOtherPlayerStatus() {
        return otherPlayerStatus;
    }

    public LiveData<PlayerStatus> getSelfStatus() {
        return selfStatus;
    }

    public void resetStatus() {
        PlayerStatus playerStatus1 = new PlayerStatus(1,
                PlayerRepository.getInstance().getPlayer().getValue().get(0).getName(),
                3, null, null, 0);
        PlayerStatus playerStatus2 = new PlayerStatus(2,
                PlayerRepository.getInstance().getPlayer().getValue().get(1).getName(),
                3, null, null, 0);
        PlayerStatus playerStatus3 = new PlayerStatus(3,
                PlayerRepository.getInstance().getPlayer().getValue().get(2).getName(),
                3, null, null, 0);
        PlayerStatus playerStatus4 = new PlayerStatus(4,
                PlayerRepository.getInstance().getPlayer().getValue().get(3).getName(),
                3, null, null, 0);
        PlayerStatus playerStatus5 = new PlayerStatus(5,
                PlayerRepository.getInstance().getPlayer().getValue().get(4).getName(),
                3, null, null, 0);

        playerStatusReference.document("PlayerStatus1").set(playerStatus1);
        playerStatusReference.document("PlayerStatus2").set(playerStatus2);
        playerStatusReference.document("PlayerStatus3").set(playerStatus3);
        playerStatusReference.document("PlayerStatus4").set(playerStatus4);
        playerStatusReference.document("PlayerStatus5").set(playerStatus5);
    }
}
