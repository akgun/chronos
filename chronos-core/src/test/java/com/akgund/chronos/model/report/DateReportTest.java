package com.akgund.chronos.model.report;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

public class DateReportTest {

    @Test
    public void testGetWorkReportList() throws Exception {
        final DateReport dateReport = new DateReport();

        assertThat(dateReport.getWorkReportList(), empty());
    }

    @Test
    public void testSetWorkReportList() throws Exception {
        final DateReport dateReport = new DateReport();
        final WorkReport workReport = new WorkReport();
        workReport.setTaskName("task1");
        dateReport.setWorkReportList(Arrays.asList(workReport));

        assertThat(dateReport.getWorkReportList(), equalTo(Arrays.asList(workReport)));
    }
}