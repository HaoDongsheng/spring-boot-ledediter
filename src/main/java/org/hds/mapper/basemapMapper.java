package org.hds.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.hds.model.basemap;

public interface basemapMapper {
	@Delete({ "delete from t_basemap", "where Id = #{id,jdbcType=INTEGER}" })
	int deleteByPrimaryKey(Integer id);

	@Delete({ "delete from t_basemap", "where projectid = #{0}" })
	int deleteByprojectid(String projectid);

	@Insert({ "insert into t_basemap (Id, basemapname, projectid,imgtype,", "basemapclassify, basemaptype, ",
			"basemapstyle,basemapdata,delindex)",
			"values (#{id,jdbcType=INTEGER}, #{basemapname,jdbcType=VARCHAR}, #{projectid,jdbcType=VARCHAR}, #{imgtype,jdbcType=INTEGER},",
			"#{basemapclassify,jdbcType=VARCHAR}, #{basemaptype,jdbcType=VARCHAR}, ",
			"#{basemapstyle,jdbcType=VARCHAR},#{basemapdata,jdbcType=LONGVARCHAR},#{delindex,jdbcType=INTEGER})" })
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") // 添加该行，product中的id将被自动添加
	int insert(basemap record);

	@InsertProvider(type = basemapSqlProvider.class, method = "insertSelective")
	int insertSelective(basemap record);

	@Select({ "select",
			"Id, basemapname, projectid,imgtype, basemapclassify, basemaptype,basemapstyle, basemapdata,delindex",
			"from t_basemap", "where Id = #{id,jdbcType=INTEGER} and delindex = 0" })
	@Results({ @Result(column = "Id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "basemapname", property = "basemapname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "projectid", property = "projectid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "imgtype", property = "imgtype", jdbcType = JdbcType.INTEGER),
			@Result(column = "basemapclassify", property = "basemapclassify", jdbcType = JdbcType.VARCHAR),
			@Result(column = "basemaptype", property = "basemaptype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "basemapstyle", property = "basemapstyle", jdbcType = JdbcType.VARCHAR),
			@Result(column = "basemapdata", property = "basemapdata", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "delindex", property = "delindex", jdbcType = JdbcType.INTEGER) })
	basemap selectByPrimaryKey(Integer id);

	@Select({ "select",
			"Id, basemapname, projectid,imgtype, basemapclassify, basemaptype,basemapstyle, basemapdata,delindex",
			"from t_basemap", "where delindex = 0" })
	@Results({ @Result(column = "Id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "basemapname", property = "basemapname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "projectid", property = "projectid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "imgtype", property = "imgtype", jdbcType = JdbcType.INTEGER),
			@Result(column = "basemapclassify", property = "basemapclassify", jdbcType = JdbcType.VARCHAR),
			@Result(column = "basemaptype", property = "basemaptype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "basemapstyle", property = "basemapstyle", jdbcType = JdbcType.VARCHAR),
			@Result(column = "basemapdata", property = "basemapdata", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "delindex", property = "delindex", jdbcType = JdbcType.INTEGER) })
	List<basemap> selectAll();

	@Select({ "select",
			"Id, basemapname, projectid,imgtype, basemapclassify, basemaptype,basemapstyle, basemapdata,delindex",
			"from t_basemap",
			"where (projectid = #{0} or projectid='0') and imgtype =#{1} and basemapclassify=#{2} and delindex = 0",
			"order by projectid asc,Id asc" })
	@Results({ @Result(column = "Id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "basemapname", property = "basemapname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "projectid", property = "projectid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "imgtype", property = "imgtype", jdbcType = JdbcType.INTEGER),
			@Result(column = "basemapclassify", property = "basemapclassify", jdbcType = JdbcType.VARCHAR),
			@Result(column = "basemaptype", property = "basemaptype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "basemapstyle", property = "basemapstyle", jdbcType = JdbcType.VARCHAR),
			@Result(column = "basemapdata", property = "basemapdata", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "delindex", property = "delindex", jdbcType = JdbcType.INTEGER) })
	List<basemap> selectbasemapbyproject(String projectid, Integer imgtype, String basemapclassify);

	@Select({ "select",
			"Id, basemapname, projectid,imgtype, basemapclassify, basemaptype,basemapstyle, basemapdata,delindex",
			"from t_basemap", "where (projectid = #{0} or projectid='0') and imgtype =#{1} and delindex = 0",
			"order by projectid asc,Id asc" })
	@Results({ @Result(column = "Id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "basemapname", property = "basemapname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "projectid", property = "projectid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "imgtype", property = "imgtype", jdbcType = JdbcType.INTEGER),
			@Result(column = "basemapclassify", property = "basemapclassify", jdbcType = JdbcType.VARCHAR),
			@Result(column = "basemaptype", property = "basemaptype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "basemapstyle", property = "basemapstyle", jdbcType = JdbcType.VARCHAR),
			@Result(column = "basemapdata", property = "basemapdata", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "delindex", property = "delindex", jdbcType = JdbcType.INTEGER) })
	List<basemap> selectbasemap2byproject(String projectid, Integer imgtype);

	@Select({ "select", "distinct(basemapclassify)", "from t_basemap",
			"where (projectid = #{0} or projectid='0') and imgtype =#{1} and delindex = 0 order by Id asc" })
	@Results({ @Result(column = "basemapclassify", property = "basemapclassify", jdbcType = JdbcType.VARCHAR), })
	List<String> selectbasemapclassifybyproject(String projectid, Integer imgtype);

	@UpdateProvider(type = basemapSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(basemap record);

	@Update({ "update t_basemap", "set basemapname = #{basemapname,jdbcType=VARCHAR},",
			"projectid = #{projectid,jdbcType=VARCHAR},", "imgtype = #{imgtype,jdbcType=INTEGER},",
			"basemapclassify = #{basemapclassify,jdbcType=VARCHAR},", "basemaptype = #{basemaptype,jdbcType=VARCHAR},",
			"basemapstyle = #{basemapstyle,jdbcType=VARCHAR},", "basemapdata = #{basemapdata,jdbcType=LONGVARCHAR}",
			"delindex = #{delindex,jdbcType=INTEGER}", "where Id = #{id,jdbcType=INTEGER}" })
	int updateByPrimaryKeyWithBLOBs(basemap record);

	@Update({ "update t_basemap", "set basemapname = #{basemapname,jdbcType=VARCHAR},",
			"projectid = #{projectid,jdbcType=VARCHAR},", "imgtype = #{imgtype,jdbcType=INTEGER},",
			"basemapclassify = #{basemapclassify,jdbcType=VARCHAR},", "basemaptype = #{basemaptype,jdbcType=VARCHAR}",
			"basemapstyle = #{basemapstyle,jdbcType=VARCHAR},", "basemapdata = #{basemapdata,jdbcType=LONGVARCHAR}",
			"delindex = #{delindex,jdbcType=INTEGER}", "where Id = #{id,jdbcType=INTEGER}" })
	int updateByPrimaryKey(basemap record);

	@Update({ "update t_basemap", "set basemapclassify = #{3}",
			"where projectid = #{0} and imgtype=#{1} and basemapclassify=#{2}" })
	int updatebasemapclassify(String projectid, Integer imgtype, String oldbasemapclassify, String newbasemapclassify);
}