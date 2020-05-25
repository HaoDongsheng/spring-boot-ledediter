package org.hds.model;

public class project {
	private String projectid;

	private String projectname;

	private Integer AutoGroupTo;

	private Integer IsOurModule;

	private String ConnectParameters;

	private Integer Total;

	private Integer DTU;

	private Integer LED;

	private Integer Updated;

	private String RecordingTime;

	private double UpdateRate;

	private String AdvertisementUpdateTime;

	private String TerminalUpdateTime;

	private String ParameterUpdateTime;

	private String CheckCode;

	private Integer DefaultStartlevel;

	private Integer startlevelControl;

	private String projectLimit;

	public String getProjectLimit() {
		return projectLimit;
	}

	public void setProjectLimit(String projectLimit) {
		this.projectLimit = projectLimit;
	}

	public Integer getAutoGroupTo() {
		return AutoGroupTo;
	}

	public void setAutoGroupTo(Integer autoGroupTo) {
		AutoGroupTo = autoGroupTo;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid == null ? null : projectid.trim();
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname == null ? null : projectname.trim();
	}

	public Integer getIsOurModule() {
		return IsOurModule;
	}

	public void setIsOurModule(Integer isOurModule) {
		IsOurModule = isOurModule;
	}

	public String getConnectParameters() {
		return ConnectParameters;
	}

	public void setConnectParameters(String connectParameters) {
		ConnectParameters = connectParameters;
	}

	public Integer getTotal() {
		return Total;
	}

	public void setTotal(Integer total) {
		Total = total;
	}

	public Integer getDTU() {
		return DTU;
	}

	public void setDTU(Integer dTU) {
		DTU = dTU;
	}

	public Integer getLED() {
		return LED;
	}

	public void setLED(Integer lED) {
		LED = lED;
	}

	public Integer getUpdated() {
		return Updated;
	}

	public void setUpdated(Integer updated) {
		Updated = updated;
	}

	public String getRecordingTime() {
		return RecordingTime;
	}

	public void setRecordingTime(String recordingTime) {
		RecordingTime = recordingTime;
	}

	public double getUpdateRate() {
		return UpdateRate;
	}

	public void setUpdateRate(double updateRate) {
		UpdateRate = updateRate;
	}

	public String getAdvertisementUpdateTime() {
		return AdvertisementUpdateTime;
	}

	public void setAdvertisementUpdateTime(String advertisementUpdateTime) {
		AdvertisementUpdateTime = advertisementUpdateTime;
	}

	public String getTerminalUpdateTime() {
		return TerminalUpdateTime;
	}

	public void setTerminalUpdateTime(String terminalUpdateTime) {
		TerminalUpdateTime = terminalUpdateTime;
	}

	public String getParameterUpdateTime() {
		return ParameterUpdateTime;
	}

	public void setParameterUpdateTime(String parameterUpdateTime) {
		ParameterUpdateTime = parameterUpdateTime;
	}

	public String getCheckCode() {
		return CheckCode;
	}

	public void setCheckCode(String checkCode) {
		CheckCode = checkCode;
	}

	public Integer getDefaultStartlevel() {
		return DefaultStartlevel;
	}

	public void setDefaultStartlevel(Integer defaultStartlevel) {
		DefaultStartlevel = defaultStartlevel;
	}

	public Integer getStartlevelControl() {
		return startlevelControl;
	}

	public void setStartlevelControl(Integer startlevelControl) {
		this.startlevelControl = startlevelControl;
	}

}