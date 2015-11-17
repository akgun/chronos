package com.akgund.chronos.gui;

import com.akgund.chronos.ChronosServiceFactory;
import com.akgund.chronos.core.impl.ChronosCoreException;
import com.akgund.chronos.gui.bus.IMessageClient;
import com.akgund.chronos.gui.bus.MessageBus;
import com.akgund.chronos.gui.bus.MessageType;
import com.akgund.chronos.gui.dialog.AddTaskDialog;
import com.akgund.chronos.gui.dialog.ReportDialog;
import com.akgund.chronos.gui.panel.MainPanel;
import com.akgund.chronos.gui.panel.TaskSelectionPanel;
import com.akgund.chronos.model.settings.Position;
import com.akgund.chronos.model.settings.Settings;
import com.akgund.chronos.service.IChronosSettingsService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ChronosGUI extends JFrame implements IMessageClient {

    public ChronosGUI() throws HeadlessException {
        MessageBus.getInstance().register(this);
        setTitle("Chronos Time Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new MainPanel());
        setJMenuBar(createMenu());
        pack();

        initPosition();
        addWindowListener(new ChronosWindowListener());
    }

    private void initPosition() {
        try {
            IChronosSettingsService settingsService = ChronosServiceFactory.createSettings();
            Settings settings = settingsService.getSettings();
            Position windowPosition = settings.getWindowLastPosition();
            setLocation(windowPosition.getX(), windowPosition.getY());
        } catch (ChronosCoreException e) {
           /* Use default settings. */
        }
    }

    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuSettings = new JMenu("Settings");
        JMenuItem menuItemAddTask = new JMenuItem("Add Task");
        menuItemAddTask.addActionListener((actionEvent) -> {
            AddTaskDialog addTaskDialog = new AddTaskDialog();
            addTaskDialog.setVisible(true);

            MessageBus.getInstance().sendMessage(TaskSelectionPanel.class, MessageType.RELOAD_DATA);
            pack();
        });
        menuSettings.add(menuItemAddTask);

        JMenuItem menuItemWorkReport = new JMenuItem("Show Work Report");
        menuItemWorkReport.addActionListener(e -> {
            ReportDialog reportDialog = new ReportDialog();
            reportDialog.setVisible(true);
        });
        menuSettings.add(menuItemWorkReport);

        menuBar.add(menuSettings);

        return menuBar;
    }

    @Override
    public void receiveMessage(MessageType message) {
        if (MessageType.PACK == message) {
            pack();
        }
    }
}

class ChronosWindowListener implements WindowListener {
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            Point locationOnScreen = e.getWindow().getLocationOnScreen();
            IChronosSettingsService settingsService = ChronosServiceFactory.createSettings();
            Settings settings = settingsService.getSettings();
            settings.getWindowLastPosition().setX((int) locationOnScreen.getX());
            settings.getWindowLastPosition().setY((int) locationOnScreen.getY());
            settingsService.saveSettings(settings);
        } catch (ChronosCoreException ex) {
           /* Do nothing. */
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
