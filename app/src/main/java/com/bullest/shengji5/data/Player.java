package com.bullest.shengji5.data;

/**
 * Created by yunfezhang on 4/19/17.
 */

public class Player {
    private String name;
    private int position;


    @SuppressWarnings("unused")
    public Player() {

    }

    public Player(int position, String name){
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
