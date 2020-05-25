package org.hds.model;

public class llog {
    private String logtime;

    private Integer adminid;

    private String functionname;

    private Integer operatetype;

    private String operate;

    public String getLogtime() {
        return logtime;
    }

    public void setLogtime(String logtime) {
        this.logtime = logtime == null ? null : logtime.trim();
    }

    public Integer getAdminid() {
        return adminid;
    }

    public void setAdminid(Integer adminid) {
        this.adminid = adminid;
    }

    public String getFunctionname() {
        return functionname;
    }

    public void setFunctionname(String functionname) {
        this.functionname = functionname == null ? null : functionname.trim();
    }

    public Integer getOperatetype() {
        return operatetype;
    }

    public void setOperatetype(Integer operatetype) {
        this.operatetype = operatetype;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate == null ? null : operate.trim();
    }
}