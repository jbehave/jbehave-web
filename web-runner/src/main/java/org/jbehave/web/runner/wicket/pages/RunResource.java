package org.jbehave.web.runner.wicket.pages;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryManager;
import org.jbehave.core.failures.BatchFailures;
import org.jbehave.core.io.ResourceLoader;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.reporters.TxtOutput;
import org.jbehave.web.runner.context.StoryContext;
import org.jbehave.web.runner.context.StoryOutputStream;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class RunResource extends Template {

	@Inject
	private ResourceLoader loader;

	@Inject
	private Embedder embedder;
	
    private StoryManager storyManager;

    private StoryOutputStream outputStream = new StoryOutputStream();

    private StoryContext storyContext = new StoryContext();

	public RunResource(final PageParameters parameters) {
        reportTo(outputStream);
        storyManager = embedder.storyManager();
        setPageTitle("Run Resource");
        add(new NoMarkupMultiLineLabel("output", "", "brush: plain"));        
        add(new NoMarkupMultiLineLabel("failure", new PropertyModel<String>(storyContext, "failureStackTrace"),
                "brush: java; gutter: false; collapse: true"));
        showOutput(parameters);
	}
	
    private void reportTo(OutputStream ouputStream) {
        final Properties outputPatterns = new Properties();
        final Keywords keywords = embedder.configuration().keywords();
        final boolean reportFailureTrace = false;
        embedder.configuration().useStoryReporterBuilder(new StoryReporterBuilder() {
            @Override
            public StoryReporter build(String storyPath) {
                return new TxtOutput(new PrintStream(outputStream), outputPatterns, keywords, reportFailureTrace);
            }
        });
    }

    private void showOutput(PageParameters parameters) {
		String uri = parameters.get("uri").toString();
		String content = loader.loadResourceAsText(uri);
		storyContext.setInput(content);
		run();
        MultiLineLabel output = (MultiLineLabel) get("output");
        output.setDefaultModelObject(content);
    }
    
    private void run() {
        if (isNotBlank(storyContext.getInput())) {
            outputStream.reset();
            storyContext.clearFailureCause();
            embedder.useMetaFilters(asList(storyContext.getMetaFilter()));
            String storyPath = storyPath();
            Story story = storyManager.storyOfText(storyContext.getInput(), storyPath);
            storyManager.runningStories(asList(story), embedder.metaFilter(), null);
            BatchFailures failures = new BatchFailures();
            storyManager.waitUntilAllDoneOrFailed(failures);
            if (!failures.isEmpty()) {
                storyContext.runFailedFor(failures.values().iterator().next());
            }
            storyContext.setOutput(outputStream.toString());
        }
    }

    private String storyPath() {
        return "web-" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date(System.currentTimeMillis()));
    }

}
