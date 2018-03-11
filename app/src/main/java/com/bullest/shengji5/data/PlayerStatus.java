package com.bullest.shengji5.data;

import java.util.List;

/**
 * Created by yunfezhang on 2/27/18.
 */

public class PlayerStatus {
    private int position;
    private String name;
    private int grade;
    private Card friendCard;
    private List<Card> showCards;
    private int point;
    private boolean isReady;

    public PlayerStatus() {
    }

    public PlayerStatus(int position, String name, int grade, Card friendCard, List<Card> showCards, int point, boolean isReady) {
        this.position = position;
        this.name = name;
        this.grade = grade;
        this.point = point;
        this.friendCard = friendCard;
        this.showCards = showCards;
        this.isReady = isReady;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Card getFriendCard() {
        return friendCard;
    }

    public void setFriendCard(Card friendCard) {
        this.friendCard = friendCard;
    }

    public List<Card> getShowCards() {
        return showCards;
    }

    public void setShowCards(List<Card> showCards) {
        this.showCards = showCards;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
