package org.hds.model;

public class statistic {
    private String mutiserverflag;

    private String recordingtime;

    private Integer actionflag;

    private Integer total;

    private Integer dtu;

    private Integer led;

    private Integer updated;

    private Integer waiting;

    private Integer renewable;

    private String dtukey;

    private String projectid;

    public String getMutiserverflag() {
        return mutiserverflag;
    }

    public void setMutiserverflag(String mutiserverflag) {
        this.mutiserverflag = mutiserverflag == null ? null : mutiserverflag.trim();
    }

    public String getRecordingtime() {
        return recordingtime;
    }

    public void setRecordingtime(String recordingtime) {
        this.recordingtime = recordingtime == null ? null : recordingtime.trim();
    }

    public Integer getActionflag() {
        return actionflag;
    }

    public void setActionflag(Integer actionflag) {
        this.actionflag = actionflag;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getDtu() {
        return dtu;
    }

    public void setDtu(Integer dtu) {
        this.dtu = dtu;
    }

    public Integer getLed() {
        return led;
    }

    public void setLed(Integer led) {
        this.led = led;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    public Integer getWaiting() {
        return waiting;
    }

    public void setWaiting(Integer waiting) {
        this.waiting = waiting;
    }

    public Integer getRenewable() {
        return renewable;
    }

    public void setRenewable(Integer renewable) {
        this.renewable = renewable;
    }

    public String getDtukey() {
        return dtukey;
    }

    public void setDtukey(String dtukey) {
        this.dtukey = dtukey == null ? null : dtukey.trim();
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid == null ? null : projectid.trim();
    }
}