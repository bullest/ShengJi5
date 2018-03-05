package com.bullest.shengji5;

/**
 * Created by yunfezhang on 3/1/18.
 */

public class SelfSingleton {
    private int selfPostion;

    private static SelfSingleton mSingleton;

    public SelfSingleton() {
    }

    public static SelfSingleton getInstance() {
        if (mSingleton == null) {
            mSingleton = new SelfSingleton();
        }
        return mSingleton;
    }

    public int getSelfPostion() {
        return selfPostion;
    }

    public void setSelfPostion(int postion) {
        this.selfPostion = postion;
    }

    public boolean isHoster() {
        return selfPostion == 5;
    }

}
