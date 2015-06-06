package com.akgund.chronos.gui;

import org.joda.time.DateTime;

import javax.swing.*;
import java.util.stream.IntStream;

public class DateTimeSelector extends JPanel {
    private DateTime dateTime;
    private JComboBox<DateTimeAbstractComboBoxItem> comboBoxMonth;
    private JComboBox<DateTimeAbstractComboBoxItem> comboBoxDay;
    private JComboBox<DateTimeAbstractComboBoxItem> comboBoxHour;
    private JComboBox<DateTimeAbstractComboBoxItem> comboBoxMinute;

    public DateTimeSelector() {
        this(DateTime.now());
    }

    public DateTimeSelector(DateTime initial) {
        this.dateTime = initial;

        initComboBoxes();

        createLayout();
    }

    private void createLayout() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(new JLabel("Month:"));
        add(comboBoxMonth);
        add(new JLabel("Day:"));
        add(comboBoxDay);
        add(new JLabel("Hour:"));
        add(comboBoxHour);
        add(new JLabel("Minute:"));
        add(comboBoxMinute);
    }

    private void initComboBoxes() {
        comboBoxMonth = new JComboBox<>();
        comboBoxDay = new JComboBox<>();
        comboBoxHour = new JComboBox<>();
        comboBoxMinute = new JComboBox<>();

        IntStream.range(1, 13).forEach(value ->
                comboBoxMonth.addItem(new DateTimeAbstractComboBoxItem(value)));
        IntStream.range(1, 32).forEach(value ->
                comboBoxDay.addItem(new DateTimeAbstractComboBoxItem(value)));
        IntStream.range(0, 24).forEach(value ->
                comboBoxHour.addItem(new DateTimeAbstractComboBoxItem(value)));
        IntStream.range(0, 60).forEach(value ->
                comboBoxMinute.addItem(new DateTimeAbstractComboBoxItem(value)));
    }

    public DateTime getDateTime() {
        return dateTime;
    }
}

class DateTimeAbstractComboBoxItem extends AbstractComboBoxItem<Integer> {

    public DateTimeAbstractComboBoxItem(Integer value) {
        super(value);
    }

    @Override
    public String getLabel() {
        return String.format("%02d", getValue());
    }
}
