package org.hds.model;

public class oldplaylist {
    private String playlistsn;

    private Integer groupid;

    private String pubid;

    private String playlistname;

    private Integer playlistlevel;

    private String playlistlifeact;

    private String playlistlifedie;

    private Integer creater;

    private String createdate;

    private Integer publisher;

    private String publishdate;

    private Integer deleter;

    private String deletedate;

    private Integer scheduletype;

    private Integer delindex;

    private String timequantum;

    private String programlist;

    private String mutiprogramlist;

    public String getPlaylistsn() {
        return playlistsn;
    }

    public void setPlaylistsn(String playlistsn) {
        this.playlistsn = playlistsn == null ? null : playlistsn.trim();
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getPubid() {
        return pubid;
    }

    public void setPubid(String pubid) {
        this.pubid = pubid == null ? null : pubid.trim();
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

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate == null ? null : createdate.trim();
    }

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public String getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(String publishdate) {
        this.publishdate = publishdate == null ? null : publishdate.trim();
    }

    public Integer getDeleter() {
        return deleter;
    }

    public void setDeleter(Integer deleter) {
        this.deleter = deleter;
    }

    public String getDeletedate() {
        return deletedate;
    }

    public void setDeletedate(String deletedate) {
        this.deletedate = deletedate == null ? null : deletedate.trim();
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

    public String getMutiprogramlist() {
        return mutiprogramlist;
    }

    public void setMutiprogramlist(String mutiprogramlist) {
        this.mutiprogramlist = mutiprogramlist == null ? null : mutiprogramlist.trim();
    }
}