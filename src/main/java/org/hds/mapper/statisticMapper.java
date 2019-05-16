package org.hds.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.hds.model.advertisement;
import org.hds.model.statistic;

public interface statisticMapper {
    @Insert({
        "insert into t_statistics (MutiServerFlag, RecordingTime, ",
        "ActionFlag, Total, ",
        "DTU, LED, Updated, ",
        "Waiting, Renewable, ",
        "DtuKey, projectid)",
        "values (#{mutiserverflag,jdbcType=VARCHAR}, #{recordingtime,jdbcType=VARCHAR}, ",
        "#{actionflag,jdbcType=INTEGER}, #{total,jdbcType=INTEGER}, ",
        "#{dtu,jdbcType=INTEGER}, #{led,jdbcType=INTEGER}, #{updated,jdbcType=INTEGER}, ",
        "#{waiting,jdbcType=INTEGER}, #{renewable,jdbcType=INTEGER}, ",
        "#{dtukey,jdbcType=VARCHAR}, #{projectid,jdbcType=VARCHAR})"
    })
    int insert(statistic record);

    @InsertProvider(type=statisticSqlProvider.class, method="insertSelective")
    int insertSelective(statistic record);
    
    @Select({
        "select",
        "MutiServerFlag, RecordingTime, ActionFlag, Total, DTU, LED, Updated, Waiting, Renewable, DtuKey, projectid",
        "from t_statistics",
        "where projectid = #{0} order by RecordingTime asc"
    })
    @Results({    	 
         @Result(column="MutiServerFlag", property="mutiserverflag", jdbcType=JdbcType.VARCHAR),
         @Result(column="RecordingTime", property="recordingtime", jdbcType=JdbcType.VARCHAR),
         @Result(column="ActionFlag", property="actionflag", jdbcType=JdbcType.INTEGER),
         @Result(column="DTU", property="dtu", jdbcType=JdbcType.VARCHAR),
         @Result(column="LED", property="led", jdbcType=JdbcType.VARCHAR),
         @Result(column="Updated", property="updated", jdbcType=JdbcType.VARCHAR),
         @Result(column="Total", property="total", jdbcType=JdbcType.VARCHAR),
         @Result(column="Waiting", property="waiting", jdbcType=JdbcType.VARCHAR),
         @Result(column="Renewable", property="renewable", jdbcType=JdbcType.VARCHAR),         
         @Result(column="DtuKey", property="dtukey", jdbcType=JdbcType.VARCHAR),
         @Result(column="projectid", property="projectid", jdbcType=JdbcType.VARCHAR)
    })
    List<statistic> selectidByprojectid(String projectid);
    
    @Select({
        "select",
        "MutiServerFlag, RecordingTime, ActionFlag, Total, DTU, LED, Updated, Waiting, Renewable, DtuKey, projectid",
        "from t_statistics",
        "where projectid = #{0} and (RecordingTime > #{1} and RecordingTime <= #{2}) order by RecordingTime asc"
    })
    @Results({    	 
         @Result(column="MutiServerFlag", property="mutiserverflag", jdbcType=JdbcType.VARCHAR),
         @Result(column="RecordingTime", property="recordingtime", jdbcType=JdbcType.VARCHAR),
         @Result(column="ActionFlag", property="actionflag", jdbcType=JdbcType.INTEGER),
         @Result(column="DTU", property="dtu", jdbcType=JdbcType.VARCHAR),
         @Result(column="LED", property="led", jdbcType=JdbcType.VARCHAR),
         @Result(column="Updated", property="updated", jdbcType=JdbcType.VARCHAR),
         @Result(column="Total", property="total", jdbcType=JdbcType.VARCHAR),
         @Result(column="Waiting", property="waiting", jdbcType=JdbcType.VARCHAR),
         @Result(column="Renewable", property="renewable", jdbcType=JdbcType.VARCHAR),         
         @Result(column="DtuKey", property="dtukey", jdbcType=JdbcType.VARCHAR),
         @Result(column="projectid", property="projectid", jdbcType=JdbcType.VARCHAR)
    })
    List<statistic> selectidByprojectidDate(String projectid, String startDate, String endDate);
}