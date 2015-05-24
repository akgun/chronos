package com.akgund.chronos.test;

import com.akgund.chronos.dal.IChronosURI;
import com.akgund.chronos.util.CDIFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestIChronosURI {

    @Test
    public void testGetURI() {
        IChronosURI iChronosURI = CDIFactory.getInstance().inject(IChronosURI.class);
        String uri = iChronosURI.getURI();
        assertEquals("C:\\Users\\akgun\\.chronos\\chronos.json", uri);
        CDIFactory.getInstance().dispose();
    }
}
