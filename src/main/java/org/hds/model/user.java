package org.hds.model;

public class user {
	private Integer adminid;

	private String adminname;

	private String adminpwd;

	private Integer adminstatus;

	private String expdate;

	private String adminpermission;

	private String projectid;

	private String groupids;

	private Integer adminlevel;

	private Integer parentid;

	private Integer inherit;

	private Integer grpcount;

	private Integer admincount;

	private Integer issuperuser;

	private Integer delindex;

	private String adminRemarks;

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

	public Integer getAdminstatus() {
		return adminstatus;
	}

	public void setAdminstatus(Integer adminstatus) {
		this.adminstatus = adminstatus;
	}

	public String getExpdate() {
		return expdate;
	}

	public void setExpdate(String expdate) {
		this.expdate = expdate == null ? null : expdate.trim();
	}

	public String getAdminpermission() {
		return adminpermission;
	}

	public void setAdminpermission(String adminpermission) {
		this.adminpermission = adminpermission == null ? null : adminpermission.trim();
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid == null ? null : projectid.trim();
	}

	public String getGroupids() {
		return groupids;
	}

	public void setGroupids(String groupids) {
		this.groupids = groupids;
	}

	public Integer getAdminlevel() {
		return adminlevel;
	}

	public void setAdminlevel(Integer adminlevel) {
		this.adminlevel = adminlevel;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public Integer getinherit() {
		return inherit;
	}

	public void setinherit(Integer inherit) {
		this.inherit = inherit;
	}

	public Integer getGrpcount() {
		return grpcount;
	}

	public void setGrpcount(Integer grpcount) {
		this.grpcount = grpcount;
	}

	public Integer getAdmincount() {
		return admincount;
	}

	public void setAdmincount(Integer admincount) {
		this.admincount = admincount;
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

	public String getAdminRemarks() {
		return adminRemarks;
	}

	public void setAdminRemarks(String adminRemarks) {
		this.adminRemarks = adminRemarks;
	}

}