package org.jbehave.web.runner.waffle.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jbehave.scenario.steps.Stepdoc;

public class StepdocContext {

	public enum View { SIMPLE, METHODS }
	
	private List<Stepdoc> stepdocs = new ArrayList<Stepdoc>();
	private View view = View.SIMPLE;
	
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
	
	public List<View> getViews(){
		return Arrays.asList(View.values());
	}
	
	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}


}
