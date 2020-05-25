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
import org.hds.model.project;

public interface projectMapper {
	@Delete({ "delete from t_project", "where projectId = #{projectid,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(String projectid);

	@Insert({
			"insert into t_project (projectId, projectName, CheckCode, DefaultStartlevel, startlevelControl, AutoGroupTo, IsOurModule, ConnectParameters, projectLimit)",
			"values (#{projectid,jdbcType=VARCHAR}, #{projectname,jdbcType=VARCHAR}, #{CheckCode,jdbcType=VARCHAR}, #{DefaultStartlevel,jdbcType=INTEGER}, #{startlevelControl,jdbcType=INTEGER}, #{AutoGroupTo,jdbcType=INTEGER}, #{IsOurModule,jdbcType=INTEGER}, #{ConnectParameters,jdbcType=VARCHAR}, #{projectLimit,jdbcType=LONGVARCHAR})" })
	int insert(project record);

	@InsertProvider(type = projectSqlProvider.class, method = "insertSelective")
	int insertSelective(project record);

	@Select({ "select",
			"projectId, projectName, CheckCode, DefaultStartlevel, startlevelControl, AutoGroupTo, IsOurModule, ConnectParameters, projectLimit",
			"from t_project", "where projectId = #{projectid,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "projectId", property = "projectid", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "projectName", property = "projectname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CheckCode", property = "CheckCode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DefaultStartlevel", property = "DefaultStartlevel", jdbcType = JdbcType.INTEGER),
			@Result(column = "startlevelControl", property = "startlevelControl", jdbcType = JdbcType.INTEGER),
			@Result(column = "AutoGroupTo", property = "AutoGroupTo", jdbcType = JdbcType.INTEGER),
			@Result(column = "IsOurModule", property = "IsOurModule", jdbcType = JdbcType.INTEGER),
			@Result(column = "ConnectParameters", property = "ConnectParameters", jdbcType = JdbcType.VARCHAR),
			@Result(column = "projectLimit", property = "projectLimit", jdbcType = JdbcType.LONGVARCHAR) })
	project selectByPrimaryKey(String projectid);

	@Select({ "select",
			"projectId, projectName, CheckCode, DefaultStartlevel, startlevelControl, AutoGroupTo, IsOurModule, ConnectParameters, projectLimit",
			"from t_project", "where projectName = #{0}" })
	@Results({ @Result(column = "projectId", property = "projectid", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "projectName", property = "projectname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CheckCode", property = "CheckCode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DefaultStartlevel", property = "DefaultStartlevel", jdbcType = JdbcType.INTEGER),
			@Result(column = "startlevelControl", property = "startlevelControl", jdbcType = JdbcType.INTEGER),
			@Result(column = "AutoGroupTo", property = "AutoGroupTo", jdbcType = JdbcType.INTEGER),
			@Result(column = "IsOurModule", property = "IsOurModule", jdbcType = JdbcType.INTEGER),
			@Result(column = "ConnectParameters", property = "ConnectParameters", jdbcType = JdbcType.VARCHAR),
			@Result(column = "projectLimit", property = "projectLimit", jdbcType = JdbcType.LONGVARCHAR) })
	project selectByprojectName(String projectName);

	@Select({ "select", "UpdateRate", "from t_project", "where projectId = #{0}" })
	@Result(column = "UpdateRate", property = "UpdateRate", jdbcType = JdbcType.DOUBLE)
	double selectUpdateRateByPrimaryKey(String projectid);

	@Select({ "select",
			"projectId, projectName, CheckCode, DefaultStartlevel, startlevelControl, AutoGroupTo, IsOurModule, ConnectParameters, projectLimit",
			"from t_project order by projectId asc" })
	@Results({ @Result(column = "projectId", property = "projectid", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "projectName", property = "projectname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CheckCode", property = "CheckCode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DefaultStartlevel", property = "DefaultStartlevel", jdbcType = JdbcType.INTEGER),
			@Result(column = "startlevelControl", property = "startlevelControl", jdbcType = JdbcType.INTEGER),
			@Result(column = "AutoGroupTo", property = "AutoGroupTo", jdbcType = JdbcType.INTEGER),
			@Result(column = "IsOurModule", property = "IsOurModule", jdbcType = JdbcType.INTEGER),
			@Result(column = "ConnectParameters", property = "ConnectParameters", jdbcType = JdbcType.VARCHAR),
			@Result(column = "projectLimit", property = "projectLimit", jdbcType = JdbcType.LONGVARCHAR) })
	List<project> selectAll();

	@UpdateProvider(type = projectSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(project record);

	@Update({ "update t_project", "set projectName = #{projectname,jdbcType=VARCHAR},",
			"CheckCode = #{CheckCode,jdbcType=VARCHAR}, DefaultStartlevel = #{DefaultStartlevel,jdbcType=INTEGER}, startlevelControl = #{startlevelControl,jdbcType=INTEGER}, AutoGroupTo = #{AutoGroupTo,jdbcType=INTEGER},",
			"IsOurModule = #{IsOurModule,jdbcType=INTEGER},",
			"ConnectParameters = #{ConnectParameters,jdbcType=INTEGER}, projectLimit = #{projectLimit,jdbcType=LONGVARCHAR}",
			"where projectId = #{projectid,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(project record);

	@Update({ "update t_project", "set AdvertisementUpdateTime = #{1}", "where projectId = #{0}" })
	int updateAdvUpdateTimeByPrimaryKey(String projectid, String AdvertisementUpdateTime);

	@Update({ "update t_project", "set TerminalUpdateTime = #{1}", "where projectId = #{0}" })
	int updateTerminalUpdateTimeByPrimaryKey(String projectid, String TerminalUpdateTime);

	@Update({ "update t_project", "set TerminalUpdateTime = #{0}" })
	int updateTerminalUpdateTimeByAll(String TerminalUpdateTime);

	@Update({ "update t_project", "set ParameterUpdateTime = #{1}", "where projectId = #{0}" })
	int updateParameterUpdateTimeByPrimaryKey(String projectid, String ParameterUpdateTime);
}