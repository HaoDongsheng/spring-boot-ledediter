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
import org.hds.model.infocode;

public interface infocodeMapper {
	@Delete({ "delete from t_infocode", "where infoSN = #{infoSN,jdbcType=VARCHAR}" })
	int deleteByinfoSN(String infoSN);

	@Insert({ "insert into t_infocode (infoCodeSN, infoSN, ", "groupid, pubid, codeCrc, ",
			"codeContext, singleCodeContext, packCount, packLength)",
			"values (#{infoCodeSN,jdbcType=VARCHAR}, #{infoSN,jdbcType=VARCHAR}, ",
			"#{groupid,jdbcType=INTEGER}, #{pubid,jdbcType=INTEGER}, #{codecrc,jdbcType=INTEGER}, ",
			"#{codecontext,jdbcType=LONGVARCHAR}, #{singleCodeContext,jdbcType=LONGVARCHAR}, #{packCount,jdbcType=INTEGER}, #{packLength,jdbcType=INTEGER})" })
	int insert(infocode record);

	@InsertProvider(type = infocodeSqlProvider.class, method = "insertSelective")
	int insertSelective(infocode record);

	@Select({ "select",
			"infoCodeSN, infoSN, groupid, pubid, codeCrc, codeContext, singleCodeContext, packCount,packLength",
			"from t_infocode", "where infoSN = #{infoSN,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "infoCodeSN", property = "infoCodeSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "codeCrc", property = "codecrc", jdbcType = JdbcType.INTEGER),
			@Result(column = "codeContext", property = "codecontext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "singleCodeContext", property = "singleCodeContext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "packCount", property = "packCount", jdbcType = JdbcType.INTEGER),
			@Result(column = "packLength", property = "packLength", jdbcType = JdbcType.INTEGER) })
	List<infocode> selectByinfoSN(String infoSN);

	@Select({ "select",
			"infoCodeSN, infoSN, groupid, pubid, codeCrc, codeContext, singleCodeContext, packCount,packLength",
			"from t_infocode" })
	@Results({ @Result(column = "infoCodeSN", property = "infoCodeSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "codeCrc", property = "codecrc", jdbcType = JdbcType.INTEGER),
			@Result(column = "codeContext", property = "codecontext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "singleCodeContext", property = "singleCodeContext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "packCount", property = "packCount", jdbcType = JdbcType.INTEGER),
			@Result(column = "packLength", property = "packLength", jdbcType = JdbcType.INTEGER) })
	List<infocode> selectAll();

	@Select({ "select",
			"infoCodeSN, infoSN, groupid, pubid, codeCrc, codeContext, singleCodeContext, packCount,packLength",
			"from t_infocode", "where groupid = #{0} order by infoSN" })
	@Results({ @Result(column = "infoCodeSN", property = "infoCodeSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "codeCrc", property = "codecrc", jdbcType = JdbcType.INTEGER),
			@Result(column = "codeContext", property = "codecontext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "singleCodeContext", property = "singleCodeContext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "packCount", property = "packCount", jdbcType = JdbcType.INTEGER),
			@Result(column = "packLength", property = "packLength", jdbcType = JdbcType.INTEGER) })
	List<infocode> selectBygroupid(Integer groupid);

	@Select({ "select",
			"infoCodeSN, infoSN, groupid, pubid, codeCrc, codeContext, singleCodeContext, packCount,packLength",
			"from t_infocode",
			"where groupid = #{0} and infosn in (select infosn  from t_advertisement where (lifeDie='' or lifeDie>=#{1}) and delindex=0) order by infoSN" })
	@Results({ @Result(column = "infoCodeSN", property = "infoCodeSN", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "infoSN", property = "infoSN", jdbcType = JdbcType.VARCHAR),
			@Result(column = "groupid", property = "groupid", jdbcType = JdbcType.INTEGER),
			@Result(column = "pubid", property = "pubid", jdbcType = JdbcType.INTEGER),
			@Result(column = "codeCrc", property = "codecrc", jdbcType = JdbcType.INTEGER),
			@Result(column = "codeContext", property = "codecontext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "singleCodeContext", property = "singleCodeContext", jdbcType = JdbcType.LONGVARCHAR),
			@Result(column = "packCount", property = "packCount", jdbcType = JdbcType.INTEGER),
			@Result(column = "packLength", property = "packLength", jdbcType = JdbcType.INTEGER) })
	List<infocode> selectBygroupidDate(Integer groupid, String lifeDie);

	@Select({ "select", "count(*)", "from t_infocode", "where infoSN = #{infoSN,jdbcType=VARCHAR}" })
	int selectCountByinfoSN(String infoSN);

	@UpdateProvider(type = infocodeSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(infocode record);

	@Update({ "update t_infocode", "set infoSN = #{infoSN,jdbcType=VARCHAR},", "pubid = #{pubid,jdbcType=INTEGER},",
			"groupid = #{groupid,jdbcType=INTEGER},", "codeCrc = #{codecrc,jdbcType=INTEGER},",
			"codeContext = #{codecontext,jdbcType=LONGVARCHAR},",
			"singleCodeContext = #{singleCodeContext,jdbcType=LONGVARCHAR},",
			"packCount = #{packCount,jdbcType=INTEGER},", "packLength = #{packLength,jdbcType=INTEGER}",
			"where infoCodeSN = #{infoCodeSN,jdbcType=VARCHAR}" })
	int updateByPrimaryKeyWithBLOBs(infocode record);

	@Update({ "update t_infocode", "set infoSN = #{infoSN,jdbcType=VARCHAR},", "groupid = #{groupid,jdbcType=INTEGER},",
			"pubid = #{pubid,jdbcType=INTEGER},", "codeCrc = #{codecrc,jdbcType=INTEGER},",
			"codeContext = #{codecontext,jdbcType=LONGVARCHAR},",
			"singleCodeContext = #{singleCodeContext,jdbcType=LONGVARCHAR},",
			"packCount = #{packCount,jdbcType=INTEGER},", "packLength = #{packLength,jdbcType=INTEGER}",
			"where infoCodeSN = #{infoCodeSN,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(infocode record);
}