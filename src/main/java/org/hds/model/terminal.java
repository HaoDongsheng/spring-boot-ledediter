package org.hds.model;

import java.util.Date;

public class terminal {
	private String dtukey;

	private String name;

	private Integer groupid;

	private String ledId;

	private String simno;

	private String mcu;

	private String taxitype;

	private String comanyname;

	private String driver;

	private String tel;

	private String papertype;

	private String paperno;

	private String remark;

	private Integer delindex;

	private Integer calibrationmode;

	private String mutiserverflag;

	private String ledLastresponsetime;

	private String ledLastsendtime;

	private Long sendbytescount;

	private Long receivebytescount;

	private String writedate;

	private String ledfactoryflag;

	private String ledversion;

	private String stateledversion;

	private Long ledparametersflag;

	private String ledstatestring;

	private String registerdate;

	private String levelevaluate;

	private Long carpoolflag;

	private Integer inspect;

	private Integer ledupgrading;

	private Integer ledupgradeindex;

	private String carnumber;

	private String carnumberset;

	private String projectid;

	private Integer starlevel;

	private Integer starlevelset;

	private Double updaterate;

	private Integer para1Id;

	private Integer para2Id;

	private Integer para3Id;

	private Integer para6IdSet;

	private Integer para6Id;

	private String licenseplatenumber;

	private String positiontime;

	private Double positionlongitude;

	private Double positionlatitude;

	private String positionaddress;

	private Date dtuOnlinetime;

	private Date dtuOfflinetime;

	private Date dtuResponsetime;

	private Date ledOnlinetime;

	private Date ledOfflinetime;

	private Date ledResponsetime;

	private String adidlist;

	private String playlist;

	public String getDtukey() {
		return dtukey;
	}

