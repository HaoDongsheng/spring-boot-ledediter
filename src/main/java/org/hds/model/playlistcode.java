package org.hds.model;

public class playlistcode {
    private Integer playlistCodeSN;

    private Integer groupid;    

	private String containADID;

	private String lifeAct;   

	private String lifeDie;    

	private Integer playlistsn;

    private Integer pubid;

    private Integer playlistcrc;

    private String codecontext;
    
    private Integer packCount;
    
    private Integer packLength;

    public Integer getplaylistCodeSN() {
        return playlistCodeSN;
    }

    public void setplaylistCodeSN(Integer playlistCodeSN) {
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

    public Integer getPlaylistsn() {
        return playlistsn;
    }

    public void setPlaylistsn(Integer playlistsn) {
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