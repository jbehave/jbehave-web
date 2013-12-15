package org.jbehave.web.runner.wicket.pages;

import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.core.util.resource.PackageResourceStream;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.velocity.markup.html.VelocityPanel;

@SuppressWarnings("serial")
public class Template extends WebPage {

    /**
     * A MultiLineLabel that uses no markup in the body so it can be rendered by
     * SyntaxHighlighter, driven by the class attribute.
     */
    public static class NoMarkupMultiLineLabel extends MultiLineLabel {

        public NoMarkupMultiLineLabel(String id, String label, String classAttribute) {
            super(id, label);
            add(new AttributeModifier("class", new Model<String>(classAttribute)));
        }

        public NoMarkupMultiLineLabel(String id, IModel<String> model, String classAttribute) {
            super(id, model);
            add(new AttributeModifier("class", new Model<String>(classAttribute)));
        }

        @Override
        public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
            String body = getDefaultModelObjectAsString();
            replaceComponentTagBody(markupStream, openTag, body);
        }

    }

    /**
     * A VelocityPanel that escapes Html characters so it can be rendered by
     * SyntaxHighlighter, driven by the class attribute.
     */
    public static class HtmlEscapingVelocityPanel extends VelocityPanel {

        private final PackageResourceStream templateResource;

        @SuppressWarnings("rawtypes")
        public HtmlEscapingVelocityPanel(String id, IModel<? extends Map> model,
                PackageResourceStream packageResourceStream, String classAttribute) {
            super(id, model);
            this.templateResource = packageResourceStream;
            add(new AttributeModifier("class", new Model<String>(classAttribute)));
        }

        @Override
        protected PackageResourceStream getTemplateResource() {
            return templateResource;
        }

        @Override
        protected boolean escapeHtml() {
            return true;
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
