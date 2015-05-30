package com.akgund.chronos.gui;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel(ChronosGUI parent) {
        setLayout(new BorderLayout());

        add(new TasksPanel(parent), BorderLayout.CENTER);
    }
}
