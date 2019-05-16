package org.hds.model;

public class video {
    private Integer videoSN;

    private String videoname;

    private Integer groupid;

    private Integer videotype;

    private String filetype;
    
    private String duration;

    private String videoclassify;

    private String videostyle;

    private Integer delindex;

    private String videofirst;

    public Integer getvideoSN() {
        return videoSN;
    }

    public void setvideoSN(Integer videoSN) {
        this.videoSN = videoSN;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname == null ? null : videoname.trim();
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getVideotype() {
        return videotype;
    }

    public void setVideotype(Integer videotype) {
        this.videotype = videotype;
    }

    public String getduration() {
        return duration;
    }

    public void setduration(String duration) {
        this.duration = duration == null ? null : duration.trim();
    }
    
    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype == null ? null : filetype.trim();
    }

    public String getVideoclassify() {
        return videoclassify;
    }

    public void setVideoclassify(String videoclassify) {
        this.videoclassify = videoclassify == null ? null : videoclassify.trim();
    }

    public String getVideostyle() {
        return videostyle;
    }

    public void setVideostyle(String videostyle) {
        this.videostyle = videostyle == null ? null : videostyle.trim();
    }

    public Integer getDelindex() {
        return delindex;
    }

    public void setDelindex(Integer delindex) {
        this.delindex = delindex;
    }

    public String getVideofirst() {
        return videofirst;
    }

    public void setVideofirst(String videofirst) {
        this.videofirst = videofirst == null ? null : videofirst.trim();
    }
}