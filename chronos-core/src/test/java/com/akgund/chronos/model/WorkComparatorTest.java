package com.akgund.chronos.model;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

public class WorkComparatorTest {
    private WorkComparator workComparator;

    @Before
    public void setUp() throws Exception {
        workComparator = new WorkComparator();
    }

    @Test
    public void whenCompareTwoNullThenReturnsNegative() throws Exception {
        final Work work1 = null;
        final Work work2 = null;

        assertThat(workComparator.compare(work1, work2), lessThan(0));
    }

    @Test
    public void whenCompareLeftSideNullThenReturnsNegative() throws Exception {
        final Work work1 = null;
        final Work work2 = new Work();

        assertThat(workComparator.compare(work1, work2), lessThan(0));
    }

    @Test
    public void whenCompareRightSideNullThenReturnsNegative() throws Exception {
        final Work work1 = new Work();
        final Work work2 = null;

        assertThat(workComparator.compare(work1, work2), lessThan(0));
    }

    @Test
    public void whenCompareTwoStartDateNullThenReturnsNegative() throws Exception {
        final Work work1 = new Work();
        final Work work2 = new Work();

        assertThat(workComparator.compare(work1, work2), lessThan(0));
    }

    @Test
    public void whenCompareLeftStartDateNullThenReturnsNegative() throws Exception {
        final Work work1 = new Work();
        final Work work2 = new Work();
        work2.setStart(DateTime.now());

        assertThat(workComparator.compare(work1, work2), lessThan(0));
    }

    @Test
    public void whenCompareRightStartDateNullThenReturnsNegative() throws Exception {
        final Work work1 = new Work();
        work1.setStart(DateTime.now());
        final Work work2 = new Work();

        assertThat(workComparator.compare(work1, work2), lessThan(0));
    }

    @Test
    public void whenCompareLeftStartDateBeforeThenReturnsNegative() throws Exception {
        final DateTime now = DateTime.now();
        final Work work1 = new Work();
        work1.setStart(now);
        final Work work2 = new Work();
        work2.setStart(now.plusMillis(1));

        assertThat(workComparator.compare(work1, work2), lessThan(0));
    }

    @Test
    public void whenCompareRightStartDateBeforeThenReturnsPositive() throws Exception {
        final DateTime now = DateTime.now();
        final Work work1 = new Work();
        work1.setStart(now.plusMillis(1));
        final Work work2 = new Work();
        work2.setStart(now);

        assertThat(workComparator.compare(work1, work2), greaterThan(0));
    }

    @Test
    public void whenCompareTwoStartDateEqualThenReturnsZero() throws Exception {
        final DateTime now = DateTime.now();
        final Work work1 = new Work();
        work1.setStart(now);
        final Work work2 = new Work();
        work2.setStart(now);

        assertThat(workComparator.compare(work1, work2), equalTo(0));
    }
}