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
import org.hds.model.advertisement;

public interface advertisementMapper {
	@Delete({ "delete from t_advertisement", "where infoSN = #{infoSN,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(String infoSN);

	@Insert({ "insert into t_advertisement (infoSN, advName, ",
			"groupid, lifeAct, lifeDie, advType, infoState, creater, createDate,",
			"playMode, pubid, playTimelength, remarks,", "delindex, BackgroundStyle)",
			"values (#{infoSN,jdbcType=VARCHAR}, #{advname,jdbcType=VARCHAR}, ",
			"#{groupid,jdbcType=INTEGER}, #{lifeAct,jdbcType=VARCHAR}, #{lifeDie,jdbcType=VARCHAR}, #{advtype,jdbcType=VARCHAR}, #{infoState,jdbcType=INTEGER}, #{creater,jdbcType=INTEGER}, #{createDate,jdbcType=VARCHAR},",
			"#{playmode,jdbcType=VARCHAR}, #{pubid,jdbcType=INTEGER}, #{playTimelength,jdbcType=INTEGER}, #{remarks,jdbcType=VARCHAR},",
			"#{delindex,jdbcType=INTEGER}, #{backgroundstyle,jdbcType=LONGVARCHAR})" })
	// @Options(useGeneratedKeys = true, keyProperty = "infoSN", keyColumn =
	// "infoSN") // 添加该行，product中的id将被自动添加
	int insert(advertisement record);

	@InsertProvider(type = advertisementSqlProvider.class, method = "insertSelective")
	int insertSelective(advertisement record);

	@Select({ "select",
			"infoSN, advName, groupid, lifeAct, lifeDie, advType, infoState, creater, createDate, auditor, auditDate, publisher, publishDate, deleter, deleteDate, playMode, pubid, playTimelength, remarks, delindex, BackgroundStyle",
			"from t_advertisement", "where infoSN = #{infoSN,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "advName", property = "advname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "lifeAct", property = "lifeAct", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifeDie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "advType", property = "advtype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "infoState", property = "infoState", jdbcType = JdbcType.INTEGER),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "auditor", property = "auditor", jdbcType = JdbcType.INTEGER),
			@Result(column = "auditDate", property = "auditDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playMode", property = "playmode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "remarks", property = "remarks", jdbcType = JdbcType.VARCHAR),
			@Result(column = "delindex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "playTimelength", property = "playTimelength", jdbcType = JdbcType.INTEGER),
			@Result(column = "BackgroundStyle", property = "backgroundstyle", jdbcType = JdbcType.LONGVARCHAR) })
	advertisement selectByPrimaryKey(String infoSN);

	@Select({ "select",
			"infoSN, advName, groupid, lifeAct, lifeDie, advType, infoState, creater, createDate, auditor, auditDate, publisher, publishDate, deleter, deleteDate, playMode, pubid, playTimelength, remarks, delindex, BackgroundStyle",
			"from t_advertisement", "where pubid = #{0} and groupid = #{1}" })
	@Results({ @Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "advName", property = "advname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "lifeAct", property = "lifeAct", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifeDie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "advType", property = "advtype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "infoState", property = "infoState", jdbcType = JdbcType.INTEGER),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "auditor", property = "auditor", jdbcType = JdbcType.INTEGER),
			@Result(column = "auditDate", property = "auditDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playMode", property = "playmode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "remarks", property = "remarks", jdbcType = JdbcType.VARCHAR),
			@Result(column = "delindex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "playTimelength", property = "playTimelength", jdbcType = JdbcType.INTEGER),
			@Result(column = "BackgroundStyle", property = "backgroundstyle", jdbcType = JdbcType.LONGVARCHAR) })
	advertisement selectByPubidandGroupid(Integer pubid, Integer groupid);

	@Select({ "select", "infoSN", "from t_advertisement",
			"where advName = #{advName,jdbcType=VARCHAR} and groupid = #{groupid,jdbcType=INTEGER} and delindex=0" })
	@Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR, id = true)
	int selectidByName(String advName, Integer groupid);

	@Select({ "select", "count(*)", "from t_advertisement",
			"where advName = #{0} and groupid = #{1} and delindex=0 and (lifeDie>curdate() or lifeDie='')" })
	int selectCountByName(String advName, Integer groupid);

	@Select({ "select", "count(*)", "from t_advertisement",
			"where advName = #{0} and groupid = #{1} and delindex=0 and infoSN <> #{2} and (lifeDie>curdate() or lifeDie='')" })
	int selectCountByName2(String advName, Integer groupid, String infoSN);

	@Select({ "select",
			"infoSN, advName, groupid, lifeAct, lifeDie, advType, infoState, creater, createDate, auditor, auditDate, publisher, publishDate, deleter, deleteDate, playMode, pubid, playTimelength, remarks, delindex, BackgroundStyle",
			"from t_advertisement", "where groupid = #{0} and delindex = #{1} and (lifeDie>=curdate() or lifeDie='')",
			"order by infoState asc, lifeDie desc" })
	@Results({ @Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "advName", property = "advname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "lifeAct", property = "lifeAct", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifeDie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "advType", property = "advtype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "infoState", property = "infoState", jdbcType = JdbcType.INTEGER),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "auditor", property = "auditor", jdbcType = JdbcType.INTEGER),
			@Result(column = "auditDate", property = "auditDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playMode", property = "playmode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "delindex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "playTimelength", property = "playTimelength", jdbcType = JdbcType.INTEGER),
			@Result(column = "BackgroundStyle", property = "backgroundstyle", jdbcType = JdbcType.LONGVARCHAR) })
	List<advertisement> selectidBygroupid(Integer groupid, Integer delindex);

	@Select({ "select",
			"infoSN, advName, groupid, lifeAct, lifeDie, advType, infoState, creater, createDate, auditor, auditDate, publisher, publishDate, deleter, deleteDate, playMode, pubid, playTimelength, delindex, BackgroundStyle",
			"from t_advertisement",
			"where groupid = #{0} and (delindex = 1 or (lifeDie<curdate() and lifeDie<>'' and lifeDie is not null))",
			"order by ${sort}", "${sortOrder}", "LIMIT #{1},#{2}" })
	@Results({ @Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "advName", property = "advname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "lifeAct", property = "lifeAct", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifeDie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "advType", property = "advtype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "infoState", property = "infoState", jdbcType = JdbcType.INTEGER),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "auditor", property = "auditor", jdbcType = JdbcType.INTEGER),
			@Result(column = "auditDate", property = "auditDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playMode", property = "playmode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "remarks", property = "remarks", jdbcType = JdbcType.VARCHAR),
			@Result(column = "delindex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "playTimelength", property = "playTimelength", jdbcType = JdbcType.INTEGER),
			@Result(column = "BackgroundStyle", property = "backgroundstyle", jdbcType = JdbcType.LONGVARCHAR) })
	List<advertisement> selectDelBygroupid(Integer groupid, int startoffset, int pageSize, @Param("sort") String sort,
			@Param("sortOrder") String sortOrder);

	@Select({ "select",
			"infoSN, advName, groupid, lifeAct, lifeDie, advType, infoState, creater, createDate, auditor, auditDate, publisher, publishDate, deleter, deleteDate, playMode, pubid, playTimelength, delindex, BackgroundStyle",
			"from t_advertisement",
			"where groupid = #{0} and (delindex = 1 or (lifeDie<curdate() and lifeDie<>'' and lifeDie is not null)) and advName like #{1} and (not lifeAct>#{3} or lifeAct='')  and (not lifeDie<#{2} or lifeDie='')",
			"order by ${sort}", "${sortOrder}", "LIMIT #{4},#{5}" })
	@Results({ @Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "advName", property = "advname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "lifeAct", property = "lifeAct", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifeDie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "advType", property = "advtype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "infoState", property = "infoState", jdbcType = JdbcType.INTEGER),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "auditor", property = "auditor", jdbcType = JdbcType.INTEGER),
			@Result(column = "auditDate", property = "auditDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playMode", property = "playmode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "remarks", property = "remarks", jdbcType = JdbcType.VARCHAR),
			@Result(column = "delindex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "playTimelength", property = "playTimelength", jdbcType = JdbcType.INTEGER),
			@Result(column = "BackgroundStyle", property = "backgroundstyle", jdbcType = JdbcType.LONGVARCHAR) })
	List<advertisement> selectDelBygroupid2(Integer groupid, String infoname, String lifeAct, String lifeDie,
			int startoffset, int pageSize, @Param("sort") String sort, @Param("sortOrder") String sortOrder);

	@Select({ "select",
			"infoSN, advName, groupid, lifeAct, lifeDie, advType, infoState, creater, createDate, auditor, auditDate, publisher, publishDate, deleter, deleteDate, playMode, pubid, playTimelength, remarks, delindex, BackgroundStyle",
			"from t_advertisement",
			"where groupid = #{0} and delindex = #{1} and (infoState=0 or infoState=2)  and (lifeDie>=curdate() or lifeDie='')" })
	@Results({ @Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "advName", property = "advname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "lifeAct", property = "lifeAct", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifeDie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "advType", property = "advtype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "infoState", property = "infoState", jdbcType = JdbcType.INTEGER),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "auditor", property = "auditor", jdbcType = JdbcType.INTEGER),
			@Result(column = "auditDate", property = "auditDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playMode", property = "playmode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "remarks", property = "remarks", jdbcType = JdbcType.VARCHAR),
			@Result(column = "delindex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "playTimelength", property = "playTimelength", jdbcType = JdbcType.INTEGER),
			@Result(column = "BackgroundStyle", property = "backgroundstyle", jdbcType = JdbcType.LONGVARCHAR) })
	List<advertisement> selectEditlistBygroupid(Integer groupid, Integer delindex);

	@Select({ "select",
			"infoSN, advName, groupid, lifeAct, lifeDie, advType, infoState, creater, createDate, auditor, auditDate, publisher, publishDate, deleter, deleteDate, playMode, pubid, playTimelength, remarks, delindex, BackgroundStyle",
			"from t_advertisement",
			"where groupid = #{0} and infoState = #{1} and (lifeDie>=curdate() or lifeDie='')" })
	@Results({ @Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "advName", property = "advname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "lifeAct", property = "lifeAct", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifeDie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "advType", property = "advtype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "infoState", property = "infoState", jdbcType = JdbcType.INTEGER),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "auditor", property = "auditor", jdbcType = JdbcType.INTEGER),
			@Result(column = "auditDate", property = "auditDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playMode", property = "playmode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "remarks", property = "remarks", jdbcType = JdbcType.VARCHAR),
			@Result(column = "delindex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "playTimelength", property = "playTimelength", jdbcType = JdbcType.INTEGER),
			@Result(column = "BackgroundStyle", property = "backgroundstyle", jdbcType = JdbcType.LONGVARCHAR) })
	List<advertisement> selectidBygroupidState(Integer groupid, Integer infoState);

	@Select({ "select",
			"infoSN, advName, groupid, lifeAct, lifeDie, advType, infoState, creater, createDate, auditor, auditDate, publisher, publishDate, deleter, deleteDate, playMode, pubid, playTimelength, remarks, delindex, BackgroundStyle",
			"from t_advertisement",
			"where groupid = #{groupid,jdbcType=INTEGER} and infoState=3 and pubid<>'0' and pubid<>'[0]' and delindex=0 and (lifeDie>=curdate() or lifeDie='') order by advName asc,publishDate desc" })
	@Results({ @Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "advName", property = "advname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "lifeAct", property = "lifeAct", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifeDie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "advType", property = "advtype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "infoState", property = "infoState", jdbcType = JdbcType.INTEGER),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "auditor", property = "auditor", jdbcType = JdbcType.INTEGER),
			@Result(column = "auditDate", property = "auditDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playMode", property = "playmode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "remarks", property = "remarks", jdbcType = JdbcType.VARCHAR),
			@Result(column = "delindex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "playTimelength", property = "playTimelength", jdbcType = JdbcType.INTEGER),
			@Result(column = "BackgroundStyle", property = "backgroundstyle", jdbcType = JdbcType.LONGVARCHAR) })
	List<advertisement> selectpubBygroupid(Integer groupid);

	@Select({ "select",
			"infoSN, advName, groupid, lifeAct, lifeDie, advType, infoState, creater, createDate, auditor, auditDate, publisher, publishDate, deleter, deleteDate, playMode, pubid, playTimelength, remarks, delindex, BackgroundStyle",
			"from t_advertisement",
			"where ((lifeDie<>'' and lifeDie < #{0}) or (deleteDate<>'' and deleteDate < #{0} and Delindex=1))",
			"order by lifeDie desc" })
	@Results({ @Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "advName", property = "advname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "lifeAct", property = "lifeAct", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifeDie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "advType", property = "advtype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "infoState", property = "infoState", jdbcType = JdbcType.INTEGER),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "auditor", property = "auditor", jdbcType = JdbcType.INTEGER),
			@Result(column = "auditDate", property = "auditDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playMode", property = "playmode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "delindex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "playTimelength", property = "playTimelength", jdbcType = JdbcType.INTEGER),
			@Result(column = "BackgroundStyle", property = "backgroundstyle", jdbcType = JdbcType.LONGVARCHAR) })
	List<advertisement> selectByDeleteDate(String DeleteDate);

	@Select({ "select",
			"infoSN, advName, groupid, lifeAct, lifeDie, advType, infoState, creater, createDate, auditor, auditDate, publisher, publishDate, deleter, deleteDate, playMode, pubid, playTimelength, remarks, delindex, BackgroundStyle",
			"from t_advertisement",
			"where ((lifeDie<>'' and lifeDie < #{0}) or (deleteDate<>'' and deleteDate < #{0} and Delindex=1)) and groupid in (select groupid from t_project where projectid = #{1})",
			"order by lifeDie desc" })
	@Results({ @Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "advName", property = "advname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "lifeAct", property = "lifeAct", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifeDie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "advType", property = "advtype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "infoState", property = "infoState", jdbcType = JdbcType.INTEGER),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "auditor", property = "auditor", jdbcType = JdbcType.INTEGER),
			@Result(column = "auditDate", property = "auditDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playMode", property = "playmode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "delindex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "playTimelength", property = "playTimelength", jdbcType = JdbcType.INTEGER),
			@Result(column = "BackgroundStyle", property = "backgroundstyle", jdbcType = JdbcType.LONGVARCHAR) })
	List<advertisement> selectByDeleteDateProjectid(String DeleteDate, String projectid);

	@Select({ "select", "count(*)", "from t_advertisement",
			"where pubid = #{0} and groupid = #{1} and (lifeDie>=curdate() or lifeDie='')" })
	int selectCountBypubid(Integer pubid, Integer groupid);

	@Select({ "select", "count(*)", "from t_advertisement",
			"where groupid = #{0} and (delindex=1 or lifeDie<curdate())" })
	int selectDelCountBygroupid(Integer groupid);

	@Select({ "select", "count(*)", "from t_advertisement",
			"where groupid = #{0} and (delindex=1 or lifeDie<curdate() and lifeDie<>'' and lifeDie is not null) and advName like #{1} and (not lifeAct>#{3} or lifeAct='')  and (not lifeDie<#{2} or lifeDie='')" })
	int selectDelCountBygroupid2(Integer groupid, String infoname, String lifeAct, String lifeDie);

	@Select({ "select", "max(pubid) as maxpubid", "from t_advertisement",
			"where groupid = #{groupid,jdbcType=INTEGER}" })
	Integer selectmaxPubidBygroupid(Integer groupid);

	@Select({ "select",
			"infoSN, advName, groupid, lifeAct, lifeDie, advType, infoState, creater, createDate, auditor, auditDate, publisher, publishDate, deleter, deleteDate, playMode, pubid, playTimelength, remarks, delindex, BackgroundStyle",
			"from t_advertisement", "where delindex=0" })
	@Results({ @Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "advName", property = "advname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "lifeAct", property = "lifeAct", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifeDie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "infoState", property = "infoState", jdbcType = JdbcType.INTEGER),
			@Result(column = "advType", property = "advtype", jdbcType = JdbcType.VARCHAR),
			@Result(column = "creater", property = "creater", jdbcType = JdbcType.INTEGER),
			@Result(column = "createDate", property = "createDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "auditor", property = "auditor", jdbcType = JdbcType.INTEGER),
			@Result(column = "auditDate", property = "auditDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "publisher", property = "publisher", jdbcType = JdbcType.INTEGER),
			@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deleter", property = "deleter", jdbcType = JdbcType.INTEGER),
			@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playMode", property = "playmode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "remarks", property = "remarks", jdbcType = JdbcType.VARCHAR),
			@Result(column = "delindex", property = "delindex", jdbcType = JdbcType.INTEGER),
			@Result(column = "playTimelength", property = "playTimelength", jdbcType = JdbcType.INTEGER),
			@Result(column = "BackgroundStyle", property = "backgroundstyle", jdbcType = JdbcType.LONGVARCHAR) })
	List<advertisement> selectAll();

	@Select({ "select", "deleteDate", "from t_advertisement", "where infoSN = #{infoSN,jdbcType=VARCHAR}" })
	@Result(column = "deleteDate", property = "deleteDate", jdbcType = JdbcType.VARCHAR)
	Object selectdeleteDateByPrimaryKey(String infoSN);

	@Select({ "select", "publishDate", "from t_advertisement", "where infoSN = #{infoSN,jdbcType=VARCHAR}" })
	@Result(column = "publishDate", property = "publishDate", jdbcType = JdbcType.VARCHAR)
	Object selectpublishDateByPrimaryKey(String infoSN);

	@UpdateProvider(type = advertisementSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(advertisement record);

	@Update({ "update t_advertisement", "set advName = #{advname,jdbcType=VARCHAR},",
			"groupid = #{groupid,jdbcType=INTEGER},", "lifeAct = #{lifeAct,jdbcType=VARCHAR},",
			"lifeDie = #{lifeDie,jdbcType=VARCHAR},", "advType = #{advtype,jdbcType=VARCHAR},",
			"infoState = #{infoState,jdbcType=INTEGER},", "creater = #{creater,jdbcType=INTEGER},",
			"createDate = #{createDate,jdbcType=VARCHAR},", "auditor = #{auditor,jdbcType=INTEGER},",
			"auditDate = #{auditDate,jdbcType=VARCHAR},", "publisher = #{publisher,jdbcType=INTEGER},",
			"publishDate = #{publishDate,jdbcType=VARCHAR},", "deleter = #{deleter,jdbcType=INTEGER},",
			"deleteDate = #{deleteDate,jdbcType=VARCHAR},", "playMode = #{playmode,jdbcType=VARCHAR},",
			"pubid = #{pubid,jdbcType=INTEGER},", "playTimelength = #{playTimelength,jdbcType=INTEGER},",
			"remarks = #{remarks,jdbcType=INTEGER},", "delindex = #{delindex,jdbcType=INTEGER},",
			"BackgroundStyle = #{backgroundstyle,jdbcType=LONGVARCHAR}", "where infoSN = #{infoSN,jdbcType=VARCHAR}" })
	int updateByPrimaryKeyWithBLOBs(advertisement record);

	@Update({ "update t_advertisement", "set advName = #{advname,jdbcType=VARCHAR},",
			"groupid = #{groupid,jdbcType=INTEGER},", "lifeAct = #{lifeAct,jdbcType=VARCHAR},",
			"lifeDie = #{lifeDie,jdbcType=VARCHAR},", "advType = #{advtype,jdbcType=VARCHAR},",
			"infoState = #{infoState,jdbcType=INTEGER},", "creater = #{creater,jdbcType=INTEGER},",
			"createDate = #{createDate,jdbcType=VARCHAR},", "auditor = #{auditor,jdbcType=INTEGER},",
			"auditDate = #{auditDate,jdbcType=VARCHAR},", "publisher = #{publisher,jdbcType=INTEGER},",
			"publishDate = #{publishDate,jdbcType=VARCHAR},", "deleter = #{deleter,jdbcType=INTEGER},",
			"deleteDate = #{deleteDate,jdbcType=VARCHAR},", "playMode = #{playmode,jdbcType=VARCHAR},",
			"pubid = #{pubid,jdbcType=INTEGER},", "playTimelength = #{playTimelength,jdbcType=INTEGER},",
			"remarks = #{remarks,jdbcType=INTEGER},", "delindex = #{delindex,jdbcType=INTEGER},",
			"BackgroundStyle = #{backgroundstyle,jdbcType=LONGVARCHAR}", "where infoSN = #{infoSN,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(advertisement record);

	@Update({ "update t_advertisement", "set playTimelength = #{1}", "where infoSN = #{0}" })
	int updateplaytimelengthByid(String infoSN, int playtimelength);

	@Update({ "update t_advertisement", "set delindex = #{1},", "deleter = #{2},", "deleteDate = #{3}",
			"where infoSN = #{0}" })
	int updateDelindexdateByid(String infoSN, int delindex, int adminid, String deldate);

	@Update({ "update t_advertisement", "set deleter = #{1},", "deleteDate = #{2}", "where infoSN = #{0}" })
	int updatedeletedateByid(String infoSN, int adminid, String deldate);

	@Update({ "update t_advertisement", "set publisher = #{1},", "publishDate = #{2}", "where infoSN = #{0}" })
	int updatepublishDateByid(String infoSN, int adminid, String deldate);

	@Update({ "update t_advertisement", "set delindex = #{1}", "where infoSN = #{0}" })
	int updateDelindexByid(String infoSN, int delindex);

	@Update({ "update t_advertisement", "set pubid = #{1},", "auditor = #{2},", "auditDate = #{3}",
			"where infoSN = #{0}" })
	int updatepubidByid(String infoSN, int pubid, int adminid, String auditDate);
}