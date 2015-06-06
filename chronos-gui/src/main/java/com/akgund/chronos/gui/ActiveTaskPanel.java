package com.akgund.chronos.gui;

import com.akgund.chronos.ChronosServiceFactory;
import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.gui.bus.IMessageClient;
import com.akgund.chronos.gui.bus.MessageBus;
import com.akgund.chronos.gui.bus.MessageType;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.service.IChronosService;
import com.akgund.chronos.util.DateTimeHelper;

import javax.swing.*;
import java.awt.*;

public class ActiveTaskPanel extends JPanel implements IMessageClient {
    private IChronosService chronosService = ChronosServiceFactory.create();
    private JLabel activateTaskLabel = new JLabel();

    public ActiveTaskPanel() {
        MessageBus.getInstance().register(this);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JLabel label = new JLabel("Active Task: ");
        activateTaskLabel.setForeground(Color.red);

        add(label);
        add(activateTaskLabel);

        updateActivateTaskLabel();
    }


    @Override
    public void receiveMessage(MessageType message) {
        if (MessageType.RELOAD_DATA == message) {
            updateActivateTaskLabel();
        }
    }

    private void updateActivateTaskLabel() {
        try {
            Task activeTask = chronosService.findActiveTask();
            if (activeTask == null) {
                activateTaskLabel.setText("No active task.");
                return;
            }
            activateTaskLabel.setText(String.format("%s", activeTask.getName()));
        } catch (ChronosCoreException e) {
            e.printStackTrace();
        }
    }
}
