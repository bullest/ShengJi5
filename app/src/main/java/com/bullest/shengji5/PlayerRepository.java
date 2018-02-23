package com.bullest.shengji5;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.bullest.shengji5.data.Player;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * Created by yunfezhang on 11/22/17.
 */

public class PlayerRepository {
    private static final Object syncLock = new Object();
    private static PlayerRepository instant;
    CollectionReference playerReference = FirebaseFirestore.getInstance().collection("Players");
    private Player currentPlayer;
    final MutableLiveData<List<Player>> player = new MutableLiveData<>();

    public PlayerRepository() {
        playerReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (documentSnapshots != null) {
                    player.setValue(documentSnapshots.toObjects(Player.class));
                }
            }
        });
    }

    public static PlayerRepository getInstance() {
        synchronized (syncLock) {
            if (instant == null) {
                instant = new PlayerRepository();
            }
        }
        return instant;
    }

    public LiveData<List<Player>> getPlayer() {
        return player;
    }

    public void addPlayer(Player player) {
        currentPlayer = player;
        playerReference.add(currentPlayer);
    }

    public boolean isAllReady() {
        return player.getValue().size() == 5;
    }
}
