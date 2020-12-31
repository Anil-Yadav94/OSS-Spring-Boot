package oss.airtel.entity.copper;

public class Builtin {
	
	private String faultstring;
	private String testConclusion;
	private String linelength;
	private String resynchronizationTimes;
	private String cpeType;
	
	public String getCpeType() {
		return cpeType;
	}
	public void setCpeType(String cpeType) {
		this.cpeType = cpeType;
	}
	public String getFaultstring() {
		return faultstring;
	}
	public void setFaultstring(String faultstring) {
		this.faultstring = faultstring;
	}
	public String getTestConclusion() {
		return testConclusion;
	}
	public void setTestConclusion(String testConclusion) {
		this.testConclusion = testConclusion;
	}
	public String getLinelength() {
		return linelength;
	}
	public void setLinelength(String linelength) {
		this.linelength = linelength;
	}
	public String getResynchronizationTimes() {
		return resynchronizationTimes;
	}
	public void setResynchronizationTimes(String resynchronizationTimes) {
		this.resynchronizationTimes = resynchronizationTimes;
	}
	@Override
	public String toString() {
		return "Builtin [faultstring=" + faultstring + ", testConclusion=" + testConclusion + ", linelength="
				+ linelength + ", resynchronizationTimes=" + resynchronizationTimes + ", cpeType=" + cpeType + "]";
	}
	
	
}
