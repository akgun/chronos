package com.akgund.chronos.test;

import com.akgund.chronos.dal.FileChronosURI;

import javax.enterprise.inject.Alternative;

@Alternative
public class MockFileChronosURI extends FileChronosURI {

    @Override
    public String userHome() {
        return "C:\\Users\\akgun";
    }
}
