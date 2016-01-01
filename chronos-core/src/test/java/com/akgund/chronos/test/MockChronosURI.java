package com.akgund.chronos.test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.akgund.chronos.core.impl.FileChronosURI;

public class MockChronosURI extends FileChronosURI {

	@Override
	public String getDataURI() throws URISyntaxException {
		Path path = Paths.get(getClass().getClassLoader().getResource("test.json").toURI());

		return path.toAbsolutePath().toString();
	}
}
