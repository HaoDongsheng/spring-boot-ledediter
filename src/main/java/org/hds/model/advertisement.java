package org.hds.model;

public class advertisement {
	private String infoSN;

	private String advname;

	private Integer groupid;

	private String lifeAct;

	private String lifeDie;

	private String advtype;

	private Integer infoState;

	private Integer creater;

	private String createDate;

	private Integer auditor;

	private String auditDate;

	private Integer publisher;

	private String publishDate;

	private Integer deleter;

	private String deleteDate;

	private String playmode;

	private Integer pubid;

	private Integer playTimelength;

	private Integer delindex;

	private String backgroundstyle;

	private String remarks;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getinfoSN() {
		return infoSN;
	}

	public void setinfoSN(String infoSN) {
		this.infoSN = infoSN;
	}

	public String getAdvname() {
		return advname;
	}

	public void setAdvname(String advname) {
		this.advname = advname == null ? null : advname.trim();
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getlifeAct() {
		return lifeAct;
	}

	public void setlifeAct(String lifeAct) {
		this.lifeAct = lifeAct == null ? null : lifeAct.trim();
	}

	public String getlifeDie() {
		return lifeDie;
	}

	public void setlifeDie(String lifeDie) {
		this.lifeDie = lifeDie == null ? null : lifeDie.trim();
	}

	public String getAdvtype() {
		return advtype;
	}

	public void setAdvtype(String advtype) {
		this.advtype = advtype == null ? null : advtype.trim();
	}

	public Integer getinfoState() {
		return infoState;
	}

	public void setinfoState(Integer infoState) {
		this.infoState = infoState;
	}

	public Integer getcreater() {
		return creater;
	}

	public void setcreater(Integer creater) {
		this.creater = creater;
	}

	public Integer getauditor() {
		return auditor;
	}

	public void setauditor(Integer auditor) {
		this.auditor = auditor;
	}

	public Integer getpublisher() {
		return publisher;
	}

	public void setpublisher(Integer publisher) {
		this.publisher = publisher;
	}

	public Integer getdeleter() {
		return deleter;
	}

	public void setdeleteid(Integer deleter) {
		this.deleter = deleter;
	}

	public String getdeleteDate() {
		return deleteDate;
	}

	public void setdeleteDate(String deleteDate) {
		this.deleteDate = deleteDate == null ? null : deleteDate.trim();
	}

	public String getpublishDate() {
		return publishDate;
	}

	public void setpublishDate(String publishDate) {
		this.publishDate = publishDate == null ? null : publishDate.trim();
	}

	public String getcreateDate() {
		return createDate;
	}

	public void setcreateDate(String createDate) {
		this.createDate = createDate == null ? null : createDate.trim();
	}

	public String getauditDate() {
		return auditDate;
	}

	public void setauditDate(String auditDate) {
		this.auditDate = auditDate == null ? null : auditDate.trim();
	}

	public String getPlaymode() {
		return playmode;
	}

	public void setPlaymode(String playmode) {
		this.playmode = playmode == null ? null : playmode.trim();
	}

	public Integer getPubid() {
		return pubid;
	}

	public void setPubid(Integer pubid) {
		this.pubid = pubid;
	}

	public Integer getplayTimelength() {
		return playTimelength;
	}

	public void setplayTimelength(Integer playTimelength) {
		this.playTimelength = playTimelength;
	}

	public Integer getDelindex() {
		return delindex;
	}

	public void setDelindex(Integer delindex) {
		this.delindex = delindex;
	}

	public String getBackgroundstyle() {
		return backgroundstyle;
	}

	public void setBackgroundstyle(String backgroundstyle) {
		this.backgroundstyle = backgroundstyle == null ? null : backgroundstyle.trim();
	}
}