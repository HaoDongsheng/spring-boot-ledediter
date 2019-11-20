package org.hds.model;

public class sensitive {
    private Integer id;

    private String projectid;

    private String sensitivestring;

    private Integer delindex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid == null ? null : projectid.trim();
    }

    public String getSensitivestring() {
        return sensitivestring;
    }

    public void setSensitivestring(String sensitivestring) {
        this.sensitivestring = sensitivestring == null ? null : sensitivestring.trim();
    }

    public Integer getDelindex() {
        return delindex;
    }

    public void setDelindex(Integer delindex) {
        this.delindex = delindex;
    }
}