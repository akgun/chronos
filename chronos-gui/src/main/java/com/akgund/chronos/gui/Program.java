package com.akgund.chronos.gui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.stream.Stream;

public class Program {
    private static final Logger logger = LogManager.getLogger(Program.class);

    public static void main(String[] args) {
        logger.debug("Program started.");

        try {
            UIManager.LookAndFeelInfo lookAndFeelInfo = Stream.of(UIManager.getInstalledLookAndFeels()).filter(info ->
                    "Nimbus".equals(info.getName())).findFirst().get();
            UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e1) {
                logger.error(e1.getMessage(), e1);
            }
        }

        SwingUtilities.invokeLater(() -> {
            ChronosGUI chronosGUI = new ChronosGUI();
            chronosGUI.setVisible(true);
        });
    }
}
