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
import org.hds.model.terminal;

public interface terminalMapper {
    @Delete({
        "delete from t_terminals",
        "where DtuKey = #{dtukey,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String dtukey);

    @Insert({
        "insert into t_terminals (DtuKey, Name, ",
        "GroupID, LED_ID, ",
        "SIMNo, TaxiType, ",
        "ComanyName, driver, ",
        "tel, paperType, ",
        "paperNo, remark, ",
        "DelIndex, CalibrationMode, ",
        "MutiServerFlag, LED_LastResponseTime, ",
        "LED_LastSendTime, SendBytesCount, ",
        "ReceiveBytesCount, WriteDate, ",
        "LedFactoryFlag, LedVersion, ",        
        "StateLedVersion, LedParametersFlag, ",
        "LedStateString, RegisterDate, ",
        "LevelEvaluate, CarPoolFlag, ",
        "Inspect, LedUpgrading, ",
        "LedUpgradeIndex, CarNumber, ",
        "CarNumberSet, projectid, ",
        "AdIdList, PlayList)",
        "values (#{dtukey,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{groupid,jdbcType=INTEGER}, #{ledId,jdbcType=VARCHAR}, ",
        "#{simno,jdbcType=VARCHAR}, #{taxitype,jdbcType=VARCHAR}, ",
        "#{comanyname,jdbcType=VARCHAR}, #{driver,jdbcType=VARCHAR}, ",
        "#{tel,jdbcType=VARCHAR}, #{papertype,jdbcType=VARCHAR}, ",
        "#{paperno,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR}, ",
        "#{delindex,jdbcType=INTEGER}, #{calibrationmode,jdbcType=INTEGER}, ",
        "#{mutiserverflag,jdbcType=VARCHAR}, #{ledLastresponsetime,jdbcType=VARCHAR}, ",
        "#{ledLastsendtime,jdbcType=VARCHAR}, #{sendbytescount,jdbcType=BIGINT}, ",
        "#{receivebytescount,jdbcType=BIGINT}, #{writedate,jdbcType=VARCHAR}, ",
        "#{ledfactoryflag,jdbcType=VARCHAR}, #{ledversion,jdbcType=VARCHAR}, ",        
        "#{stateledversion,jdbcType=VARCHAR}, #{ledparametersflag,jdbcType=BIGINT}, ",
        "#{ledstatestring,jdbcType=VARCHAR}, #{registerdate,jdbcType=VARCHAR}, ",
        "#{levelevaluate,jdbcType=VARCHAR}, #{carpoolflag,jdbcType=BIGINT}, ",
        "#{inspect,jdbcType=INTEGER}, #{ledupgrading,jdbcType=INTEGER}, ",
        "#{ledupgradeindex,jdbcType=INTEGER}, #{carnumber,jdbcType=VARCHAR}, ",
        "#{carnumberset,jdbcType=VARCHAR}, #{projectid,jdbcType=VARCHAR}, ",
        "#{adidlist,jdbcType=LONGVARCHAR}, #{playlist,jdbcType=LONGVARCHAR})"
    })
    int insert(terminal record);

    @InsertProvider(type=terminalSqlProvider.class, method="insertSelective")
    int insertSelective(terminal record);

    @Select({
        "select",
        "DtuKey, Name, GroupID, LED_ID, SIMNo, TaxiType, ComanyName, driver, tel, paperType, ",
        "paperNo, remark, DelIndex, CalibrationMode, MutiServerFlag, LED_LastResponseTime, ",
        "LED_LastSendTime, SendBytesCount, ReceiveBytesCount, WriteDate, LedFactoryFlag, ",
        "LedVersion, StateLedVersion, LedParametersFlag, LedStateString, ",
        "RegisterDate, LevelEvaluate, CarPoolFlag, Inspect, LedUpgrading, LedUpgradeIndex, ",
        "CarNumber, CarNumberSet, projectid, AdIdList, PlayList",
        "from t_terminals",
        "where DtuKey = #{dtukey,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="DtuKey", property="dtukey", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="Name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="GroupID", property="groupid", jdbcType=JdbcType.INTEGER),
        @Result(column="LED_ID", property="ledId", jdbcType=JdbcType.VARCHAR),
        @Result(column="SIMNo", property="simno", jdbcType=JdbcType.VARCHAR),
        @Result(column="TaxiType", property="taxitype", jdbcType=JdbcType.VARCHAR),
        @Result(column="ComanyName", property="comanyname", jdbcType=JdbcType.VARCHAR),
        @Result(column="driver", property="driver", jdbcType=JdbcType.VARCHAR),
        @Result(column="tel", property="tel", jdbcType=JdbcType.VARCHAR),
        @Result(column="paperType", property="papertype", jdbcType=JdbcType.VARCHAR),
        @Result(column="paperNo", property="paperno", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="DelIndex", property="delindex", jdbcType=JdbcType.INTEGER),
        @Result(column="CalibrationMode", property="calibrationmode", jdbcType=JdbcType.INTEGER),
        @Result(column="MutiServerFlag", property="mutiserverflag", jdbcType=JdbcType.VARCHAR),
        @Result(column="LED_LastResponseTime", property="ledLastresponsetime", jdbcType=JdbcType.VARCHAR),
        @Result(column="LED_LastSendTime", property="ledLastsendtime", jdbcType=JdbcType.VARCHAR),
        @Result(column="SendBytesCount", property="sendbytescount", jdbcType=JdbcType.BIGINT),
        @Result(column="ReceiveBytesCount", property="receivebytescount", jdbcType=JdbcType.BIGINT),
        @Result(column="WriteDate", property="writedate", jdbcType=JdbcType.VARCHAR),
        @Result(column="LedFactoryFlag", property="ledfactoryflag", jdbcType=JdbcType.VARCHAR),
        @Result(column="LedVersion", property="ledversion", jdbcType=JdbcType.VARCHAR),        
        @Result(column="StateLedVersion", property="stateledversion", jdbcType=JdbcType.VARCHAR),
        @Result(column="LedParametersFlag", property="ledparametersflag", jdbcType=JdbcType.BIGINT),
        @Result(column="LedStateString", property="ledstatestring", jdbcType=JdbcType.VARCHAR),
        @Result(column="RegisterDate", property="registerdate", jdbcType=JdbcType.VARCHAR),
        @Result(column="LevelEvaluate", property="levelevaluate", jdbcType=JdbcType.VARCHAR),
        @Result(column="CarPoolFlag", property="carpoolflag", jdbcType=JdbcType.BIGINT),
        @Result(column="Inspect", property="inspect", jdbcType=JdbcType.INTEGER),
        @Result(column="LedUpgrading", property="ledupgrading", jdbcType=JdbcType.INTEGER),
        @Result(column="LedUpgradeIndex", property="ledupgradeindex", jdbcType=JdbcType.INTEGER),
        @Result(column="CarNumber", property="carnumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="CarNumberSet", property="carnumberset", jdbcType=JdbcType.VARCHAR),
        @Result(column="projectid", property="projectid", jdbcType=JdbcType.VARCHAR),
        @Result(column="AdIdList", property="adidlist", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="PlayList", property="playlist", jdbcType=JdbcType.LONGVARCHAR)
    })
    terminal selectByPrimaryKey(String dtukey);
    
    @Select({
        "select",
        "DtuKey, Name, GroupID, LED_ID, SIMNo, TaxiType, ComanyName, driver, tel, paperType, ",
        "paperNo, remark, DelIndex, CalibrationMode, MutiServerFlag, LED_LastResponseTime, ",
        "LED_LastSendTime, SendBytesCount, ReceiveBytesCount, WriteDate, LedFactoryFlag, ",
        "LedVersion, StateLedVersion, LedParametersFlag, LedStateString, ",
        "RegisterDate, LevelEvaluate, CarPoolFlag, Inspect, LedUpgrading, LedUpgradeIndex, ",
        "CarNumber, CarNumberSet, projectid, AdIdList, PlayList",
        "from t_terminals",
        "order by GroupID asc ,Name asc LIMIT #{0},#{1}"
    })
    @Results({
        @Result(column="DtuKey", property="dtukey", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="Name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="GroupID", property="groupid", jdbcType=JdbcType.INTEGER),
        @Result(column="LED_ID", property="ledId", jdbcType=JdbcType.VARCHAR),
        @Result(column="SIMNo", property="simno", jdbcType=JdbcType.VARCHAR),
        @Result(column="TaxiType", property="taxitype", jdbcType=JdbcType.VARCHAR),
        @Result(column="ComanyName", property="comanyname", jdbcType=JdbcType.VARCHAR),
        @Result(column="driver", property="driver", jdbcType=JdbcType.VARCHAR),
        @Result(column="tel", property="tel", jdbcType=JdbcType.VARCHAR),
        @Result(column="paperType", property="papertype", jdbcType=JdbcType.VARCHAR),
        @Result(column="paperNo", property="paperno", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="DelIndex", property="delindex", jdbcType=JdbcType.INTEGER),
        @Result(column="CalibrationMode", property="calibrationmode", jdbcType=JdbcType.INTEGER),
        @Result(column="MutiServerFlag", property="mutiserverflag", jdbcType=JdbcType.VARCHAR),
        @Result(column="LED_LastResponseTime", property="ledLastresponsetime", jdbcType=JdbcType.VARCHAR),
        @Result(column="LED_LastSendTime", property="ledLastsendtime", jdbcType=JdbcType.VARCHAR),
        @Result(column="SendBytesCount", property="sendbytescount", jdbcType=JdbcType.BIGINT),
        @Result(column="ReceiveBytesCount", property="receivebytescount", jdbcType=JdbcType.BIGINT),
        @Result(column="WriteDate", property="writedate", jdbcType=JdbcType.VARCHAR),
        @Result(column="LedFactoryFlag", property="ledfactoryflag", jdbcType=JdbcType.VARCHAR),
        @Result(column="LedVersion", property="ledversion", jdbcType=JdbcType.VARCHAR),        
        @Result(column="StateLedVersion", property="stateledversion", jdbcType=JdbcType.VARCHAR),
        @Result(column="LedParametersFlag", property="ledparametersflag", jdbcType=JdbcType.BIGINT),
        @Result(column="LedStateString", property="ledstatestring", jdbcType=JdbcType.VARCHAR),
        @Result(column="RegisterDate", property="registerdate", jdbcType=JdbcType.VARCHAR),
        @Result(column="LevelEvaluate", property="levelevaluate", jdbcType=JdbcType.VARCHAR),
        @Result(column="CarPoolFlag", property="carpoolflag", jdbcType=JdbcType.BIGINT),
        @Result(column="Inspect", property="inspect", jdbcType=JdbcType.INTEGER),
        @Result(column="LedUpgrading", property="ledupgrading", jdbcType=JdbcType.INTEGER),
        @Result(column="LedUpgradeIndex", property="ledupgradeindex", jdbcType=JdbcType.INTEGER),
        @Result(column="CarNumber", property="carnumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="CarNumberSet", property="carnumberset", jdbcType=JdbcType.VARCHAR),
        @Result(column="projectid", property="projectid", jdbcType=JdbcType.VARCHAR),
        @Result(column="AdIdList", property="adidlist", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="PlayList", property="playlist", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<terminal> SelectTerminalsBypageNum(int startoffset,int pageSize);

    @Select({
        "select",
        "DtuKey, Name, GroupID, LED_ID, SIMNo, TaxiType, ComanyName, driver, tel, paperType, ",
        "paperNo, remark, DelIndex, CalibrationMode, MutiServerFlag, LED_LastResponseTime, ",
        "LED_LastSendTime, SendBytesCount, ReceiveBytesCount, WriteDate, LedFactoryFlag, ",
        "LedVersion, StateLedVersion, LedParametersFlag, LedStateString, ",
        "RegisterDate, LevelEvaluate, CarPoolFlag, Inspect, LedUpgrading, LedUpgradeIndex, ",
        "CarNumber, CarNumberSet, projectid, AdIdList, PlayList",
        "from t_terminals",
        "where projectid = #{2}",
        "order by GroupID asc ,Name asc LIMIT #{0},#{1}"
    })
    @Results({
        @Result(column="DtuKey", property="dtukey", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="Name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="GroupID", property="groupid", jdbcType=JdbcType.INTEGER),
        @Result(column="LED_ID", property="ledId", jdbcType=JdbcType.VARCHAR),
        @Result(column="SIMNo", property="simno", jdbcType=JdbcType.VARCHAR),
        @Result(column="TaxiType", property="taxitype", jdbcType=JdbcType.VARCHAR),
        @Result(column="ComanyName", property="comanyname", jdbcType=JdbcType.VARCHAR),
        @Result(column="driver", property="driver", jdbcType=JdbcType.VARCHAR),
        @Result(column="tel", property="tel", jdbcType=JdbcType.VARCHAR),
        @Result(column="paperType", property="papertype", jdbcType=JdbcType.VARCHAR),
        @Result(column="paperNo", property="paperno", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="DelIndex", property="delindex", jdbcType=JdbcType.INTEGER),
        @Result(column="CalibrationMode", property="calibrationmode", jdbcType=JdbcType.INTEGER),
        @Result(column="MutiServerFlag", property="mutiserverflag", jdbcType=JdbcType.VARCHAR),
        @Result(column="LED_LastResponseTime", property="ledLastresponsetime", jdbcType=JdbcType.VARCHAR),
        @Result(column="LED_LastSendTime", property="ledLastsendtime", jdbcType=JdbcType.VARCHAR),
        @Result(column="SendBytesCount", property="sendbytescount", jdbcType=JdbcType.BIGINT),
        @Result(column="ReceiveBytesCount", property="receivebytescount", jdbcType=JdbcType.BIGINT),
        @Result(column="WriteDate", property="writedate", jdbcType=JdbcType.VARCHAR),
        @Result(column="LedFactoryFlag", property="ledfactoryflag", jdbcType=JdbcType.VARCHAR),
        @Result(column="LedVersion", property="ledversion", jdbcType=JdbcType.VARCHAR),        
        @Result(column="StateLedVersion", property="stateledversion", jdbcType=JdbcType.VARCHAR),
        @Result(column="LedParametersFlag", property="ledparametersflag", jdbcType=JdbcType.BIGINT),
        @Result(column="LedStateString", property="ledstatestring", jdbcType=JdbcType.VARCHAR),
        @Result(column="RegisterDate", property="registerdate", jdbcType=JdbcType.VARCHAR),
        @Result(column="LevelEvaluate", property="levelevaluate", jdbcType=JdbcType.VARCHAR),
        @Result(column="CarPoolFlag", property="carpoolflag", jdbcType=JdbcType.BIGINT),
        @Result(column="Inspect", property="inspect", jdbcType=JdbcType.INTEGER),
        @Result(column="LedUpgrading", property="ledupgrading", jdbcType=JdbcType.INTEGER),
        @Result(column="LedUpgradeIndex", property="ledupgradeindex", jdbcType=JdbcType.INTEGER),
        @Result(column="CarNumber", property="carnumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="CarNumberSet", property="carnumberset", jdbcType=JdbcType.VARCHAR),
        @Result(column="projectid", property="projectid", jdbcType=JdbcType.VARCHAR),
        @Result(column="AdIdList", property="adidlist", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="PlayList", property="playlist", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<terminal> SelectTerminalsByprojectid(int startoffset,int pageSize,String projectid);
    
    @Select({
        "select",
        "count(*)",
        "from t_terminals"        
    })    
    int selectCount();
    
    @Select({
        "select",
        "count(*)",
        "from t_terminals", 
        "where projectid = #{0}"
    })    
    int selectCountbyprojectid(String projectid);
    
    @UpdateProvider(type=terminalSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(terminal record);

    @Update({
        "update t_terminals",
        "set Name = #{name,jdbcType=VARCHAR},",
          "GroupID = #{groupid,jdbcType=INTEGER},",
          "LED_ID = #{ledId,jdbcType=VARCHAR},",
          "SIMNo = #{simno,jdbcType=VARCHAR},",
          "TaxiType = #{taxitype,jdbcType=VARCHAR},",
          "ComanyName = #{comanyname,jdbcType=VARCHAR},",
          "driver = #{driver,jdbcType=VARCHAR},",
          "tel = #{tel,jdbcType=VARCHAR},",
          "paperType = #{papertype,jdbcType=VARCHAR},",
          "paperNo = #{paperno,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "DelIndex = #{delindex,jdbcType=INTEGER},",
          "CalibrationMode = #{calibrationmode,jdbcType=INTEGER},",
          "MutiServerFlag = #{mutiserverflag,jdbcType=VARCHAR},",
          "LED_LastResponseTime = #{ledLastresponsetime,jdbcType=VARCHAR},",
          "LED_LastSendTime = #{ledLastsendtime,jdbcType=VARCHAR},",
          "SendBytesCount = #{sendbytescount,jdbcType=BIGINT},",
          "ReceiveBytesCount = #{receivebytescount,jdbcType=BIGINT},",
          "WriteDate = #{writedate,jdbcType=VARCHAR},",
          "LedFactoryFlag = #{ledfactoryflag,jdbcType=VARCHAR},",
          "LedVersion = #{ledversion,jdbcType=VARCHAR},",          
          "StateLedVersion = #{stateledversion,jdbcType=VARCHAR},",
          "LedParametersFlag = #{ledparametersflag,jdbcType=BIGINT},",
          "LedStateString = #{ledstatestring,jdbcType=VARCHAR},",
          "RegisterDate = #{registerdate,jdbcType=VARCHAR},",
          "LevelEvaluate = #{levelevaluate,jdbcType=VARCHAR},",
          "CarPoolFlag = #{carpoolflag,jdbcType=BIGINT},",
          "Inspect = #{inspect,jdbcType=INTEGER},",
          "LedUpgrading = #{ledupgrading,jdbcType=INTEGER},",
          "LedUpgradeIndex = #{ledupgradeindex,jdbcType=INTEGER},",
          "CarNumber = #{carnumber,jdbcType=VARCHAR},",
          "CarNumberSet = #{carnumberset,jdbcType=VARCHAR},",
          "projectid = #{projectid,jdbcType=VARCHAR},",
          "AdIdList = #{adidlist,jdbcType=LONGVARCHAR},",
          "PlayList = #{playlist,jdbcType=LONGVARCHAR}",
        "where DtuKey = #{dtukey,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKeyWithBLOBs(terminal record);

    @Update({
        "update t_terminals",
        "set Name = #{name,jdbcType=VARCHAR},",
          "GroupID = #{groupid,jdbcType=INTEGER},",
          "LED_ID = #{ledId,jdbcType=VARCHAR},",
          "SIMNo = #{simno,jdbcType=VARCHAR},",
          "TaxiType = #{taxitype,jdbcType=VARCHAR},",
          "ComanyName = #{comanyname,jdbcType=VARCHAR},",
          "driver = #{driver,jdbcType=VARCHAR},",
          "tel = #{tel,jdbcType=VARCHAR},",
          "paperType = #{papertype,jdbcType=VARCHAR},",
          "paperNo = #{paperno,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "DelIndex = #{delindex,jdbcType=INTEGER},",
          "CalibrationMode = #{calibrationmode,jdbcType=INTEGER},",
          "MutiServerFlag = #{mutiserverflag,jdbcType=VARCHAR},",
          "LED_LastResponseTime = #{ledLastresponsetime,jdbcType=VARCHAR},",
          "LED_LastSendTime = #{ledLastsendtime,jdbcType=VARCHAR},",
          "SendBytesCount = #{sendbytescount,jdbcType=BIGINT},",
          "ReceiveBytesCount = #{receivebytescount,jdbcType=BIGINT},",
          "WriteDate = #{writedate,jdbcType=VARCHAR},",
          "LedFactoryFlag = #{ledfactoryflag,jdbcType=VARCHAR},",
          "LedVersion = #{ledversion,jdbcType=VARCHAR},",          
          "StateLedVersion = #{stateledversion,jdbcType=VARCHAR},",
          "LedParametersFlag = #{ledparametersflag,jdbcType=BIGINT},",
          "LedStateString = #{ledstatestring,jdbcType=VARCHAR},",
          "RegisterDate = #{registerdate,jdbcType=VARCHAR},",
          "LevelEvaluate = #{levelevaluate,jdbcType=VARCHAR},",
          "CarPoolFlag = #{carpoolflag,jdbcType=BIGINT},",
          "Inspect = #{inspect,jdbcType=INTEGER},",
          "LedUpgrading = #{ledupgrading,jdbcType=INTEGER},",
          "LedUpgradeIndex = #{ledupgradeindex,jdbcType=INTEGER},",
          "CarNumber = #{carnumber,jdbcType=VARCHAR},",
          "CarNumberSet = #{carnumberset,jdbcType=VARCHAR},",
          "projectid = #{projectid,jdbcType=VARCHAR}",
        "where DtuKey = #{dtukey,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(terminal record);
}