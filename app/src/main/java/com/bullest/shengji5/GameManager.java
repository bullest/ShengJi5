package com.bullest.shengji5;

import android.content.SharedPreferences;
import android.util.Log;

import com.bullest.shengji5.data.CARD_SUIT;
import com.bullest.shengji5.data.Card;
import com.wilddog.client.DataSnapshot;
import com.wilddog.client.SyncError;
import com.wilddog.client.SyncReference;
import com.wilddog.client.ValueEventListener;
import com.wilddog.client.WilddogSync;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by yunfezhang on 4/23/17.
 */

public class GameManager {
    private static final Object syncLock = new Object();
    private static GameManager singleton;
    private ArrayList<Card> player_cards_list_master = new ArrayList<>();
    private ArrayList<Card> player_cards_list_spade = new ArrayList<>();
    private ArrayList<Card> player_cards_list_club = new ArrayList<>();
    private ArrayList<Card> player_cards_list_diamond = new ArrayList<>();
    private ArrayList<Card> player_cards_list_heart = new ArrayList<>();
    private ArrayList<Card> player_cards_list = new ArrayList<>();


    private int currentBoss;

    public ArrayList<Card> getPlayer_cards_list_master() {
        return player_cards_list_master;
    }

    public ArrayList<Card> getPlayer_cards_list_spade() {
        return player_cards_list_spade;
    }

    public ArrayList<Card> getPlayer_cards_list_heart() {
        return player_cards_list_heart;
    }

    public ArrayList<Card> getPlayer_cards_list_club() {
        return player_cards_list_club;
    }

    public ArrayList<Card> getPlayer_cards_list_diamond() {
        return player_cards_list_diamond;
    }

    public static GameManager getInstance() {
        synchronized (syncLock) {
            if (singleton == null) {
                singleton = new GameManager();
            }
        }
        return singleton;
    }

