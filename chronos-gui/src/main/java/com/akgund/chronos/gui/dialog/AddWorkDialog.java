package com.akgund.chronos.gui.dialog;

import com.akgund.chronos.ChronosServiceFactory;
import com.akgund.chronos.core.impl.ChronosCoreException;
import com.akgund.chronos.gui.widget.DateTimeSelector;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.model.Work;
import com.akgund.chronos.service.impl.ChronosServiceException;
import com.akgund.chronos.service.IChronosService;
import com.akgund.chronos.util.GUIUtil;

import javax.swing.*;
import java.awt.*;

public class AddWorkDialog extends JDialog {
    private IChronosService chronosService = ChronosServiceFactory.create();
    private Task task;
    private DateTimeSelector timeSelectorStart = new DateTimeSelector(true, true);
    private DateTimeSelector timeSelectorEnd = new DateTimeSelector(true, true);
    private JButton buttonAddWork = new JButton("Add Work");

    public AddWorkDialog(Long taskId) {
        try {
            this.task = chronosService.getTask(taskId);
        } catch (ChronosCoreException e) {
            e.printStackTrace();
            dispose();
        }

        setTitle("Add Work");

        setContentPane(createLayout());

        initHandlers();

        setModal(true);
        pack();
    }

    public Task getTask() {
        return task;
    }

    private JPanel createLayout() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 5, 5, 5);

        int row = 0;
        GUIUtil.setConstraint(c, 0, row++, 1, 0);
        JLabel taskNameLabel = new JLabel(String.format("Task: %s", getTask().getName()));
        makeBold(taskNameLabel);
        taskNameLabel.setForeground(Color.red);
        panel.add(taskNameLabel, c);

        GUIUtil.setConstraint(c, 0, row++, 1, 0);
        JLabel startLabel = new JLabel("Start");
        makeBold(startLabel);
        panel.add(startLabel, c);

        GUIUtil.setConstraint(c, 0, row++, 1, 0);
        panel.add(timeSelectorStart, c);

        GUIUtil.setConstraint(c, 0, row++, 1, 0);
        JLabel endLabel = new JLabel("End");
        makeBold(endLabel);
        panel.add(endLabel, c);

        GUIUtil.setConstraint(c, 0, row++, 1, 0);
        panel.add(timeSelectorEnd, c);

        GUIUtil.setConstraint(c, 0, row++, 1, 0);
        panel.add(buttonAddWork, c);

        return panel;
    }

    private void initHandlers() {
        buttonAddWork.addActionListener(e -> {
            Task task = getTask();
            if (task == null) {
                return;
            }

            Work newWork = new Work();
            newWork.setTaskId(task.getId());
            newWork.setStart(timeSelectorStart.getDateTime());
            newWork.setEnd(timeSelectorEnd.getDateTime());

            try {
                chronosService.saveWork(newWork);
                dispose();
            } catch (ChronosServiceException e1) {
                e1.printStackTrace();
            } catch (ChronosCoreException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void makeBold(JLabel label) {
        label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, label.getFont().getSize() + 1));
    }
}
