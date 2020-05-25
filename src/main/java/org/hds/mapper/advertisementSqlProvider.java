package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.advertisement;

public class advertisementSqlProvider {

	public String insertSelective(advertisement record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("t_advertisement");

		if (record.getinfoSN() != null) {
			sql.VALUES("infoSN", "#{infoSN,jdbcType=VARCHAR}");
		}

		if (record.getAdvname() != null) {
			sql.VALUES("advName", "#{advname,jdbcType=VARCHAR}");
		}

		if (record.getGroupid() != null) {
			sql.VALUES("groupid", "#{groupid,jdbcType=INTEGER}");
		}

		if (record.getlifeAct() != null) {
			sql.VALUES("lifeAct", "#{lifeAct,jdbcType=VARCHAR}");
		}

		if (record.getlifeDie() != null) {
			sql.VALUES("lifeDie", "#{lifeDie,jdbcType=VARCHAR}");
		}

		if (record.getAdvtype() != null) {
			sql.VALUES("advType", "#{advtype,jdbcType=VARCHAR}");
		}

		if (record.getinfoState() != null) {
			sql.VALUES("infoState", "#{infoState,jdbcType=INTEGER}");
		}

		if (record.getcreater() != null) {
			sql.VALUES("creater", "#{creater,jdbcType=INTEGER}");
		}

		if (record.getcreateDate() != null) {
			sql.VALUES("createDate", "#{createDate,jdbcType=VARCHAR}");
		}

		if (record.getauditor() != null) {
			sql.VALUES("auditor", "#{auditor,jdbcType=INTEGER}");
		}

		if (record.getauditDate() != null) {
			sql.VALUES("auditDate", "#{auditDate,jdbcType=VARCHAR}");
		}

		if (record.getpublisher() != null) {
			sql.VALUES("publisher", "#{publisher,jdbcType=INTEGER}");
		}

		if (record.getpublishDate() != null) {
			sql.VALUES("publishDate", "#{publishDate,jdbcType=VARCHAR}");
		}

		if (record.getdeleter() != null) {
			sql.VALUES("deleter", "#{deleter,jdbcType=INTEGER}");
		}

		if (record.getdeleteDate() != null) {
			sql.VALUES("deleteDate", "#{deleteDate,jdbcType=VARCHAR}");
		}

		if (record.getPlaymode() != null) {
			sql.VALUES("playMode", "#{playmode,jdbcType=VARCHAR}");
		}

		if (record.getPubid() != null) {
			sql.VALUES("pubid", "#{pubid,jdbcType=INTEGER}");
		}

		if (record.getplayTimelength() != null) {
			sql.VALUES("playTimelength", "#{playTimelength,jdbcType=INTEGER}");
		}

		if (record.getRemarks() != null) {
			sql.VALUES("remarks", "#{remarks,jdbcType=VARCHAR}");
		}

		if (record.getDelindex() != null) {
			sql.VALUES("DelIndex", "#{delindex,jdbcType=INTEGER}");
		}

		if (record.getBackgroundstyle() != null) {
			sql.VALUES("BackgroundStyle", "#{backgroundstyle,jdbcType=LONGVARCHAR}");
		}

		return sql.toString();
	}

	public String updateByPrimaryKeySelective(advertisement record) {
		SQL sql = new SQL();
		sql.UPDATE("t_advertisement");

		if (record.getAdvname() != null) {
			sql.SET("advName = #{advname,jdbcType=VARCHAR}");
		}

		if (record.getGroupid() != null) {
			sql.SET("groupid = #{groupid,jdbcType=INTEGER}");
		}

		if (record.getlifeAct() != null) {
			sql.SET("lifeAct = #{lifeAct,jdbcType=VARCHAR}");
		}

		if (record.getlifeDie() != null) {
			sql.SET("lifeDie = #{lifeDie,jdbcType=VARCHAR}");
		}

		if (record.getAdvtype() != null) {
			sql.SET("advType = #{advtype,jdbcType=VARCHAR}");
		}

		if (record.getinfoState() != null) {
			sql.SET("infoState = #{infoState,jdbcType=INTEGER}");
		}

		if (record.getcreater() != null) {
			sql.SET("creater = #{creater,jdbcType=INTEGER}");
		}

		if (record.getcreateDate() != null) {
			sql.SET("createDate = #{createDate,jdbcType=VARCHAR}");
		}

		if (record.getauditor() != null) {
			sql.SET("auditor = #{auditor,jdbcType=INTEGER}");
		}

		if (record.getauditDate() != null) {
			sql.SET("auditDate = #{auditDate,jdbcType=VARCHAR}");
		}

		if (record.getpublisher() != null) {
			sql.SET("publisher = #{publisher,jdbcType=INTEGER}");
		}

		if (record.getpublishDate() != null) {
			sql.SET("publishDate = #{publishDate,jdbcType=VARCHAR}");
		}

		if (record.getdeleter() != null) {
			sql.SET("deleter = #{deleter,jdbcType=INTEGER}");
		}

		if (record.getdeleteDate() != null) {
			sql.SET("deleteDate = #{deleteDate,jdbcType=VARCHAR}");
		}

		if (record.getPlaymode() != null) {
			sql.SET("playMode = #{playmode,jdbcType=VARCHAR}");
		}

		if (record.getPubid() != null) {
			sql.SET("pubid = #{pubid,jdbcType=INTEGER}");
		}

		if (record.getplayTimelength() != null) {
			sql.SET("playTimelength = #{playTimelength,jdbcType=INTEGER}");
		}

		if (record.getRemarks() != null) {
			sql.SET("remarks = #{remarks,jdbcType=VARCHAR}");
		}

		if (record.getDelindex() != null) {
			sql.SET("DelIndex = #{delindex,jdbcType=INTEGER}");
		}

		if (record.getBackgroundstyle() != null) {
			sql.SET("BackgroundStyle = #{backgroundstyle,jdbcType=LONGVARCHAR}");
		}

		sql.WHERE("infoSN = #{infoSN,jdbcType=VARCHAR}");

		return sql.toString();
	}
}