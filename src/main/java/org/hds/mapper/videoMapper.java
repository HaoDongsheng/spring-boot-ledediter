package org.hds.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.hds.model.video;

public interface videoMapper {
    @Delete({
        "delete from t_video",
        "where videoSN = #{videoSN,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer videoSN);

    @Insert({
        "insert into t_video (videoSN, videoname, ",
        "groupid, videotype, ",
        "filetype, duration, videoclassify, ",
        "videostyle, delindex, ",
        "videofirst)",
        "values (#{videoSN,jdbcType=INTEGER}, #{videoname,jdbcType=VARCHAR}, ",
        "#{groupid,jdbcType=INTEGER}, #{videotype,jdbcType=INTEGER}, ",
        "#{filetype,jdbcType=VARCHAR}, #{duration,jdbcType=VARCHAR}, #{videoclassify,jdbcType=VARCHAR}, ",
        "#{videostyle,jdbcType=VARCHAR}, #{delindex,jdbcType=INTEGER}, ",
        "#{videofirst,jdbcType=LONGVARCHAR})"
    })
    @Options(useGeneratedKeys=true, keyProperty="videoSN",keyColumn="videoSN")//添加该行，product中的id将被自动添加
    int insert(video record);

    @InsertProvider(type=videoSqlProvider.class, method="insertSelective")
    int insertSelective(video record);

    @Select({
        "select",
        "videoSN, videoname, groupid, videotype, filetype, duration, videoclassify, videostyle, delindex, ",
        "videofirst",
        "from t_video",
        "where videoSN = #{videoSN,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="videoSN", property="videoSN", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="videoname", property="videoname", jdbcType=JdbcType.VARCHAR),
        @Result(column="groupid", property="groupid", jdbcType=JdbcType.INTEGER),
        @Result(column="videotype", property="videotype", jdbcType=JdbcType.INTEGER),
        @Result(column="filetype", property="filetype", jdbcType=JdbcType.VARCHAR),
        @Result(column="duration", property="duration", jdbcType=JdbcType.VARCHAR),
        @Result(column="videoclassify", property="videoclassify", jdbcType=JdbcType.VARCHAR),
        @Result(column="videostyle", property="videostyle", jdbcType=JdbcType.VARCHAR),
        @Result(column="delindex", property="delindex", jdbcType=JdbcType.INTEGER),
        @Result(column="videofirst", property="videofirst", jdbcType=JdbcType.LONGVARCHAR)
    })
    video selectByPrimaryKey(Integer videoSN);

    @Select({
        "select",
        "videoSN, videoname, groupid, videotype, filetype, duration, videoclassify, videostyle, delindex, ",
        "videofirst",
        "from t_video",
        "where (groupid = #{0} or groupid=0) and videotype =#{1} and videoclassify=#{2} and delindex = 0",
        "order by groupid asc"
    })
    @Results({
    	@Result(column="videoSN", property="videoSN", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="videoname", property="videoname", jdbcType=JdbcType.VARCHAR),
        @Result(column="groupid", property="groupid", jdbcType=JdbcType.INTEGER),
        @Result(column="videotype", property="videotype", jdbcType=JdbcType.INTEGER),
        @Result(column="filetype", property="filetype", jdbcType=JdbcType.VARCHAR),
        @Result(column="duration", property="duration", jdbcType=JdbcType.VARCHAR),
        @Result(column="videoclassify", property="videoclassify", jdbcType=JdbcType.VARCHAR),
        @Result(column="videostyle", property="videostyle", jdbcType=JdbcType.VARCHAR),
        @Result(column="delindex", property="delindex", jdbcType=JdbcType.INTEGER),
        @Result(column="videofirst", property="videofirst", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<video> selectvideobygroupid(Integer groupid,Integer imgtype,String basemapclassify);
    
    @Select({
        "select",
        "videoSN, videoname, groupid, videotype, filetype, duration, videoclassify, videostyle, delindex, ",
        "videofirst",
        "from t_video",
        "where (groupid = #{0} or groupid=0) and videotype =#{1} and delindex = 0",
        "order by groupid asc"
    })
    @Results({
    	@Result(column="videoSN", property="videoSN", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="videoname", property="videoname", jdbcType=JdbcType.VARCHAR),
        @Result(column="groupid", property="groupid", jdbcType=JdbcType.INTEGER),
        @Result(column="videotype", property="videotype", jdbcType=JdbcType.INTEGER),
        @Result(column="filetype", property="filetype", jdbcType=JdbcType.VARCHAR),
        @Result(column="duration", property="duration", jdbcType=JdbcType.VARCHAR),
        @Result(column="videoclassify", property="videoclassify", jdbcType=JdbcType.VARCHAR),
        @Result(column="videostyle", property="videostyle", jdbcType=JdbcType.VARCHAR),
        @Result(column="delindex", property="delindex", jdbcType=JdbcType.INTEGER),
        @Result(column="videofirst", property="videofirst", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<video> selectvideo2bygroupid(Integer groupid,Integer imgtype);
    
    @Select({
        "select",
        "distinct(videoclassify)",
        "from t_video",
        "where (groupid = #{0} or groupid=0) and videotype =#{1} and delindex = 0",
        "order by groupid asc"
    })
    @Results({                
        @Result(column="videoclassify", property="videoclassify", jdbcType=JdbcType.VARCHAR),        
    })
    List<String> selectvideoclassifybygroupid(int groupid,int imgtype);
    
    @UpdateProvider(type=videoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(video record);

    @Update({
        "update t_video",
        "set videoname = #{videoname,jdbcType=VARCHAR},",
          "groupid = #{groupid,jdbcType=INTEGER},",
          "videotype = #{videotype,jdbcType=INTEGER},",
          "filetype = #{filetype,jdbcType=VARCHAR},",
          "duration = #{duration,jdbcType=VARCHAR},",
          "videoclassify = #{videoclassify,jdbcType=VARCHAR},",
          "videostyle = #{videostyle,jdbcType=VARCHAR},",
          "delindex = #{delindex,jdbcType=INTEGER},",
          "videofirst = #{videofirst,jdbcType=LONGVARCHAR}",
        "where videoSN = #{videoSN,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(video record);

    @Update({
        "update t_video",
        "set videoname = #{videoname,jdbcType=VARCHAR},",
          "groupid = #{groupid,jdbcType=INTEGER},",
          "videotype = #{videotype,jdbcType=INTEGER},",
          "filetype = #{filetype,jdbcType=VARCHAR},",
          "duration = #{duration,jdbcType=VARCHAR},",
          "videoclassify = #{videoclassify,jdbcType=VARCHAR},",
          "videostyle = #{videostyle,jdbcType=VARCHAR},",
          "delindex = #{delindex,jdbcType=INTEGER}",
        "where videoSN = #{videoSN,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(video record);
    
    @Update({
        "update t_video",
        "set videoclassify = #{3}",          
        "where groupid = #{0} and videotype=#{1} and videoclassify=#{2}"
    })
    int updatevideoclassify(Integer groupid,Integer videotype,String oldvideoclassify,String newvideoclassify);
}