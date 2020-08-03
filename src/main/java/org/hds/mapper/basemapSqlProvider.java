package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.basemap;

public class basemapSqlProvider {

	public String insertSelective(basemap record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("t_basemap");

		if (record.getId() != null) {
			sql.VALUES("Id", "#{id,jdbcType=INTEGER}");
		}

		if (record.getBasemapname() != null) {
			sql.VALUES("basemapname", "#{basemapname,jdbcType=VARCHAR}");
		}

		if (record.getprojectid() != null) {
			sql.VALUES("projectid", "#{projectid,jdbcType=INTEGER}");
		}

		if (record.getimgtype() != null) {
			sql.VALUES("imgtype", "#{imgtype,jdbcType=INTEGER}");
		}

		if (record.getBasemapclassify() != null) {
			sql.VALUES("basemapclassify", "#{basemapclassify,jdbcType=VARCHAR}");
		}

		if (record.getBasemaptype() != null) {
			sql.VALUES("basemaptype", "#{basemaptype,jdbcType=VARCHAR}");
		}

		if (record.getbasemapstyle() != null) {
			sql.VALUES("basemapstyle", "#{basemapstyle,jdbcType=VARCHAR}");
		}

		if (record.getBasemapdata() != null) {
			sql.VALUES("basemapdata", "#{basemapdata,jdbcType=LONGVARCHAR}");
		}

		if (record.getdelindex() != null) {
			sql.VALUES("delindex", "#{delindex,jdbcType=INTEGER}");
		}

		return sql.toString();
	}

	public String updateByPrimaryKeySelective(basemap record) {
		SQL sql = new SQL();
		sql.UPDATE("t_basemap");

		if (record.getBasemapname() != null) {
			sql.SET("basemapname = #{basemapname,jdbcType=VARCHAR}");
		}

		if (record.getprojectid() != null) {
			sql.SET("projectid = #{projectid,jdbcType=INTEGER}");
		}

		if (record.getimgtype() != null) {
			sql.SET("imgtype = #{imgtype,jdbcType=INTEGER}");
		}

		if (record.getBasemapclassify() != null) {
			sql.SET("basemapclassify = #{basemapclassify,jdbcType=VARCHAR}");
		}

		if (record.getBasemaptype() != null) {
			sql.SET("basemaptype = #{basemaptype,jdbcType=VARCHAR}");
		}

		if (record.getbasemapstyle() != null) {
			sql.SET("basemapstyle = #{basemapstyle,jdbcType=VARCHAR}");
		}

		if (record.getBasemapdata() != null) {
			sql.SET("basemapdata = #{basemapdata,jdbcType=LONGVARCHAR}");
		}

		if (record.getdelindex() != null) {
			sql.SET("delindex = #{delindex,jdbcType=INTEGER}");
		}

		sql.WHERE("Id = #{id,jdbcType=INTEGER}");

		return sql.toString();
	}
}