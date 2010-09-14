package org.jbehave.web.runner.wicket.pages;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class Template extends WebPage {

    /**
     * A MultiLineLabel that uses no markup in the body so it can be
     * rendered by SyntaxHighlighter, driven by the class attribute.
     */
    @SuppressWarnings("serial")
    public static class SyntaxHighlighterLabel extends MultiLineLabel {
    
        public SyntaxHighlighterLabel(String id, String label, String classAttribute) {
            super(id, label);
            add(new AttributeModifier("class", true, new Model<String>(classAttribute)));
        }
        
        @Override
        protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
            final CharSequence body = getDefaultModelObjectAsString();
            replaceComponentTagBody(markupStream, openTag, body);
        }
                
    }

    private String pageTitle;

    public Template() {
        this(new PageParameters());
    }

    public Template(final PageParameters pageParameters) {
        super(pageParameters);
    }

    public final String getPageTitle() {
        return pageTitle;
    }

    public final void setPageTitle(String title) {
        pageTitle = title;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Label("title", new PropertyModel<String>(this, "pageTitle")));
        explain();
    }

    protected void explain() {
    }

    protected Component pageComponent(String id) {
        return get(id);
    }
    
    

}
