package org.openmrs.module.resourcesapp.model;

import java.util.Date;

public class TriageId {
	private Date visitdate;
	private Integer triageId;
	private String outcome;
	
	public Date getVisitDate(){
		return visitdate;
	}
	public void setDate(Date visitdate){
		this.visitdate = visitdate;
	}
	
	public Integer getTriageId(){
		return triageId;
	}
	public void setTriageId(Integer triageId){
		this.triageId = triageId;
	}
	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

}
