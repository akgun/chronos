package com.akgund.chronos.gui;

import javax.swing.*;

public class Program {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChronosGUI chronosGUI = new ChronosGUI();
            chronosGUI.setVisible(true);
        });
    }
}
