package org.hds.model;

public class playlist {
    private Integer id;

    private Integer playlistid;

    private Integer playlistcrc;

    private String playlistname;

    private Integer playlisttype;

    private String programlist;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlaylistid() {
        return playlistid;
    }

    public void setPlaylistid(Integer playlistid) {
        this.playlistid = playlistid;
    }

    public Integer getPlaylistcrc() {
        return playlistcrc;
    }

    public void setPlaylistcrc(Integer playlistcrc) {
        this.playlistcrc = playlistcrc;
    }

    public String getPlaylistname() {
        return playlistname;
    }

    public void setPlaylistname(String playlistname) {
        this.playlistname = playlistname == null ? null : playlistname.trim();
    }

    public Integer getPlaylisttype() {
        return playlisttype;
    }

    public void setPlaylisttype(Integer playlisttype) {
        this.playlisttype = playlisttype;
    }

    public String getProgramlist() {
        return programlist;
    }

    public void setProgramlist(String programlist) {
        this.programlist = programlist == null ? null : programlist.trim();
    }
}