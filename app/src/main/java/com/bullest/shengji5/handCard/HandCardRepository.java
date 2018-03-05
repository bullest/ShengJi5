package com.bullest.shengji5.handCard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.bullest.shengji5.data.CARD_SUIT;
import com.bullest.shengji5.data.Card;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * Created by yunfezhang on 3/2/18.
 */

public class HandCardRepository {
    private static HandCardRepository instance;

    MutableLiveData<List<Card>> jokerCards = new MutableLiveData<>();
    MutableLiveData<List<Card>> heartCards = new MutableLiveData<>();
    MutableLiveData<List<Card>> spadeCards = new MutableLiveData<>();
    MutableLiveData<List<Card>> diamondCards = new MutableLiveData<>();
    MutableLiveData<List<Card>> clubCards = new MutableLiveData<>();

    CollectionReference playerHandJokerReference;
    CollectionReference playerHandHeartReference;
    CollectionReference playerHandDiamondReference;
    CollectionReference playerHandClubReference;
    CollectionReference playerHandSpadeReference;

    public HandCardRepository() {
        playerHandJokerReference = FirebaseFirestore.getInstance().collection("HandCard").document("1").collection("Joker");
        playerHandJokerReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                jokerCards.setValue(documentSnapshots.toObjects(Card.class));
            }
        });

        playerHandHeartReference = FirebaseFirestore.getInstance().collection("HandCard").document("1").collection("Heart");
        playerHandHeartReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                heartCards.setValue(documentSnapshots.toObjects(Card.class));
            }
        });

        playerHandClubReference = FirebaseFirestore.getInstance().collection("HandCard").document("1").collection("Club");
        playerHandClubReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                clubCards.setValue(documentSnapshots.toObjects(Card.class));
            }
        });

        playerHandDiamondReference = FirebaseFirestore.getInstance().collection("HandCard").document("1").collection("Diamond");
        playerHandDiamondReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                diamondCards.setValue(documentSnapshots.toObjects(Card.class));
            }
        });

        playerHandSpadeReference = FirebaseFirestore.getInstance().collection("HandCard").document("1").collection("Spade");
        playerHandSpadeReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                spadeCards.setValue(documentSnapshots.toObjects(Card.class));
            }
        });
    }

    public static HandCardRepository getInstance() {
        if (instance == null) {
            instance = new HandCardRepository();
        }
        return instance;
    }

    public LiveData<List<Card>> getCardsWithSuite(CARD_SUIT suit) {
        switch (suit) {
            case JOKER:
                return jokerCards;
            case CLUB:
                return clubCards;
            case HEART:
                return heartCards;
            case SPADE:
                return spadeCards;
            case DIAMOND:
                return diamondCards;
            default:
                return null;
        }
    }
}
