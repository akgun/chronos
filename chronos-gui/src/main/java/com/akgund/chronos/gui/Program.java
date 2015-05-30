package com.akgund.chronos.gui;

import javax.swing.*;
import java.util.stream.Stream;

public class Program {

    public static void main(String[] args) {
        try {
            UIManager.LookAndFeelInfo lookAndFeelInfo = Stream.of(UIManager.getInstalledLookAndFeels()).filter(info ->
                    "Nimbus".equals(info.getName())).findFirst().get();
            UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e1) {
            }
        }

        SwingUtilities.invokeLater(() -> {
            ChronosGUI chronosGUI = new ChronosGUI();
            chronosGUI.setVisible(true);
        });
    }
}
