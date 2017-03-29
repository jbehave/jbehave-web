package org.jbehave.web.runner.context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.plexus.util.StringUtils;
import org.jbehave.core.steps.StepType;
import org.jbehave.core.steps.Stepdoc;

import static java.util.Arrays.asList;

@SuppressWarnings("serial")
public class StepdocContext implements Serializable {

    public enum View {
        PATTERNS, WITH_METHODS
    }

    public enum Sorting {
        BY_POSITION, BY_PATTERN
    }

    private List<SerializableStepdoc> allStepdocs = new ArrayList<SerializableStepdoc>();
    private List<SerializableStepdoc> stepdocs = new ArrayList<SerializableStepdoc>();
    private List<Class<?>> stepsClasses = new ArrayList<Class<?>>();
    private View view = View.PATTERNS;
    private Sorting sorting = Sorting.BY_POSITION;
    private String matchingStep = "";

    public StepdocContext() {
    }

    public List<SerializableStepdoc> getAllStepdocs() {
        return allStepdocs;
    }

    public void setAllStepdocs(List<Stepdoc> stepdocs) {
        this.allStepdocs = serializableStepdocs(stepdocs);
    }

    public List<SerializableStepdoc> getStepdocs() {
        return stepdocs;
    }

    public List<SerializableStepdoc> matchingStepdocs(String input) {
        List<SerializableStepdoc> matching = new ArrayList<SerializableStepdoc>();
        if ( StringUtils.isEmpty(input) ){
            return matching;
        }
        for (SerializableStepdoc stepdoc : allStepdocs ) {
            if ( stepdoc.asString().matches(".*"+input+".*")){
                matching.add(stepdoc);                        
            }
        }
        return matching;
    }

    public void addAllStepdocs() {
        this.stepdocs.addAll(allStepdocs);
    }

    public void addStepdocs(List<Stepdoc> stepdocs) {
        this.stepdocs.addAll(serializableStepdocs(stepdocs));
    }

    public void clearStepdocs() {
        this.stepdocs.clear();
    }

    public void sortStepdocs() {
        List<SerializableStepdoc> sorted = new ArrayList<SerializableStepdoc>(stepdocs);
        Collections.sort(sorted);
        this.stepdocs = sorted;
    }

    public void setStepsInstances(List<Object> stepsInstances) {
        this.stepsClasses = stepsClasses(stepsInstances);
    }

    public List<Class<?>> getStepsClasses() {
        return stepsClasses;
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
    
    private List<Class<?>> stepsClasses(List<Object> stepsInstances) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (Object instance : stepsInstances) {
            classes.add(instance.getClass());
        }
        return classes;
    }

    private List<SerializableStepdoc> serializableStepdocs(List<Stepdoc> stepdocs) {
        List<SerializableStepdoc> serializables = new ArrayList<SerializableStepdoc>();
        for (Stepdoc stepdoc : stepdocs) {
            serializables.add(new SerializableStepdoc(stepdoc));
        }
        return serializables;
    }

    public static class SerializableStepdoc implements Serializable, Comparable<SerializableStepdoc> {

        private StepType stepType;
        private String startingWord;
        private String pattern;
        private String methodSignature;
        private Class<?> stepsClass;

        public SerializableStepdoc(Stepdoc stepdoc) {
            this.stepType = stepdoc.getStepType();
            this.startingWord = stepdoc.getStartingWord();
            this.pattern = stepdoc.getPattern();
            this.methodSignature = stepdoc.getMethodSignature();
            this.stepsClass = stepdoc.getStepsInstance().getClass();
        }

        public StepType getStepType() {
            return stepType;
        }

        public String getStartingWord() {
            return startingWord;
        }

        public String getPattern() {
            return pattern;
        }

        public Class<?> getStepsClass() {
            return stepsClass;
        }

        public String getMethodSignature() {
            return methodSignature;
        }

        public String asString(){
            return startingWord + " " + pattern;
        }
        
        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this).toString();
        }

        public int compareTo(SerializableStepdoc that) {
            return CompareToBuilder.reflectionCompare(this, that);
        }

    }

}
