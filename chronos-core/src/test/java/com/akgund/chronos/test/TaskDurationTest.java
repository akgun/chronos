package com.akgund.chronos.test;

import com.akgund.chronos.model.Task;
import com.akgund.chronos.model.Work;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TaskDurationTest {

    @Test
    public void testTotalWorkMin1() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd HH:mm");

        Task task = new Task();
        Work work1 = new Work();
        work1.setStart(formatter.parseDateTime("05-12 09:00"));
        work1.setEnd(formatter.parseDateTime("05-12 09:45"));
        task.getWorkList().add(work1);

        assertEquals(2700000L, task.getTotalWork().getMillis());
    }

    @Test
    public void testTotalWorkMin2() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd HH:mm");

        Task task = new Task();
        Work work1 = new Work();
        work1.setStart(formatter.parseDateTime("05-12 09:00"));
        work1.setEnd(formatter.parseDateTime("05-12 09:45"));
        task.getWorkList().add(work1);

        Work work2 = new Work();
        work2.setStart(formatter.parseDateTime("05-12 10:30"));
        work2.setEnd(formatter.parseDateTime("05-12 11:45"));
        task.getWorkList().add(work2);

        assertEquals(7200000L, task.getTotalWork().getMillis());
    }
}
