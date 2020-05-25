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
import org.hds.model.sensitive;

public interface sensitiveMapper {
	@Delete({ "delete from t_sensitive", "where Id = #{id,jdbcType=INTEGER}" })
	int deleteByPrimaryKey(Integer id);

	@Insert({ "insert into t_sensitive (Id, projectid, ", "sensitiveString, delIndex)",
			"values (#{id,jdbcType=INTEGER}, #{projectid,jdbcType=VARCHAR}, ",
			"#{sensitivestring,jdbcType=VARCHAR}, #{delindex,jdbcType=INTEGER})" })
	int insert(sensitive record);

	@InsertProvider(type = sensitiveSqlProvider.class, method = "insertSelective")
	int insertSelective(sensitive record);

	@Select({ "select", "Id, projectid, sensitiveString, delIndex", "from t_sensitive",
			"where Id = #{id,jdbcType=INTEGER}" })
	@Results({ @Result(column = "Id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "projectid", property = "projectid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "sensitiveString", property = "sensitivestring", jdbcType = JdbcType.VARCHAR),
			@Result(column = "delIndex", property = "delindex", jdbcType = JdbcType.INTEGER) })
	sensitive selectByPrimaryKey(Integer id);

	@Select({ "select", "Id, projectid, sensitiveString, delIndex", "from t_sensitive", "where projectid = #{0}" })
	@Results({ @Result(column = "Id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "projectid", property = "projectid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "sensitiveString", property = "sensitivestring", jdbcType = JdbcType.VARCHAR),
			@Result(column = "delIndex", property = "delindex", jdbcType = JdbcType.INTEGER) })
	List<sensitive> selectByprojectid(String projectid);

	@Select({ "select", "Id, projectid, sensitiveString, delIndex", "from t_sensitive",
			"where projectid = #{0} and sensitiveString=#{1} " })
	int selectcountByprojectidString(String projectid, String sensitiveString);

	@UpdateProvider(type = sensitiveSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(sensitive record);

	@Update({ "update t_sensitive", "set projectid = #{projectid,jdbcType=VARCHAR},",
			"sensitiveString = #{sensitivestring,jdbcType=VARCHAR},", "delIndex = #{delindex,jdbcType=INTEGER}",
			"where Id = #{id,jdbcType=INTEGER}" })
	int updateByPrimaryKey(sensitive record);
}