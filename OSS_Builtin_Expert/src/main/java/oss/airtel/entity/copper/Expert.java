package oss.airtel.entity.copper;

public class Expert {

	private String faultstring;
	private String cause;
	private String suggestion;
	private String actualRateDS;
	private String actualNoiseMarginDS;
	private String resynchronizationTimesLive;
	private String codeViolation;

	public String getFaultstring() {
		return faultstring;
	}
	public void setFaultstring(String faultstring) {
		this.faultstring = faultstring;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public String getActualRateDS() {
		return actualRateDS;
	}
	public void setActualRateDS(String actualRateDS) {
		this.actualRateDS = actualRateDS;
	}
	public String getActualNoiseMarginDS() {
		return actualNoiseMarginDS;
	}
	public void setActualNoiseMarginDS(String actualNoiseMarginDS) {
		this.actualNoiseMarginDS = actualNoiseMarginDS;
	}
	public String getResynchronizationTimesLive() {
		return resynchronizationTimesLive;
	}
	public void setResynchronizationTimesLive(String resynchronizationTimesLive) {
		this.resynchronizationTimesLive = resynchronizationTimesLive;
	}
	public String getCodeViolation() {
		return codeViolation;
	}
	public void setCodeViolation(String codeViolation) {
		this.codeViolation = codeViolation;
	}
	@Override
	public String toString() {
		return "Expert [actualRateDS=" + actualRateDS + ", actualNoiseMarginDS=" + actualNoiseMarginDS
				+ ", resynchronizationTimesLive=" + resynchronizationTimesLive + ", cause=" + cause + ", suggestion="
				+ suggestion + ", faultstring=" + faultstring + ", codeViolation=" + codeViolation + "]";
	}
	
	
	
	
}
