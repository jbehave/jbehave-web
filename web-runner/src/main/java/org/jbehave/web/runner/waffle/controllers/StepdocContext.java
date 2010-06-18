package org.jbehave.web.runner.waffle.controllers;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jbehave.core.steps.Stepdoc;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class StepdocContext {

	public enum View { PATTERNS, WITH_METHODS }
	
	private List<Stepdoc> stepdocs = new ArrayList<Stepdoc>();
	private View view = View.PATTERNS;
	private String matchingStep = "";
	private List<Object> stepsInstances;
	
	public StepdocContext() {	
	}
	
	public List<Stepdoc> getStepdocs() {
		return stepdocs;
	}

	public void addStepdocs(List<Stepdoc> stepdocs){
		this.stepdocs.addAll(stepdocs);
	}

	public void clearStepdocs(){
		this.stepdocs.clear();
	}

	public void addStepsInstances(List<Object> stepsInstances) {
		this.stepsInstances = stepsInstances;
	}	

	public List<Object> getStepsInstances() {
		return stepsInstances;
	}

	public List<View> getViews(){
		return asList(View.values());
	}
	
	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
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
