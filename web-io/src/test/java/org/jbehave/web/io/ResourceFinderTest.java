package org.jbehave.web.io;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.jbehave.web.io.ResourceFinder.ResourceNotFoundException;
import org.junit.Test;

public class ResourceFinderTest {

	@Test
	public void canFindResourceInClasspath() throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String rootDirectory = "classpath:org/jbehave/web";
		ResourceFinder finder = new ResourceFinder(classLoader, rootDirectory);
		assertEquals("A test resource", finder.resourceAsString("io/resource.txt"));
	}
	
	@Test
	public void canChangeRootDirectory() throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String rootDirectory = "classpath:org/jbehave/web";
		ResourceFinder finder = new ResourceFinder(classLoader, rootDirectory);
		finder.useRootDirectory("classpath:org/jbehave/web/io");
		assertEquals("A test resource", finder.resourceAsString("resource.txt"));
	}
	
	@Test
	public void canFindResourceInFilesystem() throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String rootDirectory = "src/test/java/org/jbehave/web";
		ResourceFinder finder = new ResourceFinder(classLoader, rootDirectory);
		assertEquals("A test resource", finder.resourceAsString("io/resource.txt"));
	}

	@Test(expected=ResourceNotFoundException.class)
	public void cannotFindInexistenResourceInClasspath() throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String rootDirectory = "classpath:inexistent";
		ResourceFinder finder = new ResourceFinder(classLoader, rootDirectory);
		finder.resourceAsString("resource.txt");
	}

	@Test(expected=ResourceNotFoundException.class)
	public void cannotFindInexistenResourceInFileSystem() throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String rootDirectory = "/inexistent";
		ResourceFinder finder = new ResourceFinder(classLoader, rootDirectory);
		finder.resourceAsString("resource.txt");
	}

}