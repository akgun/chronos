package com.akgund.chronos.dal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IChronosURITest {

    @Test
    public void testGetURI() {
        IChronosURI iChronosURI = CDIFactory.getInstance().inject(IChronosURI.class);
        String uri = iChronosURI.getURI();
        assertEquals("C:\\Users\\akgun\\.chronos\\chronos.json", uri);
        CDIFactory.getInstance().dispose();
    }
}
