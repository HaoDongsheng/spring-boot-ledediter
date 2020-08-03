package org.hds.model;

public class group {
	private Integer groupid;

	private String groupname;

	private Integer maxPackLength;

	private Integer batchCount;

	private String Para2_User;

	private String Para3_TimeLight;

	private String Para1_Basic;

	private Integer screenwidth;

	private Integer screenheight;

	private String projectid;

	private Integer pubid;

	private Integer plpubid;

	private Integer delindex;

	private Double UpdateRate;

	private Integer ptMode;

	private String displayMode;

	private String PlayList;

	private String PlayAdList;

	private String TotalAdList;

	public String getPlayList() {
		return PlayList;
	}

	public void setPlayList(String playList) {
		PlayList = playList;
	}

	public String getPlayAdList() {
		return PlayAdList;
	}

	public void setPlayAdList(String playAdList) {
		PlayAdList = playAdList;
	}

	public String getTotalAdList() {
		return TotalAdList;
	}

	public void setTotalAdList(String totalAdList) {
		TotalAdList = totalAdList;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname == null ? null : groupname.trim();
	}

	public Integer getMaxPackLength() {
		return maxPackLength;
	}

	public void setMaxPackLength(Integer maxPackLength) {
		this.maxPackLength = maxPackLength;
	}

	public Integer getBatchCount() {
		return batchCount;
	}

	public void setBatchCount(Integer batchCount) {
		this.batchCount = batchCount;
	}

	public String getPara2_User() {
		return Para2_User;
	}

	public void setPara2_User(String Para2_User) {
		this.Para2_User = Para2_User;
	}

	public String getPara3_TimeLight() {
		return Para3_TimeLight;
	}

	public void setPara3_TimeLight(String Para3_TimeLight) {
		this.Para3_TimeLight = Para3_TimeLight;
	}

	public String getPara1_Basic() {
		return Para1_Basic;
	}

	public void setPara1_Basic(String Para1_Basic) {
		this.Para1_Basic = Para1_Basic;
	}

	public Integer getscreenwidth() {
		return screenwidth;
	}

	public void setscreenwidth(Integer screenwidth) {
		this.screenwidth = screenwidth;
	}

	public Integer getscreenheight() {
		return screenheight;
	}

	public void setscreenheight(Integer screenheight) {
		this.screenheight = screenheight;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public Integer getplpubid() {
		return plpubid;
	}

	public void setplpubid(Integer plpubid) {
		this.plpubid = plpubid;
	}

	public Integer getPubid() {
		return pubid;
	}

	public void setPubid(Integer pubid) {
		this.pubid = pubid;
	}

	public Integer getDelindex() {
		return delindex;
	}

	public void setDelindex(Integer delindex) {
		this.delindex = delindex;
	}

	public Double getUpdateRate() {
		return UpdateRate;
	}

	public void setUpdateRate(Double updateRate) {
		UpdateRate = updateRate;
	}

	public Integer getPtMode() {
		return ptMode;
	}

	public void setPtMode(Integer ptMode) {
		this.ptMode = ptMode;
	}

	public String getDisplayMode() {
		return displayMode;
	}

	public void setDisplayMode(String displayMode) {
		this.displayMode = displayMode;
	}

}