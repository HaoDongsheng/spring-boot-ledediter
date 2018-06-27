package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.playlist;

public class playlistSqlProvider {

    public String insertSelective(playlist record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_playlist");
        
        if (record.getId() != null) {
            sql.VALUES("Id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getPlaylistid() != null) {
            sql.VALUES("playlistid", "#{playlistid,jdbcType=INTEGER}");
        }
        
        if (record.getPlaylistcrc() != null) {
            sql.VALUES("playlistCRC", "#{playlistcrc,jdbcType=INTEGER}");
        }
        
        if (record.getPlaylistname() != null) {
            sql.VALUES("playlistname", "#{playlistname,jdbcType=VARCHAR}");
        }
        
        if (record.getPlaylisttype() != null) {
            sql.VALUES("playlisttype", "#{playlisttype,jdbcType=INTEGER}");
        }
        
        if (record.getProgramlist() != null) {
            sql.VALUES("programlist", "#{programlist,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(playlist record) {
        SQL sql = new SQL();
        sql.UPDATE("t_playlist");
        
        if (record.getPlaylistid() != null) {
            sql.SET("playlistid = #{playlistid,jdbcType=INTEGER}");
        }
        
        if (record.getPlaylistcrc() != null) {
            sql.SET("playlistCRC = #{playlistcrc,jdbcType=INTEGER}");
        }
        
        if (record.getPlaylistname() != null) {
            sql.SET("playlistname = #{playlistname,jdbcType=VARCHAR}");
        }
        
        if (record.getPlaylisttype() != null) {
            sql.SET("playlisttype = #{playlisttype,jdbcType=INTEGER}");
        }
        
        if (record.getProgramlist() != null) {
            sql.SET("programlist = #{programlist,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("Id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}