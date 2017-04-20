package com.bullest.shengji5;

/**
 * Created by yunfezhang on 4/19/17.
 */

class Player {
    private String name;
    private long position;
    private Boolean isReady;


    @SuppressWarnings("unused")
    private Player() {

    }

    public Player(long position, String name, long isready){
        this.name = name;
        this.position = position;
        if (isready==0){
            this.isReady = false;
        } else {
            this.isReady = true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public Boolean isReady() {
        return isReady;
    }

    public void setReady(Boolean isReady) {
        this.isReady = isReady;
    }


}
