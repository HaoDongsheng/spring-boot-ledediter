package org.hds.model;

public class playlist {
    private Integer playlistSN;

    private Integer groupid;
    
    private String pubid;

    private String playlistname;

    private Integer playlistlevel;

    private String playlistlifeact;

    private String playlistlifedie;
    
    private Integer creater;
    
    private String createDate;        
    
    private Integer publisher;
    
    private String publishDate;
    
    private Integer deleter;
    
    private String deleteDate;

    private Integer scheduletype;

    private Integer delindex;

    private String timequantum;

    private String programlist;
    
    private String mutiProgramlist;

    public Integer getplaylistSN() {
        return playlistSN;
    }

    public void setId(Integer playlistSN) {
        this.playlistSN = playlistSN;
    }

    public String getPubid() {
        return pubid;
    }

    public void setPubid(String pubid) {
        this.pubid = pubid;
    }
    
    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getPlaylistname() {
        return playlistname;
    }

    public void setPlaylistname(String playlistname) {
        this.playlistname = playlistname == null ? null : playlistname.trim();
    }

    public Integer getPlaylistlevel() {
        return playlistlevel;
    }

    public void setPlaylistlevel(Integer playlistlevel) {
        this.playlistlevel = playlistlevel;
    }

    public String getPlaylistlifeact() {
        return playlistlifeact;
    }

    public void setPlaylistlifeact(String playlistlifeact) {
        this.playlistlifeact = playlistlifeact == null ? null : playlistlifeact.trim();
    }

    public String getPlaylistlifedie() {
        return playlistlifedie;
    }

    public void setPlaylistlifedie(String playlistlifedie) {
        this.playlistlifedie = playlistlifedie == null ? null : playlistlifedie.trim();
    }
    
    public Integer getcreater() {
        return creater;
    }

    public void setcreater(Integer creater) {
        this.creater = creater;
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


    public Integer getScheduletype() {
        return scheduletype;
    }

    public void setScheduletype(Integer scheduletype) {
        this.scheduletype = scheduletype;
    }

    public Integer getDelindex() {
        return delindex;
    }

    public void setDelindex(Integer delindex) {
        this.delindex = delindex;
    }

    public String getTimequantum() {
        return timequantum;
    }

    public void setTimequantum(String timequantum) {
        this.timequantum = timequantum == null ? null : timequantum.trim();
    }

    public String getProgramlist() {
        return programlist;
    }

    public void setProgramlist(String programlist) {
        this.programlist = programlist == null ? null : programlist.trim();
    }
    
    public String getMutiProgramlist() {
        return mutiProgramlist;
    }

    public void setMutiProgramlist(String mutiProgramlist) {
        this.mutiProgramlist = mutiProgramlist == null ? null : mutiProgramlist.trim();
    }
    
}