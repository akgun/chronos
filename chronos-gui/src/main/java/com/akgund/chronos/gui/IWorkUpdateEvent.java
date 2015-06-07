package com.akgund.chronos.gui;

import com.akgund.chronos.model.Work;

@FunctionalInterface
public interface IWorkUpdateEvent {

    void onPostUpdate(Work work);
}
