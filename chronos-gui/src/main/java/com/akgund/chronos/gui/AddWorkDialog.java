package com.akgund.chronos.gui;

import com.akgund.chronos.ChronosServiceFactory;
import com.akgund.chronos.service.IChronosService;
import com.akgund.chronos.util.GUIUtil;

import javax.swing.*;
import java.awt.*;

public class AddWorkDialog extends JDialog {
    private IChronosService chronosService = ChronosServiceFactory.create();
    private Long taskId;
    private DateTimeSelector start = new DateTimeSelector();
    private DateTimeSelector end = new DateTimeSelector();

    public AddWorkDialog(Long taskId) {
        this.taskId = taskId;
        setTitle("Add Work");

        setContentPane(createLayout());

        pack();
    }

    private JPanel createLayout() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 5, 5, 5);

        GUIUtil.setConstraint(c, 0, 0, 1, 0);
        JLabel startLabel = new JLabel("Start");
        makeBold(startLabel);
        panel.add(startLabel, c);

        GUIUtil.setConstraint(c, 0, 1, 1, 0);
        panel.add(start, c);

        GUIUtil.setConstraint(c, 0, 2, 1, 0);
        JLabel endLabel = new JLabel("End");
        makeBold(endLabel);
        panel.add(endLabel, c);

        GUIUtil.setConstraint(c, 0, 3, 1, 0);
        panel.add(end, c);

        return panel;
    }

    private void makeBold(JLabel label) {
        label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, label.getFont().getSize() + 1));
    }
}
