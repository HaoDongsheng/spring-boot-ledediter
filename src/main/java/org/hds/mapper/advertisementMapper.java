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
import org.hds.model.advertisement;

public interface advertisementMapper {
    @Delete({
        "delete from t_advertisement",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into t_advertisement (Id, advName, ",
        "pubid)",
        "values (#{id,jdbcType=INTEGER}, #{advname,jdbcType=VARCHAR}, ",
        "#{pubid,jdbcType=INTEGER})"
    })
    int insert(advertisement record);

    @InsertProvider(type=advertisementSqlProvider.class, method="insertSelective")
    int insertSelective(advertisement record);

    @Select({
        "select",
        "Id, advName, pubid",
        "from t_advertisement",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="advName", property="advname", jdbcType=JdbcType.VARCHAR),
        @Result(column="pubid", property="pubid", jdbcType=JdbcType.INTEGER)
    })
    advertisement selectByPrimaryKey(Integer id);
    
    @Select({
        "select",
        "Id",
        "from t_advertisement",
        "where advName = #{advName,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.INTEGER, id=true)       
    })
    Object selectidByName(String advName);
    
    @Select({
        "select",
        "Id, advName, pubid",
        "from t_advertisement"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="advName", property="advname", jdbcType=JdbcType.VARCHAR),
        @Result(column="pubid", property="pubid", jdbcType=JdbcType.INTEGER)
    })
    java.util.List<advertisement> selectAll();

    @UpdateProvider(type=advertisementSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(advertisement record);

    @Update({
        "update t_advertisement",
        "set advName = #{advname,jdbcType=VARCHAR},",
          "pubid = #{pubid,jdbcType=INTEGER}",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(advertisement record);
}