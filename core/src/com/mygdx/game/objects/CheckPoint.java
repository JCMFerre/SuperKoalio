package com.mygdx.game.objects;

public class CheckPoint {

    private float x;
    private float y;

    public CheckPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public CheckPoint() {
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
