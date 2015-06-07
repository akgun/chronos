package com.akgund.chronos.test;

import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.model.FilterWorkRequest;
import com.akgund.chronos.model.FilterWorkResponse;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.model.Work;
import com.akgund.chronos.service.ChronosServiceException;
import com.akgund.chronos.service.IChronosService;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TaskDurationTest extends BaseTest {
    private IChronosService chronosService = MockFactory.createChronosService();

    @Test
    public void testTotalWorkMin1() throws ChronosCoreException, ChronosServiceException {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd HH:mm");

        Task task = new Task();
        Work work1 = new Work();
        work1.setStart(formatter.parseDateTime("05-12 09:00"));
        work1.setEnd(formatter.parseDateTime("05-12 09:45"));
        task.getWorkList().add(work1);

        chronosService.saveTask(task);

        FilterWorkRequest filterWorkRequest = new FilterWorkRequest();
        FilterWorkResponse filterWorkResponse = chronosService.filterWorks(task.getId(), filterWorkRequest);

        assertEquals(2700000L, filterWorkResponse.getTotalDuration().getMillis());
    }

    @Test
    public void testTotalWorkMin2() throws ChronosCoreException, ChronosServiceException {
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

        chronosService.saveTask(task);

        FilterWorkRequest filterWorkRequest = new FilterWorkRequest();
        FilterWorkResponse filterWorkResponse = chronosService.filterWorks(task.getId(), filterWorkRequest);

        assertEquals(7200000L, filterWorkResponse.getTotalDuration().getMillis());
    }

    @Test
    public void testTotalWorkMin3() throws ChronosCoreException, ChronosServiceException {
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

        Work work3 = new Work();
        work3.setStart(formatter.parseDateTime("04-12 10:30"));
        work3.setEnd(formatter.parseDateTime("04-12 11:45"));
        task.getWorkList().add(work3);

        Work work4 = new Work();
        work4.setStart(formatter.parseDateTime("04-12 12:00"));
        work4.setEnd(formatter.parseDateTime("04-12 12:55"));
        task.getWorkList().add(work4);

        Work work5 = new Work();
        work5.setStart(formatter.parseDateTime("03-02 12:00"));
        work5.setEnd(formatter.parseDateTime("03-02 12:55"));
        task.getWorkList().add(work5);

        chronosService.saveTask(task);

        assertEquals(18300000L, chronosService.filterWorks(task.getId(), new FilterWorkRequest()).getTotalDuration().getMillis());

        FilterWorkRequest filterWorkRequest12Apr = new FilterWorkRequest();
        filterWorkRequest12Apr.setDay(12);
        filterWorkRequest12Apr.setMonth(4);
        assertEquals(7800000L, chronosService.filterWorks(task.getId(), filterWorkRequest12Apr).getTotalDuration().getMillis());

        FilterWorkRequest filterWork12 = new FilterWorkRequest();
        filterWork12.setDay(12);
        assertEquals(15000000L, chronosService.filterWorks(task.getId(), filterWork12).getTotalDuration().getMillis());
    }

    @Test
    public void testTotalWorkDifferentTasks() throws ChronosCoreException, ChronosServiceException {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd HH:mm");

        Task task1 = new Task();
        Task task2 = new Task();

        Work work1ForTask1 = new Work();
        work1ForTask1.setStart(formatter.parseDateTime("04-30 09:00"));
        work1ForTask1.setEnd(formatter.parseDateTime("04-30 09:30"));
        task1.getWorkList().add(work1ForTask1);

        Work work1ForTask2 = new Work();
        work1ForTask2.setStart(formatter.parseDateTime("05-30 10:00"));
        work1ForTask2.setEnd(formatter.parseDateTime("05-30 10:20"));
        task2.getWorkList().add(work1ForTask2);

        chronosService.saveTask(task1);
        chronosService.saveTask(task2);

        FilterWorkRequest filterWorkRequestDay30 = new FilterWorkRequest();
        filterWorkRequestDay30.setDay(30);
        FilterWorkResponse filterWorkResponse = chronosService.filterWorks(task1.getId(), filterWorkRequestDay30);

        assertEquals(30 * 60 * 1000L, filterWorkResponse.getTotalDuration().getMillis());
    }
}
