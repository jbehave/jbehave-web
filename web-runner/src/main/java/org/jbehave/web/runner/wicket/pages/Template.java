package org.jbehave.web.runner.wicket.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

public class Template extends WebPage {

    private String pageTitle;

    public Template() {
        this(new PageParameters());
    }

    public Template(final PageParameters pageParameters) {
        super(pageParameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Label("title", new PropertyModel<String>(this, "pageTitle")));
        // add(new
        // Navigation(Strings.afterLast(getClass().getPackage().getName(), '.'),
        // this));
        explain();
    }

    protected void explain() {
    }

    public final String getPageTitle() {
        return pageTitle;
    }

    public final void setPageTitle(String title) {
        pageTitle = title;
    }
}
