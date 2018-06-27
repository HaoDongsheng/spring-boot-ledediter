package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.programlist;

public class programlistSqlProvider {

    public String insertSelective(programlist record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_programlist");
        
        if (record.getId() != null) {
            sql.VALUES("Id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getProgramid() != null) {
            sql.VALUES("programid", "#{programid,jdbcType=INTEGER}");
        }
        
        if (record.getProgramcrc() != null) {
            sql.VALUES("programCRC", "#{programcrc,jdbcType=INTEGER}");
        }
        
        if (record.getProgramname() != null) {
            sql.VALUES("programname", "#{programname,jdbcType=VARCHAR}");
        }
        
        if (record.getProgramtype() != null) {
            sql.VALUES("programtype", "#{programtype,jdbcType=INTEGER}");
        }
        
        if (record.getProgramstart() != null) {
            sql.VALUES("programstart", "#{programstart,jdbcType=VARCHAR}");
        }
        
        if (record.getProgramstop() != null) {
            sql.VALUES("programstop", "#{programstop,jdbcType=VARCHAR}");
        }
        
        if (record.getAdvlist() != null) {
            sql.VALUES("advlist", "#{advlist,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(programlist record) {
        SQL sql = new SQL();
        sql.UPDATE("t_programlist");
        
        if (record.getProgramid() != null) {
            sql.SET("programid = #{programid,jdbcType=INTEGER}");
        }
        
        if (record.getProgramcrc() != null) {
            sql.SET("programCRC = #{programcrc,jdbcType=INTEGER}");
        }
        
        if (record.getProgramname() != null) {
            sql.SET("programname = #{programname,jdbcType=VARCHAR}");
        }
        
        if (record.getProgramtype() != null) {
            sql.SET("programtype = #{programtype,jdbcType=INTEGER}");
        }
        
        if (record.getProgramstart() != null) {
            sql.SET("programstart = #{programstart,jdbcType=VARCHAR}");
        }
        
        if (record.getProgramstop() != null) {
            sql.SET("programstop = #{programstop,jdbcType=VARCHAR}");
        }
        
        if (record.getAdvlist() != null) {
            sql.SET("advlist = #{advlist,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("Id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}