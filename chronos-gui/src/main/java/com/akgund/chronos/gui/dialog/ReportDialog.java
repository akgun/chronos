package com.akgund.chronos.gui.dialog;

import com.akgund.chronos.gui.panel.ReportPanel;

import javax.swing.*;

public class ReportDialog extends JDialog {

    public ReportDialog() {
        setContentPane(new ReportPanel());
        setTitle("Work Report");
        pack();
    }
}
