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
import org.hds.model.programlist;

public interface programlistMapper {
    @Delete({
        "delete from t_programlist",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into t_programlist (Id, programid, ",
        "programCRC, programname, ",
        "programtype, programstart, ",
        "programstop, advlist)",
        "values (#{id,jdbcType=INTEGER}, #{programid,jdbcType=INTEGER}, ",
        "#{programcrc,jdbcType=INTEGER}, #{programname,jdbcType=VARCHAR}, ",
        "#{programtype,jdbcType=INTEGER}, #{programstart,jdbcType=VARCHAR}, ",
        "#{programstop,jdbcType=VARCHAR}, #{advlist,jdbcType=LONGVARCHAR})"
    })
    int insert(programlist record);

    @InsertProvider(type=programlistSqlProvider.class, method="insertSelective")
    int insertSelective(programlist record);

    @Select({
        "select",
        "Id, programid, programCRC, programname, programtype, programstart, programstop, ",
        "advlist",
        "from t_programlist",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="programid", property="programid", jdbcType=JdbcType.INTEGER),
        @Result(column="programCRC", property="programcrc", jdbcType=JdbcType.INTEGER),
        @Result(column="programname", property="programname", jdbcType=JdbcType.VARCHAR),
        @Result(column="programtype", property="programtype", jdbcType=JdbcType.INTEGER),
        @Result(column="programstart", property="programstart", jdbcType=JdbcType.VARCHAR),
        @Result(column="programstop", property="programstop", jdbcType=JdbcType.VARCHAR),
        @Result(column="advlist", property="advlist", jdbcType=JdbcType.LONGVARCHAR)
    })
    programlist selectByPrimaryKey(Integer id);

    @UpdateProvider(type=programlistSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(programlist record);

    @Update({
        "update t_programlist",
        "set programid = #{programid,jdbcType=INTEGER},",
          "programCRC = #{programcrc,jdbcType=INTEGER},",
          "programname = #{programname,jdbcType=VARCHAR},",
          "programtype = #{programtype,jdbcType=INTEGER},",
          "programstart = #{programstart,jdbcType=VARCHAR},",
          "programstop = #{programstop,jdbcType=VARCHAR},",
          "advlist = #{advlist,jdbcType=LONGVARCHAR}",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(programlist record);

    @Update({
        "update t_programlist",
        "set programid = #{programid,jdbcType=INTEGER},",
          "programCRC = #{programcrc,jdbcType=INTEGER},",
          "programname = #{programname,jdbcType=VARCHAR},",
          "programtype = #{programtype,jdbcType=INTEGER},",
          "programstart = #{programstart,jdbcType=VARCHAR},",
          "programstop = #{programstop,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(programlist record);
}