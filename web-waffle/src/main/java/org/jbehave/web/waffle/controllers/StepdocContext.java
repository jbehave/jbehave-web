package org.jbehave.web.waffle.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jbehave.scenario.steps.Stepdoc;

public class StepdocContext {

	private List<Stepdoc> stepdocs = new ArrayList<Stepdoc>();
	private boolean methodsShown = false;

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
	
	public boolean isMethodsShown() {
		return methodsShown;
	}

	public void setMethodsShown(boolean methodsShown) {
		this.methodsShown = methodsShown;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}


}
