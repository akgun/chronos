package com.akgund.chronos.gui.widget;

import com.akgund.chronos.gui.event.IWorkUpdateEvent;
import com.akgund.chronos.model.Work;
import com.akgund.chronos.util.DateTimeHelper;
import org.joda.time.DateTime;
import org.joda.time.Period;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class WorkTableModel extends AbstractTableModel {
    private final String[] COLUMNS = new String[]{"Start", "End", "Duration"};
    private List<Work> workList = new ArrayList<>();
    private IWorkUpdateEvent workUpdateEvent = work -> {
    };

    public WorkTableModel() {
    }

    @Override
    public int getRowCount() {
        return getWorkList().size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Work work = getWorkList().get(rowIndex);
        DateTime start = work.getStart();
        DateTime end = work.getEnd();

        Period duration = Period.millis(-1);
        if (end != null) {
            duration = DateTimeHelper.getDiff(start, end);
        }

        switch (columnIndex) {
            case 0:
                return DateTimeHelper.printDate(work.getStart());
            case 1:
                return end != null ? DateTimeHelper.printDate(end) : "current";
            case 2:
                return DateTimeHelper.printDuration(duration);
        }

        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Work work = getWorkList().get(rowIndex);

        switch (columnIndex) {
            case 0:
                work.setStart(DateTimeHelper.parseDate("" + aValue));
                break;
            case 1:
                work.setEnd(DateTimeHelper.parseDate("" + aValue));
                break;
        }

        fireTableDataChanged();

        getWorkUpdateEvent().onPostUpdate(work);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0 || columnIndex == 1) {
            return true;
        }

        return false;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    public List<Work> getWorkList() {
        return workList;
    }

    public void setWorkList(List<Work> workList) {
        this.workList = workList;
    }

    public IWorkUpdateEvent getWorkUpdateEvent() {
        return workUpdateEvent;
    }

    public void setWorkUpdateEvent(IWorkUpdateEvent workUpdateEvent) {
        this.workUpdateEvent = workUpdateEvent;
    }
}
