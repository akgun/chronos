package com.akgund.chronos.model;

import com.akgund.chronos.model.report.WorkReport;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class WorkReportComparatorTest {

    @Test
    public void whenFirstWorkReportNullThenZero() throws Exception {
        final WorkReportComparator workReportComparator = new WorkReportComparator();

        final int result = workReportComparator.compare(null, new WorkReport());

        assertThat(result, equalTo(0));
    }

    @Test
    public void whenSecondWorkReportNullThenZero() throws Exception {
        final WorkReportComparator workReportComparator = new WorkReportComparator();

        final int result = workReportComparator.compare(new WorkReport(), null);

        assertThat(result, equalTo(0));
    }

    @Test
    public void whenFirstWorkReportWorkNullThenZero() throws Exception {
        final WorkReportComparator workReportComparator = new WorkReportComparator();
        final WorkReport workReport1 = new WorkReport();
        workReport1.setWork(null);
        final WorkReport workReport2 = new WorkReport();
        workReport2.setWork(new Work());

        final int result = workReportComparator.compare(workReport1, workReport2);

        assertThat(result, equalTo(0));
    }

    @Test
    public void whenSecondWorkReportWorkNullThenZero() throws Exception {
        final WorkReportComparator workReportComparator = new WorkReportComparator();
        final WorkReport workReport1 = new WorkReport();
        workReport1.setWork(new Work());
        final WorkReport workReport2 = new WorkReport();
        workReport2.setWork(null);

        final int result = workReportComparator.compare(workReport1, workReport2);

        assertThat(result, equalTo(0));
    }

    @Test
    public void whenFirstWorkReportWorkStartDateNullThenZero() throws Exception {
        final WorkReportComparator workReportComparator = new WorkReportComparator();
        final WorkReport workReport1 = new WorkReport();
        final Work work1 = new Work();
        work1.setStart(null);
        workReport1.setWork(work1);
        final WorkReport workReport2 = new WorkReport();
        final Work work2 = new Work();
        work2.setStart(DateTime.now());
        workReport2.setWork(work2);

        final int result = workReportComparator.compare(workReport1, workReport2);

        assertThat(result, equalTo(0));
    }

    @Test
    public void whenSecondWorkReportWorkStartDateNullThenZero() throws Exception {
        final WorkReportComparator workReportComparator = new WorkReportComparator();
        final WorkReport workReport1 = new WorkReport();
        final Work work1 = new Work();
        work1.setStart(DateTime.now());
        workReport1.setWork(work1);
        final WorkReport workReport2 = new WorkReport();
        final Work work2 = new Work();
        work2.setStart(null);
        workReport2.setWork(work2);

        final int result = workReportComparator.compare(workReport1, workReport2);

        assertThat(result, equalTo(0));
    }

    @Test
    public void whenFirstArgIsBeforeThanSecondArgThenMinus1() throws Exception {
        final DateTime now = DateTime.now();
        final WorkReportComparator workReportComparator = new WorkReportComparator();
        final WorkReport workReport1 = new WorkReport();
        final Work work1 = new Work();
        work1.setStart(now.minusMillis(1));
        workReport1.setWork(work1);
        final WorkReport workReport2 = new WorkReport();
        final Work work2 = new Work();
        work2.setStart(now);
        workReport2.setWork(work2);

        final int result = workReportComparator.compare(workReport1, workReport2);

        assertThat(result, equalTo(-1));
    }

    @Test
    public void whenFirstArgIsAfterThanSecondArgThenPlus1() throws Exception {
        final DateTime now = DateTime.now();
        final WorkReportComparator workReportComparator = new WorkReportComparator();
        final WorkReport workReport1 = new WorkReport();
        final Work work1 = new Work();
        work1.setStart(now);
        workReport1.setWork(work1);
        final WorkReport workReport2 = new WorkReport();
        final Work work2 = new Work();
        work2.setStart(now.minusMillis(1));
        workReport2.setWork(work2);

        final int result = workReportComparator.compare(workReport1, workReport2);

        assertThat(result, equalTo(1));
    }

    @Test
    public void whenBothEqualThen0() throws Exception {
        final DateTime now = DateTime.now();
        final WorkReportComparator workReportComparator = new WorkReportComparator();
        final WorkReport workReport1 = new WorkReport();
        final Work work1 = new Work();
        work1.setStart(now);
        workReport1.setWork(work1);
        final WorkReport workReport2 = new WorkReport();
        final Work work2 = new Work();
        work2.setStart(now);
        workReport2.setWork(work2);

        final int result = workReportComparator.compare(workReport1, workReport2);

        assertThat(result, equalTo(0));
    }
}