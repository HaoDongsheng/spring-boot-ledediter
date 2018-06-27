package org.hds.model;

public class admin {
    private Integer adminid;

    private String adminname;

    private String adminpwd;

    private String adminpermission;

    private String admingrps;

    private Integer issuperuser;

    private Integer delindex;

    public Integer getAdminid() {
        return adminid;
    }

    public void setAdminid(Integer adminid) {
        this.adminid = adminid;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname == null ? null : adminname.trim();
    }

    public String getAdminpwd() {
        return adminpwd;
    }

    public void setAdminpwd(String adminpwd) {
        this.adminpwd = adminpwd == null ? null : adminpwd.trim();
    }

    public String getAdminpermission() {
        return adminpermission;
    }

    public void setAdminpermission(String adminpermission) {
        this.adminpermission = adminpermission == null ? null : adminpermission.trim();
    }

    public String getAdmingrps() {
        return admingrps;
    }

    public void setAdmingrps(String admingrps) {
        this.admingrps = admingrps == null ? null : admingrps.trim();
    }

    public Integer getIssuperuser() {
        return issuperuser;
    }

    public void setIssuperuser(Integer issuperuser) {
        this.issuperuser = issuperuser;
    }

    public Integer getDelindex() {
        return delindex;
    }

    public void setDelindex(Integer delindex) {
        this.delindex = delindex;
    }
}