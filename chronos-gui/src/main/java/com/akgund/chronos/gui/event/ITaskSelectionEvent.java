package com.akgund.chronos.gui.event;

import com.akgund.chronos.model.Task;

import java.io.Serializable;

@FunctionalInterface
public interface ITaskSelectionEvent extends Serializable {

    void onSelected(Task task);
}
