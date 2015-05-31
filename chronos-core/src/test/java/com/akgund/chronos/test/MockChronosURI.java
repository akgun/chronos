package com.akgund.chronos.test;

import com.akgund.chronos.core.FileChronosURI;

public class MockChronosURI extends FileChronosURI {
    @Override
    public String userHome() {
        return "";
    }

    @Override
    public String chronosFolder() {
        return "";
    }

    @Override
    public String chronosDataFile() {
        return "";
    }

    @Override
    public String getURI() {
        return getClass().getClassLoader().getResource("test.json").toString().replace("file:/", "");
    }
}
