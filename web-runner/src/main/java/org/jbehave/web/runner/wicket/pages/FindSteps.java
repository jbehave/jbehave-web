package org.jbehave.web.runner.wicket.pages;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.MapModel;
import org.apache.wicket.util.resource.IStringResourceStream;
import org.apache.wicket.util.resource.PackageResourceStream;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.velocity.markup.html.VelocityPanel;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.StepFinder;
import org.jbehave.core.steps.Stepdoc;
import org.jbehave.web.runner.context.StepdocContext;

import com.google.inject.Inject;

public class FindSteps extends Template {

    @Inject
    private Configuration configuration;
    @Inject
    private List<CandidateSteps> steps;

    private StepdocContext stepdocContext = new StepdocContext();
    private StepFinder stepFinder;
    private List<Stepdoc> stepdocs;

    public FindSteps() {
        this.stepFinder = configuration.stepFinder();
        this.stepdocs = stepFinder.stepdocs(this.steps);
        setPageTitle("Find Steps");
        add(new StepsForm("stepsForm"));
        this.stepdocContext.addStepsInstances(stepFinder.stepsInstances(this.steps));
    }

    @SuppressWarnings("serial")
    public final class StepsForm extends Form<ValueMap> {
        public StepsForm(final String id) {
            super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
            setMarkupId("stepsForm");
            add(new TextArea<String>("matchingStep").setType(String.class));
            add(new VelocityPanel("stepdocs", new MapModel<String, List<Stepdoc>>(new HashMap<String, List<Stepdoc>>())) {
                @Override
                protected IStringResourceStream getTemplateResource() {
                    return new PackageResourceStream(FindSteps.class, "stepdocs.vm");
                }
            
                @Override
                protected boolean parseGeneratedMarkup() {
                    return true;
                }
            });
            add(new VelocityPanel("stepsInstances", new MapModel<String, List<Object>>(new HashMap<String, List<Object>>())) {
                @Override
                protected IStringResourceStream getTemplateResource() {
                    return new PackageResourceStream(FindSteps.class, "stepsInstances.vm");
                }
            
                @Override
                protected boolean parseGeneratedMarkup() {
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
            VelocityPanel stepdocs = (VelocityPanel)get("stepdocs");
            stepdocs.setDefaultModel(listModel("stepdocs", stepdocContext.getStepdocs()));
            VelocityPanel stepsInstances = (VelocityPanel)get("stepsInstances");
            stepsInstances.setDefaultModel(listModel("stepsInstances", stepdocContext.getStepsInstances()));
        }
    }

    private <T> IModel<?> listModel(String id, List<T> list) {
        Map<String, List<T>> map = new HashMap<String, List<T>>();
        map.put(id, list);
        return new MapModel<String, List<T>>(map);
    }

    public void run() {
        stepdocContext.clearStepdocs();
        String matchingStep = stepdocContext.getMatchingStep();
        if (isNotBlank(matchingStep)) {
            stepdocContext.addStepdocs(stepFinder.findMatching(matchingStep, steps));
        } else {
            stepdocContext.addStepdocs(stepdocs);
        }
    }

}
