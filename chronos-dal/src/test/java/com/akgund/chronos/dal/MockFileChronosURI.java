package com.akgund.chronos.dal;

import javax.enterprise.inject.Alternative;

@Alternative
public class MockFileChronosURI extends FileChronosURI {

    @Override
    public String userHome() {
        return "C:\\Users\\akgun";
    }
}
