package com.bullest.shengji5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.wilddog.client.DataSnapshot;
import com.wilddog.client.SyncError;
import com.wilddog.client.SyncReference;
import com.wilddog.client.ValueEventListener;
import com.wilddog.client.WilddogSync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void register_cards_list() {
        final SyncReference ref_master = WilddogSync.getInstance().getReference().child("game").child("game_cards")
                .child("game_card_"+UserManager.getInstance().getPlayerPosition()).child("master_cards");
        ref_master.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (int i = 0; snapshot.child(Integer.toString(i)).exists(); i++) {
                    HashMap map = (HashMap) snapshot.child(Integer.toString(i)).getValue();
                    Card card = new Card((String) map.get("suit"), (long) map.get("value"));
                    player_cards_list_master.add(card);

                }
            }

            @Override
            public void onCancelled(SyncError error) {

            }
        });
        final SyncReference ref_spade = WilddogSync.getInstance().getReference().child("game").child("game_cards")
                .child("game_card_"+UserManager.getInstance().getPlayerPosition()).child("spade_cards");
        ref_spade.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (int i = 0; snapshot.child(Integer.toString(i)).exists(); i++) {
                    HashMap map = (HashMap) snapshot.child(Integer.toString(i)).getValue();
                    Card card = new Card((String) map.get("suit"), (long) map.get("value"));
                    player_cards_list_spade.add(card);
                }
            }

            @Override
            public void onCancelled(SyncError error) {

            }
        });
        final SyncReference ref_heart = WilddogSync.getInstance().getReference().child("game").child("game_cards")
                .child("game_card_"+UserManager.getInstance().getPlayerPosition()).child("heart_cards");
        ref_heart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (int i = 0; snapshot.child(Integer.toString(i)).exists(); i++) {
                    HashMap map = (HashMap) snapshot.child(Integer.toString(i)).getValue();
                    Card card = new Card((String) map.get("suit"), (long) map.get("value"));
                    player_cards_list_heart.add(card);
                }
            }

            @Override
            public void onCancelled(SyncError error) {

            }
        });
        final SyncReference ref_club = WilddogSync.getInstance().getReference().child("game").child("game_cards")
                .child("game_card_"+UserManager.getInstance().getPlayerPosition()).child("club_cards");
        ref_club.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (int i = 0; snapshot.child(Integer.toString(i)).exists(); i++) {
                    HashMap map = (HashMap) snapshot.child(Integer.toString(i)).getValue();
                    Card card = new Card((String) map.get("suit"), (long) map.get("value"));
                    player_cards_list_club.add(card);
                }
            }

            @Override
            public void onCancelled(SyncError error) {

            }
        });
        final SyncReference ref_diamond = WilddogSync.getInstance().getReference().child("game").child("game_cards")
                .child("game_card_"+UserManager.getInstance().getPlayerPosition()).child("diamond_cards");
        ref_diamond.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (int i = 0; snapshot.child(Integer.toString(i)).exists(); i++) {
                    HashMap map = (HashMap) snapshot.child(Integer.toString(i)).getValue();
                    Card card = new Card((String) map.get("suit"), (long) map.get("value"));
                    player_cards_list_diamond.add(card);
                }
            }

            @Override
            public void onCancelled(SyncError error) {

            }
        });
    }

    private SharedPreferences getSharedPreference(String prefName, int mode) {
        return ShengJi5Application.getInstance().getSharedPreferences(prefName, mode);
    }

    public void initGame() {
        WilddogSync.getInstance().getReference().child("game").child("boss").setValue(currentBoss);
        WilddogSync.getInstance().getReference().child("game").child("boss_companion").setValue(0);
        WilddogSync.getInstance().getReference().child("game").child("game_suit").setValue(0);
        WilddogSync.getInstance().getReference().child("game").child("game_suit").setValue(0);
        for (int i = 1; i <= 5; i++) {
            WilddogSync.getInstance().getReference().child("game").child("game_point").child("player_point_"+i).setValue(0);
        }
        clearAllCards();
        register_cards_list();
        dispatchCards();

    }

    private void clearAllCards() {
        WilddogSync.getInstance().getReference().child("game").child("game_cards").child("game_card_5").child("club_cards").setValue(player_cards_list_club);
        WilddogSync.getInstance().getReference().child("game").child("game_cards").child("game_card_5").child("spade_cards").setValue(player_cards_list_spade);
//        WilddogSync.getInstance().getReference().child("game").child("game_cards").child("game_card_1").child("club_cards").setValue(player_cards_list_club);
//        WilddogSync.getInstance().getReference().child("game").child("game_cards").child("game_card_1").child("club_cards").setValue(player_cards_list_club);
//        WilddogSync.getInstance().getReference().child("game").child("game_cards").child("game_card_1").child("club_cards").setValue(player_cards_list_club);

    }

    private void dispatchCards() {
        ArrayList<Card> allDeck = new ArrayList<>();
        ArrayList<Card> game_card_0 = new ArrayList<>();

        ArrayList<Card> game_card_1_master = new ArrayList<>();
        ArrayList<Card> game_card_1_spade = new ArrayList<>();
        ArrayList<Card> game_card_1_heart = new ArrayList<>();
        ArrayList<Card> game_card_1_club = new ArrayList<>();
        ArrayList<Card> game_card_1_diamond = new ArrayList<>();

        ArrayList<Card> game_card_2_master = new ArrayList<>();
        ArrayList<Card> game_card_2_spade = new ArrayList<>();
        ArrayList<Card> game_card_2_heart = new ArrayList<>();
        ArrayList<Card> game_card_2_club = new ArrayList<>();
        ArrayList<Card> game_card_2_diamond = new ArrayList<>();

        ArrayList<Card> game_card_3_master = new ArrayList<>();
        ArrayList<Card> game_card_3_spade = new ArrayList<>();
        ArrayList<Card> game_card_3_heart = new ArrayList<>();
        ArrayList<Card> game_card_3_club = new ArrayList<>();
        ArrayList<Card> game_card_3_diamond = new ArrayList<>();

        ArrayList<Card> game_card_4_master = new ArrayList<>();
        ArrayList<Card> game_card_4_spade = new ArrayList<>();
        ArrayList<Card> game_card_4_heart = new ArrayList<>();
        ArrayList<Card> game_card_4_club = new ArrayList<>();
        ArrayList<Card> game_card_4_diamond = new ArrayList<>();

        ArrayList<Card> game_card_5_master = new ArrayList<>();
        ArrayList<Card> game_card_5_spade = new ArrayList<>();
        ArrayList<Card> game_card_5_heart = new ArrayList<>();
        ArrayList<Card> game_card_5_club = new ArrayList<>();
        ArrayList<Card> game_card_5_diamond = new ArrayList<>();

        for (int j = 0; j <= 2; j++) {
            for (int i = 2; i <= 14; i++) {
                allDeck.add(new Card(CARD_SUIT.CLUB, i));
                allDeck.add(new Card(CARD_SUIT.DIAMOND, i));
                allDeck.add(new Card(CARD_SUIT.HEART, i));
                allDeck.add(new Card(CARD_SUIT.SPADE, i));
            }
            allDeck.add(new Card(CARD_SUIT.JOKER, Constants.BIG_JOKER_VALUE));
            allDeck.add(new Card(CARD_SUIT.JOKER, Constants.LITTLE_JOKER_VALUE));
        }

        for (int i = 0; i < 7; i++){
            Random rand = new Random();
            int randomInt = rand.nextInt(allDeck.size());
            game_card_0.add(allDeck.get(randomInt));
            allDeck.remove(randomInt);
        }
        WilddogSync.getInstance().getReference().child("game").child("game_cards").child("game_card_0").setValue(game_card_0);

        for (int i = 0; i < 31; i++) {
            Random rand;
            int randomInt;

            rand = new Random();
            randomInt = rand.nextInt(allDeck.size());
            switch (allDeck.get(randomInt).getSuit()){
                case JOKER:
                    game_card_1_master.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_1").child("master_cards").setValue(game_card_1_master);
                    break;
                case SPADE:
                    game_card_1_spade.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_1").child("spade_cards").setValue(game_card_1_spade);
                    break;
                case HEART:
                    game_card_1_heart.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_1").child("heart_cards").setValue(game_card_1_heart);
                    break;
                case CLUB:
                    game_card_1_club.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_1").child("club_cards").setValue(game_card_1_club);
                    break;
                case DIAMOND:
                    game_card_1_diamond.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_1").child("diamond_cards").setValue(game_card_1_diamond);
                    break;
            }
            allDeck.remove(randomInt);

            rand = new Random();
            randomInt = rand.nextInt(allDeck.size());
            switch (allDeck.get(randomInt).getSuit()){
                case JOKER:
                    game_card_2_master.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_2").child("master_cards").setValue(game_card_2_master);
                    break;
                case SPADE:
                    game_card_2_spade.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_2").child("spade_cards").setValue(game_card_2_spade);
                    break;
                case HEART:
                    game_card_2_heart.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_2").child("heart_cards").setValue(game_card_2_heart);
                    break;
                case CLUB:
                    game_card_2_club.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_2").child("club_cards").setValue(game_card_2_club);
                    break;
                case DIAMOND:
                    game_card_2_diamond.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_2").child("diamond_cards").setValue(game_card_2_diamond);
                    break;
            }
            allDeck.remove(randomInt);

            rand = new Random();
            randomInt = rand.nextInt(allDeck.size());
            switch (allDeck.get(randomInt).getSuit()){
                case JOKER:
                    game_card_3_master.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_3").child("master_cards").setValue(game_card_3_master);
                    break;
                case SPADE:
                    game_card_3_spade.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_3").child("spade_cards").setValue(game_card_3_spade);
                    break;
                case HEART:
                    game_card_3_heart.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_3").child("heart_cards").setValue(game_card_3_heart);
                    break;
                case CLUB:
                    game_card_3_club.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_3").child("club_cards").setValue(game_card_3_club);
                    break;
                case DIAMOND:
                    game_card_3_diamond.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_3").child("diamond_cards").setValue(game_card_3_diamond);
                    break;
            }
            allDeck.remove(randomInt);


            rand = new Random();
            randomInt = rand.nextInt(allDeck.size());
            switch (allDeck.get(randomInt).getSuit()){
                case JOKER:
                    game_card_4_master.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_4").child("master_cards").setValue(game_card_4_master);
                    break;
                case SPADE:
                    game_card_4_spade.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_4").child("spade_cards").setValue(game_card_4_spade);
                    break;
                case HEART:
                    game_card_4_heart.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_4").child("heart_cards").setValue(game_card_4_heart);
                    break;
                case CLUB:
                    game_card_4_club.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_1").child("club_cards").setValue(game_card_4_club);
                    break;
                case DIAMOND:
                    game_card_4_diamond.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_4").child("diamond_cards").setValue(game_card_4_diamond);
                    break;
            }
            allDeck.remove(randomInt);


            rand = new Random();
            randomInt = rand.nextInt(allDeck.size());
            switch (allDeck.get(randomInt).getSuit()){
                case JOKER:
                    game_card_5_master.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_5").child("master_cards").setValue(game_card_5_master);
                    break;
                case SPADE:
                    game_card_5_spade.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_5").child("spade_cards").setValue(game_card_5_spade);
                    break;
                case HEART:
                    game_card_5_heart.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_5").child("heart_cards").setValue(game_card_5_heart);
                    break;
                case CLUB:
                    game_card_5_club.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_5").child("club_cards").setValue(game_card_5_club);
                    break;
                case DIAMOND:
                    game_card_5_diamond.add(allDeck.get(randomInt));
                    WilddogSync.getInstance().getReference().child("game").child("game_cards")
                            .child("game_card_5").child("diamond_cards").setValue(game_card_5_diamond);
                    break;
            }
            allDeck.remove(randomInt);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getCurrentBoss() {
        return currentBoss;
    }

    public void setCurrentBoss(int currentBoss) {
        this.currentBoss = currentBoss;

    }

}
