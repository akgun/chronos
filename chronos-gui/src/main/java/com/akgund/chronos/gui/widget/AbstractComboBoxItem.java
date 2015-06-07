package com.akgund.chronos.gui.widget;

public abstract class AbstractComboBoxItem<T> {
    private String label;
    private T value;

    public AbstractComboBoxItem() {
    }

    public AbstractComboBoxItem(T value) {
        setValue(value);
    }

    public abstract String getLabel();

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}