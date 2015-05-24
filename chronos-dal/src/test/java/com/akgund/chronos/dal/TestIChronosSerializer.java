package com.akgund.chronos.dal;

import com.akgund.chronos.model.ChronosTasks;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.model.Work;
import hirondelle.date4j.DateTime;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestIChronosSerializer {
    private IChronosSerializer chronosSerializer = new GsonChronosSerializer();

    @Test
    public void testSerialize() throws IOException {
        ChronosTasks chronosTasks = new ChronosTasks();

        Task filmIzle = new Task();
        filmIzle.setName("film izle");
        filmIzle.setId(1L);
        filmIzle.setActive(true);

        Work work = new Work();
        work.setComment("1. filmi izledim.");
        work.setStart(new DateTime("2015-05-24"));
        work.setEnd(new DateTime("2015-05-25"));
        filmIzle.getWorkList().add(work);

        chronosTasks.getAllTasks().add(filmIzle);
        String data = chronosSerializer.serialize(chronosTasks);
        String expected = "{\"allTasks\":[{\"id\":1,\"name\":\"film izle\",\"workList\":[{\"start\":{\"fDateTime\":\"2015-05-24\",\"fIsAlreadyParsed\":false,\"fHashCode\":0},\"end\":{\"fDateTime\":\"2015-05-25\",\"fIsAlreadyParsed\":false,\"fHashCode\":0},\"comment\":\"1. filmi izledim.\"}],\"active\":true}]}";

        assertEquals(expected, data);
    }

    @Test
    public void testDeserilize() {
        String data = "{\"allTasks\":[{\"id\":1,\"name\":\"film izle\",\"workList\":[{\"start\":{\"fDateTime\":\"2015-05-24\",\"fIsAlreadyParsed\":false,\"fHashCode\":0},\"end\":{\"fDateTime\":\"2015-05-25\",\"fIsAlreadyParsed\":false,\"fHashCode\":0},\"comment\":\"1. filmi izledim.\"}],\"active\":true}]}";

        ChronosTasks chronosTasks = chronosSerializer.deserialize(data);
        assertNotNull(chronosTasks);

        assertEquals(1, chronosTasks.getAllTasks().size());
    }
}
