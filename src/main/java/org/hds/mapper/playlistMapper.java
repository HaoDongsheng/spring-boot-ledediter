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
import org.hds.model.playlist;

public interface playlistMapper {
    @Delete({
        "delete from t_playlist",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into t_playlist (Id, playlistid, ",
        "playlistCRC, playlistname, ",
        "playlisttype, programlist)",
        "values (#{id,jdbcType=INTEGER}, #{playlistid,jdbcType=INTEGER}, ",
        "#{playlistcrc,jdbcType=INTEGER}, #{playlistname,jdbcType=VARCHAR}, ",
        "#{playlisttype,jdbcType=INTEGER}, #{programlist,jdbcType=LONGVARCHAR})"
    })
    int insert(playlist record);

    @InsertProvider(type=playlistSqlProvider.class, method="insertSelective")
    int insertSelective(playlist record);

    @Select({
        "select",
        "Id, playlistid, playlistCRC, playlistname, playlisttype, programlist",
        "from t_playlist",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="playlistid", property="playlistid", jdbcType=JdbcType.INTEGER),
        @Result(column="playlistCRC", property="playlistcrc", jdbcType=JdbcType.INTEGER),
        @Result(column="playlistname", property="playlistname", jdbcType=JdbcType.VARCHAR),
        @Result(column="playlisttype", property="playlisttype", jdbcType=JdbcType.INTEGER),
        @Result(column="programlist", property="programlist", jdbcType=JdbcType.LONGVARCHAR)
    })
    playlist selectByPrimaryKey(Integer id);

    @UpdateProvider(type=playlistSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(playlist record);

    @Update({
        "update t_playlist",
        "set playlistid = #{playlistid,jdbcType=INTEGER},",
          "playlistCRC = #{playlistcrc,jdbcType=INTEGER},",
          "playlistname = #{playlistname,jdbcType=VARCHAR},",
          "playlisttype = #{playlisttype,jdbcType=INTEGER},",
          "programlist = #{programlist,jdbcType=LONGVARCHAR}",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(playlist record);

    @Update({
        "update t_playlist",
        "set playlistid = #{playlistid,jdbcType=INTEGER},",
          "playlistCRC = #{playlistcrc,jdbcType=INTEGER},",
          "playlistname = #{playlistname,jdbcType=VARCHAR},",
          "playlisttype = #{playlisttype,jdbcType=INTEGER}",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(playlist record);
}