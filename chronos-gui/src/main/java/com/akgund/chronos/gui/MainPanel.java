package com.akgund.chronos.gui;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        setLayout(new BorderLayout());

        add(new TasksPanel(), BorderLayout.CENTER);
    }
}
