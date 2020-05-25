package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.project;

public class projectSqlProvider {

	public String insertSelective(project record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("t_project");

		if (record.getProjectid() != null) {
			sql.VALUES("projectId", "#{projectid,jdbcType=VARCHAR}");
		}

		if (record.getProjectname() != null) {
			sql.VALUES("projectName", "#{projectname,jdbcType=VARCHAR}");
		}

		if (record.getCheckCode() != null) {
			sql.VALUES("CheckCode", "#{CheckCode,jdbcType=VARCHAR}");
		}

		if (record.getDefaultStartlevel() != null) {
			sql.VALUES("DefaultStartlevel", "#{DefaultStartlevel,jdbcType=INTEGER}");
		}

		if (record.getStartlevelControl() != null) {
			sql.VALUES("startlevelControl", "#{startlevelControl,jdbcType=INTEGER}");
		}

		if (record.getAutoGroupTo() != null) {
			sql.VALUES("AutoGroupTo", "#{AutoGroupTo,jdbcType=INTEGER}");
		}

		if (record.getIsOurModule() != null) {
			sql.VALUES("IsOurModule", "#{IsOurModule,jdbcType=INTEGER}");
		}

		if (record.getConnectParameters() != null) {
			sql.VALUES("ConnectParameters", "#{ConnectParameters,jdbcType=VARCHAR}");
		}

		if (record.getProjectLimit() != null) {
			sql.VALUES("projectLimit", "#{projectLimit,jdbcType=LONGVARCHAR}");
		}

		return sql.toString();
	}

	public String updateByPrimaryKeySelective(project record) {
		SQL sql = new SQL();
		sql.UPDATE("t_project");

		if (record.getProjectname() != null) {
			sql.SET("projectName = #{projectname,jdbcType=VARCHAR}");
		}

		if (record.getCheckCode() != null) {
			sql.SET("CheckCode = #{CheckCode,jdbcType=VARCHAR}");
		}

		if (record.getDefaultStartlevel() != null) {
			sql.SET("DefaultStartlevel = #{DefaultStartlevel,jdbcType=INTEGER}");
		}

		if (record.getStartlevelControl() != null) {
			sql.SET("startlevelControl = #{startlevelControl,jdbcType=INTEGER}");
		}

		if (record.getAutoGroupTo() != null) {
			sql.SET("AutoGroupTo = #{AutoGroupTo,jdbcType=INTEGER}");
		}

		if (record.getIsOurModule() != null) {
			sql.SET("IsOurModule = #{IsOurModule,jdbcType=INTEGER}");
		}

		if (record.getConnectParameters() != null) {
			sql.SET("ConnectParameters = #{ConnectParameters,jdbcType=VARCHAR}");
		}

		if (record.getProjectLimit() != null) {
			sql.SET("projectLimit = #{projectLimit,jdbcType=LONGVARCHAR}");
		}

		sql.WHERE("projectId = #{projectid,jdbcType=VARCHAR}");

		return sql.toString();
	}
}