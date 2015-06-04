package com.akgund.chronos.gui;

import com.akgund.chronos.model.Task;

public interface ITaskSelectionEvent {

    void onSelected(Task task);
}
