package org.hds.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.hds.model.playlist;

public interface playlistMapper {
	@Delete({ "delete from t_playlist", "where playlistSN = #{playlistSN,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(String playlistSN);

	@Delete({ "delete from t_playlist", "where groupid = #{0}" })
	int deleteBygroupid(Integer groupid);

	@Insert({ "insert into t_playlist (playlistSN, groupid, pubid,", "playlistname, playlistlevel, ",
			"playlistLifeAct, playlistLifeDie, creater, createDate,", "ScheduleType, DelIndex, ",
			"Timequantum, programlist, mutiProgramlist)",
			"values (#{playlistSN,jdbcType=VARCHAR}, #{groupid,jdbcType=INTEGER},#{pubid,jdbcType=VARCHAR}, ",
			"#{playlistname,jdbcType=VARCHAR}, #{playlistlevel,jdbcType=INTEGER}, ",
			"#{playlistlifeact,jdbcType=VARCHAR}, #{playlistlifedie,jdbcType=VARCHAR}, #{creater,jdbcType=INTEGER}, #{createDate,jdbcType=VARCHAR},",
			"#{scheduletype,jdbcType=INTEGER}, #{delindex,jdbcType=INTEGER}, ",
			"#{timequantum,jdbcType=LONGVARCHAR}, #{programlist,jdbcType=LONGVARCHAR}, #{mutiProgramlist,jdbcType=LONGVARCHAR})" })
	int insert(playlist record);

	@InsertProvider(type = playlistSqlProvider.class, method = "insertSelective")
	int insertSelective(playlist record);

	@Select({ "select",
			"playlistSN, groupid, pubid, playlistname, playlistlevel, playlistLifeAct, playlistLifeDie, creater, createDate, publisher, publishDate, deleter, deleteDate,",
			"ScheduleType, DelIndex, Timequantum, programlist, mutiProgramlist", "from t_playlist",
			"where playlistSN = #{playlistSN,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "playlistSN", property = "playlistSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistname", property = "playlistname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistlevel", property = "playlistlevel", jdbcType = JdbcType.INTEGER),
			@Result(column = "playlistLifeAct", property = "playlistlifeact", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistLifeDie", property = "playlistlifedie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ScheduleType", property = "scheduletype", jdbcType = JdbcType.INTEGER),
			@Result(column = "DelIndex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "Timequantum", property = "timequantum", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "programlist", property = "programlist", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "mutiProgramlist", property = "mutiProgramlist", jdbcType = JdbcType.LONGVARCHAR) })
	playlist selectByPrimaryKey(String playlistSN);

	@Select({ "select",
			"playlistSN, groupid, pubid, playlistname, playlistlevel, playlistLifeAct, playlistLifeDie, creater, createDate, publisher, publishDate, deleter, deleteDate,",
			"ScheduleType, DelIndex, Timequantum, programlist, mutiProgramlist", "from t_playlist",
			"where groupid = #{groupid,jdbcType=INTEGER} and DelIndex=0" })
	@Results({ @Result(column = "playlistSN", property = "playlistSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistname", property = "playlistname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistlevel", property = "playlistlevel", jdbcType = JdbcType.INTEGER),
			@Result(column = "playlistLifeAct", property = "playlistlifeact", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistLifeDie", property = "playlistlifedie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ScheduleType", property = "scheduletype", jdbcType = JdbcType.INTEGER),
			@Result(column = "DelIndex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "Timequantum", property = "timequantum", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "programlist", property = "programlist", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "mutiProgramlist", property = "mutiProgramlist", jdbcType = JdbcType.LONGVARCHAR) })
	List<playlist> selectbygroupid(Integer groupid);

	@Select({ "select",
			"playlistSN, groupid, pubid, playlistname, playlistlevel, playlistLifeAct, playlistLifeDie, creater, createDate, publisher, publishDate, deleter, deleteDate,",
			"ScheduleType, DelIndex, Timequantum, programlist, mutiProgramlist", "from t_playlist",
			"where groupid = #{0} and DelIndex=0 and (playlistLifeDie='' or playlistLifeDie >= #{1})" })
	@Results({ @Result(column = "playlistSN", property = "playlistSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistname", property = "playlistname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistlevel", property = "playlistlevel", jdbcType = JdbcType.INTEGER),
			@Result(column = "playlistLifeAct", property = "playlistlifeact", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistLifeDie", property = "playlistlifedie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ScheduleType", property = "scheduletype", jdbcType = JdbcType.INTEGER),
			@Result(column = "DelIndex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "Timequantum", property = "timequantum", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "programlist", property = "programlist", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "mutiProgramlist", property = "mutiProgramlist", jdbcType = JdbcType.LONGVARCHAR) })
	List<playlist> selectbygroupidDate(Integer groupid, String nowDate);

	@Select({ "select",
			"playlistSN, groupid, pubid, playlistname, playlistlevel, playlistLifeAct, playlistLifeDie, creater, createDate, publisher, publishDate, deleter, deleteDate,",
			"ScheduleType, DelIndex, Timequantum, programlist, mutiProgramlist", "from t_playlist",
			"where groupid = #{0} and DelIndex=0 and (playlistLifeDie='' or playlistLifeDie >= #{1}) and (playlistLifeAct='' or playlistLifeAct <= #{2})" })
	@Results({ @Result(column = "playlistSN", property = "playlistSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistname", property = "playlistname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistlevel", property = "playlistlevel", jdbcType = JdbcType.INTEGER),
			@Result(column = "playlistLifeAct", property = "playlistlifeact", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistLifeDie", property = "playlistlifedie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ScheduleType", property = "scheduletype", jdbcType = JdbcType.INTEGER),
			@Result(column = "DelIndex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "Timequantum", property = "timequantum", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "programlist", property = "programlist", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "mutiProgramlist", property = "mutiProgramlist", jdbcType = JdbcType.LONGVARCHAR) })
	List<playlist> selectbygroupidDate2(Integer groupid, String lifeAct, String lifeDie);

	@Select({ "select",
			"playlistSN, groupid, pubid, playlistname, playlistlevel, playlistLifeAct, playlistLifeDie, creater, createDate, publisher, publishDate, deleter, deleteDate,",
			"ScheduleType, DelIndex, Timequantum, programlist, mutiProgramlist", "from t_playlist" })
	@Results({ @Result(column = "playlistSN", property = "playlistSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistname", property = "playlistname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistlevel", property = "playlistlevel", jdbcType = JdbcType.INTEGER),
			@Result(column = "playlistLifeAct", property = "playlistlifeact", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistLifeDie", property = "playlistlifedie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ScheduleType", property = "scheduletype", jdbcType = JdbcType.INTEGER),
			@Result(column = "DelIndex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "Timequantum", property = "timequantum", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "programlist", property = "programlist", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "mutiProgramlist", property = "mutiProgramlist", jdbcType = JdbcType.LONGVARCHAR) })
	List<playlist> selectAll();

	@Select({ "select",
			"playlistSN, groupid, pubid, playlistname, playlistlevel, playlistLifeAct, playlistLifeDie, creater, createDate, publisher, publishDate, deleter, deleteDate,",
			"ScheduleType, DelIndex, Timequantum, programlist, mutiProgramlist", "from t_playlist",
			"where groupid = #{4} and (not (deleteDate< #{2} or publishDate> #{3}) or ((deleteDate is null or deleteDate='') and publishDate< #{3}))",
			"order by ${sort}", "${sortOrder}", "LIMIT #{0},#{1}" })
	@Results({ @Result(column = "playlistSN", property = "playlistSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistname", property = "playlistname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistlevel", property = "playlistlevel", jdbcType = JdbcType.INTEGER),
			@Result(column = "playlistLifeAct", property = "playlistlifeact", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistLifeDie", property = "playlistlifedie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ScheduleType", property = "scheduletype", jdbcType = JdbcType.INTEGER),
			@Result(column = "DelIndex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "Timequantum", property = "timequantum", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "programlist", property = "programlist", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "mutiProgramlist", property = "mutiProgramlist", jdbcType = JdbcType.LONGVARCHAR) })
	List<playlist> selecthistorybypageNum(int startoffset, int pageSize, String lifeAct, String lifeDie, int groupid,
			@Param("sort") String sort, @Param("sortOrder") String sortOrder);

	@Select({ "select",
			"playlistSN, groupid, pubid, playlistname, playlistlevel, playlistLifeAct, playlistLifeDie, creater, createDate, publisher, publishDate, deleter, deleteDate,",
			"ScheduleType, DelIndex, Timequantum, programlist, mutiProgramlist", "from t_playlist",
			"where groupid = #{2} and (not (deleteDate< #{0} or publishDate> #{1}) or ((deleteDate is null or deleteDate='') and publishDate< #{1})) and (not (playlistLifeDie< #{0} or playlistLifeAct> #{1}) or ((playlistLifeDie is null or playlistLifeDie='') and playlistLifeAct< #{1}))",
			"order by playlistSN asc" })
	@Results({ @Result(column = "playlistSN", property = "playlistSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistname", property = "playlistname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistlevel", property = "playlistlevel", jdbcType = JdbcType.INTEGER),
			@Result(column = "playlistLifeAct", property = "playlistlifeact", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistLifeDie", property = "playlistlifedie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ScheduleType", property = "scheduletype", jdbcType = JdbcType.INTEGER),
			@Result(column = "DelIndex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "Timequantum", property = "timequantum", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "programlist", property = "programlist", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "mutiProgramlist", property = "mutiProgramlist", jdbcType = JdbcType.LONGVARCHAR) })
	List<playlist> selecthistorybyDate(String lifeAct, String lifeDie, int groupid);

	@Select({ "select",
			"playlistSN, groupid, pubid, playlistname, playlistlevel, playlistLifeAct, playlistLifeDie, creater, createDate, publisher, publishDate, deleter, deleteDate,",
			"ScheduleType, DelIndex, Timequantum, programlist, mutiProgramlist", "from t_playlist",
			"where ((playlistLifeDie<>'' and playlistLifeDie < #{0}) or (deleteDate<>'' and deleteDate < #{0}  and Delindex=1))",
			"order by playlistSN asc" })
	@Results({ @Result(column = "playlistSN", property = "playlistSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistname", property = "playlistname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistlevel", property = "playlistlevel", jdbcType = JdbcType.INTEGER),
			@Result(column = "playlistLifeAct", property = "playlistlifeact", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistLifeDie", property = "playlistlifedie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ScheduleType", property = "scheduletype", jdbcType = JdbcType.INTEGER),
			@Result(column = "DelIndex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "Timequantum", property = "timequantum", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "programlist", property = "programlist", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "mutiProgramlist", property = "mutiProgramlist", jdbcType = JdbcType.LONGVARCHAR) })
	List<playlist> selectByDeleteDate(String DeleteDate);

	@Select({ "select",
			"playlistSN, groupid, pubid, playlistname, playlistlevel, playlistLifeAct, playlistLifeDie, creater, createDate, publisher, publishDate, deleter, deleteDate,",
			"ScheduleType, DelIndex, Timequantum, programlist, mutiProgramlist", "from t_playlist",
			"where ((playlistLifeDie<>'' and playlistLifeDie < #{0}) or (deleteDate<>'' and deleteDate < #{0} and Delindex=1)) and groupid in (select groupid from t_project where projectid = #{1})",
			"order by playlistSN asc" })
	@Results({ @Result(column = "playlistSN", property = "playlistSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistname", property = "playlistname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistlevel", property = "playlistlevel", jdbcType = JdbcType.INTEGER),
			@Result(column = "playlistLifeAct", property = "playlistlifeact", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistLifeDie", property = "playlistlifedie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ScheduleType", property = "scheduletype", jdbcType = JdbcType.INTEGER),
			@Result(column = "DelIndex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "Timequantum", property = "timequantum", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "programlist", property = "programlist", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "mutiProgramlist", property = "mutiProgramlist", jdbcType = JdbcType.LONGVARCHAR) })
	List<playlist> selectByDeleteDateProjectid(String DeleteDate, String projectid);

	@Select({ "select", "count(*)", "from t_playlist",
			"where  groupid = #{2} and (not (deleteDate< #{0} or publishDate> #{1}) or ((deleteDate is null or deleteDate='') and publishDate< #{1}))" })
	int selectCount(String lifeAct, String lifeDie, int groupid);

	@Select({ "select", "programlist", "from t_playlist",
			"where groupid = #{groupid,jdbcType=INTEGER} and pubid<>0 and pubid<>'[0]' and DelIndex=0" })
	@Results({ @Result(column = "programlist", property = "programlist", jdbcType = JdbcType.LONGVARCHAR) })
	List<Object> selectprogramlistbygroupid(Integer groupid);

	@Select({ "select", "playlistSN", "from t_playlist", "where groupid = #{0} and playlistname=#{1} and DelIndex=0" })
	@Result(column = "playlistSN", property = "playlistSN", jdbcType = JdbcType.VARCHAR, id = true)
	List<String> selectlistsnbyplaylistname(Integer groupid, String playlistname);

	@Select({ "select", "pubid", "from t_playlist", "where playlistSN = #{0}" })
	@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.VARCHAR, id = true)
	Object selectpubidid(String playlistSN);

	@Select({ "select", "programlist", "from t_playlist", "where playlistSN = #{0}" })
	@Result(column = "programlist", property = "programlist", jdbcType = JdbcType.LONGVARCHAR)
	Object selectprogramlistbyid(String playlistSN);

	@Select({ "select", "playlistSN", "from t_playlist",
			"where playlistname = #{0} and groupid = #{1} and DelIndex=0" })
	@Result(column = "playlistSN", property = "playlistSN", jdbcType = JdbcType.VARCHAR, id = true)
	Object selectidByName(String playlistName, Integer groupid);

	@Select({ "select", "count(*)", "from t_playlist", "where playlistname = #{0} and groupid = #{1} and DelIndex=0" })
	@Result(column = "playlistSN", property = "playlistSN", jdbcType = JdbcType.VARCHAR, id = true)
	int selectByNameCount(String playlistName, Integer groupid);

	/*
	 * @Select({ "select", "count(*)", "from t_playlist",
	 * "where pubid = #{0} and groupid = #{1}" }) int selectCountBypubid(Integer
	 * pubid,Integer groupid);
	 * 
	 * @Select({ "select", "max(pubid) as maxpubid", "from t_playlist",
	 * "where groupid = #{groupid,jdbcType=INTEGER}" }) Integer
	 * selectmaxPubidBygroupid(Integer groupid);
	 */
	@Select({ "select",
			"playlistSN, groupid, pubid, playlistname, playlistlevel, playlistLifeAct, playlistLifeDie, creater, createDate, publisher, publishDate, deleter, deleteDate,",
			"ScheduleType, DelIndex, Timequantum, programlist, mutiProgramlist", "from t_playlist",
			"where playlistlevel = #{0} and groupid=#{1} and DelIndex=0" })
	@Results({ @Result(column = "playlistSN", property = "playlistSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistname", property = "playlistname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistlevel", property = "playlistlevel", jdbcType = JdbcType.INTEGER),
			@Result(column = "playlistLifeAct", property = "playlistlifeact", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistLifeDie", property = "playlistlifedie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ScheduleType", property = "scheduletype", jdbcType = JdbcType.INTEGER),
			@Result(column = "DelIndex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "Timequantum", property = "timequantum", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "programlist", property = "programlist", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "mutiProgramlist", property = "mutiProgramlist", jdbcType = JdbcType.LONGVARCHAR) })
	playlist selectBylevel(Integer playlistlevel, Integer groupid);

	@UpdateProvider(type = playlistSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(playlist record);

	@Update({ "update t_playlist", "set groupid = #{groupid,jdbcType=INTEGER},", "pubid = #{pubid,jdbcType=VARCHAR},",
			"playlistname = #{playlistname,jdbcType=VARCHAR},", "playlistlevel = #{playlistlevel,jdbcType=INTEGER},",
			"playlistLifeAct = #{playlistlifeact,jdbcType=VARCHAR},",
			"playlistLifeDie = #{playlistlifedie,jdbcType=VARCHAR},", "creater = #{creater,jdbcType=INTEGER},",
			"createDate = #{createDate,jdbcType=VARCHAR},", "publisher = #{publisher,jdbcType=INTEGER},",
			"publishDate = #{publishDate,jdbcType=VARCHAR},", "deleter = #{deleter,jdbcType=INTEGER},",
			"deleteDate = #{deleteDate,jdbcType=VARCHAR},", "ScheduleType = #{scheduletype,jdbcType=INTEGER},",
			"DelIndex = #{delindex,jdbcType=INTEGER},", "Timequantum = #{timequantum,jdbcType=LONGVARCHAR},",
			"programlist = #{programlist,jdbcType=LONGVARCHAR},",
			"mutiProgramlist = #{mutiProgramlist,jdbcType=LONGVARCHAR}",
			"where playlistSN = #{playlistSN,jdbcType=VARCHAR}" })
	int updateByPrimaryKeyWithBLOBs(playlist record);

	@Update({ "update t_playlist", "set groupid = #{groupid,jdbcType=INTEGER},", "pubid = #{pubid,jdbcType=VARCHAR},",
			"playlistname = #{playlistname,jdbcType=VARCHAR},", "playlistlevel = #{playlistlevel,jdbcType=INTEGER},",
			"playlistLifeAct = #{playlistlifeact,jdbcType=VARCHAR},",
			"playlistLifeDie = #{playlistlifedie,jdbcType=VARCHAR},", "creater = #{creater,jdbcType=INTEGER},",
			"createDate = #{createDate,jdbcType=VARCHAR},", "publisher = #{publisher,jdbcType=INTEGER},",
			"publishDate = #{publishDate,jdbcType=VARCHAR},", "deleter = #{deleter,jdbcType=INTEGER},",
			"deleteDate = #{deleteDate,jdbcType=VARCHAR},", "ScheduleType = #{scheduletype,jdbcType=INTEGER},",
			"DelIndex = #{delindex,jdbcType=INTEGER},", "Timequantum = #{timequantum,jdbcType=LONGVARCHAR},",
			"programlist = #{programlist,jdbcType=LONGVARCHAR},",
			"mutiProgramlist = #{mutiProgramlist,jdbcType=LONGVARCHAR}",
			"where playlistSN = #{playlistSN,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(playlist record);

	@Update({ "update t_playlist", "set pubid = #{1},", "publisher = #{2},", "publishDate = #{3}",
			"where playlistSN = #{0}" })
	int updatepubidByPrimaryKey(String playlistid, String pubid, int adminid, String deldate);

	@Update({ "update t_playlist", "set pubid = #{1},", "publisher = #{2},", "publishDate = #{3},",
			"mutiProgramlist = #{4}", "where playlistSN = #{0}" })
	int updatemutipubidByPrimaryKey(String playlistid, String pubids, int adminid, String pubdate,
			String mutiProgramlist);

	@Update({ "update t_playlist", "set DelIndex = #{1},", "deleter = #{2},", "deleteDate = #{3}",
			"where playlistSN = #{0}" })
	int updatedelindexByPrimaryKey(String playlistSN, int delindex, int adminid, String deldate);
}