package com.akgund.chronos.model.settings;

public class Settings {
    private Position windowLastPosition = new Position();
    /**
     * Refresh interval in seconds.
     */
    private long workLogRefreshInterval = 0;

    public Position getWindowLastPosition() {
        return windowLastPosition;
    }

    public void setWindowLastPosition(Position windowLastPosition) {
        this.windowLastPosition = windowLastPosition;
    }

    public long getWorkLogRefreshInterval() {
        return workLogRefreshInterval;
    }

    public void setWorkLogRefreshInterval(long workLogRefreshInterval) {
        this.workLogRefreshInterval = workLogRefreshInterval;
    }
}