	public void setDtukey(String dtukey) {
		this.dtukey = dtukey == null ? null : dtukey.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getLedId() {
		return ledId;
	}

	public void setLedId(String ledId) {
		this.ledId = ledId == null ? null : ledId.trim();
	}

	public String getSimno() {
		return simno;
	}

	public void setSimno(String simno) {
		this.simno = simno == null ? null : simno.trim();
	}

	public String getMcu() {
		return mcu;
	}

	public void setMcu(String mcu) {
		this.mcu = mcu == null ? null : mcu.trim();
	}

	public String getTaxitype() {
		return taxitype;
	}

	public void setTaxitype(String taxitype) {
		this.taxitype = taxitype == null ? null : taxitype.trim();
	}

	public String getComanyname() {
		return comanyname;
	}

	public void setComanyname(String comanyname) {
		this.comanyname = comanyname == null ? null : comanyname.trim();
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver == null ? null : driver.trim();
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel == null ? null : tel.trim();
	}

	public String getPapertype() {
		return papertype;
	}

	public void setPapertype(String papertype) {
		this.papertype = papertype == null ? null : papertype.trim();
	}

	public String getPaperno() {
		return paperno;
	}

	public void setPaperno(String paperno) {
		this.paperno = paperno == null ? null : paperno.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Integer getDelindex() {
		return delindex;
	}

	public void setDelindex(Integer delindex) {
		this.delindex = delindex;
	}

	public Integer getCalibrationmode() {
		return calibrationmode;
	}

	public void setCalibrationmode(Integer calibrationmode) {
		this.calibrationmode = calibrationmode;
	}

	public String getMutiserverflag() {
		return mutiserverflag;
	}

	public void setMutiserverflag(String mutiserverflag) {
		this.mutiserverflag = mutiserverflag == null ? null : mutiserverflag.trim();
	}

	public String getLedLastresponsetime() {
		return ledLastresponsetime;
	}

	public void setLedLastresponsetime(String ledLastresponsetime) {
		this.ledLastresponsetime = ledLastresponsetime == null ? null : ledLastresponsetime.trim();
	}

	public String getLedLastsendtime() {
		return ledLastsendtime;
	}

	public void setLedLastsendtime(String ledLastsendtime) {
		this.ledLastsendtime = ledLastsendtime == null ? null : ledLastsendtime.trim();
	}

	public Long getSendbytescount() {
		return sendbytescount;
	}

	public void setSendbytescount(Long sendbytescount) {
		this.sendbytescount = sendbytescount;
	}

	public Long getReceivebytescount() {
		return receivebytescount;
	}

	public void setReceivebytescount(Long receivebytescount) {
		this.receivebytescount = receivebytescount;
	}

	public String getWritedate() {
		return writedate;
	}

	public void setWritedate(String writedate) {
		this.writedate = writedate == null ? null : writedate.trim();
	}

	public String getLedfactoryflag() {
		return ledfactoryflag;
	}

	public void setLedfactoryflag(String ledfactoryflag) {
		this.ledfactoryflag = ledfactoryflag == null ? null : ledfactoryflag.trim();
	}

	public String getLedversion() {
		return ledversion;
	}

	public void setLedversion(String ledversion) {
		this.ledversion = ledversion == null ? null : ledversion.trim();
	}

	public String getStateledversion() {
		return stateledversion;
	}

	public void setStateledversion(String stateledversion) {
		this.stateledversion = stateledversion == null ? null : stateledversion.trim();
	}

	public Long getLedparametersflag() {
		return ledparametersflag;
	}

	public void setLedparametersflag(Long ledparametersflag) {
		this.ledparametersflag = ledparametersflag;
	}

	public String getLedstatestring() {
		return ledstatestring;
	}

	public void setLedstatestring(String ledstatestring) {
		this.ledstatestring = ledstatestring == null ? null : ledstatestring.trim();
	}

	public String getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(String registerdate) {
		this.registerdate = registerdate == null ? null : registerdate.trim();
	}

	public String getLevelevaluate() {
		return levelevaluate;
	}

	public void setLevelevaluate(String levelevaluate) {
		this.levelevaluate = levelevaluate == null ? null : levelevaluate.trim();
	}

	public Long getCarpoolflag() {
		return carpoolflag;
	}

	public void setCarpoolflag(Long carpoolflag) {
		this.carpoolflag = carpoolflag;
	}

	public Integer getInspect() {
		return inspect;
	}

	public void setInspect(Integer inspect) {
		this.inspect = inspect;
	}

	public Integer getLedupgrading() {
		return ledupgrading;
	}

	public void setLedupgrading(Integer ledupgrading) {
		this.ledupgrading = ledupgrading;
	}

	public Integer getLedupgradeindex() {
		return ledupgradeindex;
	}

	public void setLedupgradeindex(Integer ledupgradeindex) {
		this.ledupgradeindex = ledupgradeindex;
	}

	public String getCarnumber() {
		return carnumber;
	}

	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber == null ? null : carnumber.trim();
	}

	public String getCarnumberset() {
		return carnumberset;
	}

	public void setCarnumberset(String carnumberset) {
		this.carnumberset = carnumberset == null ? null : carnumberset.trim();
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid == null ? null : projectid.trim();
	}

	public Integer getStarlevel() {
		return starlevel;
	}

	public void setStarlevel(Integer starlevel) {
		this.starlevel = starlevel;
	}

	public Integer getStarlevelset() {
		return starlevelset;
	}

	public void setStarlevelset(Integer starlevelset) {
		this.starlevelset = starlevelset;
	}

	public Double getUpdaterate() {
		return updaterate;
	}

	public void setUpdaterate(Double updaterate) {
		this.updaterate = updaterate;
	}

	public Integer getPara1Id() {
		return para1Id;
	}

	public void setPara1Id(Integer para1Id) {
		this.para1Id = para1Id;
	}

	public Integer getPara2Id() {
		return para2Id;
	}

	public void setPara2Id(Integer para2Id) {
		this.para2Id = para2Id;
	}

	public Integer getPara3Id() {
		return para3Id;
	}

	public void setPara3Id(Integer para3Id) {
		this.para3Id = para3Id;
	}

	public Integer getPara6IdSet() {
		return para6IdSet;
	}

	public void setPara6IdSet(Integer para6IdSet) {
		this.para6IdSet = para6IdSet;
	}

	public Integer getPara6Id() {
		return para6Id;
	}

	public void setPara6Id(Integer para6Id) {
		this.para6Id = para6Id;
	}

	public String getLicenseplatenumber() {
		return licenseplatenumber;
	}

	public void setLicenseplatenumber(String licenseplatenumber) {
		this.licenseplatenumber = licenseplatenumber == null ? null : licenseplatenumber.trim();
	}

	public String getPositiontime() {
		return positiontime;
	}

	public void setPositiontime(String positiontime) {
		this.positiontime = positiontime == null ? null : positiontime.trim();
	}

	public Double getPositionlongitude() {
		return positionlongitude;
	}

	public void setPositionlongitude(Double positionlongitude) {
		this.positionlongitude = positionlongitude;
	}

	public Double getPositionlatitude() {
		return positionlatitude;
	}

	public void setPositionlatitude(Double positionlatitude) {
		this.positionlatitude = positionlatitude;
	}

	public String getPositionaddress() {
		return positionaddress;
	}

	public void setPositionaddress(String positionaddress) {
		this.positionaddress = positionaddress == null ? null : positionaddress.trim();
	}

	public Date getDtuOnlinetime() {
		return dtuOnlinetime;
	}

	public void setDtuOnlinetime(Date dtuOnlinetime) {
		this.dtuOnlinetime = dtuOnlinetime;
	}

	public Date getDtuOfflinetime() {
		return dtuOfflinetime;
	}

	public void setDtuOfflinetime(Date dtuOfflinetime) {
		this.dtuOfflinetime = dtuOfflinetime;
	}

	public Date getDtuResponsetime() {
		return dtuResponsetime;
	}

	public void setDtuResponsetime(Date dtuResponsetime) {
		this.dtuResponsetime = dtuResponsetime;
	}

	public Date getLedOnlinetime() {
		return ledOnlinetime;
	}

	public void setLedOnlinetime(Date ledOnlinetime) {
		this.ledOnlinetime = ledOnlinetime;
	}

	public Date getLedOfflinetime() {
		return ledOfflinetime;
	}

	public void setLedOfflinetime(Date ledOfflinetime) {
		this.ledOfflinetime = ledOfflinetime;
	}

	public Date getLedResponsetime() {
		return ledResponsetime;
	}

	public void setLedResponsetime(Date ledResponsetime) {
		this.ledResponsetime = ledResponsetime;
	}

	public String getAdidlist() {
		return adidlist;
	}

	public void setAdidlist(String adidlist) {
		this.adidlist = adidlist == null ? null : adidlist.trim();
	}

	public String getPlaylist() {
		return playlist;
	}

	public void setPlaylist(String playlist) {
		this.playlist = playlist == null ? null : playlist.trim();
	}
}