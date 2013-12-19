package org.jbehave.web.runner.wicket.pages;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jbehave.core.io.ResourceLoader;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class ViewResource extends Template {

	@Inject
	private ResourceLoader loader;
	
	public ViewResource(final PageParameters parameters) {
        setPageTitle("View Resource");
        add(new NoMarkupMultiLineLabel("output", "", "brush: plain"));        
        showOutput(parameters);
	}
	
    private void showOutput(PageParameters parameters) {
		String uri = parameters.get("uri").toString();
		String content = resourceContent(uri);
        MultiLineLabel output = (MultiLineLabel) get("output");
        output.setDefaultModelObject(content);
    }

	private String resourceContent(String uri) {
		try {
			return loader.loadResourceAsText(uri);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}
