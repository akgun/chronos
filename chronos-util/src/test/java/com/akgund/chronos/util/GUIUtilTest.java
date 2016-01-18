package com.akgund.chronos.util;

import org.junit.Test;

import java.awt.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class GUIUtilTest {

    @Test
    public void testSetConstraint() throws Exception {
        final GridBagConstraints constraints = new GridBagConstraints();

        GUIUtil.setConstraint(constraints, 1, 2, 3, 4);

        assertThat(1, equalTo(constraints.gridx));
        assertThat(2, equalTo(constraints.gridy));
        assertThat(3, equalTo(constraints.gridwidth));
        assertThat(4.0, closeTo(constraints.weightx, 0));
    }
}