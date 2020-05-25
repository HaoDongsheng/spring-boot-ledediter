package org.hds.model;

public class infocode {
	private String infoCodeSN;

	private String infoSN;

	private Integer groupid;

	private Integer pubid;

	private Integer codecrc;

	private String codecontext;

	private String singleCodeContext;

	private Integer packCount;

	private Integer packLength;

	public String getInfoCodeSN() {
		return infoCodeSN;
	}

	public void setInfoCodeSN(String infoCodeSN) {
		this.infoCodeSN = infoCodeSN;
	}

	public String getInfoSN() {
		return infoSN;
	}

	public void setInfoSN(String infoSN) {
		this.infoSN = infoSN;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public Integer getPubid() {
		return pubid;
	}

	public void setPubid(Integer pubid) {
		this.pubid = pubid;
	}

	public Integer getCodecrc() {
		return codecrc;
	}

	public void setCodecrc(Integer codecrc) {
		this.codecrc = codecrc;
	}

	public String getCodecontext() {
		return codecontext;
	}

	public String getSingleCodeContext() {
		return singleCodeContext;
	}

	public void setSingleCodeContext(String singleCodeContext) {
		this.singleCodeContext = singleCodeContext;
	}

	public void setCodecontext(String codecontext) {
		this.codecontext = codecontext == null ? null : codecontext.trim();
	}

	public void setPackCount(Integer packCount) {
		this.packCount = packCount;
	}

	public Integer getPackCount() {
		return packCount;
	}

	public void setPackLength(Integer packLength) {
		this.packLength = packLength;
	}

	public Integer getPackLength() {
		return packLength;
	}
}