package org.jbehave.web.runner.context;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.BatchFailures;

@SuppressWarnings("serial")
public class StoryContext implements Serializable {

    private static final String EMPTY = "";
	private String input = EMPTY;
    private String metaFilter = EMPTY;	
	private String output = EMPTY;
	private List<String> messages = new ArrayList<String>();
	private Throwable cause = null;
    private BatchFailures batchFailures = new BatchFailures();
    private List<Future<Embedder.ThrowableStory>> futures = new ArrayList<Future<Embedder.ThrowableStory>>();

	public StoryContext(){    
	}
	
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

    public String getMetaFilter() {
        return metaFilter;
    }

    public void setMetaFilter(String metaFilter) {
        this.metaFilter = metaFilter;
    }	
	
	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}


    public BatchFailures getBatchFailures() {
        return batchFailures;
    }

    public void setBatchFailures(BatchFailures batchFailures) {
        this.batchFailures = batchFailures;
    }

    public List<Future<Embedder.ThrowableStory>> getFutures() {
        return futures;
    }

    public void setFutures(List<Future<Embedder.ThrowableStory>> futures) {
        this.futures = futures;
    }

	public List<String> getFailureMessages() {
		messages.clear();
		addFailureMessage(cause);
		return messages;
	}

	private void addFailureMessage(Throwable cause) {
		if ( cause != null ){
			if ( cause.getMessage() != null ){
				messages.add(cause.getMessage());
			}
			// recurse
			addFailureMessage(cause.getCause());
		}
	}

	public void clearFailureCause() {
		this.cause = null;		
	}
	
	public void runFailedFor(Throwable cause) {
		this.cause = cause;
	}

	public String getFailureStackTrace() {
		StringWriter writer = new StringWriter();
		if (cause != null) {
			cause.printStackTrace(new PrintWriter(writer));
		}
		return writer.getBuffer().toString();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}



}
