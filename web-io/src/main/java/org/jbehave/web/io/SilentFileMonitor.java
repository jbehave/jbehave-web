package org.jbehave.web.io;

import java.io.PrintStream;

public class SilentFileMonitor extends PrintStreamFileMonitor {

	protected void print(PrintStream output, String message) {
		// print nothing
	}

}
