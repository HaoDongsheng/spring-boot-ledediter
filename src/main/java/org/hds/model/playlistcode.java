package org.hds.model;

public class playlistcode {
	private String playlistCodeSN;

	private Integer groupid;

	private String containADID;

	private String lifeAct;

	private String lifeDie;

	private String playlistsn;

	private Integer pubid;

	private Integer playlistcrc;

	private String codecontext;

	private String singleCodeContext;

	private Integer packCount;

	private Integer packLength;

	public String getplaylistCodeSN() {
		return playlistCodeSN;
	}

	public void setplaylistCodeSN(String playlistCodeSN) {
		this.playlistCodeSN = playlistCodeSN;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getContainADID() {
		return containADID;
	}

	public void setContainADID(String containADID) {
		this.containADID = containADID;
	}

	public String getLifeAct() {
		return lifeAct;
	}

	public void setLifeAct(String lifeAct) {
		this.lifeAct = lifeAct;
	}

	public String getLifeDie() {
		return lifeDie;
	}

	public void setLifeDie(String lifeDie) {
		this.lifeDie = lifeDie;
	}

	public String getPlaylistsn() {
		return playlistsn;
	}

	public void setPlaylistsn(String playlistsn) {
		this.playlistsn = playlistsn;
	}

	public Integer getPubid() {
		return pubid;
	}

	public void setPubid(Integer pubid) {
		this.pubid = pubid;
	}

	public Integer getPlaylistcrc() {
		return playlistcrc;
	}

	public void setPlaylistcrc(Integer playlistcrc) {
		this.playlistcrc = playlistcrc;
	}

	public String getCodecontext() {
		return codecontext;
	}

	public void setCodecontext(String codecontext) {
		this.codecontext = codecontext == null ? null : codecontext.trim();
	}

	public String getSingleCodeContext() {
		return singleCodeContext;
	}

	public void setSingleCodeContext(String singleCodeContext) {
		this.singleCodeContext = singleCodeContext;
	}

	public Integer getPackCount() {
		return packCount;
	}

	public void setPackCount(Integer packCount) {
		this.packCount = packCount;
	}

	public Integer getPackLength() {
		return packLength;
	}

	public void setPackLength(Integer packLength) {
		this.packLength = packLength;
	}
}