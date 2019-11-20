package org.hds.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.hds.model.oldadv;

public interface oldadvMapper {
	@Delete({ "delete from t_oldadv", "where oldadvsn = #{oldadvsn,jdbcType=INTEGER}" })
	int deleteByPrimaryKey(Integer oldadvsn);

	@Insert({ "insert into t_oldadv (oldadvsn, infoSN, ", "advName, groupid, ", "lifeAct, lifeDie, ",
			"advType, infoState, ", "creater, createDate, ", "auditor, auditDate, ", "publisher, publishDate, ",
			"deleter, deleteDate, ", "playMode, pubid, ", "playTimelength, BackgroundStyle, ", "itemlist)",
			"values (#{oldadvsn,jdbcType=INTEGER}, #{infosn,jdbcType=INTEGER}, ",
			"#{advname,jdbcType=VARCHAR}, #{groupid,jdbcType=INTEGER}, ",
			"#{lifeact,jdbcType=VARCHAR}, #{lifedie,jdbcType=VARCHAR}, ",
			"#{advtype,jdbcType=VARCHAR}, #{infostate,jdbcType=INTEGER}, ",
			"#{creater,jdbcType=INTEGER}, #{createdate,jdbcType=VARCHAR}, ",
			"#{auditor,jdbcType=VARCHAR}, #{auditdate,jdbcType=VARCHAR}, ",
			"#{publisher,jdbcType=INTEGER}, #{publishdate,jdbcType=VARCHAR}, ",
			"#{deleter,jdbcType=VARCHAR}, #{deletedate,jdbcType=VARCHAR}, ",
			"#{playmode,jdbcType=VARCHAR}, #{pubid,jdbcType=INTEGER}, ",
			"#{playtimelength,jdbcType=INTEGER}, #{backgroundstyle,jdbcType=LONGVARCHAR}, ",
			"#{itemlist,jdbcType=LONGVARCHAR})" })
	int insert(oldadv record);

	@InsertProvider(type = oldadvSqlProvider.class, method = "insertSelective")
	int insertSelective(oldadv record);

	@Select({ "select", "oldadvsn, infoSN, advName, groupid, lifeAct, lifeDie, advType, infoState, creater, ",
			"createDate, auditor, auditDate, publisher, publishDate, deleter, deleteDate, ",
			"playMode, pubid, playTimelength, BackgroundStyle, itemlist", "from t_oldadv",
			"where oldadvsn = #{oldadvsn,jdbcType=INTEGER}" })
	@Results({ @Result(column = "oldadvsn", property = "oldadvsn", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "infoSN", property = "infosn", jdbcType = JdbcType.INTEGER),
			@Result(column = "advName", property = "advname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "lifeAct", property = "lifeact", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifedie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "advType", property = "advtype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "infoState", property = "infostate", jdbcType = JdbcType.INTEGER),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createdate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "auditor", property = "auditor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "auditDate", property = "auditdate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishdate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleteDate", property = "deletedate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playMode", property = "playmode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "playTimelength", property = "playtimelength", jdbcType = JdbcType.INTEGER),
			@Result(column = "BackgroundStyle", property = "backgroundstyle", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "itemlist", property = "itemlist", jdbcType = JdbcType.LONGVARCHAR) })
	oldadv selectByPrimaryKey(Integer oldadvsn);

	@Select({ "select", "oldadvsn, infoSN, advName, groupid, lifeAct, lifeDie, advType, infoState, creater, ",
			"createDate, auditor, auditDate, publisher, publishDate, deleter, deleteDate, ",
			"playMode, pubid, playTimelength, BackgroundStyle, itemlist", "from t_oldadv",
			"where infoSN = #{infoSN,jdbcType=INTEGER}" })
	@Results({ @Result(column = "oldadvsn", property = "oldadvsn", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "infoSN", property = "infosn", jdbcType = JdbcType.INTEGER),
			@Result(column = "advName", property = "advname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "lifeAct", property = "lifeact", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifedie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "advType", property = "advtype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "infoState", property = "infostate", jdbcType = JdbcType.INTEGER),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createdate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "auditor", property = "auditor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "auditDate", property = "auditdate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishdate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleteDate", property = "deletedate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playMode", property = "playmode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "playTimelength", property = "playtimelength", jdbcType = JdbcType.INTEGER),
			@Result(column = "BackgroundStyle", property = "backgroundstyle", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "itemlist", property = "itemlist", jdbcType = JdbcType.LONGVARCHAR) })
	oldadv selectByPrimaryinfosn(Integer infoSN);

	@UpdateProvider(type = oldadvSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(oldadv record);

	@Update({ "update t_oldadv", "set infoSN = #{infosn,jdbcType=INTEGER},", "advName = #{advname,jdbcType=VARCHAR},",
			"groupid = #{groupid,jdbcType=INTEGER},", "lifeAct = #{lifeact,jdbcType=VARCHAR},",
			"lifeDie = #{lifedie,jdbcType=VARCHAR},", "advType = #{advtype,jdbcType=VARCHAR},",
			"infoState = #{infostate,jdbcType=INTEGER},", "creater = #{creater,jdbcType=INTEGER},",
			"createDate = #{createdate,jdbcType=VARCHAR},", "auditor = #{auditor,jdbcType=VARCHAR},",
			"auditDate = #{auditdate,jdbcType=VARCHAR},", "publisher = #{publisher,jdbcType=INTEGER},",
			"publishDate = #{publishdate,jdbcType=VARCHAR},", "deleter = #{deleter,jdbcType=VARCHAR},",
			"deleteDate = #{deletedate,jdbcType=VARCHAR},", "playMode = #{playmode,jdbcType=VARCHAR},",
			"pubid = #{pubid,jdbcType=INTEGER},", "playTimelength = #{playtimelength,jdbcType=INTEGER},",
			"BackgroundStyle = #{backgroundstyle,jdbcType=LONGVARCHAR},", "itemlist = #{itemlist,jdbcType=LONGVARCHAR}",
			"where oldadvsn = #{oldadvsn,jdbcType=INTEGER}" })
	int updateByPrimaryKeyWithBLOBs(oldadv record);

	@Update({ "update t_oldadv", "set infoSN = #{infosn,jdbcType=INTEGER},", "advName = #{advname,jdbcType=VARCHAR},",
			"groupid = #{groupid,jdbcType=INTEGER},", "lifeAct = #{lifeact,jdbcType=VARCHAR},",
			"lifeDie = #{lifedie,jdbcType=VARCHAR},", "advType = #{advtype,jdbcType=VARCHAR},",
			"infoState = #{infostate,jdbcType=INTEGER},", "creater = #{creater,jdbcType=INTEGER},",
			"createDate = #{createdate,jdbcType=VARCHAR},", "auditor = #{auditor,jdbcType=VARCHAR},",
			"auditDate = #{auditdate,jdbcType=VARCHAR},", "publisher = #{publisher,jdbcType=INTEGER},",
			"publishDate = #{publishdate,jdbcType=VARCHAR},", "deleter = #{deleter,jdbcType=VARCHAR},",
			"deleteDate = #{deletedate,jdbcType=VARCHAR},", "playMode = #{playmode,jdbcType=VARCHAR},",
			"pubid = #{pubid,jdbcType=INTEGER},", "playTimelength = #{playtimelength,jdbcType=INTEGER}",
			"where oldadvsn = #{oldadvsn,jdbcType=INTEGER}" })
	int updateByPrimaryKey(oldadv record);
}