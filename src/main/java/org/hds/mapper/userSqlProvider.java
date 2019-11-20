package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.user;

public class userSqlProvider {

	public String insertSelective(user record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("t_user");

		if (record.getAdminid() != null) {
			sql.VALUES("adminID", "#{adminid,jdbcType=INTEGER}");
		}

		if (record.getAdminname() != null) {
			sql.VALUES("adminName", "#{adminname,jdbcType=VARCHAR}");
		}

		if (record.getAdminpwd() != null) {
			sql.VALUES("adminPwd", "#{adminpwd,jdbcType=VARCHAR}");
		}

		if (record.getAdminstatus() != null) {
			sql.VALUES("adminStatus", "#{adminstatus,jdbcType=INTEGER}");
		}

		if (record.getExpdate() != null) {
			sql.VALUES("expDate", "#{expdate,jdbcType=VARCHAR}");
		}

		if (record.getAdminpermission() != null) {
			sql.VALUES("adminPermission", "#{adminpermission,jdbcType=VARCHAR}");
		}

		if (record.getProjectid() != null) {
			sql.VALUES("projectid", "#{projectid,jdbcType=VARCHAR}");
		}

		if (record.getGroupids() != null) {
			sql.VALUES("groupids", "#{groupids,jdbcType=VARCHAR}");
		}

		if (record.getAdminlevel() != null) {
			sql.VALUES("adminLevel", "#{adminlevel,jdbcType=INTEGER}");
		}

		if (record.getParentid() != null) {
			sql.VALUES("parentId", "#{parentid,jdbcType=INTEGER}");
		}

		if (record.getinherit() != null) {
			sql.VALUES("inherit", "#{inherit,jdbcType=INTEGER}");
		}

		if (record.getGrpcount() != null) {
			sql.VALUES("GrpCount", "#{grpcount,jdbcType=INTEGER}");
		}

		if (record.getAdmincount() != null) {
			sql.VALUES("adminCount", "#{admincount,jdbcType=INTEGER}");
		}

		if (record.getIssuperuser() != null) {
			sql.VALUES("isSuperuser", "#{issuperuser,jdbcType=INTEGER}");
		}

		if (record.getDelindex() != null) {
			sql.VALUES("DelIndex", "#{delindex,jdbcType=INTEGER}");
		}

		if (record.getAdminRemarks() != null) {
			sql.VALUES("adminRemarks", "#{adminRemarks,jdbcType=VARCHAR}");
		}
		return sql.toString();
	}

	public String updateByPrimaryKeySelective(user record) {
		SQL sql = new SQL();
		sql.UPDATE("t_user");

		if (record.getAdminname() != null) {
			sql.SET("adminName = #{adminname,jdbcType=VARCHAR}");
		}

		if (record.getAdminpwd() != null) {
			sql.SET("adminPwd = #{adminpwd,jdbcType=VARCHAR}");
		}

		if (record.getAdminstatus() != null) {
			sql.SET("adminStatus = #{adminstatus,jdbcType=INTEGER}");
		}

		if (record.getExpdate() != null) {
			sql.SET("expDate = #{expdate,jdbcType=VARCHAR}");
		}

		if (record.getAdminpermission() != null) {
			sql.SET("adminPermission = #{adminpermission,jdbcType=VARCHAR}");
		}

		if (record.getProjectid() != null) {
			sql.SET("projectid = #{projectid,jdbcType=VARCHAR}");
		}

		if (record.getGroupids() != null) {
			sql.SET("groupids = #{groupids,jdbcType=VARCHAR}");
		}

		if (record.getAdminlevel() != null) {
			sql.SET("adminLevel = #{adminlevel,jdbcType=INTEGER}");
		}

		if (record.getParentid() != null) {
			sql.SET("parentId = #{parentid,jdbcType=INTEGER}");
		}

		if (record.getinherit() != null) {
			sql.SET("inherit = #{inherit,jdbcType=INTEGER}");
		}

		if (record.getGrpcount() != null) {
			sql.SET("GrpCount = #{grpcount,jdbcType=INTEGER}");
		}

		if (record.getAdmincount() != null) {
			sql.SET("adminCount = #{admincount,jdbcType=INTEGER}");
		}

		if (record.getIssuperuser() != null) {
			sql.SET("isSuperuser = #{issuperuser,jdbcType=INTEGER}");
		}

		if (record.getDelindex() != null) {
			sql.SET("DelIndex = #{delindex,jdbcType=INTEGER}");
		}

		if (record.getAdminRemarks() != null) {
			sql.SET("adminRemarks = #{adminRemarks,jdbcType=VARCHAR}");
		}

		sql.WHERE("adminID = #{adminid,jdbcType=INTEGER}");

		return sql.toString();
	}
}