package org.jbehave.web.runner.wicket.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.MapModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.BookmarkablePageRequestHandler;
import org.apache.wicket.request.handler.PageProvider;
import org.apache.wicket.util.resource.PackageResourceStream;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.velocity.markup.html.VelocityPanel;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.StepFinder;
import org.jbehave.core.steps.Stepdoc;
import org.jbehave.web.runner.context.StepdocContext;
import org.jbehave.web.runner.context.StepdocContext.SerializableStepdoc;
import org.jbehave.web.runner.context.StepdocContext.Sorting;
import org.jbehave.web.runner.context.StepdocContext.View;

import com.google.inject.Inject;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@SuppressWarnings("serial")
public class FindSteps extends Template {

    @Inject
    private Embedder embedder;

    private StepdocContext stepdocContext = new StepdocContext();

    private StepFinder stepFinder;
    private List<CandidateSteps> candidateSteps;

    public FindSteps() {
        setPageTitle("Find Steps");

        stepFinder = embedder.configuration().stepFinder();
        candidateSteps = embedder.stepsFactory().createCandidateSteps();
        stepdocContext.setAllStepdocs(stepFinder.stepdocs(candidateSteps));
        stepdocContext.setStepsInstances(stepFinder.stepsInstances(candidateSteps));

        StepsForm form = new StepsForm("stepsForm");
        add(form);

        final AutoCompleteTextField<String> field = new AutoCompleteTextField<String>("exploringStep", new Model<String>("")) {
            @Override
            protected Iterator<String> getChoices(String input) {
                if (Strings.isEmpty(input)) {
                    List<String> emptyList = Collections.emptyList();
                    return emptyList.iterator();
                }

                return matchingPatterns(input).iterator();
            }

            private List<String> matchingPatterns(String input) {
                List<String> patterns = new ArrayList<String>();
                for (SerializableStepdoc stepdoc : stepdocContext.getAllStepdocs()) {
                    String pattern = stepdoc.asString();
                    if ( pattern.matches(".*"+input+".*")){
                        patterns.add(pattern);                        
                    }
                }
                return patterns;
            }
        };
        final Label label = new Label("exploredStep", field.getDefaultModel());
        label.setOutputMarkupId(true);
        form.add(label);
        field.add(new AjaxFormSubmitBehavior(form, "change") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                BookmarkablePageRequestHandler bookmarkablePageRequestHandler = new BookmarkablePageRequestHandler(
                        new PageProvider(Home.class));
                RequestCycle requestCycle = RequestCycle.get();
                requestCycle.urlFor(bookmarkablePageRequestHandler);
                target.add(label);
            }

            @Override
            protected void onError(AjaxRequestTarget target) {
            }
        });
        form.add(field);
    }

    public final class StepsForm extends Form<ValueMap> {
        public StepsForm(final String id) {
            super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
            setMarkupId("stepsForm");
            add(new TextArea<String>("matchingStep").setType(String.class));
            add(new HtmlEscapingVelocityPanel("stepdocs", new MapModel<String, List<Stepdoc>>(
                    new HashMap<String, List<Stepdoc>>()), new PackageResourceStream(FindSteps.class, "stepdocs.vm"),
                    "brush: plain"));
            add(new HtmlEscapingVelocityPanel("stepsInstances", new MapModel<String, List<Stepdoc>>(
                    new HashMap<String, List<Stepdoc>>()), new PackageResourceStream(FindSteps.class,
                    "stepsInstances.vm"), "brush: java"));
            add(new DropDownChoice<View>("viewSelect", Arrays.asList(View.values())) {

                protected void onSelectionChanged(View newSelection) {
                    stepdocContext.setView(newSelection);
                    updatePanels();
                    setResponsePage(FindSteps.this);
                }

                protected boolean wantOnSelectionChangedNotifications() {
                    return true;
                }

            });
            add(new DropDownChoice<Sorting>("sortingSelect", Arrays.asList(Sorting.values())) {

                protected void onSelectionChanged(Sorting newSelection) {
                    stepdocContext.setSorting(newSelection);
                    switch (stepdocContext.getSorting()) {
                    case BY_POSITION:
                        run();
                        break;
                    case BY_PATTERN:
                        stepdocContext.sortStepdocs();
                        break;
                    }
                    updatePanels();
                    setResponsePage(FindSteps.this);
                }

                protected boolean wantOnSelectionChangedNotifications() {
                    return true;
                }

            });
            add(new Button("findButton"));
        }

        @Override
        public final void onSubmit() {
            String matchingStep = (String) getModelObject().get("matchingStep");
            stepdocContext.setMatchingStep(matchingStep);
            run();
            updatePanels();
        }

        private void updatePanels() {
            updateStepdocsPanel();
            updateStepsInstancesPanel();
        }

        private void updateStepdocsPanel() {
            VelocityPanel panel = (VelocityPanel) get("stepdocs");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("stepdocs", stepdocContext.getStepdocs());
            map.put("view", stepdocContext.getView());
            panel.setDefaultModel(new MapModel<String, Object>(map));
        }

        private void updateStepsInstancesPanel() {
            VelocityPanel panel = (VelocityPanel) get("stepsInstances");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("stepsInstances", stepdocContext.getStepsClasses());
            panel.setDefaultModel(new MapModel<String, Object>(map));
        }

    }

    public void run() {
        stepdocContext.clearStepdocs();
        String matchingStep = stepdocContext.getMatchingStep();
        if (isNotBlank(matchingStep)) {
            stepdocContext.addStepdocs(stepFinder.findMatching(matchingStep, candidateSteps));
        } else {
            stepdocContext.addAllStepdocs();
        }
    }

}
