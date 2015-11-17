package com.akgund.chronos.model.settings;

public class Settings {
    private Position windowLastPosition = new Position();

    public Position getWindowLastPosition() {
        return windowLastPosition;
    }

    public void setWindowLastPosition(Position windowLastPosition) {
        this.windowLastPosition = windowLastPosition;
    }
}
