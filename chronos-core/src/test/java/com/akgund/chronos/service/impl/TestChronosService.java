package com.akgund.chronos.service.impl;

import com.akgund.chronos.core.IChronosTasksDAL;
import com.akgund.chronos.core.impl.ChronosCoreException;
import com.akgund.chronos.model.ChronosTasks;
import com.akgund.chronos.model.FilterWorkRequest;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.model.Work;
import com.akgund.chronos.model.report.DateReport;
import com.akgund.chronos.model.report.WorkReport;
import com.akgund.chronos.service.impl.ChronosService;
import com.akgund.chronos.service.impl.ChronosServiceException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TestChronosService {
    @Mock
    private IChronosTasksDAL chronosTasksDAL;
    @InjectMocks
    private ChronosService chronosService;
    private ChronosTasks chronosTasks;
    private Task task1Archived;
    private Task task2;
    private Work work2;

    @Before
    public void setUp() throws Exception {
        chronosService = new ChronosService();

        MockitoAnnotations.initMocks(this);

        chronosTasks = new ChronosTasks();

        task1Archived = new Task();
        task1Archived.setId(1L);
        task1Archived.setName("task1Archived");
        chronosTasks.getTasks().put(task1Archived.getId(), task1Archived);
        task1Archived.setArchived(true);

        task2 = new Task();
        task2.setId(2L);
        task2.setName("task2");
        chronosTasks.getTasks().put(task2.getId(), task2);
        task2.setArchived(false);

        work2 = new Work();
        work2.setId(123L);
        work2.setTaskId(task2.getId());
        work2.setStart(DateTime.now().withDayOfMonth(7));
        work2.setEnd(DateTime.now().withDayOfMonth(7).plusHours(2));
        task2.getWorkList().add(work2);
    }

    @Test
    public void testListTasks() throws ChronosCoreException {
        when(chronosTasksDAL.get()).thenReturn(chronosTasks);

        List<Task> tasks = chronosService.listTasks();

        assertThat(Arrays.asList(task2), equalTo(tasks));
    }

    @Test
    public void testSaveTak() throws ChronosCoreException {
        when(chronosTasksDAL.get()).thenReturn(chronosTasks);

        Task task = new Task();
        task.setName("new task");
        chronosService.saveTask(task);

        assertNotNull(task.getId());
        assertThat(task.getId(), notNullValue());
    }

    @Test
    public void testSaveWork() throws ChronosCoreException, ChronosServiceException {
        when(chronosTasksDAL.get()).thenReturn(chronosTasks);

        final Work work = new Work();
        work.setTaskId(task2.getId());

        chronosService.saveWork(work);

        assertThat(work.getId(), notNullValue());
    }

    @Test
    public void testDeleteWork() throws ChronosCoreException, ChronosServiceException {
        when(chronosTasksDAL.get()).thenReturn(chronosTasks);

        chronosService.deleteWork(task2.getId(), work2.getId());

        assertThat(chronosService.getTask(task2.getId()).getWorkList(), hasSize(0));
    }

    @Test
    public void testGetReportSize() throws ChronosCoreException {
        when(chronosTasksDAL.get()).thenReturn(chronosTasks);

        FilterWorkRequest filterWorkRequest = new FilterWorkRequest();
        filterWorkRequest.setDay(7);

        DateReport report = chronosService.getReport(filterWorkRequest);
        assertThat(report.getWorkReportList(), hasSize(1));
    }

    @Test
    public void testGetReport() throws ChronosCoreException {
        when(chronosTasksDAL.get()).thenReturn(chronosTasks);

        FilterWorkRequest filterWorkRequest = new FilterWorkRequest();
        filterWorkRequest.setDay(7);

        DateReport report = chronosService.getReport(filterWorkRequest);
        List<WorkReport> workReportList = report.getWorkReportList();

        assertThat(workReportList.get(0).getTaskName(), equalTo(task2.getName()));
    }
}