    public void register_card_list() {
        final SyncReference ref_all = WilddogSync.getInstance().getReference().child("game_cards")
                .child("game_card_"+UserManager.getInstance().getPlayerPosition()).child("all_cards");

        ref_all.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                player_cards_list.clear();
                Card card = null;
                for (int i = 0; snapshot.child(Integer.toString(i)).exists(); i++) {
                    HashMap map = (HashMap) snapshot.child(Integer.toString(i)).getValue();
                    card = new Card((String) map.get("suit"), (long) map.get("value"));

                    if (card.getValue() == 2 || card.getValue() == 3) {
                        player_cards_list_master.add(card);
                        EventBus.getDefault().post(new PlayerCardsChangeEvent(CARD_SUIT.JOKER));
                        Log.d("diapatching", "master");
                    } else {
                        Log.d("diapatching", "non");
                        switch (card.getSuit()){
                            case JOKER:
                                player_cards_list_master.add(card);
                                EventBus.getDefault().post(new PlayerCardsChangeEvent(CARD_SUIT.JOKER));
                                break;
                            case SPADE:
                                player_cards_list_spade.add(card);
                                EventBus.getDefault().post(new PlayerCardsChangeEvent(CARD_SUIT.SPADE));
                                break;
                            case HEART:
                                player_cards_list_heart.add(card);
                                EventBus.getDefault().post(new PlayerCardsChangeEvent(CARD_SUIT.HEART));
                                break;
                            case DIAMOND:
                                player_cards_list_diamond.add(card);
                                EventBus.getDefault().post(new PlayerCardsChangeEvent(CARD_SUIT.DIAMOND));
                                break;
                            case CLUB:
                                player_cards_list_club.add(card);
                                EventBus.getDefault().post(new PlayerCardsChangeEvent(CARD_SUIT.CLUB));
                                break;
                        }
                    }

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Collections.sort(player_cards_list_master, new SingleCardComparator());
                EventBus.getDefault().post(new PlayerCardsChangeEvent(CARD_SUIT.JOKER));
                Collections.sort(player_cards_list_spade, new SingleCardComparator());
                EventBus.getDefault().post(new PlayerCardsChangeEvent(CARD_SUIT.SPADE));
                Collections.sort(player_cards_list_heart, new SingleCardComparator());
                EventBus.getDefault().post(new PlayerCardsChangeEvent(CARD_SUIT.HEART));
                Collections.sort(player_cards_list_club, new SingleCardComparator());
                EventBus.getDefault().post(new PlayerCardsChangeEvent(CARD_SUIT.CLUB));
                Collections.sort(player_cards_list_diamond, new SingleCardComparator());
                EventBus.getDefault().post(new PlayerCardsChangeEvent(CARD_SUIT.DIAMOND));

            }

            @Override
            public void onCancelled(SyncError error) {
                dispatchCards();
            }
        });
    }

    private SharedPreferences getSharedPreference(String prefName, int mode) {
        return ShengJi5Application.getInstance().getSharedPreferences(prefName, mode);
    }

    public void initGame() {
        WilddogSync.getInstance().getReference().child("game").child("boss_companion").setValue(0);
        WilddogSync.getInstance().getReference().child("game").child("game_suit").setValue(0);
        WilddogSync.getInstance().getReference().child("game").child("boss_card").setValue(new Card(CARD_SUIT.JOKER, 0));
        for (int i = 1; i <= 5; i++) {
            WilddogSync.getInstance().getReference().child("game").child("game_point").child("player_point_"+i).setValue(0);
        }
        register_card_list();

    }


    public void dispatchCards() {
        ArrayList<Card> allDeck;
        ArrayList<Card> game_card_1 = new ArrayList<>();
        ArrayList<Card> game_card_2 = new ArrayList<>();
        ArrayList<Card> game_card_3 = new ArrayList<>();
        ArrayList<Card> game_card_4 = new ArrayList<>();
        ArrayList<Card> game_card_5 = new ArrayList<>();

        allDeck = generateAllDeck();

        dispatchSecretCard(7, allDeck);

        for (int i = 0; i < 31; i++) {
            game_card_1.add(pickCardFrom(allDeck));
            game_card_2.add(pickCardFrom(allDeck));
            game_card_3.add(pickCardFrom(allDeck));
            game_card_4.add(pickCardFrom(allDeck));
            game_card_5.add(pickCardFrom(allDeck));
        }

        WilddogSync.getInstance().getReference().child("game_cards").child("game_card_1").child("all_cards")
                .setValue(game_card_1);
        WilddogSync.getInstance().getReference().child("game_cards").child("game_card_2").child("all_cards")
                .setValue(game_card_2);
        WilddogSync.getInstance().getReference().child("game_cards").child("game_card_3").child("all_cards")
                .setValue(game_card_3);
        WilddogSync.getInstance().getReference().child("game_cards").child("game_card_4").child("all_cards")
                .setValue(game_card_4);
        WilddogSync.getInstance().getReference().child("game_cards").child("game_card_5").child("all_cards")
                .setValue(game_card_5);

    }

    private Card pickCardFrom(ArrayList<Card> allDeck) {
        Random rand = new Random();
        Card selectedCard;
        int randomInt;
        randomInt = rand.nextInt(allDeck.size());

        selectedCard = allDeck.get(randomInt);
        allDeck.remove(randomInt);

        return selectedCard;
    }

    private void dispatchSecretCard(int cardNumber, ArrayList<Card> allDeck) {
        ArrayList<Card> game_card_0 = new ArrayList<>();

        for (int i = 0; i < cardNumber; i++){
            Random rand = new Random();
            int randomInt = rand.nextInt(allDeck.size());
            game_card_0.add(allDeck.get(randomInt));
            allDeck.remove(randomInt);
        }
        WilddogSync.getInstance().getReference().child("game").child("game_cards").child("game_card_0").setValue(game_card_0);

    }

    private ArrayList<Card> generateAllDeck() {
        ArrayList<Card> allCards = new ArrayList<>();

        for (int j = 0; j <= 2; j++) {
            for (int i = 2; i <= 14; i++) {
                allCards.add(new Card(CARD_SUIT.CLUB, i));
                allCards.add(new Card(CARD_SUIT.DIAMOND, i));
                allCards.add(new Card(CARD_SUIT.HEART, i));
                allCards.add(new Card(CARD_SUIT.SPADE, i));
            }
            allCards.add(new Card(CARD_SUIT.JOKER, Constants.BIG_JOKER_VALUE));
            allCards.add(new Card(CARD_SUIT.JOKER, Constants.LITTLE_JOKER_VALUE));
        }

        return allCards;
    }

    public int getCurrentBoss() {
        return currentBoss;
    }

    public void setCurrentBoss(int currentBoss) {
        this.currentBoss = currentBoss;

    }

}
