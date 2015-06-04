package com.akgund.chronos.util;

import java.awt.*;

public class GUIUtil {

    /**
     * Adjusts grid bag constraints.
     *
     * @param c      Constraint to modify.
     * @param x      gridx
     * @param y      gridy
     * @param width  gridwidth
     * @param weight weightx
     */
    public static void setConstraint(GridBagConstraints c, int x, int y, int width, int weight) {
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.weightx = weight;
    }
}
