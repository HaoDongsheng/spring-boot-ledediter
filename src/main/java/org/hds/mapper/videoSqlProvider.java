package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.video;

public class videoSqlProvider {

    public String insertSelective(video record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_video");
        
        if (record.getvideoSN() != null) {
            sql.VALUES("videoSN", "#{videoSN,jdbcType=INTEGER}");
        }
        
        if (record.getVideoname() != null) {
            sql.VALUES("videoname", "#{videoname,jdbcType=VARCHAR}");
        }
        
        if (record.getGroupid() != null) {
            sql.VALUES("groupid", "#{groupid,jdbcType=INTEGER}");
        }
        
        if (record.getVideotype() != null) {
            sql.VALUES("videotype", "#{videotype,jdbcType=INTEGER}");
        }
        
        if (record.getFiletype() != null) {
            sql.VALUES("filetype", "#{filetype,jdbcType=VARCHAR}");
        }
        
        if (record.getduration() != null) {
            sql.VALUES("duration", "#{duration,jdbcType=VARCHAR}");
        }
        
        if (record.getVideoclassify() != null) {
            sql.VALUES("videoclassify", "#{videoclassify,jdbcType=VARCHAR}");
        }
        
        if (record.getVideostyle() != null) {
            sql.VALUES("videostyle", "#{videostyle,jdbcType=VARCHAR}");
        }
        
        if (record.getDelindex() != null) {
            sql.VALUES("delindex", "#{delindex,jdbcType=INTEGER}");
        }
        
        if (record.getVideofirst() != null) {
            sql.VALUES("videofirst", "#{videofirst,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(video record) {
        SQL sql = new SQL();
        sql.UPDATE("t_video");
        
        if (record.getVideoname() != null) {
            sql.SET("videoname = #{videoname,jdbcType=VARCHAR}");
        }
        
        if (record.getGroupid() != null) {
            sql.SET("groupid = #{groupid,jdbcType=INTEGER}");
        }
        
        if (record.getVideotype() != null) {
            sql.SET("videotype = #{videotype,jdbcType=INTEGER}");
        }
        
        if (record.getFiletype() != null) {
            sql.SET("filetype = #{filetype,jdbcType=VARCHAR}");
        }
        
        if (record.getduration() != null) {
            sql.SET("duration = #{duration,jdbcType=VARCHAR}");
        }
        
        if (record.getVideoclassify() != null) {
            sql.SET("videoclassify = #{videoclassify,jdbcType=VARCHAR}");
        }
        
        if (record.getVideostyle() != null) {
            sql.SET("videostyle = #{videostyle,jdbcType=VARCHAR}");
        }
        
        if (record.getDelindex() != null) {
            sql.SET("delindex = #{delindex,jdbcType=INTEGER}");
        }
        
        if (record.getVideofirst() != null) {
            sql.SET("videofirst = #{videofirst,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("videoSN = #{videoSN,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}