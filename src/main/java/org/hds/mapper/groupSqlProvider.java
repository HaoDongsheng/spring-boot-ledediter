package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.group;

public class groupSqlProvider {

    public String insertSelective(group record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_group");
        
        if (record.getGroupid() != null) {
            sql.VALUES("groupid", "#{groupid,jdbcType=INTEGER}");
        }
        
        if (record.getGroupname() != null) {
            sql.VALUES("groupName", "#{groupname,jdbcType=VARCHAR}");
        }
        
        if (record.getMaxPackLength() != null) {
            sql.VALUES("maxPackLength", "#{maxPackLength,jdbcType=INTEGER}");
        }
        
        if (record.getPara2_User() != null) {
            sql.VALUES("Para2_User", "#{Para2_User,jdbcType=VARCHAR}");
        }                
        
        if (record.getPara3_TimeLight() != null) {
            sql.VALUES("Para3_TimeLight", "#{Para3_TimeLight,jdbcType=VARCHAR}");
        }                
        
        if (record.getPara1_Basic() != null) {
            sql.VALUES("Para1_Basic", "#{Para1_Basic,jdbcType=VARCHAR}");
        }                
        
        if (record.getscreenwidth() != null) {
            sql.VALUES("screenwidth", "#{screenwidth,jdbcType=INTEGER}");
        }
        
        if (record.getscreenheight() != null) {
            sql.VALUES("screenheight", "#{screenheight,jdbcType=INTEGER}");
        }
        
        if (record.getProjectid() != null) {
            sql.VALUES("projectid", "#{projectid,jdbcType=VARCHAR}");
        }
        
        if (record.getPubid() != null) {
            sql.VALUES("pubid", "#{pubid,jdbcType=INTEGER}");
        }
        
        if (record.getplpubid() != null) {
            sql.VALUES("plpubid", "#{plpubid,jdbcType=INTEGER}");
        }
        
        if (record.getDelindex() != null) {
            sql.VALUES("DelIndex", "#{delindex,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateRate() != null) {
            sql.VALUES("UpdateRate", "#{UpdateRate,jdbcType=double}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(group record) {
        SQL sql = new SQL();
        sql.UPDATE("t_group");
        
        if (record.getGroupname() != null) {
            sql.SET("groupName = #{groupname,jdbcType=VARCHAR}");
        }
        
        if (record.getMaxPackLength() != null) {
            sql.SET("maxPackLength = #{maxPackLength,jdbcType=INTEGER}");
        }
        
        if (record.getPara2_User() != null) {
            sql.SET("Para2_User = #{Para2_User,jdbcType=VARCHAR}");
        }               
        
        if (record.getPara3_TimeLight() != null) {
            sql.SET("Para3_TimeLight = #{Para3_TimeLight,jdbcType=VARCHAR}");
        }                
        
        if (record.getPara1_Basic() != null) {
            sql.SET("Para1_Basic = #{Para1_Basic,jdbcType=VARCHAR}");
        }                
        
        if (record.getscreenheight() != null) {
            sql.SET("screenwidth = #{screenwidth,jdbcType=INTEGER}");
        }
        
        if (record.getscreenheight() != null) {
            sql.SET("screenheight = #{screenheight,jdbcType=INTEGER}");
        }
        
        if (record.getProjectid() != null) {
            sql.SET("projectid = #{projectid,jdbcType=VARCHAR}");
        }
        
        if (record.getPubid() != null) {
            sql.SET("pubid = #{pubid,jdbcType=INTEGER}");
        }
        
        if (record.getplpubid() != null) {
            sql.SET("plpubid = #{plpubid,jdbcType=INTEGER}");
        }
        
        if (record.getDelindex() != null) {
            sql.SET("DelIndex = #{delindex,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateRate() != null) {
            sql.SET("UpdateRate = #{UpdateRate,jdbcType=double}");
        }
        
        sql.WHERE("groupid = #{groupid,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}