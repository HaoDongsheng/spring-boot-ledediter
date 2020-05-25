package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.infocode;

public class infocodeSqlProvider {

	public String insertSelective(infocode record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("t_infocode");

		if (record.getInfoCodeSN() != null) {
			sql.VALUES("infoCodeSN", "#{infoCodeSN,jdbcType=VARCHAR}");
		}

		if (record.getInfoSN() != null) {
			sql.VALUES("infoSN", "#{infoSN,jdbcType=VARCHAR}");
		}

		if (record.getGroupid() != null) {
			sql.VALUES("groupid", "#{groupid,jdbcType=INTEGER}");
		}

		if (record.getPubid() != null) {
			sql.VALUES("pubid", "#{pubid,jdbcType=INTEGER}");
		}

		if (record.getCodecrc() != null) {
			sql.VALUES("codeCrc", "#{codecrc,jdbcType=INTEGER}");
		}

		if (record.getCodecontext() != null) {
			sql.VALUES("codeContext", "#{codecontext,jdbcType=LONGVARCHAR}");
		}

		if (record.getSingleCodeContext() != null) {
			sql.VALUES("singleCodeContext", "#{singleCodeContext,jdbcType=LONGVARCHAR}");
		}

		if (record.getPackCount() != null) {
			sql.VALUES("packCount", "#{packCount,jdbcType=INTEGER}");
		}

		if (record.getPackLength() != null) {
			sql.VALUES("packLength", "#{packLength,jdbcType=INTEGER}");
		}

		return sql.toString();
	}

	public String updateByPrimaryKeySelective(infocode record) {
		SQL sql = new SQL();
		sql.UPDATE("t_infocode");

		if (record.getInfoSN() != null) {
			sql.SET("infoSN = #{infosn,jdbcType=VARCHAR}");
		}

		if (record.getGroupid() != null) {
			sql.SET("groupid = #{groupid,jdbcType=INTEGER}");
		}

		if (record.getPubid() != null) {
			sql.SET("pubid = #{pubid,jdbcType=INTEGER}");
		}

		if (record.getCodecrc() != null) {
			sql.SET("codeCrc = #{codecrc,jdbcType=INTEGER}");
		}

		if (record.getCodecontext() != null) {
			sql.SET("codeContext = #{codecontext,jdbcType=LONGVARCHAR}");
		}

		if (record.getSingleCodeContext() != null) {
			sql.SET("singleCodeContext = #{singleCodeContext,jdbcType=LONGVARCHAR}");
		}

		if (record.getPackCount() != null) {
			sql.SET("packCount = #{packCount,jdbcType=INTEGER}");
		}

		if (record.getPackLength() != null) {
			sql.SET("packLength = #{packLength,jdbcType=INTEGER}");
		}

		sql.WHERE("infoCodeSN = #{infoCodeSN,jdbcType=VARCHAR}");

		return sql.toString();
	}
}