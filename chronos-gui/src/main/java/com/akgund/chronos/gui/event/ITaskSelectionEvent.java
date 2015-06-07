package com.akgund.chronos.gui.event;

import com.akgund.chronos.model.Task;

@FunctionalInterface
public interface ITaskSelectionEvent {

    void onSelected(Task task);
}
