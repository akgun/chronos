package com.akgund.chronos.gui;

import javax.swing.*;
import java.awt.*;

public class ChronosGUI extends JFrame {

    public ChronosGUI() throws HeadlessException {
        setTitle("Chronos Time Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new MainPanel(this));
        pack();
    }
}
