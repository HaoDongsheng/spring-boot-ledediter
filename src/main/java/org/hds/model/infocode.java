package org.hds.model;

public class infocode {
    private Integer infoCodeSN;

    private Integer infoSN;

    private Integer groupid;
    
    private Integer pubid;

    private Integer codecrc;

    private String codecontext;

    private Integer packCount;
    
    private Integer packLength;
    
    public Integer getinfoCodeSN() {
        return infoCodeSN;
    }

    public void setinfoCodeSN(Integer infoCodeSN) {
        this.infoCodeSN = infoCodeSN;
    }

    public Integer getinfoSN() {
        return infoSN;
    }

    public void setinfoSN(Integer infoSN) {
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