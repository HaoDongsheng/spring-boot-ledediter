package org.hds.model;

public class programlist {
    private Integer id;

    private Integer programid;

    private Integer programcrc;

    private String programname;

    private Integer programtype;

    private String programstart;

    private String programstop;

    private String advlist;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProgramid() {
        return programid;
    }

    public void setProgramid(Integer programid) {
        this.programid = programid;
    }

    public Integer getProgramcrc() {
        return programcrc;
    }

    public void setProgramcrc(Integer programcrc) {
        this.programcrc = programcrc;
    }

    public String getProgramname() {
        return programname;
    }

    public void setProgramname(String programname) {
        this.programname = programname == null ? null : programname.trim();
    }

    public Integer getProgramtype() {
        return programtype;
    }

    public void setProgramtype(Integer programtype) {
        this.programtype = programtype;
    }

    public String getProgramstart() {
        return programstart;
    }

    public void setProgramstart(String programstart) {
        this.programstart = programstart == null ? null : programstart.trim();
    }

    public String getProgramstop() {
        return programstop;
    }

    public void setProgramstop(String programstop) {
        this.programstop = programstop == null ? null : programstop.trim();
    }

    public String getAdvlist() {
        return advlist;
    }

    public void setAdvlist(String advlist) {
        this.advlist = advlist == null ? null : advlist.trim();
    }
}