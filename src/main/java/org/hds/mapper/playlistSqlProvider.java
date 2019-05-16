package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.playlist;

public class playlistSqlProvider {

    public String insertSelective(playlist record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_playlist");
        
        if (record.getplaylistSN() != null) {
            sql.VALUES("playlistSN", "#{playlistSN,jdbcType=INTEGER}");
        }
        
        if (record.getGroupid() != null) {
            sql.VALUES("groupid", "#{groupid,jdbcType=INTEGER}");
        }
        
        if (record.getPubid() != null) {
            sql.VALUES("pubid", "#{pubid,jdbcType=VARCHAR}");
        }
        
        if (record.getPlaylistname() != null) {
            sql.VALUES("playlistname", "#{playlistname,jdbcType=VARCHAR}");
        }
        
        if (record.getPlaylistlevel() != null) {
            sql.VALUES("playlistlevel", "#{playlistlevel,jdbcType=INTEGER}");
        }
        
        if (record.getPlaylistlifeact() != null) {
            sql.VALUES("playlistLifeAct", "#{playlistlifeact,jdbcType=VARCHAR}");
        }
        
        if (record.getPlaylistlifedie() != null) {
            sql.VALUES("playlistLifeDie", "#{playlistlifedie,jdbcType=VARCHAR}");
        }
        
        if (record.getcreater() != null) {
            sql.VALUES("creater", "#{creater,jdbcType=INTEGER}");
        }
        
        if (record.getcreateDate() != null) {
            sql.VALUES("createDate", "#{createDate,jdbcType=VARCHAR}");
        }                
        
        if (record.getpublisher() != null) {
            sql.VALUES("publisher", "#{publisher,jdbcType=INTEGER}");
        }
        
        if (record.getpublishDate() != null) {
            sql.VALUES("publishDate", "#{publishDate,jdbcType=VARCHAR}");
        }
        
        if (record.getdeleter() != null) {
            sql.VALUES("deleter", "#{deleter,jdbcType=INTEGER}");
        }
        
        if (record.getdeleteDate() != null) {
            sql.VALUES("deleteDate", "#{deleteDate,jdbcType=VARCHAR}");
        }

        
        if (record.getScheduletype() != null) {
            sql.VALUES("ScheduleType", "#{scheduletype,jdbcType=INTEGER}");
        }
        
        if (record.getDelindex() != null) {
            sql.VALUES("DelIndex", "#{delindex,jdbcType=INTEGER}");
        }
        
        if (record.getTimequantum() != null) {
            sql.VALUES("Timequantum", "#{timequantum,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getProgramlist() != null) {
            sql.VALUES("programlist", "#{programlist,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getMutiProgramlist() != null) {
            sql.VALUES("mutiProgramlist", "#{mutiProgramlist,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(playlist record) {
        SQL sql = new SQL();
        sql.UPDATE("t_playlist");
        
        if (record.getGroupid() != null) {
            sql.SET("groupid = #{groupid,jdbcType=INTEGER}");
        }
        
        if (record.getPubid() != null) {
            sql.SET("pubid = #{pubid,jdbcType=VARCHAR}");
        }
        
        if (record.getPlaylistname() != null) {
            sql.SET("playlistname = #{playlistname,jdbcType=VARCHAR}");
        }
        
        if (record.getPlaylistlevel() != null) {
            sql.SET("playlistlevel = #{playlistlevel,jdbcType=INTEGER}");
        }
        
        if (record.getPlaylistlifeact() != null) {
            sql.SET("playlistLifeAct = #{playlistlifeact,jdbcType=VARCHAR}");
        }
        
        if (record.getPlaylistlifedie() != null) {
            sql.SET("playlistLifeDie = #{playlistlifedie,jdbcType=VARCHAR}");
        }
        
        if (record.getcreater() != null) {
            sql.SET("creater = #{creater,jdbcType=INTEGER}");
        }
        
        if (record.getcreateDate() != null) {
            sql.SET("createDate = #{createDate,jdbcType=VARCHAR}");
        }                
        
        if (record.getpublisher() != null) {
            sql.SET("publisher = #{publisher,jdbcType=INTEGER}");
        }
        
        if (record.getpublishDate() != null) {
            sql.SET("publishDate = #{publishDate,jdbcType=VARCHAR}");
        }
        
        if (record.getdeleter() != null) {
            sql.SET("deleter = #{deleter,jdbcType=INTEGER}");
        }
        
        if (record.getdeleteDate() != null) {
            sql.SET("deleteDate = #{deleteDate,jdbcType=VARCHAR}");
        }
        
        if (record.getScheduletype() != null) {
            sql.SET("ScheduleType = #{scheduletype,jdbcType=INTEGER}");
        }
        
        if (record.getDelindex() != null) {
            sql.SET("DelIndex = #{delindex,jdbcType=INTEGER}");
        }
        
        if (record.getTimequantum() != null) {
            sql.SET("Timequantum = #{timequantum,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getProgramlist() != null) {
            sql.SET("programlist = #{programlist,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getMutiProgramlist() != null) {
            sql.SET("mutiProgramlist = #{mutiProgramlist,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("playlistSN = #{playlistSN,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}