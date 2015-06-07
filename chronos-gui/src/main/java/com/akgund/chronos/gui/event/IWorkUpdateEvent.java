package com.akgund.chronos.gui.event;

import com.akgund.chronos.model.Work;

@FunctionalInterface
public interface IWorkUpdateEvent {

    void onPostUpdate(Work work);
}
