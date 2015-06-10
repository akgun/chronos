package com.akgund.chronos.gui.widget;

import com.akgund.chronos.gui.event.IDateTimeSelectionChange;
import org.joda.time.DateTime;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.IntStream;

public class DateTimeSelector extends JPanel implements ActionListener {
    private DateTime initialTime;
    private JComboBox<DateTimeAbstractComboBoxItem> comboBoxMonth;
    private JComboBox<DateTimeAbstractComboBoxItem> comboBoxDay;
    private JComboBox<DateTimeAbstractComboBoxItem> comboBoxHour;
    private JComboBox<DateTimeAbstractComboBoxItem> comboBoxMinute;
    private boolean showDate;
    private boolean showTime;
    /* TODO: Support multiple handlers. */
    private IDateTimeSelectionChange dateTimeSelectionChange;

    public DateTimeSelector(boolean showDate, boolean showTime) {
        this(DateTime.now(), showDate, showTime);
    }

    public DateTimeSelector(DateTime initial, boolean showDate, boolean showTime) {
        this.initialTime = initial;
        setShowDate(showDate);
        setShowTime(showTime);

        initComboBoxes();

        createLayout();
    }

    private void createLayout() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        if (isShowDate()) {
            add(new JLabel("Month:"));
            add(comboBoxMonth);
            add(new JLabel("Day:"));
            add(comboBoxDay);
        }

        if (isShowTime()) {
            add(new JLabel("Hour:"));
            add(comboBoxHour);
            add(new JLabel("Minute:"));
            add(comboBoxMinute);
        }
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

        comboBoxMonth.setSelectedIndex(initialTime.getMonthOfYear() - 1);
        comboBoxDay.setSelectedIndex(initialTime.getDayOfMonth() - 1);
        comboBoxHour.setSelectedIndex(initialTime.getHourOfDay());
        comboBoxMinute.setSelectedIndex(initialTime.getMinuteOfHour());

        comboBoxMonth.addActionListener(this);
        comboBoxDay.addActionListener(this);
        comboBoxHour.addActionListener(this);
        comboBoxMinute.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DateTime dateTime = getDateTime();
        getDateTimeSelectionChange().onDateTimeChange(dateTime);
    }

    public DateTime getDateTime() {
        DateTimeAbstractComboBoxItem selectedMonth = getSelection(comboBoxMonth);
        DateTimeAbstractComboBoxItem selectedDay = getSelection(comboBoxDay);
        DateTimeAbstractComboBoxItem selectedHour = getSelection(comboBoxHour);
        DateTimeAbstractComboBoxItem selectedMinute = getSelection(comboBoxMinute);

        DateTime dateTime = initialTime;
        if (selectedMonth != null && selectedMonth.getValue() != null) {
            dateTime = dateTime.withMonthOfYear(selectedMonth.getValue());
        }

        if (selectedDay != null && selectedDay.getValue() != null) {
            dateTime = dateTime.withDayOfMonth(selectedDay.getValue());
        }

        if (selectedHour != null && selectedHour.getValue() != null) {
            dateTime = dateTime.withHourOfDay(selectedHour.getValue());
        }

        if (selectedMinute != null && selectedMinute.getValue() != null) {
            dateTime = dateTime.withMinuteOfHour(selectedMinute.getValue());
        }

        return dateTime;


    }

    private <T> T getSelection(JComboBox<T> comboBox) {
        return (T) comboBox.getSelectedItem();
    }

    public boolean isShowDate() {
        return showDate;
    }

    public void setShowDate(boolean showDate) {
        this.showDate = showDate;
    }

    public boolean isShowTime() {
        return showTime;
    }

    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }

    public IDateTimeSelectionChange getDateTimeSelectionChange() {
        if (dateTimeSelectionChange == null) {
            setDateTimeSelectionChange(dateTime -> {
            });
        }

        return dateTimeSelectionChange;
    }

    public void setDateTimeSelectionChange(IDateTimeSelectionChange dateTimeSelectionChange) {
        this.dateTimeSelectionChange = dateTimeSelectionChange;
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
