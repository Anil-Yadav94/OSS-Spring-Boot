package oss.airtel.entity.copper;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Selt {

	@JsonIgnore
	private String faultstring;
	private String seltConclusion;
	private String linelength;
	
	public String getFaultstring() {
		return faultstring;
	}
	public void setFaultstring(String faultstring) {
		this.faultstring = faultstring;
	}
	public String getSeltConclusion() {
		return seltConclusion;
	}
	public void setSeltConclusion(String seltConclusion) {
		this.seltConclusion = seltConclusion;
	}
	public String getLinelength() {
		return linelength;
	}
	public void setLinelength(String linelength) {
		this.linelength = linelength;
	}
	
	
	
}
