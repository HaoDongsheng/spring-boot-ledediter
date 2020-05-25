package org.hds.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.hds.model.playlistcode;

public interface playlistcodeMapper {
	@Delete({ "delete from t_playlistcode", "where playlistCodeSN = #{playlistCodeSN,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(String playlistCodeSN);

	@Delete({ "delete from t_playlistcode", "where groupid = #{0}" })
	int deleteBygroupid(Integer groupid);

	@Delete({ "delete from t_playlistcode", "where playlistSN = #{playlistSN,jdbcType=VARCHAR}" })
	int deleteByplaylistSN(String playlistSN);

	@Delete({ "delete from t_playlistcode",
			"where playlistSN not in (select playlistSN from t_playlist where DelIndex=0)" })
	int deletecodeByDelindex();

	@Insert({ "insert into t_playlistcode (playlistCodeSN, groupid, containADID, lifeAct, lifeDie, playlistSN, ",
			"pubid, playlistCrc, ", "codeContext, singleCodeContext, packCount, packLength)",
			"values (#{playlistCodeSN,jdbcType=VARCHAR}, #{groupid,jdbcType=INTEGER}, #{containADID,jdbcType=LONGVARCHAR},  #{lifeAct,jdbcType=VARCHAR},  #{lifeDie,jdbcType=VARCHAR}, #{playlistsn,jdbcType=VARCHAR}, ",
			"#{pubid,jdbcType=INTEGER}, #{playlistcrc,jdbcType=INTEGER}, ",
			"#{codecontext,jdbcType=LONGVARCHAR}, #{singleCodeContext,jdbcType=LONGVARCHAR}, #{packCount,jdbcType=INTEGER}, #{packLength,jdbcType=INTEGER})" })
	int insert(playlistcode record);

	@InsertProvider(type = playlistcodeSqlProvider.class, method = "insertSelective")
	int insertSelective(playlistcode record);

	@Select({ "select",
			"playlistCodeSN, groupid, containADID, lifeAct, lifeDie, playlistSN, pubid, playlistCrc, codeContext, singleCodeContext, packCount, packLength",
			"from t_playlistcode", "where playlistcodeSN = #{playlistcodeSN,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "playlistCodeSN", property = "playlistCodeSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "containADID", property = "containADID", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "lifeAct", property = "lifeAct", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifeDie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistSN", property = "playlistsn", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "playlistCrc", property = "playlistcrc", jdbcType = JdbcType.INTEGER),
			@Result(column = "codeContext", property = "codecontext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "singleCodeContext", property = "singleCodeContext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "packCount", property = "packCount", jdbcType = JdbcType.INTEGER),
			@Result(column = "packLength", property = "packLength", jdbcType = JdbcType.INTEGER) })
	playlistcode selectByPrimaryKey(Integer playlistcodeSN);

	@Select({ "select",
			"playlistCodeSN, groupid, containADID, lifeAct, lifeDie, playlistSN, pubid, playlistCrc, codeContext, singleCodeContext, packCount, packLength",
			"from t_playlistcode" })
	@Results({ @Result(column = "playlistCodeSN", property = "playlistCodeSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "containADID", property = "containADID", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "lifeAct", property = "lifeAct", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifeDie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistSN", property = "playlistsn", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "playlistCrc", property = "playlistcrc", jdbcType = JdbcType.INTEGER),
			@Result(column = "codeContext", property = "codecontext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "singleCodeContext", property = "singleCodeContext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "packCount", property = "packCount", jdbcType = JdbcType.INTEGER),
			@Result(column = "packLength", property = "packLength", jdbcType = JdbcType.INTEGER) })
	List<playlistcode> selectAll();

	@Select({ "select",
			"playlistCodeSN, groupid, containADID, lifeAct, lifeDie, playlistSN, pubid, playlistCrc, codeContext, singleCodeContext, packCount, packLength",
			"from t_playlistcode", "where groupid = #{0}" })
	@Results({ @Result(column = "playlistCodeSN", property = "playlistCodeSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "containADID", property = "containADID", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "lifeAct", property = "lifeAct", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifeDie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistSN", property = "playlistsn", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "playlistCrc", property = "playlistcrc", jdbcType = JdbcType.INTEGER),
			@Result(column = "codeContext", property = "codecontext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "singleCodeContext", property = "singleCodeContext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "packCount", property = "packCount", jdbcType = JdbcType.INTEGER),
			@Result(column = "packLength", property = "packLength", jdbcType = JdbcType.INTEGER) })
	List<playlistcode> selectByGroupid(int groupid);

	@Select({ "select",
			"playlistCodeSN, groupid, containADID, lifeAct, lifeDie, playlistSN, pubid, playlistCrc, codeContext, singleCodeContext, packCount, packLength",
			"from t_playlistcode", "where groupid = #{0} and lifeDie>=#{1}" })
	@Results({ @Result(column = "playlistCodeSN", property = "playlistCodeSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "containADID", property = "containADID", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "lifeAct", property = "lifeAct", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lifeDie", property = "lifeDie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "playlistSN", property = "playlistsn", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "playlistCrc", property = "playlistcrc", jdbcType = JdbcType.INTEGER),
			@Result(column = "codeContext", property = "codecontext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "singleCodeContext", property = "singleCodeContext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "packCount", property = "packCount", jdbcType = JdbcType.INTEGER),
			@Result(column = "packLength", property = "packLength", jdbcType = JdbcType.INTEGER) })
	List<playlistcode> selectByGroupidDate(int groupid, String nowDate);

	@Select({ "select", "pubid", "from t_playlistcode", "where playlistSN = #{playlistSN,jdbcType=VARCHAR}" })
	@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER)
	int selectpubidByplaylistSN(String playlistSN);

	@Select({ "select", "count(playlistSN)", "from t_playlistcode",
			"where playlistSN = #{playlistSN,jdbcType=VARCHAR}" })
	int selectcoutByplaylistSN(String playlistSN);

	@Select({ "select", "max(pubid)", "from t_playlistcode",
			"where playlistSN in (select playlistSN from t_playlist where groupid = #{groupid,jdbcType=INTEGER})" })
	int selectmaxplPubidBygroupid(Integer groupid);

	@Select({ "select", "count(pubid)", "from t_playlistcode", "where pubid = #{pubid,jdbcType=INTEGER}" })
	int selectcoutBypubid(Integer pubid);

	@UpdateProvider(type = playlistcodeSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(playlistcode record);

	@Update({ "update t_playlistcode", "set playlistSN = #{playlistsn,jdbcType=VARCHAR},",
			"groupid = #{groupid,jdbcType=INTEGER},", "containADID = #{containADID,jdbcType=LONGVARCHAR},",
			"lifeAct = #{lifeAct,jdbcType=VARCHAR},", "lifeDie = #{lifeDie,jdbcType=VARCHAR},",
			"pubid = #{pubid,jdbcType=INTEGER},", "playlistCrc = #{playlistcrc,jdbcType=INTEGER},",
			"codeContext = #{codecontext,jdbcType=LONGVARCHAR},",
			"singleCodeContext = #{singleCodeContext,jdbcType=LONGVARCHAR},",
			"packCount = #{packCount,jdbcType=INTEGER},", "packLength = #{packLength,jdbcType=INTEGER}",
			"where playlistCodeSN = #{playlistCodeSN,jdbcType=VARCHAR}" })
	int updateByPrimaryKeyWithBLOBs(playlistcode record);

	@Update({ "update t_playlistcode", "set codeContext = #{codecontext,jdbcType=LONGVARCHAR},",
			"groupid = #{groupid,jdbcType=INTEGER},", "containADID = #{containADID,jdbcType=LONGVARCHAR},",
			"lifeAct = #{lifeAct,jdbcType=VARCHAR},", "lifeDie = #{lifeDie,jdbcType=VARCHAR},",
			"pubid = #{pubid,jdbcType=INTEGER},", "playlistCrc = #{playlistcrc,jdbcType=INTEGER},",
			"codeContext = #{codecontext,jdbcType=LONGVARCHAR},",
			"singleCodeContext = #{singleCodeContext,jdbcType=LONGVARCHAR},",
			"packCount = #{packCount,jdbcType=INTEGER},", "packLength = #{packLength,jdbcType=INTEGER}",
			"where playlistSN = #{playlistsn,jdbcType=VARCHAR}" })
	int updateByplaylistSN(playlistcode record);
}