package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.playlistcode;

public class playlistcodeSqlProvider {

    public String insertSelective(playlistcode record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_playlistcode");
        
        if (record.getplaylistCodeSN() != null) {
            sql.VALUES("playlistCodeSN", "#{playlistCodeSN,jdbcType=INTEGER}");
        }
        
        if (record.getGroupid() != null) {
            sql.VALUES("groupid", "#{groupid,jdbcType=INTEGER}");
        }
        
        if (record.getContainADID() != null) {
            sql.VALUES("containADID", "#{groupid,jdbcType=VARCHAR}");
        }
        
        if (record.getLifeAct() != null) {
            sql.VALUES("lifeAct", "#{lifeAct,jdbcType=VARCHAR}");
        }
        
        if (record.getLifeDie() != null) {
            sql.VALUES("lifeDie", "#{lifeDie,jdbcType=VARCHAR}");
        }
        
        if (record.getPlaylistsn() != null) {
            sql.VALUES("playlistSN", "#{playlistsn,jdbcType=INTEGER}");
        }
        
        if (record.getPubid() != null) {
            sql.VALUES("pubid", "#{pubid,jdbcType=INTEGER}");
        }
        
        if (record.getPlaylistcrc() != null) {
            sql.VALUES("playlistCrc", "#{playlistcrc,jdbcType=INTEGER}");
        }
        
        if (record.getCodecontext() != null) {
            sql.VALUES("codeContext", "#{codecontext,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getPackCount() != null) {
            sql.VALUES("packCount", "#{packCount,jdbcType=INTEGER}");
        }
        
        if (record.getPackLength() != null) {
            sql.VALUES("packLength", "#{packLength,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(playlistcode record) {
        SQL sql = new SQL();
        sql.UPDATE("t_playlistcode");
        
        if (record.getPlaylistsn() != null) {
            sql.SET("playlistSN = #{playlistsn,jdbcType=INTEGER}");
        }
        
        if (record.getGroupid() != null) {
            sql.SET("groupid = #{groupid,jdbcType=INTEGER}");
        }
        
        if (record.getContainADID() != null) {
            sql.SET("containADID = #{groupid,jdbcType=VARCHAR}");
        }
        
        if (record.getLifeAct() != null) {
            sql.SET("lifeAct = #{lifeAct,jdbcType=VARCHAR}");
        }
        
        if (record.getLifeDie() != null) {
            sql.SET("lifeDie = #{lifeDie,jdbcType=VARCHAR}");
        }
        
        if (record.getPubid() != null) {
            sql.SET("pubid = #{pubid,jdbcType=INTEGER}");
        }
        
        if (record.getPlaylistcrc() != null) {
            sql.SET("playlistCrc = #{playlistcrc,jdbcType=INTEGER}");
        }
        
        if (record.getCodecontext() != null) {
            sql.SET("codeContext = #{codecontext,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getPackCount() != null) {
            sql.SET("packCount = #{packCount,jdbcType=INTEGER}");
        }
        
        if (record.getPackLength() != null) {
            sql.SET("packLength = #{packLength,jdbcType=INTEGER}");
        }
        
        sql.WHERE("playlistCodeSN = #{playlistCodeSN,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}