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
    @Delete({
        "delete from t_project",
        "where projectId = #{projectid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String projectid);

    @Insert({
        "insert into t_project (projectId, projectName, AutoGroupTo, IsOurModule, ConnectParameters)",
        "values (#{projectid,jdbcType=VARCHAR}, #{projectname,jdbcType=VARCHAR}, #{AutoGroupTo,jdbcType=INTEGER}, #{IsOurModule,jdbcType=INTEGER}, #{ConnectParameters,jdbcType=VARCHAR})"
    })
    int insert(project record);

    @InsertProvider(type=projectSqlProvider.class, method="insertSelective")
    int insertSelective(project record);

    @Select({
        "select",
        "projectId, projectName, AutoGroupTo, IsOurModule, ConnectParameters",
        "from t_project",
        "where projectId = #{projectid,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="projectId", property="projectid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="projectName", property="projectname", jdbcType=JdbcType.VARCHAR),
        @Result(column="AutoGroupTo", property="AutoGroupTo", jdbcType=JdbcType.INTEGER),
        @Result(column="IsOurModule", property="IsOurModule", jdbcType=JdbcType.INTEGER),
        @Result(column="ConnectParameters", property="ConnectParameters", jdbcType=JdbcType.VARCHAR)
    })
    project selectByPrimaryKey(String projectid);
    
    @Select({
        "select",
        "UpdateRate",
        "from t_project",
        "where projectId = #{0}"
    })    
    @Result(column="UpdateRate", property="UpdateRate", jdbcType=JdbcType.DOUBLE)
    double selectUpdateRateByPrimaryKey(String projectid);
    
    @Select({
        "select",
        "projectId, projectName, AutoGroupTo, IsOurModule, ConnectParameters",
        "from t_project order by projectId asc"        
    })
    @Results({
        @Result(column="projectId", property="projectid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="projectName", property="projectname", jdbcType=JdbcType.VARCHAR),
        @Result(column="AutoGroupTo", property="AutoGroupTo", jdbcType=JdbcType.INTEGER),
        @Result(column="IsOurModule", property="IsOurModule", jdbcType=JdbcType.INTEGER),
        @Result(column="ConnectParameters", property="ConnectParameters", jdbcType=JdbcType.VARCHAR)
    })
    List<project> selectAll();

    @UpdateProvider(type=projectSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(project record);

    @Update({
        "update t_project",
        "set projectName = #{projectname,jdbcType=VARCHAR},",
        "AutoGroupTo = #{AutoGroupTo,jdbcType=INTEGER},",
        "IsOurModule = #{IsOurModule,jdbcType=INTEGER},",
        "ConnectParameters = #{ConnectParameters,jdbcType=INTEGER}",
        "where projectId = #{projectid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(project record);
    
    @Update({
        "update t_project",
        "set AdvertisementUpdateTime = #{1}",
        "where projectId = #{0}"
    })
    int updateAdvUpdateTimeByPrimaryKey(String projectid, String AdvertisementUpdateTime);
    
    @Update({
        "update t_project",
        "set TerminalUpdateTime = #{1}",
        "where projectId = #{0}"
    })
    int updateTerminalUpdateTimeByPrimaryKey(String projectid, String TerminalUpdateTime);
    
    @Update({
        "update t_project",
        "set TerminalUpdateTime = #{0}"        
    })
    int updateTerminalUpdateTimeByAll(String TerminalUpdateTime);
    
    @Update({
        "update t_project",
        "set ParameterUpdateTime = #{1}",
        "where projectId = #{0}"
    })
    int updateParameterUpdateTimeByPrimaryKey(String projectid, String ParameterUpdateTime);
}