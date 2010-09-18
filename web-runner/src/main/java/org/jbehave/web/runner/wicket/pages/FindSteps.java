package org.jbehave.web.runner.wicket.pages;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.util.MapModel;
import org.apache.wicket.util.resource.PackageResourceStream;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.velocity.markup.html.VelocityPanel;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.Stepdoc;
import org.jbehave.web.runner.context.StepdocContext;
import org.jbehave.web.runner.context.StepdocContext.Sorting;
import org.jbehave.web.runner.context.StepdocContext.View;

import com.google.inject.Inject;

public class FindSteps extends Template {

    @Inject
    private Configuration configuration;
    @Inject
    private List<CandidateSteps> steps;

    private StepdocContext stepdocContext = new StepdocContext();

    public FindSteps() {
        setPageTitle("Find Steps");
        add(new StepsForm("stepsForm"));
        stepdocContext.setAllStepdocs(configuration.stepFinder().stepdocs(this.steps));
        stepdocContext.setStepsInstances(configuration.stepFinder().stepsInstances(this.steps));
    }

    @SuppressWarnings("serial")
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

                @Override
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

                @Override
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
            stepdocContext.addStepdocs(configuration.stepFinder().findMatching(matchingStep, steps));
        } else {
            stepdocContext.addAllStepdocs();
        }
    }

}
