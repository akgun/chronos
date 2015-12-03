package com.akgund.chronos.model.settings;

public class Position {
    private int x;
    private int y;

    public int getX() {
        if (x < 0) {
            return 0;
        }
        
        return x;
    }

    public void setX(int x) {
        if (x < 0) {
            x = 0;
        }

        this.x = x;
    }

    public int getY() {
        if (y < 0) {
            return 0;
        }

        return y;
    }

    public void setY(int y) {
        if (y < 0) {
            y = 0;
        }

        this.y = y;
    }
}
