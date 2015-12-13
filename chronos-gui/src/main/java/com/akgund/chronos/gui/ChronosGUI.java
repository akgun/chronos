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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class ChronosGUI extends JFrame implements IMessageClient {
    private static final Logger logger = LogManager.getLogger(ChronosGUI.class);

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
            logger.error(e.getMessage(), e);
           /* Use default settings. */
        }
    }

    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuPreferences = new JMenu("Preferences");
        JMenuItem menuItemAddTask = new JMenuItem("Add Task");
        menuItemAddTask.addActionListener((actionEvent) -> {
            AddTaskDialog addTaskDialog = new AddTaskDialog();
            addTaskDialog.setLocationRelativeTo(this);
            addTaskDialog.setVisible(true);

            MessageBus.getInstance().sendMessage(TaskSelectionPanel.class, MessageType.RELOAD_DATA);
            pack();
        });
        menuPreferences.add(menuItemAddTask);

        JMenuItem menuItemWorkReport = new JMenuItem("Show Work Report");
        menuItemWorkReport.addActionListener(e -> {
            ReportDialog reportDialog = new ReportDialog();
            reportDialog.setLocationRelativeTo(this);
            reportDialog.setVisible(true);
        });
        menuPreferences.add(menuItemWorkReport);

        JMenuItem menuItemSettingsFile = new JMenuItem("Show Settings");
        menuItemSettingsFile.addActionListener(e -> {
            try {
                String settingsUri = ChronosServiceFactory.createSettings().getSettingsUri();
                Desktop.getDesktop().open(new File(settingsUri));
            } catch (URISyntaxException | IOException ex) {
                logger.error(ex.getMessage(), ex);
                /* Do nothing. */
            }
        });
        menuPreferences.add(menuItemSettingsFile);

        menuBar.add(menuPreferences);

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
    private static final Logger logger = LogManager.getLogger(ChronosWindowListener.class);

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
            logger.error(ex.getMessage(), ex);
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
