package com.bullest.shengji5;

import java.util.HashMap;

import static java.lang.Math.toIntExact;

/**
 * Created by yunfezhang on 4/22/17.
 */

public class Card {

    private CARD_SUIT suit;
    private int value;
    private String display_value;
    private String display_suit;

    public Card(HashMap<String, String> value) {
    }

    public CARD_SUIT getSuit() {
        return suit;
    }

    public void setSuit(CARD_SUIT suit) {
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setDisplay_suit(String display_suit) {
        this.display_suit = display_suit;
    }

    public Boolean aJoker() {
        return suit == CARD_SUIT.JOKER;
    }

    public Boolean aBigJoker() {
        return value == Constants.BIG_JOKER_VALUE;
    }

    public Boolean aLittleJoker() {
        return value == Constants.LITTLE_JOKER_VALUE;
    }

    public String getDisplay_value() {
        return display_value;
    }

    public String getDisplay_suit() {
        return display_suit;
    }



    public void setDisplay_value(String display_value) {
        this.display_value = display_value;
    }

    public Card(CARD_SUIT suit, int value) {
        this.suit = suit;
        this.value = value;
        this.display_suit = translateSuit(suit);
        this.display_value = translateValue(value);
    }

    public Card (String suit, long value) {
        this.value = (int) value;
        this.suit = translateSuit(suit);
        this.display_suit = translateSuit(this.suit);
        this.display_value = translateValue(value);
    }

    private CARD_SUIT translateSuit(String suit) {
        CARD_SUIT suit_temp;
        switch (suit){
            case "DIAMOND":
                suit_temp = CARD_SUIT.DIAMOND;
                break;
            case "CLUB":
                suit_temp = CARD_SUIT.CLUB;
                break;
            case "HEART":
                suit_temp = CARD_SUIT.HEART;
                break;
            case "SPADE":
                suit_temp = CARD_SUIT.SPADE;
                break;
            default:
                suit_temp = CARD_SUIT.JOKER;
                break;
        }
        return  suit_temp;
    }

    private String translateSuit(CARD_SUIT suit) {
        String display_suit = "";

        switch (suit){
            case JOKER:
                display_suit = "J";
                break;
            case DIAMOND:
                display_suit = "♦";
                break;
            case CLUB:
                display_suit = "♣";
                break;
            case HEART:
                display_suit = "♥";
                break;
            case SPADE:
                display_suit = "♠";
                break;
        }

        return display_suit;
    }

    private String translateValue(long value) {
        String display_value = "";

        if (suit == CARD_SUIT.JOKER) {
            display_value = "O";
        } else {
            switch ((int) value){
                case 11:
                    display_value = "J";
                    break;
                case 12:
                    display_value = "Q";
                    break;
                case 13:
                    display_value = "K";
                    break;
                case 14:
                    display_value = "A";
                    break;
                default:
                    display_value = String.valueOf(value);
            }
        }

        return display_value;
    }


}
