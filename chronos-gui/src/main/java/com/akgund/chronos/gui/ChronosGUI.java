package com.akgund.chronos.gui;

import com.akgund.chronos.gui.bus.MessageBus;
import com.akgund.chronos.gui.bus.MessageType;

import javax.swing.*;
import java.awt.*;

public class ChronosGUI extends JFrame {

    public ChronosGUI() throws HeadlessException {
        setTitle("Chronos Time Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new MainPanel(this));
        setJMenuBar(createMenu());
        pack();
    }

    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuSettings = new JMenu("Settings");
        JMenuItem menuItemAddTask = new JMenuItem("Add Task");
        menuItemAddTask.addActionListener((actionEvent) -> {
            AddTaskDialog addTaskDialog = new AddTaskDialog();
            addTaskDialog.setVisible(true);

            MessageBus.getInstance().sendMessage(TasksPanel.class, MessageType.RELOAD_DATA.toString());
        });
        menuSettings.add(menuItemAddTask);

        menuBar.add(menuSettings);

        return menuBar;
    }
}
