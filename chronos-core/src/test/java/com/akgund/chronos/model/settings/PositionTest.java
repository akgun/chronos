package com.akgund.chronos.model.settings;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PositionTest {

    @Test
    public void whenPositionFirstCreatedThenXZero() throws Exception {
        final Position position = new Position();

        assertThat(position.getX(), equalTo(0));
    }

    @Test
    public void whenPositionFirstCreatedThenYZero() throws Exception {
        final Position position = new Position();

        assertThat(position.getY(), equalTo(0));
    }

    @Test
    public void whenXSetToNegativeThanXZero() throws Exception {
        final Position position = new Position();

        position.setX(-1);

        assertThat(position.getX(), equalTo(0));
    }

    @Test
    public void whenXSetToZeroThanXZero() throws Exception {
        final Position position = new Position();

        position.setX(0);

        assertThat(position.getX(), equalTo(0));
    }

    @Test
    public void whenXSetToPositiveThanXPositive() throws Exception {
        final Position position = new Position();

        position.setX(1);

        assertThat(position.getX(), equalTo(1));
    }

    @Test
    public void whenYSetToNegativeThanYZero() throws Exception {
        final Position position = new Position();

        position.setY(-1);

        assertThat(position.getY(), equalTo(0));
    }

    @Test
    public void whenYSetToZeroThanYZero() throws Exception {
        final Position position = new Position();

        position.setY(0);

        assertThat(position.getY(), equalTo(0));
    }

    @Test
    public void whenYSetToPositiveThanYPositive() throws Exception {
        final Position position = new Position();

        position.setY(1);

        assertThat(position.getY(), equalTo(1));
    }
}