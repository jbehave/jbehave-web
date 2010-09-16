package org.jbehave.web.runner.context;

import static java.util.Arrays.asList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jbehave.core.steps.Stepdoc;

@SuppressWarnings("serial")
public class StepdocContext implements Serializable {

    public enum View {
        PATTERNS, WITH_METHODS
    }

    public enum Sorting {
        BY_POSITION, BY_PATTERN
    }

    private List<Stepdoc> allStepdocs = new ArrayList<Stepdoc>();
    private List<Stepdoc> stepdocs = new ArrayList<Stepdoc>();
    private View view = View.PATTERNS;
    private Sorting sorting = Sorting.BY_POSITION;
    private String matchingStep = "";
    private List<Object> stepsInstances;

    public StepdocContext() {
    }

    public List<Stepdoc> getAllStepdocs() {
        return allStepdocs;
    }

    public void setAllStepdocs(List<Stepdoc> stepdocs) {
        this.allStepdocs = stepdocs;
    }

    public List<Stepdoc> getStepdocs() {
        return stepdocs;
    }

    public void setStepdocs(List<Stepdoc> stepdocs) {
        this.stepdocs = stepdocs;
    }

    public void addStepdocs(List<Stepdoc> stepdocs) {
        this.stepdocs.addAll(stepdocs);
    }

    public void clearStepdocs() {
        this.stepdocs.clear();
    }

    public void addStepsInstances(List<Object> stepsInstances) {
        this.stepsInstances = stepsInstances;
    }

    public List<Object> getStepsInstances() {
        return stepsInstances;
    }

    public List<Class<?>> getStepsClasses() {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (Object instance : stepsInstances) {
            classes.add(instance.getClass());
        }
        return classes;
    }

    public List<View> getViews() {
        return asList(View.values());
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public List<Sorting> getSortings() {
        return asList(Sorting.values());
    }

    public Sorting getSorting() {
        return sorting;
    }

    public void setSorting(Sorting sorting) {
        this.sorting = sorting;
    }

    public String getMatchingStep() {
        return matchingStep;
    }

    public void setMatchingStep(String matchingStep) {
        this.matchingStep = matchingStep;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


}
